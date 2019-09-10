package edu.pes.laresistencia.repository

import edu.pes.laresistencia.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleRepository : CrudRepository<Role, Long> {
    fun findByName(name: String): Role
}