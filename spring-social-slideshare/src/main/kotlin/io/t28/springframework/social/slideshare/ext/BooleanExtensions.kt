package io.t28.springframework.social.slideshare.ext

/**
 * Convert to a specified type.
 *
 * @param positive The value corresponding to true.
 * @param negative The value corresponding to false.
 * @return Positive if true, negative if false.
 */
fun <T> Boolean.`as`(positive: T, negative: T): T {
    return if (this) positive else negative
}
