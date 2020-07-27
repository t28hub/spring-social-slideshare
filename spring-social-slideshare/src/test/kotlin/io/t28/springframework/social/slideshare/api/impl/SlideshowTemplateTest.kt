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

import io.t28.springframework.social.slideshare.api.EditSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SearchSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.social.ApiException
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess

internal class SlideshowTemplateTest : AbstractSlideShareApiTest() {
    private val slideshowOperations: SlideshowOperations
        get() = slideShare.slideshowOperations()

    @Test
    fun `getSlideshow should return slideshow for a specified ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "179842138"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshow.xml".readResource()))

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
            .andExpect(queryParam("slideshow_url", "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshow.xml".readResource())
            )

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshow(id = null, url = "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019", options = options)

        // Assert
        assertEquals(slideshow.url, "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
        assertEquals(slideshow.title, "Be A Great Product Leader (Amplify, Oct 2019)")
    }

    @Test
    fun `getSlideshow should throw ApiException when both ID and URL are null`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshow(id = null, url = null)
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `getSlideshow should throw ApiException when both ID and URL are empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshow(id = "", url = "")
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `getSlideshowById should return slideshow for a specified ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshow?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "179842138"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshow.xml".readResource())
            )

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
            .andExpect(queryParam("slideshow_url", "https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshow.xml".readResource())
            )

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
            .andExpect(queryParam("tag", "kotlin"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshows_by_tag.xml".readResource())
            )

        // Act
        val options = GetSlideshowsOptions(offset = 0, limit = 10, detailed = true)
        val slideshows = slideshowOperations.getSlideshowsByTag("kotlin", options)

        // Assert
        assertEquals(slideshows.name, "kotlin")
        assertEquals(slideshows.count, 2408)
        assertEquals(slideshows.slideshows.size, 10)
    }

    @Test
    fun `getSlideshowsByTag should throw ApiException if tag is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshowsByTag("")
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `getSlideshowsByUser should return collection of slideshows for a specified user`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/get_slideshows_by_user?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("username_for", "GoogleCloudPlatformJP"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("get_slideshows_by_user.xml".readResource())
            )

        // Act
        val options = GetSlideshowsOptions(offset = 0, limit = 10, detailed = true)
        val slideshows = slideshowOperations.getSlideshowsByUser("GoogleCloudPlatformJP", options)

        // Assert
        assertEquals(slideshows.name, "GoogleCloudPlatformJP")
        assertEquals(slideshows.count, 162)
        assertEquals(slideshows.slideshows.size, 10)
    }

    @Test
    fun `getSlideshowsByUser should throw ApiException if username is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshowsByUser("")
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `searchSlideshows should return collection of matching slideshows`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/search_slideshows?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("q", "kotlin"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("search_slideshows.xml".readResource())
            )

        // Act
        val options = SearchSlideshowsOptions(
            page = 1,
            perPage = 10,
            detailed = true
        )
        val results = slideshowOperations.searchSlideshows("kotlin", options)

        // Assert
        assertEquals(results.meta.query, "kotlin")
        assertEquals(results.meta.resultOffset, null)
        assertEquals(results.meta.numResults, 18)
        assertEquals(results.meta.totalResults, 4268)
        assertEquals(results.slideshows.size, 18)
    }

    @Test
    fun `searchSlideshows should throw ApiException if query is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.searchSlideshows("")
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `editSlideshow should return edited slideshow ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/edit_slideshow?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "32795564"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("edit_slideshow.xml".readResource())
            )

        // Act
        val options = EditSlideshowOptions(
            title = "New title",
            private = true
        )
        val edited = slideshowOperations.editSlideshow("32795564", options)

        // Assert
        assertEquals(edited.id, "32795564")
    }

    @Test
    fun `editSlideshow should throw ApiException if slideshow ID is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            val options = EditSlideshowOptions(
                title = "New title",
                private = true
            )
            slideshowOperations.editSlideshow("", options)
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }

    @Test
    fun `deleteSlideshow should return deleted slideshow ID`() {
        // Arrange
        mockServer.expect(requestTo(matchesPattern("^https://www.slideshare.net/api/2/delete_slideshow?.+")))
            .andExpect(method(GET))
            .andExpect(queryParam("slideshow_id", "32795564"))
            .andRespond(
                withSuccess()
                    .contentType(APPLICATION_XML)
                    .body("delete_slideshow.xml".readResource())
            )

        // Act
        val deleted = slideshowOperations.deleteSlideshow("32795564")

        // Assert
        assertEquals(deleted.id, "32795564")
    }

    @Test
    fun `deleteSlideshow should throw ApiException if slideshow ID is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.deleteSlideshow("")
        }

        // Assert
        assertEquals(exception.providerId, "SlideShare")
    }
}
