package io.t28.springframework.social.slideshare.api

/**
 * Slideshow search results
 */
data class SearchResults(
    val meta: Meta,
    val slideshows: List<Slideshow>
) {
    /**
     * Search result metadata
     */
    data class Meta(
        val query: String,
        val resultOffset: Int?,
        val numResults: Int,
        val totalResults: Int
    )
}
