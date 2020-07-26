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
