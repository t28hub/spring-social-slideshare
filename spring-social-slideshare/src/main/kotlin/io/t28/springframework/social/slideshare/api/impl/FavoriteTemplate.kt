/*
 * Copyright 2020 Tatsuya Maki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
