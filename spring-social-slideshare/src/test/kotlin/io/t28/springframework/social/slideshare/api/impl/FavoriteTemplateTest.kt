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
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

internal class FavoriteTemplateTest : AbstractSlideShareApiTest() {
    private val favoriteTemplate: FavoriteOperations
        get() = slideShare.favoriteOperations()

    @Test
    fun `addFavorite should return favorited slideshow ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/add_favorite?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "49627175"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("add_favorite.xml".readResource())
            )

        // Act
        val results = favoriteTemplate.addFavorite("49627175")

        // Assert
        assertEquals(results.id, "49627175")
    }

    @Test
    fun `checkFavorite should return true if a specified slideshow is favorited`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/check_favorite?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "49627175"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("check_favorite_favorited.xml".readResource())
            )

        // Act
        val state = favoriteTemplate.checkFavorite("49627175")

        // Assert
        assertEquals(state.id, "49627175")
        assertEquals(state.user, "60146491")
        assertTrue(state.favorited)
    }

    @Test
    fun `checkFavorite should return true if a specified slideshow is not favorited`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/check_favorite?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "49627178"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("check_favorite_not_favorited.xml".readResource())
            )

        // Act
        val state = favoriteTemplate.checkFavorite("49627178")

        // Assert
        assertEquals(state.id, "49627178")
        assertEquals(state.user, "60146491")
        assertFalse(state.favorited)
    }
}
