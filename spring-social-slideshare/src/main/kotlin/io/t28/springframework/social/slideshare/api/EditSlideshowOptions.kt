package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for editing slideshows
 *
 * @constructor
 *
 * @param title The slideshow title.
 * @param description The slideshow description.
 * @param tags The slideshow tags.
 * @param private Whether or not to set slideshow private.
 * @param generateSecretUrl Whether or not to generate a secret URL.
 * @param allowEmbed Whether or not to allow embedding the slideshow.
 * @param shareWithContacts Whether or not the contacts on SlideShare can view the slideshow.
 *
 * - [SlideshowOperations.editSlideshow]
 */
data class EditSlideshowOptions(
    val title: String? = null,
    val description: String? = null,
    val tags: List<String>? = null,
    val private: Boolean? = null,
    val generateSecretUrl: Boolean? = null,
    val allowEmbed: Boolean? = null,
    val shareWithContacts: Boolean? = null
)
