package io.t28.springframework.social.slideshare.api.impl

import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.UserOperations
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

internal class UserTemplateTest {
    private lateinit var slideShare: SlideShareTemplate
    private lateinit var userOperations: UserOperations
    private lateinit var mockServer: MockRestServiceServer

    @BeforeEach
    fun setup() {
        val credentials = Credentials(username = SLIDESHARE_USERNAME, password = SLIDESHARE_PASSWORD)
        slideShare = SlideShareTemplate(API_KEY, SHARED_SECRET, credentials)
        userOperations = slideShare.userOperations()
        mockServer = MockRestServiceServer.bindTo(slideShare.restTemplate()).build()
    }

    @Test
    fun `getUserFavorites should return a collection of slideshow favorited`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_user_favorites?.+")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_user_favorites.xml", SlideShare::class.java)))

        // Act
        val favorites = userOperations.getUserFavorites("t28hub")

        // Assert
        assertEquals(favorites.size, 11)
        assertEquals(favorites[0].slideshowId, "49627175")
        assertEquals(favorites[0].tags.size, 0)
        assertEquals(favorites[10].slideshowId, "39065162")
        assertEquals(favorites[10].tags.size, 8)
    }

    @Test
    fun `getUserContacts should return a collection of contact information`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_user_contacts?.+")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_XML)
                .body(ClassPathResource("get_user_contacts.xml", SlideShare::class.java)))

        // Act
        val options = GetUserContactsOptions(limit = 10, offset = 1)
        val contacts = userOperations.getUserContacts("t28hub", options)

        // Assert
        assertEquals(contacts.size, 10)
        assertEquals(contacts[0].username, "jreffell")
        assertEquals(contacts[0].numSlideshows, 5)
        assertEquals(contacts[0].numComments, 1)
    }

    companion object {
        const val API_KEY = "TEST_API_KEY"
        const val SHARED_SECRET = "TEST_SHARED_SECRET"
        const val SLIDESHARE_USERNAME = "YOUR_SLIDESHARE_USERNAME"
        const val SLIDESHARE_PASSWORD = "YOUR_SLIDESHARE_PASSWORD"
    }
}
