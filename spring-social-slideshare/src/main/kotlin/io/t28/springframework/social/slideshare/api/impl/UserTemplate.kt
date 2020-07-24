package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.Tag
import io.t28.springframework.social.slideshare.api.UserOperations
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

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
        // Convert to a list after deserialize as an array because getForObject erasures generic type.
        return restTemplate.getForObject<Array<Favorite>>(uri).asList()
    }

    override fun getUserContacts(user: String, options: GetUserContactsOptions): List<Contact> {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }

        val uri = buildUriString("/get_user_contacts") {
            queryParam("username_for", user)
            options.limit?.let { limit ->
                queryParam("limit", limit)
            }
            options.offset?.let { offset ->
                queryParam("offset", offset)
            }
        }
        // Convert to a list after deserialize as an array because getForObject erasures generic type.
        return restTemplate.getForObject<Array<Contact>>(uri).asList()
    }

    override fun getUserTags(): List<Tag> {
        requireAuthorization()

        val uri = buildUriString("/get_user_tags")
        return restTemplate.getForObject<Array<Tag>>(uri).asList()
    }
}
