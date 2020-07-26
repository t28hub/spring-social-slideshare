package io.t28.springframework.social.slideshare.api.impl

import org.springframework.social.MissingAuthorizationException
import org.springframework.web.util.UriComponentsBuilder

/**
 * Abstract superclass for implementation of SlideShare operations
 *
 * @constructor
 * @param isAuthorized Whether or not it is authorized.
 */
internal abstract class AbstractSlideShareOperations(private val isAuthorized: Boolean) {
    /**
     * Throws a [MissingAuthorizationException] if it is not authorized.
     *
     * @throws MissingAuthorizationException If it is not authorized.
     */
    @Throws(MissingAuthorizationException::class)
    protected fun requireAuthorization() {
        if (isAuthorized) {
            return
        }
        throw MissingAuthorizationException(PROVIDER_ID)
    }

    companion object {
        const val PROVIDER_ID = "SlideShare"

        private const val API_BASE_URL = "https://www.slideshare.net/api/2/"

        /**
         * Build a SlideShare API URI string.
         *
         * @param path The path of operation.
         * @param init Initializer.
         */
        @JvmStatic
        protected fun buildUriString(path: String, init: UriComponentsBuilder.() -> Unit = {}): String {
            return UriComponentsBuilder.fromUriString(API_BASE_URL).path(path).apply(init).toUriString()
        }
    }
}
