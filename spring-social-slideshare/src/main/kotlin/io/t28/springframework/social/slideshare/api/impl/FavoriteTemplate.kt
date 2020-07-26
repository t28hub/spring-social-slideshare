package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.FavoriteOperations
import io.t28.springframework.social.slideshare.api.FavoriteResults
import io.t28.springframework.social.slideshare.api.FavoriteState
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * Implementation class for [FavoriteOperations]
 *
 * @constructor
 *
 * @param restTemplate A [RestTemplate] instance.
 * @param isAuthorized Whether or not it is authorized.
 */
class FavoriteTemplate(
    private val restTemplate: RestTemplate,
     isAuthorized: Boolean
) : AbstractSlideShareOperations(isAuthorized), FavoriteOperations {
    override fun addFavorite(slideshowId: String): FavoriteResults {
        if (slideshowId.isEmpty()) {
            throw ApiException(PROVIDER_ID, "Slideshow ID must be non-empty string")
        }
        requireAuthorization()

        val uri = buildUriString("/add_favorite") {
            queryParam("slideshow_id", slideshowId)
        }
        return restTemplate.getForObject(uri)
    }

    override fun checkFavorite(slideshowId: String): FavoriteState {
        if (slideshowId.isEmpty()) {
            throw ApiException(PROVIDER_ID, "Slideshow ID must be non-empty string")
        }
        requireAuthorization()

        val uri = buildUriString("/check_favorite") {
            queryParam("slideshow_id", slideshowId)
        }
        return restTemplate.getForObject(uri)
    }
}
