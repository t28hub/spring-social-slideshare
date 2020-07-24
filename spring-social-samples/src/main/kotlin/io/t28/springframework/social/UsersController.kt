package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users", produces = [APPLICATION_JSON_VALUE])
class UsersController(private val slideShare: SlideShare) {
    @GetMapping("/{user}/favorites")
    fun getFavorites(@PathVariable user: String): List<Favorite> {
        return slideShare.userOperations().getUserFavorites(user)
    }

    @GetMapping("/{user}/contacts")
    fun getContacts(
        @PathVariable user: String,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): List<Contact> {
        val options = GetUserContactsOptions(
            offset = offset,
            limit = limit
        )
        return slideShare.userOperations().getUserContacts(user, options)
    }
}
