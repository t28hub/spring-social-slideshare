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

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.xml.ObjectMapperHolder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.OK
import org.springframework.http.client.ClientHttpResponse
import org.springframework.mock.http.client.MockClientHttpResponse
import org.springframework.social.ApiException
import org.springframework.social.InsufficientPermissionException
import org.springframework.social.InvalidAuthorizationException
import org.springframework.social.OperationNotPermittedException
import org.springframework.social.RateLimitExceededException
import org.springframework.social.ResourceNotFoundException
import org.springframework.social.UncategorizedApiException
import java.io.InputStream
import java.util.stream.Stream
import kotlin.reflect.KClass

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SlideShareErrorHandlerTest {
    private lateinit var errorHandler: SlideShareErrorHandler

    @BeforeAll
    fun setup() {
        errorHandler = SlideShareErrorHandler(ObjectMapperHolder.xmlMapper())
    }

    @Test
    fun `hasError should return true if body contains an error message`() {
        // Arrange
        val response = BufferingClientHttpResponse(resource("error_no_api_key_provided.xml"))

        // Act
        val hasError = errorHandler.hasError(response)

        // Assert
        assertTrue(hasError)
    }

    @Test
    fun `hasError should return true if status code is 4xx or 5xx`() {
        // Arrange
        val response = MockClientHttpResponse("".byteInputStream(), INTERNAL_SERVER_ERROR)

        // Act
        val hasError = errorHandler.hasError(response)

        // Assert
        assertTrue(hasError)
    }

    @Test
    fun `hasError should return false if status code is 2xx and body does not contain error message`() {
        // Arrange
        val response = BufferingClientHttpResponse(resource("get_slideshow.xml"), OK)

        // Act
        val hasError = errorHandler.hasError(response)

        // Assert
        assertFalse(hasError)
    }

    @ParameterizedTest(name = "handleError should throw {1} if response is {0}")
    @MethodSource("provideErrors")
    fun `handleError should throw ApiException`(path: String, expectedClass: KClass<out ApiException>) {
        // Arrange
        val response = BufferingClientHttpResponse(resource(path), OK)

        // Act
        val exception = assertThrows<ApiException> {
            errorHandler.handleError(response)
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
        assertEquals(exception::class, expectedClass)
    }

    @Test
    fun `handleError should return throw UncategorizedApiException if body does not contain error message`() {
        // Arrange
        val response = BufferingClientHttpResponse(resource("get_slideshow.xml"), OK)

        // Act
        val exception = assertThrows<UncategorizedApiException> {
            errorHandler.handleError(response)
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    private fun resource(path: String): Resource {
        return ClassPathResource(path, SlideShare::class.java)
    }

    @Suppress("unused")
    companion object {
        @JvmStatic
        fun provideErrors(): Stream<Arguments> {
            return Stream.of(
                // ApiException
                arguments("error_blank_title.xml", ApiException::class),
                arguments("error_incorrect_parameters.xml", ApiException::class),
                arguments("error_missing_file_for_upload.xml", ApiException::class),
                arguments("error_missing_title.xml", ApiException::class),
                arguments("error_no_api_key_provided.xml", ApiException::class),
                arguments("error_no_tag_provided.xml", ApiException::class),
                arguments("error_required_parameter_missing.xml", ApiException::class),
                arguments("error_search_query_cannot_be_blank.xml", ApiException::class),
                // ResourceNotFoundException
                arguments("error_group_not_found.xml", ResourceNotFoundException::class),
                arguments("error_slideshow_not_found.xml", ResourceNotFoundException::class),
                arguments("error_tag_not_found.xml", ResourceNotFoundException::class),
                arguments("error_user_not_found.xml", ResourceNotFoundException::class),
                // InvalidAuthorizationException
                arguments("error_failed_api_validation.xml", InvalidAuthorizationException::class),
                arguments("error_invalid_application_id.xml", InvalidAuthorizationException::class),
                // OperationNotPermittedException
                arguments("error_failed_user_authentication.xml", OperationNotPermittedException::class),
                arguments("error_your_account_has_been_blocked.xml", OperationNotPermittedException::class),
                // InsufficientPermissionException
                arguments("error_insufficient_permissions.xml", InsufficientPermissionException::class),
                // RateLimitExceededException
                arguments("error_account_exceeded_daily_limit.xml", RateLimitExceededException::class),
                // UncategorizedApiException
                arguments("error_account_already_linked.xml", UncategorizedApiException::class),
                arguments("error_email_already_exists.xml", UncategorizedApiException::class),
                arguments("error_file_size_too_big.xml", UncategorizedApiException::class),
                arguments("error_invalid_extension.xml", UncategorizedApiException::class),
                arguments("error_login_already_exists.xml", UncategorizedApiException::class),
                arguments("error_no_linked_account_found.xml", UncategorizedApiException::class),
                arguments("error_slideshow_file_is_not_a_source_object.xml", UncategorizedApiException::class),
                arguments("error_user_not_created.xml", UncategorizedApiException::class),
                arguments("error_unknown.xml", UncategorizedApiException::class)
            )
        }
    }

    inner class BufferingClientHttpResponse(
        bodyResource: Resource,
        private val statusCode: HttpStatus = OK
    ) : ClientHttpResponse {
        private val body: ByteArray

        init {
            require(bodyResource.isFile and bodyResource.file.exists()) {
                "Resource file does not exist: ${bodyResource.filename}"
            }
            body = bodyResource.file.readBytes()
        }

        override fun getStatusCode(): HttpStatus {
            return statusCode
        }

        override fun getRawStatusCode(): Int {
            return statusCode.value()
        }

        override fun getStatusText(): String {
            return statusCode.reasonPhrase
        }

        override fun getHeaders(): HttpHeaders {
            return HttpHeaders.EMPTY
        }

        override fun getBody(): InputStream {
            return body.inputStream()
        }

        override fun close() {
        }
    }
}
