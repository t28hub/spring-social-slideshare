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

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.social.MissingAuthorizationException

internal class AbstractSlideShareOperationsTest {
    @Test
    fun `requireAuthorization should throw MissingAuthorizationException if not authorized`() {
        // Arrange
        val operations = object : AbstractSlideShareOperations(false) {
            fun checkAuthorization() {
                requireAuthorization()
            }
        }

        // Act & Assert
        assertThrows<MissingAuthorizationException> {
            operations.checkAuthorization()
        }
    }

    @Test
    fun `requireAuthorization should not throw MissingAuthorizationException if authorized`() {
        // Arrange
        val operations = object : AbstractSlideShareOperations(true) {
            fun checkAuthorization() {
                requireAuthorization()
            }
        }

        // Act & Assert
        assertDoesNotThrow {
            operations.checkAuthorization()
        }
    }
}
