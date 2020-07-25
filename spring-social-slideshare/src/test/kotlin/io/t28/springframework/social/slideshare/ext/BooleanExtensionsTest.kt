package io.t28.springframework.social.slideshare.ext

import org.junit.jupiter.api.Assertions.*
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
            assertEquals(actual, 1)
        }

        @Test
        fun `should return 0 if false`() {
            // Act
            val actual = false.`as`(positive = 1, negative = 0)

            // Assert
            assertEquals(actual, 0)
        }
    }

    @Nested
    inner class asString {
        @Test
        fun `should return YES if true`() {
            // Act
            val actual = true.`as`(positive = "YES", negative = "NO")

            // Assert
            assertEquals(actual, "YES")
        }

        @Test
        fun `should return NO if false`() {
            // Act
            val actual = false.`as`(positive = "YES", negative = "NO")

            // Assert
            assertEquals(actual, "NO")
        }
    }
}
