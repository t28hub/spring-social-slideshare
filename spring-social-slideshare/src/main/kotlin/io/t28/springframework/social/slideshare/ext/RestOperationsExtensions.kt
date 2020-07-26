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
