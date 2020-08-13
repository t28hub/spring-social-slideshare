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
package io.t28.springframework.social.slideshare.connect

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.Credentials
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import org.springframework.social.ServiceProvider

/**
 * [ServiceProvider] implementation for SlideShare
 */
class SlideShareServiceProvider(
    private val apiKey: String,
    private val sharedSecret: String,
    private val credentials: Credentials?
) : ServiceProvider<SlideShare> {
    fun getApi(): SlideShare {
        return SlideShareTemplate(apiKey = apiKey, sharedSecret = sharedSecret, credentials = credentials)
    }
}
