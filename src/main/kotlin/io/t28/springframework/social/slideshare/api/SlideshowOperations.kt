package io.t28.springframework.social.slideshare.api

import org.springframework.social.ApiException

/**
 * Defining the operations for working with slideshows.
 */
interface SlideshowOperations {
    /**
     * Retrieve slideshow information for a specified ID or URL.
     * [Get Slideshow Information | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#id=get_slideshow)
     *
     * @param id the Slideshow ID
     * @param url the Slideshow URL
     * @param options the Optional parameters
     * @return the Slideshow information
     * @throws ApiException if there is an error while communicating with SlideShare API
     *
     * - [getSlideshowById]
     * - [getSlideshowByUrl]
     */
    @Throws(ApiException::class)
    fun getSlideshow(id: String?, url: String?, options: GetSlideshowOptions? = null): Slideshow

    /**
     * Retrieve slideshow information for a specified ID.
     * [Get Slideshow Information | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#id=get_slideshow)
     *
     * @param id the Slideshow ID
     * @param options the Optional parameters
     * @return the Slideshow information
     * @throws ApiException if there is an error while communicating with SlideShare API
     *
     * - [getSlideshow]
     * - [getSlideshowByUrl]
     */
    @Throws(ApiException::class)
    fun getSlideshowById(id: String, options: GetSlideshowOptions? = null): Slideshow

    /**
     * Retrieve slideshow information for a specified URL.
     * [Get Slideshow Information | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#id=get_slideshow)
     *
     * @param url the Slideshow URL
     * @param options the Optional parameters
     * @return the Slideshow information
     * @throws ApiException if there is an error while communicating with SlideShare API
     *
     * - [getSlideshow]
     * - [getSlideshowById]
     */
    @Throws(ApiException::class)
    fun getSlideshowByUrl(url: String, options: GetSlideshowOptions? = null): Slideshow
}
