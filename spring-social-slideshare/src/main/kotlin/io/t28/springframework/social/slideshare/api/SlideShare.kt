package io.t28.springframework.social.slideshare.api

import org.springframework.social.ApiBinding

/**
 * Defining the operations for interacting with SlideShare API.
 * [SlideShare API Documentation](https://www.slideshare.net/developers/documentation)
 */
interface SlideShare : ApiBinding {
    /**
     * Operations for slideshows.
     *
     * @return [SlideshowOperations]
     */
    fun slideshowOperations(): SlideshowOperations

    /**
     * Operations for users.
     *
     * @return [UserOperations]
     */
    fun userOperations(): UserOperations

    /**
     * Operations for favorites.
     *
     * @return [FavoriteOperations]
     */
    fun favoriteOperations(): FavoriteOperations
}
