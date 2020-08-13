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
package io.t28.springframework.social.slideshare.connect

import com.google.common.truth.Truth.assertThat
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.Credentials
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.social.InvalidAuthorizationException
import org.springframework.social.connect.ConnectionValues
import org.springframework.social.connect.UserProfile

@Suppress("ClassName")
internal class SlideShareAdapterTest {
    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var slideShare: SlideShare
    private lateinit var apiAdapter: SlideShareAdapter

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        apiAdapter = SlideShareAdapter()
    }

    @Nested
    inner class test {
        @Test
        fun `should return true if api validation is successful`() {
            // Act
            val isFunctional = apiAdapter.test(slideShare)

            // Assert
            assertThat(isFunctional).isTrue()
        }

        @Test
        fun `should return false if api validation is failed`() {
            // Arrange
            `when`(slideShare.slideshowOperations()).thenThrow(InvalidAuthorizationException::class.java)

            // Act
            val isFunctional = apiAdapter.test(slideShare)

            // Assert
            assertThat(isFunctional).isFalse()
        }
    }

    @Nested
    inner class setConnectionValues {
        @Mock(answer = Answers.RETURNS_MOCKS)
        private lateinit var values: ConnectionValues

        @BeforeEach
        fun setup() {
            MockitoAnnotations.initMocks(this)
        }

        @Test
        fun `should set user ID and profile URL`() {
            // Arrange
            val slideShare = SlideShareTemplate(
                apiKey = "TEST_API_KEY",
                sharedSecret = "TEST_SHARED_SECRET",
                credentials = Credentials(username = "TEST_USERNAME", password = "TEST_PASSWORD")
            )

            // Act
            apiAdapter.setConnectionValues(slideShare, values)

            // Arrange
            verify(values).setProviderUserId(eq("TEST_USERNAME"))
            verify(values).setProfileUrl("https://www.slideshare.net/TEST_USERNAME")
            verifyNoMoreInteractions(values)
        }

        @Test
        fun `should not set any property if credential is missing`() {
            // Arrange
            val slideShare = SlideShareTemplate(
                apiKey = "TEST_API_KEY",
                sharedSecret = "TEST_SHARED_SECRET",
                credentials = null
            )

            // Act
            apiAdapter.setConnectionValues(slideShare, values)

            // Arrange
            verifyZeroInteractions(values)
        }

        @Test
        fun `should not set any property if instance of SlideShare does not have credentials`() {
            // Act
            apiAdapter.setConnectionValues(slideShare, values)

            // Arrange
            verifyZeroInteractions(values)
        }
    }

    @Nested
    inner class fetchUserProfile {
        @Test
        fun `should return empty user profile`() {
            // Act
            val userProfile = apiAdapter.fetchUserProfile(slideShare)

            // Assert
            assertThat(userProfile).isEqualTo(UserProfile.EMPTY)
            verifyZeroInteractions(slideShare)
        }
    }

    @Nested
    inner class updateStatus {
        @Test
        fun `should do nothing`() {
            // Act
            apiAdapter.updateStatus(slideShare, "Status updated")

            // Assert
            verifyZeroInteractions(slideShare)
        }
    }
}
