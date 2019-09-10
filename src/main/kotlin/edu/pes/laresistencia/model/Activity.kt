package edu.pes.laresistencia.model

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull
import java.util.Date

@Entity(name = "activities")
class Activity(
        @Id
        @NotNull
        var id: Int,

        @NotNull
        val title: String,

        @NotNull
        val date: Date,

        @NotNull
        val from_: String,

        @NotNull
        val to_: String,

        @NotNull
        val description: String,

        @NotNull
        val location: String
)