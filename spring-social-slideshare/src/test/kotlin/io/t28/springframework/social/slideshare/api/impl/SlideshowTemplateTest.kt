package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.social.ApiException
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

internal class SlideshowTemplateTest {
    private lateinit var slideShare: SlideShareTemplate
    private lateinit var slideshowOperations: SlideshowOperations
    private lateinit var mockServer: MockRestServiceServer

    @BeforeEach
    fun setup() {
        slideShare = SlideShareTemplate(API_KEY, SHARED_SECRET)
        slideshowOperations = slideShare.slideshowOperations()
        mockServer = MockRestServiceServer.bindTo(slideShare.restTemplate()).build()
    }

    @Test
    fun `getSlideshow should return slideshow for a specified ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshow.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshow(id = "179842138", url = null, options = options)

        // Assert
        assertEquals(slideshow.id, "179842138")
        assertEquals(slideshow.title, "Be A Great Product Leader (Amplify, Oct 2019)")
    }

    @Test
    fun `getSlideshow should return slideshow for a specified URL`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshow.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshow(id = null, url = "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019", options = options)

        // Assert
        assertEquals(slideshow.url, "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
        assertEquals(slideshow.title, "Be A Great Product Leader (Amplify, Oct 2019)")
    }

    @Test
    fun `getSlideshow should throw APIException when both ID and URL are missing`() {
        // Assert
        val exception = assertThrows<ApiException> {
            // Act
            slideshowOperations.getSlideshow(id = null, url = null)
        }
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `getSlideshowById should return slideshow for a specified ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshow.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshowById(id = "179842138", options = options)

        // Assert
        assertEquals(slideshow.id, "179842138")
        assertEquals(slideshow.title, "Be A Great Product Leader (Amplify, Oct 2019)")
    }

    @Test
    fun `getSlideshowByUrl should return slideshow for a specified URL`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshow.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshowByUrl(url = "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019", options = options)

        // Assert
        assertEquals(slideshow.url, "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
        assertEquals(slideshow.title, "Be A Great Product Leader (Amplify, Oct 2019)")
    }

    @Test
    fun `getSlideshowsByTag should return collection of slideshows for a specified tag`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshows_by_tag?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshows_by_tag.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowsOptions(offset = 0, limit = 10, detailed = true)
        val slideshows = slideshowOperations.getSlideshowsByTag("kotlin", options)

        // Assert
        assertEquals(slideshows.name, "kotlin")
        assertEquals(slideshows.count, 2408)
        assertEquals(slideshows.slideshows.size, 10)
    }

    @Test
    fun `getSlideshowsByUser should return collection of slideshows for a specified user`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshows_by_user?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_slideshows_by_user.xml", SlideShare::class.java)))

        // Act
        val options = GetSlideshowsOptions(offset = 0, limit = 10, detailed = true)
        val slideshows = slideshowOperations.getSlideshowsByUser("GoogleCloudPlatformJP", options)

        // Assert
        assertEquals(slideshows.name, "GoogleCloudPlatformJP")
        assertEquals(slideshows.count, 162)
        assertEquals(slideshows.slideshows.size, 10)
    }

    companion object {
        const val API_KEY = "TEST_API_KEY"
        const val SHARED_SECRET = "TEST_SHARED_SECRET"
    }
}
