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

import com.nhaarman.mockitokotlin2.argThat
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import io.t28.springframework.social.slideshare.ext.sha1
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Answers.RETURNS_MOCKS
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod.GET
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.Clock
import java.time.Instant

internal class ValidationInterceptorTest {
    @Mock
    private lateinit var clock: Clock
    @Mock(answer = RETURNS_MOCKS)
    private lateinit var execution: ClientHttpRequestExecution
    private lateinit var interceptor: ValidationInterceptor

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        interceptor = ValidationInterceptor(API_KEY, SHARED_SECRET, clock)
    }

    @Test
    fun `intercept should append required parameters`() {
        // Arrange
        val instant = Instant.ofEpochMilli(1577804400) // 2020/01/01 00:00:00
        `when`(clock.instant()).thenReturn(instant)

        val request = object : HttpRequest {
            override fun getHeaders(): HttpHeaders {
                return HttpHeaders.EMPTY
            }

            override fun getMethodValue(): String {
                return GET.name
            }

            override fun getURI(): URI {
                return UriComponentsBuilder.fromUriString("https://www.slideshare.net")
                    .path("/api/2/get_slideshow")
                    .queryParam("slideshow_url", "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
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
                if (method != GET || !headers.isNullOrEmpty()) {
                    return@argThat false
                }

                uri.scheme == "https" &&
                    uri.host == "www.slideshare.net" &&
                    uri.path == "/api/2/get_slideshow" &&
                    uri.query.contains("slideshow_url=https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019") &&
                    uri.query.contains("api_key=$API_KEY") &&
                    uri.query.contains("ts=1577804") &&
                    uri.query.contains("hash=${"${SHARED_SECRET}1577804".sha1().toLowerCase()}")
            },
            eq(emptyBody)
        )
        verifyNoMoreInteractions(execution)
    }

    companion object {
        private const val API_KEY = "TEST_API_KEY"
        private const val SHARED_SECRET = "TEST_SHARED_SECRET"
    }
}
