/*
 * Copyright 2020 Tatsuya Maki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for searching slideshows
 *
 * - [SlideshowOperations.searchSlideshows]
 *
 * @param page The page number of the results.
 * @param perPage The results per page.
 * @param language The language of slideshows.
 * @param sort Sorts the results.
 * @param uploadDate The time period of restricting search.
 * @param fileType File type to search.
 * @param what Type of search.
 * @param detailed Whether or not to include detailed information.
 */
data class SearchSlideshowsOptions(
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
     * Supported language.
     *
     * @param code The language code defined by SlideShare.
     */
    @Suppress("unused")
    enum class Language(internal val code: String) {
        /**
         * All languages.
         */
        ALL("**"),

        /**
         * Chinese.
         */
        CHINESE("zh"),

        /**
         * Dutch.
         */
        DUTCH("nl"),

        /**
         * English.
         */
        ENGLISH("en"),

        /**
         * French.
         */
        FRENCH("fr"),

        /**
         * German.
         */
        GERMAN("de"),

        /**
         * Italian.
         */
        ITALIAN("it"),

        /**
         * Japanese.
         */
        JAPANESE("ja"),

        /**
         * Korean.
         */
        KOREAN("ko"),

        /**
         * Portuguese.
         */
        PORTUGUESE("pt"),

        /**
         * Romanian.
         */
        ROMANIAN("ro"),

        /**
         * Spanish.
         */
        SPANISH("es"),

        /**
         * Other languages.
         */
        OTHER("!!")
    }

    /**
     * Sorting options.
     *
     * @param value The string representation of the sort.
     */
    @Suppress("unused")
    enum class Sort(internal val value: String) {
        /**
         * Sorts by descending date.
         */
        LATEST("latest"),

        /**
         * Sorts by relevance.
         */
        RELEVANCE("relevance"),

        /**
         * Sorts by descending viewed count.
         */
        MOST_VIEWED("mostviewed"),

        /**
         * Sorts by descending downloaded count.
         */
        MOST_DOWNLOADED("mostdownloaded")
    }

    /**
     * Type of search results restriction.
     */
    @Suppress("unused")
    enum class UploadDate {
        /**
         * Any upload date.
         */
        ANY,

        /**
         * Upload date within a week.
         */
        WEEK,

        /**
         * Upload date within a month.
         */
        MONTH,

        /**
         * Upload date within a year.
         */
        YEAR;

        /**
         * The string representation of [UploadDate]
         */
        internal val value: String = name.toLowerCase()
    }

    /**
     * Type of search.
     */
    @Suppress("unused")
    enum class SearchType {
        /**
         * Search tags.
         */
        TAG,

        /**
         * Search text.
         */
        TEXT;

        /**
         * The string representation of [SearchType]
         */
        internal val value: String = name.toLowerCase()
    }

    /**
     * Supported file type.
     *
     * @param value The string representation of [FileType].
     */
    @Suppress("unused")
    enum class FileType(internal val value: String) {
        /**
         * Search any file type.
         */
        ALL("all"),

        /**
         * Search documents.
         */
        DOCUMENT("documents"),

        /**
         * Search infographics.
         */
        INFOGRAPHIC("infographics"),

        /**
         * Search presentations.
         */
        PRESENTATION("presentations"),

        /**
         * Search videos.
         */
        VIDEO("videos"),

        /**
         * Search webinars.
         */
        WEBINAR("webinars")
    }
}
