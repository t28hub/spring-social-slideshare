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
package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.t28.springframework.social.slideshare.api.Slideshow
import java.util.Date

/**
 * Annotated mixin to add annotations to [Slideshow]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Slideshow")
interface SlideshowMixin {
    @get:JacksonXmlProperty(localName = "ID")
    val id: String

    @get:JacksonXmlProperty(localName = "Title")
    val title: String

    @get:JacksonXmlProperty(localName = "Description")
    val description: String

    @get:JacksonXmlProperty(localName = "Status")
    val status: Slideshow.Status

    @get:JacksonXmlProperty(localName = "Username")
    val username: String

    @get:JacksonXmlProperty(localName = "URL")
    val url: String

    @get:JacksonXmlProperty(localName = "ThumbnailURL")
    val thumbnailUrl: String

    @get:JacksonXmlProperty(localName = "ThumbnailSize")
    val thumbnailSize: String

    @get:JacksonXmlProperty(localName = "ThumbnailSmallURL")
    val thumbnailSmallUrl: String

    @get:JacksonXmlProperty(localName = "ThumbnailXLargeURL")
    val thumbnailXLargeUrl: String

    @get:JacksonXmlProperty(localName = "ThumbnailXXLargeURL")
    val thumbnailXXLargeUrl: String

    @get:JacksonXmlProperty(localName = "Embed")
    val embed: String

    @get:JacksonXmlProperty(localName = "Created")
    val created: Date

    @get:JacksonXmlProperty(localName = "Updated")
    val updated: Date

    @get:JacksonXmlProperty(localName = "Language")
    val language: String

    @get:JacksonXmlProperty(localName = "Format")
    @get:JsonDeserialize(using = SlideshowFormatDeserializer::class)
    val format: Slideshow.Format

    @get:JacksonXmlProperty(localName = "Download")
    val download: Slideshow.Download

    @get:JacksonXmlProperty(localName = "DownloadUrl")
    val downloadUrl: String?

    @get:JacksonXmlProperty(localName = "SecretKey")
    val secretKey: String

    @get:JacksonXmlProperty(localName = "SlideshowEmbedUrl")
    val slideshowEmbedUrl: String

    @get:JacksonXmlProperty(localName = "SlideshowType")
    val slideshowType: Slideshow.SlideshowType

    @get:JacksonXmlProperty(localName = "InContest")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val inContest: Boolean

    @get:JacksonXmlProperty(localName = "UserID")
    val userId: String?

    @get:JacksonXmlProperty(localName = "PPTLocation")
    val pptLocation: String?

    @get:JacksonXmlProperty(localName = "StrippedTitle")
    val strippedTitle: String?

    @get:JacksonXmlProperty(localName = "Tags")
    @get:JsonDeserialize(using = TagListDeserializer::class)
    val tags: List<Slideshow.Tag>

    @get:JacksonXmlProperty(localName = "NumDownloads")
    val numDownloads: Int?

    @get:JacksonXmlProperty(localName = "NumViews")
    val numViews: Int?

    @get:JacksonXmlProperty(localName = "NumComments")
    val numComments: Int?

    @get:JacksonXmlProperty(localName = "NumSlides")
    val numSlides: Int?

    @get:JacksonXmlProperty(localName = "RelatedSlideshows")
    @get:JsonDeserialize(using = RelatedSlideshowIdListDeserializer::class)
    val relatedSlideshows: List<Slideshow.RelatedSlideshowId>

    @get:JacksonXmlProperty(localName = "PrivacyLevel")
    val privacyLevel: Slideshow.PrivacyLevel?

    @get:JacksonXmlProperty(localName = "FlagVisible")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val flagVisible: Boolean?

    @get:JacksonXmlProperty(localName = "ShowOnSS")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val showOnSs: Boolean?

    @get:JacksonXmlProperty(localName = "SecretURL")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val secretUrl: Boolean?

    @get:JacksonXmlProperty(localName = "AllowEmbed")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val allowEmbed: Boolean?

    @get:JacksonXmlProperty(localName = "ShareWithContacts")
    @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
    val shareWithContacts: Boolean?

    /**
     * Annotated mixin to add annotations to [Slideshow.Tag]
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    interface TagMixin {
        @get:JacksonXmlProperty(localName = "Count", isAttribute = true)
        val count: Int

        @get:JacksonXmlProperty(localName = "Owner", isAttribute = true)
        val owner: Int

        @get:JacksonXmlProperty(localName = "InnerText")
        val name: String
    }

    /**
     * Annotated mixin to add annotations to [Slideshow.RelatedSlideshowId]
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    interface RelatedSlideshowIdMixin {
        @get:JacksonXmlProperty(localName = "rank", isAttribute = true)
        val rank: Int

        @get:JacksonXmlProperty(localName = "InnerText")
        val id: String
    }
}
