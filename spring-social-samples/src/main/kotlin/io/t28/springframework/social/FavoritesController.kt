package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/favorites", produces = [APPLICATION_JSON_VALUE])
class FavoritesController(private val slideShare: SlideShare) {
    @PutMapping("/{id}")
    fun favorite(@PathVariable id: String): ResponseEntity<Void> {
        slideShare.favoriteOperations().addFavorite(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun checkFavorite(@PathVariable id: String): ResponseEntity<Void> {
        val state = slideShare.favoriteOperations().checkFavorite(id)
        return if (state.favorited) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
