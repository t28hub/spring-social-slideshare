package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for retrieving a collection of slideshows
 *
 * @constructor
 *
 * @param offset The offset of result.
 * @param limit The number of slideshows to return per result.
 * @param detailed Whether or not to include detailed information.
 * @param unconverted Whether or not to include unconverted slideshows.
 *
 * The unconverted parameter is only supported by getSlideshowsByUser.
 *
 * - [SlideshowOperations.getSlideshowsByTag]
 * - [SlideshowOperations.getSlideshowsByUser]
 */
data class GetSlideshowsOptions(
    val offset: Int? = null,
    val limit: Int? = null,
    val detailed: Boolean = false,
    val unconverted: Boolean = false
)
