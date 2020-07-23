package io.t28.springframework.social.slideshare.api

/**
 * Collection of slideshows
 */
data class Slideshows(
    val name: String,
    val count: Int,
    val slideshows: List<Slideshow>
)
