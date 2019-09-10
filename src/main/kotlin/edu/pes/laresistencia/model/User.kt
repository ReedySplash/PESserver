package edu.pes.laresistencia.model

import org.springframework.data.annotation.LastModifiedDate
import java.util.*
import javax.mail.internet.InternetAddress
import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

enum class Gender {
    MALE,
    FEMALE,
    OTHER
}

@Entity(name = "users")
class User(
    email: String,

    @NotNull
    val name: String,

    @NotNull
    val surname: String,

    val gender: Gender,

    val birthDate: Date,

    val country: String,

    @Min(value = 8) @NotNull
    var password: String,

    val enabled: Boolean,

    val creationDate: Date,

    @LastModifiedDate
    val modifiedDate: Date,


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "email")],
            inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
              )
    val roles: List<Role>) {
    @Id
    @NotNull
    val email: String

    init {
        val validation = InternetAddress(email)
        validation.validate()
        this.email = email.toLowerCase()
    }

    override fun equals(other: Any?): Boolean {
        if (other == null || this::class != other::class) return false;
        if (other is User) {
            if (email == other.email)
                return true
        }
        return false
    }
}