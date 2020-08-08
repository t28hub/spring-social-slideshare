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
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.SlideshowOperations
import io.t28.springframework.social.slideshare.truth.assertThat
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.http.HttpMethod.GET
import org.springframework.http.MediaType.APPLICATION_XML
import org.springframework.social.ApiException
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.queryParam
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.text.SimpleDateFormat
import java.util.TimeZone

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
                    .body("get_slideshow.xml".readResource())
            )

        // Act
        val options = GetSlideshowOptions(excludeTags = false, detailed = true)
        val slideshow = slideshowOperations.getSlideshow(id = "179842138", url = null, options = options)

        // Assert
        assertSlideshow(slideshow)
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
        assertSlideshow(slideshow)
    }

    @Test
    fun `getSlideshow should throw ApiException when both ID and URL are null`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshow(id = null, url = null)
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("Either ID or URL must be required")
        }
    }

    @Test
    fun `getSlideshow should throw ApiException when both ID and URL are empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshow(id = "", url = "")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("Either ID or URL must be required")
        }
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
        assertThat(slideshow).apply {
            hasId("179842138")
            hasUrl("https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
            hasTitle("Be A Great Product Leader (Amplify, Oct 2019)")
            description().contains("Be A Great Product Leader")
            status().isConverted()
        }
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
        assertThat(slideshow).apply {
            hasId("179842138")
            hasUrl("https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
            hasTitle("Be A Great Product Leader (Amplify, Oct 2019)")
            description().contains("Be A Great Product Leader")
            status().isConverted()
        }
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
        assertThat(slideshows).apply {
            hasName("kotlin")
            hasCount(2408)
            hasSlideshowsThat().hasSize(10)
            hasSlideshowAt(0).apply {
                hasId("237062843")
                hasUrl("https://www.slideshare.net/Rapidvaluesolutions/selenium-automation-with-kotlin")
                hasTitle("Selenium Automation with Kotlin")
            }
        }
    }

    @Test
    fun `getSlideshowsByTag should throw ApiException if tag is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshowsByTag("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("Tag name must be non-empty string")
        }
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
        assertThat(slideshows).apply {
            hasName("GoogleCloudPlatformJP")
            hasCount(162)
            hasSlideshowsThat().hasSize(10)
            hasSlideshowAt(0).apply {
                hasId("236199628")
                hasUrl("https://www.slideshare.net/GoogleCloudPlatformJP/cloud-onair-cloud-run-firestore-2020625")
                hasTitle("[Cloud OnAir] Cloud Run &amp; Firestore で、実践アジャイル開発 2020年6月25日 放送")
            }
        }
    }

    @Test
    fun `getSlideshowsByUser should throw ApiException if username is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.getSlideshowsByUser("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("User name must be non-empty string")
        }
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
        assertThat(results).apply {
            hasMetaThat().apply {
                hasQuery("kotlin")
                hasNumResults(18)
                hasTotalResults(4268)
                hasResultOffset(null)
            }
            hasSlideshowsThat().hasSize(18)
            hasSlideshowAt(0).apply {
                hasId("120024751")
                hasUrl("https://www.slideshare.net/elizarov/kotlin-coroutines-in-practice-kotlinconf-2018")
                hasTitle("Kotlin Coroutines in Practice @ KotlinConf 2018")
            }
        }
    }

    @Test
    fun `searchSlideshows should throw ApiException if query is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.searchSlideshows("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("Query string must be non-empty string")
        }
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
        assertThat(edited).hasId("32795564")
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
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("ID must be non-empty string")
        }
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
        assertThat(deleted).hasId("32795564")
    }

    @Test
    fun `deleteSlideshow should throw ApiException if slideshow ID is empty`() {
        // Act
        val exception = assertThrows<ApiException> {
            slideshowOperations.deleteSlideshow("")
        }

        // Assert
        assertThat(exception).apply {
            hasProviderId("SlideShare")
            hasMessageThat().isEqualTo("ID must be non-empty string")
        }
    }

    private fun assertSlideshow(actual: Slideshow) {
        assertThat(actual).apply {
            hasId("179842138")
            hasUrl("https://www.slideshare.net/adamnash/be-a-great-product-leader-amplify-oct-2019")
            hasTitle("Be A Great Product Leader (Amplify, Oct 2019)")
            description().contains("Be A Great Product Leader")
            status().isConverted()
            hasUserId("5502746")
            hasUsername("adamnash")
            hasThumbnailSize("[170,130]")
            hasThumbnailUrl("//cdn.slidesharecdn.com/ss_thumbnails/beagreatproductleader-amplifyoct2019v5-191007205738-thumbnail.jpg?cb=1580173593")
            hasThumbnailSmallUrl("//cdn.slidesharecdn.com/ss_thumbnails/beagreatproductleader-amplifyoct2019v5-191007205738-thumbnail-2.jpg?cb=1580173593")
            hasThumbnailXLargeUrl("//cdn.slidesharecdn.com/ss_thumbnails/beagreatproductleader-amplifyoct2019v5-191007205738-thumbnail-3.jpg?cb=1580173593")
            hasThumbnailXXLargeUrl("//cdn.slidesharecdn.com/ss_thumbnails/beagreatproductleader-amplifyoct2019v5-191007205738-thumbnail-4.jpg?cb=1580173593")
            embed().isNotEmpty()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }
            hasCreated(dateFormat.parse("2019-10-07 20:57:38 UTC"))
            hasUpdated(dateFormat.parse("2020-01-28 01:06:33 UTC"))
            hasLanguage("en")
            format().isPdf()
            download().isAvailable()
            download().isNotUnavailable()
            hasDownloadUrl(null)
            hasSecretKey("7dIwzX8sWUvlab")
            hasSlideshowEmbedUrl("https://www.slideshare.net/slideshow/embed_code/key/7dIwzX8sWUvlab")
            hasSlideshowType(Slideshow.SlideshowType.PRESENTATION)
            isNotInContest()
            hasPptLocation("beagreatproductleader-amplifyoct2019v5-191007205738")
            hasStrippedTitle("be-a-great-product-leader-amplify-oct-2019")
            tags().hasSize(3)
            hasNumDownloads(0)
            hasNumViews(414174)
            hasNumComments(399)
            hasNumFavorites(766)
            hasNumSlides(22)
            relatedSlideshows().isEmpty()
            hasPrivacyLevel(Slideshow.PrivacyLevel.PUBLIC)
            isFlagVisible()
            isNotShowOnSs()
            isNotSecretUrl()
            isNotAllowEmbed()
            isNotShareWithContacts()
        }
    }
}
