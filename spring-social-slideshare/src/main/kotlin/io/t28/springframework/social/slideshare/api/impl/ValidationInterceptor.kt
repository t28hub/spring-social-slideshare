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

import io.t28.springframework.social.slideshare.ext.sha1
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.social.support.URIBuilder
import java.net.URI
import java.time.Clock

/**
 * Interceptor for the SlideShare API validation
 *
 * The SlideShare API requires the following parameters for all requests:
 * - api_key: the API key provided by SlideShare
 * - ts: the current Unix timeStamp
 * - hash: the SHA-1 hash of the concatenation of the shared secret and the timestamp
 * [API Validation using the SlideShare API](https://www.slideshare.net/developers/documentation)
 *
 * @constructor
 * @param apiKey the API key provided by SlideShare
 * @param sharedSecret the shared secret provided by SlideShare
 * @param clock the [Clock] instance to retrieve the current Unix timestamp
 */
internal class ValidationInterceptor(
    private val apiKey: String,
    private val sharedSecret: String,
    private val clock: Clock
) : ClientHttpRequestInterceptor {
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val timestamp = clock.instant().epochSecond.toString()
        val hash = "$sharedSecret$timestamp".sha1().toLowerCase()
        val decorated = object : HttpRequest by request {
            override fun getURI(): URI {
                return URIBuilder.fromUri(request.uri).apply {
                    queryParam("api_key", apiKey)
                    queryParam("ts", timestamp)
                    queryParam("hash", hash)
                }.build()
            }
        }
        return execution.execute(decorated, body)
    }
}
