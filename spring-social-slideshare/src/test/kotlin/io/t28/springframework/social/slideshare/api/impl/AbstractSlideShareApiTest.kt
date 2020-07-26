package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.SlideShare
import org.junit.jupiter.api.BeforeEach
import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.test.web.client.MockRestServiceServer

internal abstract class AbstractSlideShareApiTest {
    protected lateinit var slideShare: SlideShareTemplate
    protected lateinit var mockServer: MockRestServiceServer
    protected lateinit var unauthorizedSlideShare: SlideShareTemplate
    protected lateinit var unauthorizedMockServer: MockRestServiceServer

    @BeforeEach
    fun setup() {
        val credentials = Credentials(username = SLIDESHARE_USERNAME, password = SLIDESHARE_PASSWORD)
        slideShare = SlideShareTemplate(API_KEY, SHARED_SECRET, credentials)
        mockServer = MockRestServiceServer.bindTo(slideShare.restTemplate())
            .bufferContent()
            .build()

        unauthorizedSlideShare = SlideShareTemplate(API_KEY, SHARED_SECRET)
        unauthorizedMockServer = MockRestServiceServer.bindTo(unauthorizedSlideShare.restTemplate())
            .bufferContent()
            .build()
    }

    protected fun String.readResource(): Resource {
        return ClassPathResource(this, SlideShare::class.java)
    }

    companion object {
        const val API_KEY = "TEST_API_KEY"
        const val SHARED_SECRET = "TEST_SHARED_SECRET"
        const val SLIDESHARE_USERNAME = "YOUR_SLIDESHARE_USERNAME"
        const val SLIDESHARE_PASSWORD = "YOUR_SLIDESHARE_PASSWORD"
    }
}
