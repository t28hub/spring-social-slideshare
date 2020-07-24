package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.UserOperations
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

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
    private val isAuthorized: Boolean
) : UserOperations {
    override fun getFavorites(user: String): List<Favorite> {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }

        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path("/get_user_favorites")
            queryParam("username_for", user)
        }.toUriString()
        // Convert to a list after deserialize as an array because getForObject erasures generic type.
        return restTemplate.getForObject<Array<Favorite>>(uri).asList()
    }

    companion object {
        private const val PROVIDER_ID = "SlideShare"
        private const val API_BASE_URL = "https://www.slideshare.net/api/2/"
    }
}
