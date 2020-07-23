package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for retrieving a slideshow
 *
 * @constructor
 *
 * @param excludeTags Whether or not to exclude tags
 * @param detailed Whether or not to include detailed information.
 *
 * - [SlideshowOperations.getSlideshow]
 * - [SlideshowOperations.getSlideshowById]
 * - [SlideshowOperations.getSlideshowByUrl]
 */
data class GetSlideshowOptions(
    val excludeTags: Boolean = false,
    val detailed: Boolean = false
)
