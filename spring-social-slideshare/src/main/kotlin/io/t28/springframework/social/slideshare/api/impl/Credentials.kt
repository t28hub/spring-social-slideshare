package io.t28.springframework.social.slideshare.api.impl

/**
 * The user credentials.
 *
 * @constructor
 *
 * @param username The username of SlideShare
 * @param password The password of SlideShare
 */
data class Credentials(
    val username: String,
    val password: String
)
