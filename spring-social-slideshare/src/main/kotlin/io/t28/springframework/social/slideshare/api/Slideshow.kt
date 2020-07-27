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

import java.util.Date

/**
 * A slideshow information.
 *
 * @param id The slideshow ID.
 * @param title The slideshow title.
 * @param description The slideshow description.
 * @param status The file conversion status.
 * @param username The username who uploads the slideshow.
 * @param url The slideshow URL.
 * @param thumbnailUrl The default thumbnail image URL.
 * @param thumbnailSize The default thumbnail image size.
 * @param thumbnailSmallUrl The small thumbnail image URL.
 * @param thumbnailXLargeUrl The x-large thumbnail image URL.
 * @param thumbnailXXLargeUrl The xx-large thumbnail image URL.
 * @param embed The embed html code.
 * @param created The created date.
 * @param updated The updated date.
 * @param language The 2 letter language code.
 * @param format The slideshow file format.
 * @param download Whether downloadable or not.
 * @param downloadUrl The download URL.
 * @param secretKey The secret key.
 * @param slideshowEmbedUrl The slideshow embed URL.
 * @param slideshowType Type of slideshow.
 * @param inContest Whether or not the slideshow is part of contest.
 * @param userId The user ID.
 * @param pptLocation The ppt location.
 * @param strippedTitle The stripped title.
 * @param tags The added tags.
 * @param numDownloads The number of downloads.
 * @param numViews The number of views.
 * @param numComments The number of comments.
 * @param numFavorites The number of favorites.
 * @param numSlides The number of slides.
 * @param relatedSlideshows The related slideshows.
 * @param privacyLevel Whether public or private.
 * @param flagVisible Whether or not the slideshow is flagged.
 * @param showOnSs Whether or not the slideshow is shown on SlideShare.
 * @param secretUrl Whether or not secret URL is available.
 * @param allowEmbed Whether or not the slideshow can be embedded.
 * @param shareWithContacts Whether the slideshow can be visible by contacts if private.
 */
data class Slideshow(
    val id: String,
    val title: String,
    val description: String,
    val status: Status,
    val username: String,
    val url: String,
    val thumbnailUrl: String,
    val thumbnailSize: String,
    val thumbnailSmallUrl: String,
    val thumbnailXLargeUrl: String,
    val thumbnailXXLargeUrl: String,
    val embed: String,
    val created: Date,
    val updated: Date,
    val language: String,
    val format: Format,
    val download: Download,
    val downloadUrl: String?,
    val secretKey: String,
    val slideshowEmbedUrl: String,
    val slideshowType: SlideshowType,
    val inContest: Boolean,
    val userId: String?,
    val pptLocation: String?,
    val strippedTitle: String?,
    val tags: List<Tag> = emptyList(),
    val numDownloads: Int?,
    val numViews: Int?,
    val numComments: Int?,
    val numFavorites: Int?,
    val numSlides: Int?,
    val relatedSlideshows: List<RelatedSlideshowId> = emptyList(),
    val privacyLevel: PrivacyLevel?,
    val flagVisible: Boolean?,
    val showOnSs: Boolean?,
    val secretUrl: Boolean?,
    val allowEmbed: Boolean?,
    val shareWithContacts: Boolean?
) {
    /**
     * A conversion status.
     */
    @Suppress("unused")
    enum class Status {
        /**
         * 0 if queued for conversion.
         */
        QUEUED,

        /**
         * 1 if converting.
         */
        CONVERTING,

        /**
         * 2 if converted.
         */
        CONVERTED,

        /**
         * 3 if conversion failed
         */
        FAILED
    }

    /**
     * Slideshow file format.
     */
    @Suppress("unused")
    enum class Format {
        PPTX,
        PPT,
        PDF,
        PPS,
        ODP,
        DOC,
        POT,
        TXT,
        RDF,
        UNKNOWN
    }

    /**
     * Whether a slideshow is downloadable or not.
     */
    @Suppress("unused")
    enum class Download {
        /**
         * Downloadable.
         */
        AVAILABLE,
        /**
         * Non-downloadable
         */
        UNAVAILABLE
    }

    /**
     * Type of slideshow
     */
    @Suppress("unused")
    enum class SlideshowType {
        PRESENTATION,
        DOCUMENT,
        PORTFOLIO,
        VIDEO
    }

    /**
     * Tag of slideshow.
     *
     * @param count Number of times used.
     * @param owner Whether or not the tag is used by owner.
     * @param name The tag name.
     */
    data class Tag(
        val count: Int,
        val owner: Int,
        val name: String
    )

    /**
     * Level of privacy.
     */
    @Suppress("unused")
    enum class PrivacyLevel {
        PUBLIC,
        PRIVATE
    }

    /**
     * Related slideshow information.
     *
     * @param rank The rank of slideshow.
     * @param id The related slideshow ID.
     */
    data class RelatedSlideshowId(
        val rank: Int,
        var id: String
    )
}
