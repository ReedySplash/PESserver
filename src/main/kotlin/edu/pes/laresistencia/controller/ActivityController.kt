package edu.pes.laresistencia.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import edu.pes.laresistencia.service.ActivityCommentService
import edu.pes.laresistencia.service.ActivityService
import edu.pes.laresistencia.util.KotlinUtilBase64ByteArrayImage
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.text.SimpleDateFormat


@RestController
@RequestMapping("/activities")
class ActivityController(
        private val activityService: ActivityService,
        private val activityCommentService: ActivityCommentService
) {

    @Value("\${img.path.activities}")
    private lateinit var imgPathActivities: String

    @GetMapping("")
    fun all(): ResponseEntity<List<ActivityResponseList>> {
        val activities = activityService.findAll()

        return ResponseEntity.ok(activities.map {
            val format = SimpleDateFormat("MM/dd/yyyy")
            val date = format.format(it.date)
            ActivityResponseList(it.id, it.title, date)
        })
    }

    @GetMapping("/{id}/comments")
    fun getActivityComments(@PathVariable(value = "id") id: String): ResponseEntity<List<ActivityCommentsResponse>> {
        val activity = activityService.findById(id.toInt())
        val activityComments = activityCommentService.findByActivity(activity)
        val format = SimpleDateFormat("MM/dd/yyyy hh:mm")
        return ResponseEntity.ok(activityComments.map {
            ActivityCommentsResponse(it.activity.id, it.user.email, it.user.name, it.user.surname, it.comment, format.format(it.creationDate))
        })
    }

    @PostMapping("/{id}/comments")
    fun createActivityComment(@PathVariable(value = "id") id: String, @RequestBody request: ActivityCommentsRequest): ResponseEntity<String> {
        val auth = SecurityContextHolder.getContext().authentication
        val currentUser = auth.name
        activityCommentService.createComment(id.toInt(), currentUser, request.comment)
        return ResponseEntity.ok().build()
    }

    @GetMapping("/{id}")
    fun getActivity(@PathVariable(value = "id") id: String): ResponseEntity<ActivityResponse> {
        val activity = activityService.findById(id.toInt())
        val format = SimpleDateFormat("MM/dd/yyyy")
        val date = format.format(activity.date)
        return ResponseEntity.ok(ActivityResponse(activity.id, activity.title, date, activity.from_,
                activity.to_, activity.description, activity.location))
    }

    @GetMapping("/{id}/image")
    fun getActivityImage(@PathVariable(value = "id") id: String): ResponseEntity<ByteArray> {
        val path = "$imgPathActivities/$id.png"
        val imageByteArray = KotlinUtilBase64ByteArrayImage.getByteArrayImg(path)
                ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageByteArray)
    }

    @PostMapping("/{id}/image")
    fun postActivityImage(@PathVariable(value = "id") id: String, @RequestBody request: ActivityImageRequest): ResponseEntity<String> {
        if (request.image != null) {
            KotlinUtilBase64ByteArrayImage.saveBase64Img(request.image, "$imgPathActivities/${request.id}.png")
        }
        return ResponseEntity.noContent().build()
    }
}

data class ActivityResponse @JsonCreator constructor(
        @JsonProperty("id") val id: Int,
        @JsonProperty("title") val title: String,
        @JsonProperty("date") val date: String,
        @JsonProperty("from") val from_: String,
        @JsonProperty("to") val to_: String,
        @JsonProperty("description") val description: String,
        @JsonProperty("location") val location: String
)

data class ActivityCommentsResponse @JsonCreator constructor(
        @JsonProperty("activity_id") val activityId: Int,
        @JsonProperty("user_email") val userEmail: String,
        @JsonProperty("user_name") val userName: String,
        @JsonProperty("user_surname") val userSurname: String,
        @JsonProperty("comment") val comment: String,
        @JsonProperty("creation_date") val creationDate: String
)

data class ActivityCommentsRequest @JsonCreator constructor(
        @JsonProperty("comment") val comment: String
)

data class ActivityResponseList @JsonCreator constructor(
        @JsonProperty("id") val id: Int,
        @JsonProperty("title") val title: String,
        @JsonProperty("date") val date: String
)

data class ActivityImageRequest @JsonCreator constructor(
        @JsonProperty("id") val id: Int,
        @JsonProperty("image") val image: String
)