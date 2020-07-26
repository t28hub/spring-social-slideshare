package io.t28.springframework.social.slideshare.ext

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

private val UPPER_DIGITS = arrayOf(
    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
)

/**
 * Return SHA-1 cryptographic hash of the string
 *
 * @return the hashed string
 */
fun String.sha1(): String {
    return hash("SHA-1")
}

/**
 * Return cryptographic hash of the string by using a specified algorithm
 *
 * @param algorithm the name of the hash algorithm
 * @return the name of the hash algorithm
 * @throws NoSuchAlgorithmException if the specified hash algorithm is not supported
 */
@Throws(NoSuchAlgorithmException::class)
internal fun String.hash(algorithm: String): String {
    val bytes = MessageDigest.getInstance(algorithm).digest(toByteArray())
    return bytes.fold(
        StringBuilder(bytes.size shl 1),
        @Suppress("MagicNumber")
        { builder, byte ->
            builder.append(UPPER_DIGITS[byte.toInt() and 0xF0 shr 4])
            builder.append(UPPER_DIGITS[byte.toInt() and 0x0F])
        }
    ).toString()
}
