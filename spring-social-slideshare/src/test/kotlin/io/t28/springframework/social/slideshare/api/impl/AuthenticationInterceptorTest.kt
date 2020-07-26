package io.t28.springframework.social.slideshare.api.impl

import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

internal class AuthenticationInterceptorTest {
    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var execution: ClientHttpRequestExecution
    private lateinit var interceptor: AuthenticationInterceptor

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        val credentials = Credentials(username = SLIDESHARE_USERNAME, password = SLIDESHARE_PASSWORD)
        interceptor = AuthenticationInterceptor(credentials)
    }

    @Test
    fun `intercept should append username and password to query strings`() {
        // Arrange
        val request = object : HttpRequest {
            override fun getHeaders(): HttpHeaders {
                return HttpHeaders.EMPTY
            }

            override fun getMethodValue(): String {
                return HttpMethod.GET.name
            }

            override fun getURI(): URI {
                return UriComponentsBuilder.fromUriString("https://www.slideshare.net")
                    .path("/api/2/delete_slideshow")
                    .queryParam("slideshow_id", "32795564")
                    .build()
                    .toUri()
            }
        }
        val emptyBody = ByteArray(0)

        // Act
        interceptor.intercept(request, emptyBody, execution)

        // Assert
        verify(execution).execute(
            argThat {
                if (method != HttpMethod.GET || !headers.isNullOrEmpty()) {
                    return@argThat false
                }

                uri.scheme == "https" &&
                    uri.host == "www.slideshare.net" &&
                    uri.path == "/api/2/delete_slideshow" &&
                    uri.query.contains("slideshow_id=32795564") &&
                    uri.query.contains("username=$SLIDESHARE_USERNAME") &&
                    uri.query.contains("password=$SLIDESHARE_PASSWORD")
            },
            eq(emptyBody)
        )
        verifyNoMoreInteractions(execution)
    }

    companion object {
        private const val SLIDESHARE_USERNAME = "TEST_SLIDESHARE_USERNAME"
        private const val SLIDESHARE_PASSWORD = "TEST_SLIDESHARE_PASSWORD"
    }
}
