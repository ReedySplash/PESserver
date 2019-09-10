package edu.pes.laresistencia.controller

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
//import edu.pes.laresistencia.model.Kind
import edu.pes.laresistencia.service.PointsLocService
import org.springframework.web.bind.annotation.PathVariable

@RestController
@RequestMapping("/pointsLocs")
class PointsLocController(
        private val pointsLocService: PointsLocService
){
    @GetMapping("/all")
    fun all(): ResponseEntity<List<PointsLocResponse>> {
        val pointsLoc = pointsLocService.findAll()

        return ResponseEntity.ok(pointsLoc.map {
            PointsLocResponse(it.name, it.latitude, it.longitude, it.kind)
        })
    }

    @GetMapping("/{kind}")
    fun findByKind(@PathVariable("kind") kind: String): ResponseEntity<List<PointsLocResponse>> {
        val pointsLoc = pointsLocService.findByKind(kind)

        return ResponseEntity.ok(pointsLoc.map {
            PointsLocResponse(it.name, it.latitude, it.longitude, it.kind)
        })
    }

    data class PointsLocResponse @JsonCreator constructor(
            @JsonProperty("name") val name: String,
            @JsonProperty("latitude") val latitude: Double,
            @JsonProperty("longitude") val longitude: Double,
            @JsonProperty("kind") val kind: String
    )
}
