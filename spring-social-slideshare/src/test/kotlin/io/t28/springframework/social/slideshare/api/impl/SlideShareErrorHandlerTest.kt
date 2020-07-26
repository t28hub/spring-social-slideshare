package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.xml.ObjectMappers
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.OK
import org.springframework.http.client.ClientHttpResponse
import org.springframework.mock.http.client.MockClientHttpResponse
import java.io.InputStream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SlideShareErrorHandlerTest {
    private lateinit var errorHandler: SlideShareErrorHandler

    @BeforeAll
    fun setup() {
        errorHandler = SlideShareErrorHandler(ObjectMappers.xmlMapper())
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

    private fun resource(path: String): Resource {
        return ClassPathResource(path, SlideShare::class.java)
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
