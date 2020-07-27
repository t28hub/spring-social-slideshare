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

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SlideShareTemplateTest {
    private lateinit var slideShare: SlideShareTemplate
    private lateinit var unauthorizedSlideShare: SlideShareTemplate

    @BeforeAll
    fun setup() {
        val credentials = Credentials("username", "password")
        slideShare = SlideShareTemplate(apiKey = API_KEY, sharedSecret = SHARED_SECRET, credentials = credentials)
        unauthorizedSlideShare = SlideShareTemplate(apiKey = API_KEY, sharedSecret = SHARED_SECRET)
    }

    @Test
    fun `isAuthorized should return true if credentials exists`() {
        // Act
        val isAuthorized = slideShare.isAuthorized

        // Assert
        assertTrue(isAuthorized)
    }

    @Test
    fun `isAuthorized should return false if credentials does not exist`() {
        // Act
        val isAuthorized = unauthorizedSlideShare.isAuthorized

        // Assert
        assertFalse(isAuthorized)
    }

    @Test
    fun `restTemplate should add AuthenticationInterceptor if credential exists`() {
        // Act
        val restTemplate = slideShare.restTemplate()

        // Assert
        assertEquals(restTemplate.messageConverters.size, 2)
        assertEquals(restTemplate.interceptors.size, 2)
        assertTrue(restTemplate.errorHandler is SlideShareErrorHandler)
    }

    @Test
    fun `restTemplate should not add AuthenticationInterceptor if credential does not exist`() {
        // Act
        val restTemplate = unauthorizedSlideShare.restTemplate()

        // Assert
        assertEquals(restTemplate.messageConverters.size, 2)
        assertEquals(restTemplate.interceptors.size, 1)
        assertTrue(restTemplate.errorHandler is SlideShareErrorHandler)
    }

    companion object {
        private const val API_KEY = "TEST_API_KEY"
        private const val SHARED_SECRET = "TEST_SHARED_SECRET"
    }
}
