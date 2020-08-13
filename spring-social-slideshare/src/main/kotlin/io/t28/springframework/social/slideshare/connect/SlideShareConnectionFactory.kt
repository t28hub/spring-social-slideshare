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
import io.t28.springframework.social.slideshare.api.impl.AbstractSlideShareOperations
import io.t28.springframework.social.slideshare.api.impl.Credentials
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionData
import org.springframework.social.connect.ConnectionFactory

/**
 * [ConnectionFactory] implementation for SlideShare
 */
class SlideShareConnectionFactory(
    apiKey: String,
    sharedSecret: String,
    credentials: Credentials?
) : ConnectionFactory<SlideShare>(
    AbstractSlideShareOperations.PROVIDER_ID,
    SlideShareServiceProvider(apiKey, sharedSecret, credentials),
    SlideShareAdapter()
) {
    override fun createConnection(data: ConnectionData): Connection<SlideShare> {
        return SlideShareConnection(apiAdapter, serviceProvider as SlideShareServiceProvider)
    }
}
