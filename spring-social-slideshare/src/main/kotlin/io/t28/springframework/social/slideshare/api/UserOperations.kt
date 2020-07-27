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

import org.springframework.social.ApiException

/**
 * Defining the operations for working with users.
 */
interface UserOperations {
    /**
     * Retrieve favorited slideshows for a specified user.
     *
     * - [Get User Favorites | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_user_favorites)
     *
     * @param user The user name.
     * @return A collection of favorited slideshows.
     * @throws ApiException If there is an error while communicating with SlideShare API.
     */
    @Throws(ApiException::class)
    fun getUserFavorites(user: String): List<Favorite>

    /**
     * Retrieve contact information for a specified user.
     *
     * - [Get User Contacts | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_user_contacts)
     *
     * @param user The user name.
     * @param options The optional parameters.
     * @return A collection of contact information.
     * @throws ApiException If there is an error while communicating with SlideShare API.
     */
    @Throws(ApiException::class)
    fun getUserContacts(user: String, options: GetUserContactsOptions = GetUserContactsOptions()): List<Contact>

    /**
     * Retrieve tags by the authenticated user.
     *
     * - [Get User Tags | SlideShare API Documentation](https://www.slideshare.net/developers/documentation#get_user_tags)
     *
     * @return A collection of tag.
     * @throws ApiException If there is an error while communicating with SlideShare API.
     */
    fun getUserTags(): List<Tag>
}
