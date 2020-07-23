package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for searching slideshows
 *
 * @constructor
 *
 * @param page The page number of the results.
 * @param perPage The results per page.
 * @param language The language of slideshows.
 * @param sort Sorts the results.
 * @param uploadDate The time period of restricting search.
 * @param fileType File type to search.
 * @param what Type of search.
 * @param detailed Whether or not to include detailed information.
 *
 * - [SlideshowOperations.searchSlideshows]
 */
data class SearchOptions(
    val page: Int = 1,
    val perPage: Int = 12,
    val language: Language = Language.ENGLISH,
    val sort: Sort = Sort.RELEVANCE,
    val uploadDate: UploadDate = UploadDate.ANY,
    val fileType: FileType = FileType.ALL,
    val what: SearchType = SearchType.TEXT,
    val detailed: Boolean = false
) {
    /**
     * Supported language
     *
     * @constructor
     *
     * @param code The language code defined by SlideShare.
     */
    enum class Language(internal val code: String) {
        ALL("**"),
        CHINESE("zh"),
        DUTCH("nl"),
        ENGLISH("en"),
        FRENCH("fr"),
        GERMAN("de"),
        ITALIAN("it"),
        JAPANESE("ja"),
        KOREAN("ko"),
        PORTUGUESE("pt"),
        ROMANIAN("ro"),
        SPANISH("es"),
        OTHER("!!")
    }

    /**
     * Sorting options
     *
     * @constructor
     *
     * @param value The string representation of the sort
     */
    enum class Sort(internal val value: String) {
        LATEST("latest"),
        RELEVANCE("relevance"),
        MOST_VIEWED("mostviewed"),
        MOST_DOWNLOADED("mostdownloaded")
    }

    /**
     * Type of search results restriction.
     */
    enum class UploadDate {
        ANY,
        WEEK,
        MONTH,
        YEAR;

        internal val value: String = name.toLowerCase()
    }

    /**
     * Type of search
     */
    enum class SearchType {
        TAG,
        TEXT;

        internal val value: String = name.toLowerCase()
    }

    /**
     * Supported file type
     *
     * @constructor
     *
     * @param value The string representation of file type
     */
    enum class FileType(internal val value: String) {
        ALL("all"),
        DOCUMENT("documents"),
        INFOGRAPHIC("inforgraphics"),
        PRESENTATION("presentations"),
        VIDEO("videos"),
        WEBINAR("webinars")
    }
}
