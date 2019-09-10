package edu.pes.laresistencia.service

import edu.pes.laresistencia.model.PointsLoc
import edu.pes.laresistencia.repository.PointsLocRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class PointsLocService(private val pointsLocRepository: PointsLocRepository){
    fun findAll(): List<PointsLoc> {
        return pointsLocRepository.findAll().toList()
    }

    fun findByKind(kind: String): List<PointsLoc> {
        return pointsLocRepository.findByKind(kind).toList()
    }
}