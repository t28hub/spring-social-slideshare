package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for retrieving a collection of contact information
 *
 * @constructor
 *
 * @param offset The offset of result.
 * @param limit The number of contacts to return per result.
 *
 * - [UserOperations.getUserContacts]
 */
data class GetUserContactsOptions(
    val offset: Int? = null,
    val limit: Int? = null
)
