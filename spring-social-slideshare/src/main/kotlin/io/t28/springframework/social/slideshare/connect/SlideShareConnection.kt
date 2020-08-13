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
import org.springframework.social.connect.ApiAdapter
import org.springframework.social.connect.Connection
import org.springframework.social.connect.ConnectionData
import org.springframework.social.connect.support.AbstractConnection

/**
 * [Connection] implementation for SlideShare
 */
class SlideShareConnection(
    apiAdapter: ApiAdapter<SlideShare>,
    private val serviceProvider: SlideShareServiceProvider
) : AbstractConnection<SlideShare>(apiAdapter) {
    override fun getApi(): SlideShare {
        return serviceProvider.getApi()
    }

    override fun createData(): ConnectionData? {
        return null
    }
}
