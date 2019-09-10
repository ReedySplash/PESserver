package edu.pes.laresistencia.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import edu.pes.laresistencia.model.Gender
import edu.pes.laresistencia.service.UserService
import edu.pes.laresistencia.util.KotlinUtilBase64ByteArrayImage
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.security.core.context.SecurityContextHolder
import java.text.SimpleDateFormat


@RestController
@RequestMapping("/user")
class UserController(
        private val userService: UserService
) {
    @Value("\${img.path.users}")
    private lateinit var imgPath: String

    @PostMapping("/register")
    fun register(@RequestBody request: UserRequest): ResponseEntity<String> {
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date = format.parse(request.birthDate)
        userService.registerUser(
                email = request.email,
                password = request.password,
                name = request.name,
                surname = request.surname,
                country = request.country,
                birthDate = date,
                gender = request.gender
        )
        if (request.photo != null) {
            KotlinUtilBase64ByteArrayImage.saveBase64Img(request.photo, "$imgPath/${request.email}.jpeg")
        }
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{email:.+}")
    fun delete(@PathVariable(value = "email") userEmail: String): ResponseEntity<String> {
        val auth = SecurityContextHolder.getContext().authentication
        val current_user = auth.name
        if (current_user.equals(userEmail)) {
            userService.deleteUser(userEmail)
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.status(403).build()

    }

    @PutMapping("/{email:.+}")
    fun update(@RequestBody request: UserRequest, @PathVariable(value = "email") userEmail: String): ResponseEntity<String> {
        val user = userService.findByEmail(userEmail)

        val auth = SecurityContextHolder.getContext().authentication
        val currentUser = auth.name
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date = format.parse(request.birthDate)

        if (currentUser == userEmail) {
            if (userEmail != request.email) return ResponseEntity.status(403).build()
            userService.updateUser(
                    email = request.email,
                    password = request.password,
                    name = request.name,
                    surname = request.surname,
                    country = request.country,
                    birthDate = date,
                    gender = request.gender,
                    roles = user.roles
            )
            return ResponseEntity.ok().build()
        }
        if (request.photo != null) {
            KotlinUtilBase64ByteArrayImage.saveBase64Img(request.photo, "$imgPath/$userEmail.jpeg")
        }

        return ResponseEntity.status(403).build()
    }

    @PutMapping("/{email:.+}/change-password")
    fun changePassword(@RequestBody request: ChangePasswordReq, @PathVariable(value = "email") userEmail: String): ResponseEntity<String> {

        val auth = SecurityContextHolder.getContext().authentication
        val currentUser = auth.name

        if (currentUser == userEmail) {

            userService.changePassword(
                    email = userEmail,
                    oldPassword = request.oldPassword,
                    newPassword = request.newPassword
            )
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.status(403).build()
    }

    @PutMapping("/{email:.+}/change-photo")
    fun changePhoto(@RequestBody request: ChangePhotoReq, @PathVariable(value = "email") userEmail: String): ResponseEntity<String> {

        val auth = SecurityContextHolder.getContext().authentication
        val currentUser = auth.name

        if (currentUser == userEmail) {
            KotlinUtilBase64ByteArrayImage.saveBase64Img(request.photo, "$imgPath/$userEmail.jpeg")
            return ResponseEntity.ok().build()
        }

        return ResponseEntity.status(403).build()
    }

    @GetMapping("")
    fun list(): ResponseEntity<List<UsersResponse>> {
        val users = userService.findAll()

        return ResponseEntity.ok(users.map {
            UsersResponse(it.name, it.email)
        })
    }

    @GetMapping("/{email:.+}")
    fun getUser(@PathVariable(value = "email") userEmail: String): ResponseEntity<UserResponse> {
        val user = userService.findByEmail(userEmail)
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date = format.format(user.birthDate)
        return ResponseEntity.ok(UserResponse(user.name, user.surname, user.gender, date, user.email,
                user.country))
    }

    @GetMapping("/{email:.+}/photo")
    fun getUserPhoto(@PathVariable(value = "email") userEmail: String): ResponseEntity<ByteArray> {
        val path = "$imgPath/$userEmail.jpeg"
        val imageByteArray = KotlinUtilBase64ByteArrayImage.getByteArrayImg(path) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageByteArray)
    }
}

data class UsersResponse @JsonCreator constructor(
        @JsonProperty("username") val name: String,
        @JsonProperty("email") val email: String
)

data class ChangePasswordReq @JsonCreator constructor(
        @JsonProperty("old_password") val oldPassword: String,
        @JsonProperty("new_password") val newPassword: String

)

data class ChangePhotoReq @JsonCreator constructor(
        @JsonProperty("photo") val photo: String
)

data class UserRequest @JsonCreator constructor(
        @JsonProperty("name") val name: String,
        @JsonProperty("surname") val surname: String,
        @JsonProperty("gender") val gender: Gender,
        @JsonProperty("birthDate") val birthDate: String,
        @JsonProperty("email") val email: String,
        @JsonProperty("country") val country: String,
        @JsonProperty("password") val password: String,
        @JsonProperty("photo") val photo: String?
)

data class UserResponse @JsonCreator constructor(
        @JsonProperty("name") val name: String,
        @JsonProperty("surname") val surname: String,
        @JsonProperty("gender") val gender: Gender,
        @JsonProperty("birthDate") val birthDate: String,
        @JsonProperty("email") val email: String,
        @JsonProperty("country") val country: String
)