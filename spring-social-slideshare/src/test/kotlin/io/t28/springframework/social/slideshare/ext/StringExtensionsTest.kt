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
package io.t28.springframework.social.slideshare.ext

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.security.NoSuchAlgorithmException

internal class StringExtensionsTest {
    @Test
    fun `sha1 should return SHA-1 hex string`() {
        // Act
        val sha1 = "abc".sha1()

        // Assert
        assertThat(sha1).apply {
            hasLength(40)
            isEqualTo("A9993E364706816ABA3E25717850C26C9CD0D89D")
        }
    }

    @Test
    fun `hash should return hash of specified algorithm`() {
        // Act
        val hash = "abc".hash("MD5")

        // Assert
        assertThat(hash).apply {
            hasLength(32)
            isEqualTo("900150983CD24FB0D6963F7D28E17F72")
        }
    }

    @Test
    fun `hash should throw exception if specified algorithm is unsupported`() {
        // Assert
        assertThrows<NoSuchAlgorithmException> {
            // Act
            "abc".hash("UNKNOWN_ALGORITHM")
        }
    }
}
