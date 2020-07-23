package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.api.impl.xml.ObjectMappers
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter
import org.springframework.social.support.ClientHttpRequestFactorySelector
import org.springframework.web.client.RestTemplate
import java.time.Clock

/**
 * Implementation class for [SlideShare]
 *
 * @constructor
 * @param apiKey the API key provided by SlideShare
 * @param sharedSecret the shared secret provided by SlideShare
 */
class SlideShareTemplate(
    private val apiKey: String,
    private val sharedSecret: String
) : SlideShare {
    private val restTemplate by lazy {
        createRestTemplate()
    }

    private val slideshowOperations by lazy {
        SlideshowTemplate(restTemplate)
    }

    override fun slideshowOperations(): SlideshowOperations {
        return slideshowOperations
    }

    override fun isAuthorized(): Boolean {
        return false
    }

    internal fun restTemplate(): RestTemplate {
        return restTemplate
    }

    private fun createRestTemplate(): RestTemplate {
        return RestTemplate(getMessageConverters()).apply {
            requestFactory = ClientHttpRequestFactorySelector.bufferRequests(ClientHttpRequestFactorySelector.getRequestFactory())
            interceptors.add(ValidationInterceptor(apiKey, sharedSecret, Clock.systemDefaultZone()))
        }
    }

    private fun getMessageConverters(): List<HttpMessageConverter<out Any>> {
        return listOf(
            StringHttpMessageConverter(),
            MappingJackson2XmlHttpMessageConverter(ObjectMappers.xmlMapper())
        )
    }
}
