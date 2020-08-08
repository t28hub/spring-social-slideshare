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

import com.google.common.truth.Truth.assertThat
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.UserOperations
import io.t28.springframework.social.slideshare.truth.assertThat
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.social.ApiException
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam
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
            .andExpect(queryParam("username_for", "t28hub"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_user_favorites.xml".readResource())
            )

        // Act
        val favorites = userOperations.getUserFavorites("t28hub")

        // Assert
        assertThat(favorites).hasSize(11)
        assertThat(favorites[0]).apply {
            hasSlideshowId("49627175")
            tags().isEmpty()
        }
        assertThat(favorites[10]).apply {
            hasSlideshowId("39065162")
            tags().hasSize(8)
            hasTagAt(0).isEqualTo("Deep Learning")
            hasTagAt(1).isEqualTo("Machine Learning")
            hasTagAt(2).isEqualTo("Natural Language Processing")
            hasTagAt(3).isEqualTo("Data Mining")
            hasTagAt(4).isEqualTo("NLP")
            hasTagAt(5).isEqualTo("ML")
            hasTagAt(6).isEqualTo("DM")
            hasTagAt(7).isEqualTo("DL")
        }
    }

    @Test
    fun `getUserFavorites should throw ApiException if username is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            userOperations.getUserFavorites("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("User name must be non-empty string")
        }
    }

    @Test
    fun `getUserContacts should return a collection of contact information`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_user_contacts?.+")))
            .andExpect(method(HttpMethod.GET))
            .andExpect(queryParam("username_for", "t28hub"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_user_contacts.xml".readResource())
            )

        // Act
        val options = GetUserContactsOptions(limit = 10, offset = 1)
        val contacts = userOperations.getUserContacts("t28hub", options)

        // Assert
        assertThat(contacts).hasSize(10)
        assertThat(contacts[0]).apply {
            hasUsername("jreffell")
            hasNumSlideshows(5)
            hasNumComments(1)
        }
        assertThat(contacts[9]).apply {
            hasUsername("mraible")
            hasNumSlideshows(219)
            hasNumComments(13)
        }
    }

    @Test
    fun `getUserContacts should throw ApiException if username is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            userOperations.getUserContacts("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("User name must be non-empty string")
        }
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
        assertThat(tags).hasSize(3)
        assertThat(tags[0]).apply {
            hasName("kotlin")
            hasCount(5)
        }
        assertThat(tags[2]).apply {
            hasName("spring boot")
            hasCount(2)
        }
    }
}
