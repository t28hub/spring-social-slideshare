package io.t28.springframework.social.slideshare.api

/**
 * Slideshow favorite results
 *
 * @constructor
 * @param id The favorited slideshow ID.
 * @param user The user name.
 * @param favorited Whether or not the user favorites the slideshow.
 */
data class FavoriteState(val id: String, val user: String, val favorited: Boolean)
