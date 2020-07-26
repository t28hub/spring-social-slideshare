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
 * A slideshow
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
     * A conversion status
     */
    enum class Status {
        QUEUED, // 0 if queued for conversion
        CONVERTING, // 1 if converting
        CONVERTED, // 2 if converted
        FAILED // 3 if conversion failed
    }

    /**
     * Slideshow format
     */
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
    enum class Download {
        UNAVAILABLE,
        AVAILABLE
    }

    /**
     * Type of slideshow
     */
    enum class SlideshowType {
        PRESENTATION,
        DOCUMENT,
        PORTFOLIO,
        VIDEO
    }

    /**
     * Tag of slideshow
     */
    data class Tag(
        val count: Int,
        val owner: Int,
        val name: String
    )

    /**
     * Level of privacy
     */
    enum class PrivacyLevel {
        PUBLIC,
        PRIVATE
    }

    /**
     * Related slideshow information
     */
    data class RelatedSlideshowId(
        val rank: Int,
        var id: String
    )
}
