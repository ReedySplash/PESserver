package edu.pes.laresistencia.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotNull

/*enum class Kind {
        hygiene,
        food,
        lodging,
        clothes,
        medicine
}*/

@Entity(name = "pointsLoc")
class PointsLoc(
        @JsonIgnore
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long? = null,

        @NotNull
        val name: String,

        @NotNull
        val latitude: Double,

        @NotNull
        val longitude: Double,

        @NotNull
        val kind: String
)