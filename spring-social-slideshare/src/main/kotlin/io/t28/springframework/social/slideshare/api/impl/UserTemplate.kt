package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.Tag
import io.t28.springframework.social.slideshare.api.UserOperations
import io.t28.springframework.social.slideshare.ext.getForListObject
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate

/**
 * Implementation class for [UserOperations]
 *
 * @constructor
 *
 * @param restTemplate A [RestTemplate] instance.
 * @param isAuthorized Whether or not it is authorized.
 */
class UserTemplate(
    private val restTemplate: RestTemplate,
     isAuthorized: Boolean
) : AbstractSlideShareOperations(isAuthorized), UserOperations {
    override fun getUserFavorites(user: String): List<Favorite> {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }

        val uri = buildUriString("/get_user_favorites") {
            queryParam("username_for", user)
        }
        return restTemplate.getForListObject(uri)
    }

    override fun getUserContacts(user: String, options: GetUserContactsOptions): List<Contact> {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }

        val uri = buildUriString("/get_user_contacts") {
            queryParam("username_for", user)
            with(options) {
                limit?.let { limit -> queryParam("limit", limit) }
                offset?.let { offset -> queryParam("offset", offset) }
            }
        }
        return restTemplate.getForListObject(uri)
    }

    override fun getUserTags(): List<Tag> {
        requireAuthorization()

        val uri = buildUriString("/get_user_tags")
        return restTemplate.getForListObject(uri)
    }
}
