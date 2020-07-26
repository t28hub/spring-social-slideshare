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

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.t28.springframework.social.slideshare.api.SlideShareError
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.ACCOUNT_EXCEEDED_DAILY_LIMIT
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.BLANK_TITLE
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.FAILED_API_VALIDATION
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.FAILED_USER_AUTHENTICATION
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.GROUP_NOT_FOUND
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.INCORRECT_PARAMETERS
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.INSUFFICIENT_PERMISSIONS
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.INVALID_APPLICATION_ID
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.MISSING_FILE_FOR_UPLOAD
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.MISSING_TITLE
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.NO_API_KEY_PROVIDED
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.NO_TAG_PROVIDED
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.REQUIRED_PARAMETER_MISSING
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.SEARCH_QUERY_CANNOT_BE_BLANK
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.SLIDESHOW_NOT_FOUND
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.TAG_NOT_FOUND
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.USER_NOT_FOUND
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType.YOUR_ACCOUNT_HAS_BEEN_BLOCKED
import io.t28.springframework.social.slideshare.api.impl.AbstractSlideShareOperations.Companion.PROVIDER_ID
import org.springframework.http.client.ClientHttpResponse
import org.springframework.social.ApiException
import org.springframework.social.InsufficientPermissionException
import org.springframework.social.InvalidAuthorizationException
import org.springframework.social.NotAuthorizedException
import org.springframework.social.OperationNotPermittedException
import org.springframework.social.RateLimitExceededException
import org.springframework.social.ResourceNotFoundException
import org.springframework.social.UncategorizedApiException
import org.springframework.web.client.DefaultResponseErrorHandler

/**
 * Response error handler for SlideShare API.
 *
 * @constructor
 * @param xmlMapper The [XmlMapper] for response deserialization.
 */
internal class SlideShareErrorHandler(private val xmlMapper: XmlMapper) : DefaultResponseErrorHandler() {
    override fun hasError(response: ClientHttpResponse): Boolean {
        if (super.hasError(response)) {
            return true
        }

        // The SlideShare API also responds with 200 if there is an error.
        return try {
            xmlMapper.readValue<SlideShareError>(response.body)
            true
        } catch (_: JsonProcessingException) {
            // Ignore thrown exception due to check whether response body is deserializable as a SlideShareError.
            false
        }
    }

    override fun handleError(response: ClientHttpResponse) {
        val error = try {
            xmlMapper.readValue<SlideShareError>(response.body)
        } catch (e: JsonProcessingException) {
            throw UncategorizedApiException(PROVIDER_ID, "Failed to parse API error response", e)
        }

        val message = error.message
        when (message.id) {
            MISSING_TITLE,
            MISSING_FILE_FOR_UPLOAD,
            BLANK_TITLE,
            NO_TAG_PROVIDED,
            REQUIRED_PARAMETER_MISSING,
            SEARCH_QUERY_CANNOT_BE_BLANK,
            INCORRECT_PARAMETERS -> {
                throw ApiException(PROVIDER_ID, message.text)
            }
            SLIDESHOW_NOT_FOUND,
            USER_NOT_FOUND,
            GROUP_NOT_FOUND,
            TAG_NOT_FOUND -> {
                throw ResourceNotFoundException(PROVIDER_ID, message.text)
            }
            NO_API_KEY_PROVIDED -> {
                throw NotAuthorizedException(PROVIDER_ID, message.text)
            }
            INVALID_APPLICATION_ID,
            FAILED_API_VALIDATION -> {
                throw InvalidAuthorizationException(PROVIDER_ID, message.text)
            }
            FAILED_USER_AUTHENTICATION,
            YOUR_ACCOUNT_HAS_BEEN_BLOCKED -> {
                throw OperationNotPermittedException(PROVIDER_ID, message.text)
            }
            INSUFFICIENT_PERMISSIONS -> {
                throw InsufficientPermissionException(PROVIDER_ID, message.text)
            }
            ACCOUNT_EXCEEDED_DAILY_LIMIT -> {
                throw RateLimitExceededException(PROVIDER_ID)
            }
            else -> throw UncategorizedApiException(PROVIDER_ID, message.text, null)
        }
    }
}
