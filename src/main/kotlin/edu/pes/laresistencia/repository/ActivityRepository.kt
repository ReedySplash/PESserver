package edu.pes.laresistencia.repository

import edu.pes.laresistencia.model.Activity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ActivityRepository : CrudRepository<Activity, String>{

    @Query("SELECT a FROM activities a WHERE a.id = ?1")
    fun findById(id: Int): Activity?
}