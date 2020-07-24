package io.t28.springframework.social.slideshare.api

/**
 * A contact information.
 *
 * @constructor
 * @param username The user name of SlideShare.
 * @param numSlideshows The number of slideshows uploaded.
 * @param numComments The number of comments posted.
 */
data class Contact(
    val username: String,
    val numSlideshows: Int,
    val numComments: Int
)
