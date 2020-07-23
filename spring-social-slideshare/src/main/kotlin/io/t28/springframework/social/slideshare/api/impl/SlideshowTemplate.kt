package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SearchOptions
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.api.Slideshows
import org.springframework.social.ApiException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation class for [SlideshowOperations]
 *
 * @constructor
 * @param restTemplate The [RestTemplate]
 */
class SlideshowTemplate(private val restTemplate: RestTemplate) : SlideshowOperations {
    override fun getSlideshow(id: String?, url: String?, options: GetSlideshowOptions?): Slideshow {
        if (id.isNullOrEmpty() and url.isNullOrEmpty()) {
            throw ApiException("SlideShare", "Either ID or URL must be required")
        }

        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path("/get_slideshow")
            if (!id.isNullOrEmpty()) queryParam("slideshow_id", id)
            if (!url.isNullOrEmpty()) queryParam("slideshow_url", url)
            options?.let {
                queryParam("exclude_tags", if (it.excludeTags) 1 else 0)
                queryParam("detailed", if (it.detailed) 1 else 0)
            }
        }.toUriString()
        return restTemplate.getForObject(uri)
    }

    override fun getSlideshowById(id: String, options: GetSlideshowOptions?): Slideshow {
        return getSlideshow(id = id, url = null, options = options)
    }

    override fun getSlideshowByUrl(url: String, options: GetSlideshowOptions?): Slideshow {
        return getSlideshow(id = null, url = url, options = options)
    }

    override fun getSlideshowsByTag(tag: String, options: GetSlideshowsOptions?): Slideshows {
        if (tag.isEmpty()) {
            throw ApiException("SlideShare", "Tag name must be non-empty string")
        }

        return getSlideshows("get_slideshows_by_tag", tag = tag, options = options)
    }

    override fun getSlideshowsByUser(user: String, options: GetSlideshowsOptions?): Slideshows {
        if (user.isEmpty()) {
            throw ApiException("SlideShare", "User name must be non-empty string")
        }

        return getSlideshows("get_slideshows_by_user", user = user, options = options)
    }

    override fun searchSlideshows(query: String, options: SearchOptions?): SearchResults {
        if (query.isEmpty()) {
            throw ApiException("SlideShare", "Query string must be non-empty string")
        }

        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path("/search_slideshows")
            queryParam("q", query)
            options?.let {
                queryParam("page", it.page)
                queryParam("items_per_page", it.perPage)
                queryParam("lang", it.language.code)
                queryParam("sort", it.sort.value)
                queryParam("upload_date", it.uploadDate.value)
                queryParam("what", it.what.value)
                queryParam("file_type", it.fileType.value)
                queryParam("detailed", if (it.detailed) 1 else 0)
            }
        }.toUriString()
        return restTemplate.getForObject(uri)
    }

    private fun getSlideshows(path: String, tag: String = "", user: String = "", options: GetSlideshowsOptions?): Slideshows {
        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path(path)
            if (tag.isNotEmpty()) queryParam("tag", tag)
            if (user.isNotEmpty()) queryParam("username_for", user)
            options?.let {
                it.limit?.run { queryParam("limit", this) }
                it.offset?.run { queryParam("offset", this) }
                queryParam("detailed", if (it.detailed) 1 else 0)
                queryParam("get_unconverted", if (it.unconverted) 1 else 0)
            }
        }.toUriString()
        return restTemplate.getForObject(uri)
    }

    companion object {
        private const val API_BASE_URL = "https://www.slideshare.net/api/2/"
    }
}
