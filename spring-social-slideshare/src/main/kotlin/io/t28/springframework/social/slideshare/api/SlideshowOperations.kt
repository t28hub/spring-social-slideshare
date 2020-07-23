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
     * @param id The slideshow ID
     * @param url The slideshow URL
     * @param options The optional parameters
     * @return A slideshow information
     * @throws ApiException If there is an error while communicating with SlideShare API
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
     * @param id The slideshow ID
     * @param options The optional parameters
     * @return A slideshow information
     * @throws ApiException If there is an error while communicating with SlideShare API
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
     * @param url The slideshow URL
     * @param options The optional parameters
     * @return The Slideshow information
     * @throws ApiException If there is an error while communicating with SlideShare API
     *
     * - [getSlideshow]
     * - [getSlideshowById]
     */
    @Throws(ApiException::class)
    fun getSlideshowByUrl(url: String, options: GetSlideshowOptions? = null): Slideshow

    /**
     * Retrieve a collection of slideshows for a specified tag.
     * [Get Slideshows By Tag | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_slideshows_by_tag)
     *
     * @param tag The tag name
     * @param options The optional parameters
     * @return A collection of slideshows
     * @throws ApiException If there is an error while communicating with SlideShare API
     *
     * - [getSlideshowsByUser]
     */
    @Throws(ApiException::class)
    fun getSlideshowsByTag(tag: String, options: GetSlideshowsOptions? = null): Slideshows

    /**
     * Retrieve a collection of slideshows for a specified user.
     * [Get Slideshows By User | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_slideshows_by_user)
     *
     * @param user The user name
     * @param options The optional parameters
     * @return A collection of slideshows
     * @throws ApiException If there is an error while communicating with SlideShare API
     *
     * - [getSlideshowsByUser]
     */
    @Throws(ApiException::class)
    fun getSlideshowsByUser(user: String, options: GetSlideshowsOptions? = null): Slideshows

    /**
     * Search slideshows
     * [Slideshow Search | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#search_slideshows)
     *
     * @param query The search query
     * @param options The optional parameters
     * @return A search results containing the metadata and a collection of matching slideshows
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun searchSlideshows(query: String, options: SearchOptions? = null): SearchResults

    /**
     * Edit information of slideshow
     * [Edit existing slideshow | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#edit_slideshow)
     *
     * @param id The slideshow ID to be edited
     * @param options The optional parameters
     * @return The edited slideshow ID
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun editSlideshow(id: String, options: EditOptions): UpdateResults

    /**
     * Delete the slideshow
     * [Delete slideshow | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#delete_slideshow)
     *
     * @param id The slideshow ID to be deleted
     * @return The deleted slideshow ID
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun deleteSlideshow(id: String): UpdateResults
}
