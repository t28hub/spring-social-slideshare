package io.t28.springframework.social.slideshare.api.impl

import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.ClientHttpResponse
import org.springframework.social.support.URIBuilder
import java.net.URI

/**
 * Interceptor for the SlideShare API authentication
 *
 * The SlideShare API requires the following parameters to request private data:
 * - username: The username of SlideShare
 * - password: The password of SlideShare
 * [Authentication using the slideshare API](https://www.slideshare.net/developers/documentation)
 *
 * @constructor
 * @param credentials The user credentials
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
