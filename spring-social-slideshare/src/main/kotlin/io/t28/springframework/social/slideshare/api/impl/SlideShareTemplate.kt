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

import io.t28.springframework.social.slideshare.api.FavoriteOperations
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.api.UserOperations
import io.t28.springframework.social.slideshare.api.impl.xml.ObjectMapperHolder
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.social.support.ClientHttpRequestFactorySelector
import org.springframework.web.client.RestTemplate
import java.time.Clock

/**
 * Implementation class for [SlideShare].
 *
 * @param apiKey The API key provided by SlideShare.
 * @param sharedSecret The shared secret provided by SlideShare.
 * @param credentials The user credentials.
 */
class SlideShareTemplate(
    private val apiKey: String,
    private val sharedSecret: String,
    internal val credentials: Credentials? = null
) : SlideShare {
    private val restTemplate by lazy {
        createRestTemplate()
    }

    private val slideshowOperations by lazy {
        SlideshowTemplate(restTemplate, isAuthorized)
    }

    private val userOperations by lazy {
        UserTemplate(restTemplate, isAuthorized)
    }

    private val favoriteOperations by lazy {
        FavoriteTemplate(restTemplate, isAuthorized)
    }

    override fun slideshowOperations(): SlideshowOperations {
        return slideshowOperations
    }

    override fun userOperations(): UserOperations {
        return userOperations
    }

    override fun favoriteOperations(): FavoriteOperations {
        return favoriteOperations
    }

    override fun isAuthorized(): Boolean {
        return credentials != null
    }

    internal fun restTemplate(): RestTemplate {
        return restTemplate
    }

    private fun createRestTemplate(): RestTemplate {
        return RestTemplate(getMessageConverters()).apply {
            requestFactory = ClientHttpRequestFactorySelector.bufferRequests(ClientHttpRequestFactorySelector.getRequestFactory())
            interceptors = mutableListOf<ClientHttpRequestInterceptor>().apply {
                add(ValidationInterceptor(apiKey, sharedSecret, Clock.systemDefaultZone()))
                credentials?.let { add(AuthenticationInterceptor(it)) }
            }.toList()
            errorHandler = SlideShareErrorHandler(ObjectMapperHolder.xmlMapper())
        }
    }

    private fun getMessageConverters(): List<HttpMessageConverter<out Any>> {
        return listOf(
            StringHttpMessageConverter(),
            MappingJackson2XmlHttpMessageConverter(ObjectMapperHolder.xmlMapper())
        )
    }
}
