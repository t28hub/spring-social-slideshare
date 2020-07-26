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

import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.UserOperations
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

internal class UserTemplateTest : AbstractSlideShareApiTest() {
    private val userOperations: UserOperations
        get() = slideShare.userOperations()

    @Test
    fun `getUserFavorites should return a collection of slideshow favorited`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_user_favorites?.+")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_user_favorites.xml".readResource())
            )

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
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_user_contacts.xml".readResource())
            )

        // Act
        val options = GetUserContactsOptions(limit = 10, offset = 1)
        val contacts = userOperations.getUserContacts("t28hub", options)

        // Assert
        assertEquals(contacts.size, 10)
        assertEquals(contacts[0].username, "jreffell")
        assertEquals(contacts[0].numSlideshows, 5)
        assertEquals(contacts[0].numComments, 1)
    }

    @Test
    fun `getUserTags should return a collection of tags by the authenticated user`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_user_tags?.+")))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_user_tags.xml".readResource())
            )

        // Act
        val tags = userOperations.getUserTags()

        // Assert
        assertEquals(tags.size, 3)
        assertEquals(tags[0].name, "kotlin")
        assertEquals(tags[0].count, 5)
    }
}
