package io.t28.springframework.social.slideshare.api

/**
 * Favorited slideshow.
 *
 * @constructor
 * @param slideshowId The favorited slideshow ID.
 * @param tags The tags added to the slideshow.
 */
data class Favorite(
    val slideshowId: String,
    val tags: List<String>
)
