package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users", produces = [APPLICATION_JSON_VALUE])
class UsersController(private val slideShare: SlideShare) {
    @GetMapping("/{user}/favorites")
    fun getFavorites(@PathVariable user: String): List<Favorite> {
        return slideShare.userOperations().getFavorites(user)
    }
}
