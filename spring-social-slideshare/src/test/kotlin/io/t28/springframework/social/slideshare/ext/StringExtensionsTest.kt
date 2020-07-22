package io.t28.springframework.social.slideshare.ext

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.security.NoSuchAlgorithmException

internal class StringExtensionsTest {
    @Test
    fun `sha1 should return SHA-1 hex string`() {
        // Act
        val sha1 = "abc".sha1()

        // Assert
        assertEquals(sha1.length, 40)
        assertEquals(sha1, "A9993E364706816ABA3E25717850C26C9CD0D89D")
    }

    @Test
    fun `hash should return hash of specified algorithm`() {
        // Act
        val hash = "abc".hash("MD5")

        // Assert
        assertEquals(hash.length, 32)
        assertEquals(hash, "900150983CD24FB0D6963F7D28E17F72")
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
