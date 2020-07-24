package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.EditOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SearchOptions
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.api.Slideshows
import io.t28.springframework.social.slideshare.api.UpdateResults
import org.springframework.social.ApiException
import org.springframework.social.MissingAuthorizationException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation class for [SlideshowOperations]
 *
 * @constructor
 *
 * @param restTemplate A [RestTemplate] instance.
 * @param isAuthorized Whether or not it is authorized.
 */
class SlideshowTemplate(
    private val restTemplate: RestTemplate,
    private val isAuthorized: Boolean
) : SlideshowOperations {
    override fun getSlideshow(id: String?, url: String?, options: GetSlideshowOptions?): Slideshow {
        if (id.isNullOrEmpty() and url.isNullOrEmpty()) {
            throw ApiException(PROVIDER_ID, "Either ID or URL must be required")
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
            throw ApiException(PROVIDER_ID, "Tag name must be non-empty string")
        }

        return getSlideshows("get_slideshows_by_tag", tag = tag, options = options)
    }

    override fun getSlideshowsByUser(user: String, options: GetSlideshowsOptions?): Slideshows {
        if (user.isEmpty()) {
            throw ApiException(PROVIDER_ID, "User name must be non-empty string")
        }

        return getSlideshows("get_slideshows_by_user", user = user, options = options)
    }

    override fun searchSlideshows(query: String, options: SearchOptions?): SearchResults {
        if (query.isEmpty()) {
            throw ApiException(PROVIDER_ID, "Query string must be non-empty string")
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

    override fun editSlideshow(id: String, options: EditOptions): UpdateResults {
        requireAuthorization()
        if (id.isEmpty()) {
            throw ApiException(PROVIDER_ID, "ID must be non-empty string")
        }

        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path("/edit_slideshow")
            queryParam("slideshow_id", id)
            with(options) {
                title?.let { title ->
                    queryParam("slideshow_title", title)
                }
                description?.let { description ->
                    queryParam("slideshow_description", description)
                }
                tags?.let { tags ->
                    queryParam("slideshow_tags", tags.joinToString(","))
                }
                private?.let { private ->
                    queryParam("make_slideshow_private", if (private) "Y" else "N")
                }
                generateSecretUrl?.let { enabled ->
                    queryParam("generate_secret_url", if (enabled) "Y" else "N")
                }
                allowEmbed?.let { enabled ->
                    queryParam("allow_embeds", if (enabled) "Y" else "N")
                }
                shareWithContacts?.let { enabled ->
                    queryParam("share_with_contacts", if (enabled) "Y" else "N")
                }
            }
        }.toUriString()
        return restTemplate.getForObject(uri)
    }

    override fun deleteSlideshow(id: String): UpdateResults {
        requireAuthorization()
        if (id.isEmpty()) {
            throw ApiException(PROVIDER_ID, "ID must be non-empty string")
        }

        val uri = UriComponentsBuilder.fromUriString(API_BASE_URL).apply {
            path("/delete_slideshow")
            queryParam("slideshow_id", id)
        }.toUriString()
        return restTemplate.getForObject(uri)
    }

    @Throws(MissingAuthorizationException::class)
    private fun requireAuthorization() {
        if (isAuthorized) {
            return
        }
        throw MissingAuthorizationException(PROVIDER_ID)
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
        private const val PROVIDER_ID = "SlideShare"
        private const val API_BASE_URL = "https://www.slideshare.net/api/2/"
    }
}
