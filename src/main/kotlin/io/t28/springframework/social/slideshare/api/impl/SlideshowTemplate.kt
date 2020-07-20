package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import org.springframework.social.ApiException
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject
import org.springframework.web.util.UriComponentsBuilder

/**
 * Implementation class for [SlideshowOperations]
 *
 * @constructor
 * @param restTemplate the [RestTemplate]
 */
class SlideshowTemplate(private val restTemplate: RestTemplate) : SlideshowOperations {
    override fun getSlideshow(id: String?, url: String?, options: GetSlideshowOptions?): Slideshow {
        if (id.isNullOrEmpty() and url.isNullOrEmpty()) {
            throw ApiException("SlideShare", "Either ID or URL is required")
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

    companion object {
        private const val API_BASE_URL = "https://www.slideshare.net/api/2/"
    }
}
