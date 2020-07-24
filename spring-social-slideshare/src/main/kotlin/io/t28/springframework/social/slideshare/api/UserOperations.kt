package io.t28.springframework.social.slideshare.api

import org.springframework.social.ApiException

/**
 * Defining the operations for working with users.
 */
interface UserOperations {
    /**
     * Retrieve favorited slideshows for a specified user.
     * [Get User Favorites | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_user_favorites)
     *
     * @param user The user name.
     * @return A collection of favorited slideshows.
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun getUserFavorites(user: String): List<Favorite>

    /**
     * Retrieve contact information for a specified user.
     * [Get User Contacts | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_user_contacts)
     *
     * @param user The user name.
     * @param options The optional parameters.
     * @return A collection of contact information.
     * @throws ApiException If there is an error while communicating with SlideShare API
     */
    @Throws(ApiException::class)
    fun getUserContacts(user: String, options: GetUserContactsOptions = GetUserContactsOptions()): List<Contact>
}
