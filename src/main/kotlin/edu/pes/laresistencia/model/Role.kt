package edu.pes.laresistencia.model

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity(name = "roles")
class Role (
        @Column(unique = true) val name: String
) {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null
    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    lateinit var users: List<User>
}