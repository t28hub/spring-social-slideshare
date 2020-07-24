package io.t28.springframework.social.slideshare.api

/**
 * A user's tag
 *
 * @constructor
 * @param name The tag name.
 * @param count The count of tag used.
 */
data class Tag(
    val name: String,
    val count: Int
)
