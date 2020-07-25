package io.t28.springframework.social.slideshare.ext

import org.hamcrest.Matchers.matchesPattern
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RestOperationsExtensionsTest {
    private lateinit var restOperations: RestOperations
    private lateinit var mockServer: MockRestServiceServer

    @BeforeAll
    fun setup() {
        restOperations = RestTemplate().apply {
            mockServer = MockRestServiceServer.bindTo(this).build()
        }
    }

    @BeforeEach
    fun `setup each`() {
        mockServer.reset()
    }

    @Test
    fun `getForListObject should return a list instead of a typed array`() {
        // Arrange
        @Language("json")
        val content = """
            [ "Alice", "Bob", "Charlie"]
        """.trimIndent()
        mockServer.expect(requestTo(matchesPattern("^https://www.example.com/?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_JSON)
                .body(content))

        // Act
        val tags = restOperations.getForListObject<String>("https://www.example.com/")

        // Assert
        assertEquals(tags.size, 3)
        assertEquals(tags[0], "Alice")
        assertEquals(tags[1], "Bob")
        assertEquals(tags[2], "Charlie")
    }

    @Test
    fun `getForListObject should return an empty list when element is empty`() {
        // Arrange
        @Language("json")
        val content = """
            []
        """.trimIndent()
        mockServer.expect(requestTo(matchesPattern("^https://www.example.com/?.+")))
            .andExpect(method(GET))
            .andRespond(withSuccess()
                .contentType(APPLICATION_JSON)
                .body(content))

        // Act
        val tags = restOperations.getForListObject<String>("https://www.example.com/")

        // Assert
        assertTrue(tags.isEmpty())
    }
}
