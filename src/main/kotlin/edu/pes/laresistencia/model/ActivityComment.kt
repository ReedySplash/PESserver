package edu.pes.laresistencia.model

import org.codehaus.jackson.annotate.JsonIgnore
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity(name = "activitiescomments")
class ActivityComment(
        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0,

        @ManyToOne(targetEntity = User::class)
        val user: User,

        @ManyToOne(targetEntity = Activity::class)
        val activity: Activity,

        @NotNull
        val comment: String,

        val creationDate: Date

)