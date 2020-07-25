package io.t28.springframework.social.slideshare.ext

import org.springframework.web.client.RestOperations
import org.springframework.web.client.getForObject

/**
 * Extension for [RestOperations.getForObject] converting object to a list.
 *
 * @param url The URL string.
 * @param uriVariables The URI variables to expand the template.
 * @return The converted list.
 */
inline fun <reified T> RestOperations.getForListObject(url: String, vararg uriVariables: Any): List<T> {
    // Convert to a list after deserialize as an array because getForObject erasures generic type.
    val array = getForObject<Array<T>>(url, *uriVariables)
    return array.asList()
}
