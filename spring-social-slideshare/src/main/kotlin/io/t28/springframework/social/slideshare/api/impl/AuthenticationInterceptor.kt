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

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.social.support.URIBuilder
import java.net.URI

/**
 * Interceptor for the SlideShare API authentication.
 *
 * - [Authentication using the slideshare API](https://www.slideshare.net/developers/documentation)
 *
 * The SlideShare API requires the following parameters to request private data:
 * - username: The username of SlideShare.
 * - password: The password of SlideShare.
 *
 * @param credentials The user credentials.
 */
internal class AuthenticationInterceptor(private val credentials: Credentials) : ClientHttpRequestInterceptor {
    override fun intercept(request: HttpRequest, body: ByteArray, execution: ClientHttpRequestExecution): ClientHttpResponse {
        val decorated = object : HttpRequest by request {
            override fun getURI(): URI {
                return URIBuilder.fromUri(request.uri).apply {
                    queryParam("username", credentials.username)
                    queryParam("password", credentials.password)
                }.build()
            }
        }
        return execution.execute(decorated, body)
    }
}
