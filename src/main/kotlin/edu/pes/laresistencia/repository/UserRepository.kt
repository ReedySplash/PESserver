package edu.pes.laresistencia.repository

import edu.pes.laresistencia.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, String> {
    fun findByEmail(email: String): User?
    fun findByEmailNotIn(users: Collection<String>): List<User>
}