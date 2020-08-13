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
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Answers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

internal class SlideShareConnectionTest {
    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var apiAdapter: SlideShareAdapter

    @Mock(answer = Answers.RETURNS_MOCKS)
    private lateinit var serviceProvider: SlideShareServiceProvider
    private lateinit var connection: SlideShareConnection

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
        connection = SlideShareConnection(apiAdapter, serviceProvider)
    }

    @Test
    fun `getApi should retrieve instance from ServiceProvider`() {
        // Act
        val api = connection.api

        // Assert
        assertThat(api).isNotNull()
        verify(serviceProvider).getApi()
        verifyNoMoreInteractions(serviceProvider)
    }

    @Test
    fun `createData should return null`() {
        // Act
        val data = connection.createData()

        // Assert
        assertThat(data).isNull()
    }
}
