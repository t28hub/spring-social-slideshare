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
package io.t28.springframework.social.slideshare.config.boot

import io.t28.springframework.social.slideshare.api.impl.Credentials
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

/**
 * Properties for SlideShare
 *
 * @param apiKey The API key provided by SlideShare.
 * @param sharedSecret The shared secret provided by SlideShare.
 * @param username The SlideShare username.
 * @param password The SlideShare password.
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.social.slideshare")
data class SlideShareProperties(
    val apiKey: String,
    val sharedSecret: String,
    val username: String? = null,
    val password: String? = null
) {
    internal val credentials: Credentials?
        get() {
            return if (username != null && password != null) {
                Credentials(username = username, password = password)
            } else {
                null
            }
        }
}
