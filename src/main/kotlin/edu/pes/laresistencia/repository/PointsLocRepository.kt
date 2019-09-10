package edu.pes.laresistencia.repository

//import edu.pes.laresistencia.model.Kind
import edu.pes.laresistencia.model.PointsLoc
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PointsLocRepository : CrudRepository<PointsLoc, String> {
    fun findByKind(kind: String): Array<PointsLoc>
}
