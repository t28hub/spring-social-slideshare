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
package io.t28.springframework.social.slideshare.api

import org.springframework.social.ApiException

/**
 * Defining the operations for working with favorites.
 */
interface FavoriteOperations {
    /**
     * Favorite a slideshow for the authenticated user.
     * [Favorite Slideshow | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#add_favorite)
     *
     * @param slideshowId The slideshow ID to be favorited.
     * @return The favorited slideshow ID.
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun addFavorite(slideshowId: String): FavoriteResults

    /**
     * Check if a slideshow is favorited by the authenticated user.
     * [Check Favorite | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#check_favorite)
     *
     * @param slideshowId The slideshow ID to be checked.
     * @return Whether or not the slideshow is favorited.
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun checkFavorite(slideshowId: String): FavoriteState
}
