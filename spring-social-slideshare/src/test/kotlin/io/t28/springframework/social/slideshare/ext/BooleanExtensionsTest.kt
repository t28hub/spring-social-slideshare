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
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

@Suppress("ClassName")
internal class BooleanExtensionsTest {
    @Nested
    inner class asInt {
        @Test
        fun `should return 1 if true`() {
            // Act
            val actual = true.`as`(positive = 1, negative = 0)

            // Assert
            assertThat(actual).isEqualTo(1)
        }

        @Test
        fun `should return 0 if false`() {
            // Act
            val actual = false.`as`(positive = 1, negative = 0)

            // Assert
            assertThat(actual).isEqualTo(0)
        }
    }

    @Nested
    inner class asString {
        @Test
        fun `should return YES if true`() {
            // Act
            val actual = true.`as`(positive = "YES", negative = "NO")

            // Assert
            assertThat(actual).isEqualTo("YES")
        }

        @Test
        fun `should return NO if false`() {
            // Act
            val actual = false.`as`(positive = "YES", negative = "NO")

            // Assert
            assertThat(actual).isEqualTo("NO")
        }
    }
}
