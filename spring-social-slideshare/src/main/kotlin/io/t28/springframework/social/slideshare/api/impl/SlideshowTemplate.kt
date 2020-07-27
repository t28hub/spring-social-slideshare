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
package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.EditSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.SearchSlideshowsOptions
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.api.Slideshows
import io.t28.springframework.social.slideshare.api.UpdateResults
import io.t28.springframework.social.slideshare.ext.`as`
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject

/**
 * Implementation class for [SlideshowOperations]
 *
 * @param restTemplate A [RestTemplate] instance.
 * @param isAuthorized Whether or not it is authorized.
 */
class SlideshowTemplate(
    private val restTemplate: RestTemplate,
    isAuthorized: Boolean
) : AbstractSlideShareOperations(isAuthorized), SlideshowOperations {
    override fun getSlideshow(id: String?, url: String?, options: GetSlideshowOptions): Slideshow {
        if (id.isNullOrEmpty() and url.isNullOrEmpty()) {
            throw ApiException(PROVIDER_ID, "Either ID or URL must be required")
        }

        val uri = buildUriString("/get_slideshow") {
            if (!id.isNullOrEmpty()) queryParam("slideshow_id", id)
            if (!url.isNullOrEmpty()) queryParam("slideshow_url", url)
            with(options) {
                queryParam("exclude_tags", excludeTags.asInt())
                queryParam("detailed", detailed.asInt())
            }
        }
        return restTemplate.getForObject(uri)
    }

    override fun getSlideshowById(id: String, options: GetSlideshowOptions): Slideshow {
        return getSlideshow(id = id, url = null, options = options)
    }

    override fun getSlideshowByUrl(url: String, options: GetSlideshowOptions): Slideshow {
        return getSlideshow(id = null, url = url, options = options)
    }

    override fun getSlideshowsByTag(tag: String, options: GetSlideshowsOptions): Slideshows {
        if (tag.isEmpty()) {
            throw ApiException(PROVIDER_ID, "Tag name must be non-empty string")
        }
        return getSlideshows("/get_slideshows_by_tag", tag = tag, options = options)
    }

    override fun getSlideshowsByUser(user: String, options: GetSlideshowsOptions): Slideshows {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }
        return getSlideshows("/get_slideshows_by_user", user = user, options = options)
    }

    override fun searchSlideshows(query: String, options: SearchSlideshowsOptions): SearchResults {
        if (query.isEmpty()) {
            throw ApiException(PROVIDER_ID, "Query string must be non-empty string")
        }

        val uri = buildUriString("/search_slideshows") {
            queryParam("q", query)
            with(options) {
                queryParam("page", page)
                queryParam("items_per_page", perPage)
                queryParam("lang", language.code)
                queryParam("sort", sort.value)
                queryParam("upload_date", uploadDate.value)
                queryParam("what", what.value)
                queryParam("file_type", fileType.value)
                queryParam("detailed", detailed.asInt())
            }
        }
        return restTemplate.getForObject(uri)
    }

    override fun editSlideshow(id: String, options: EditSlideshowOptions): UpdateResults {
        requireAuthorization()
        if (id.isEmpty()) {
            throw ApiException(PROVIDER_ID, "ID must be non-empty string")
        }

        val uri = buildUriString("/edit_slideshow") {
            queryParam("slideshow_id", id)
            with(options) {
                title?.let { title -> queryParam("slideshow_title", title) }
                description?.let { description -> queryParam("slideshow_description", description) }
                tags?.let { tags -> queryParam("slideshow_tags", tags.joinToString(",")) }
                private?.let { private -> queryParam("make_slideshow_private", private.asString()) }
                generateSecretUrl?.let { enabled -> queryParam("generate_secret_url", enabled.asString()) }
                allowEmbed?.let { enabled -> queryParam("allow_embeds", enabled.asString()) }
                shareWithContacts?.let { enabled -> queryParam("share_with_contacts", enabled.asString()) }
            }
        }
        return restTemplate.getForObject(uri)
    }

    override fun deleteSlideshow(id: String): UpdateResults {
        requireAuthorization()
        if (id.isEmpty()) {
            throw ApiException(PROVIDER_ID, "ID must be non-empty string")
        }

        val uri = buildUriString("/delete_slideshow") {
            queryParam("slideshow_id", id)
        }
        return restTemplate.getForObject(uri)
    }

    private fun getSlideshows(path: String, tag: String = "", user: String = "", options: GetSlideshowsOptions): Slideshows {
        val uri = buildUriString(path) {
            if (tag.isNotEmpty()) queryParam("tag", tag)
            if (user.isNotEmpty()) queryParam("username_for", user)
            with(options) {
                limit?.run { queryParam("limit", this) }
                offset?.run { queryParam("offset", this) }
                queryParam("detailed", detailed.asInt())
                queryParam("get_unconverted", unconverted.asInt())
            }
        }
        return restTemplate.getForObject(uri)
    }

    companion object {
        private fun Boolean.asInt() = `as`(positive = 1, negative = 0)
        private fun Boolean.asString() = `as`(positive = "Y", negative = "N")
    }
}
