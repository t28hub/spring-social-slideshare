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
package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.Credentials
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SlideShareProperties::class)
class SlideShareConfiguration(
    @Autowired private val properties: SlideShareProperties
) {
    @Bean
    fun slideShare(): SlideShare {
        val credentials = if (properties.username.isNullOrEmpty() or properties.password.isNullOrEmpty()) {
            null
        } else {
            Credentials(username = properties.username!!, password = properties.password!!)
        }
        return SlideShareTemplate(apiKey = properties.apiKey, sharedSecret = properties.sharedSecret, credentials = credentials)
    }
}
