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
