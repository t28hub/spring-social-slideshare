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
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import org.springframework.social.ApiException
import org.springframework.social.connect.ApiAdapter
import org.springframework.social.connect.ConnectionValues
import org.springframework.social.connect.UserProfile

/**
 * [ApiAdapter] implementation for SlideShare
 */
class SlideShareAdapter : ApiAdapter<SlideShare> {
    override fun test(slideShare: SlideShare): Boolean {
        return try {
            slideShare.slideshowOperations().searchSlideshows("test")
            true
        } catch (_: ApiException) {
            false
        }
    }

    override fun setConnectionValues(slideShare: SlideShare, values: ConnectionValues) {
        if (slideShare !is SlideShareTemplate) {
            return
        }

        slideShare.credentials?.also { credentials ->
            values.setProviderUserId(credentials.username)
            values.setProfileUrl("https://www.slideshare.net/${credentials.username}")
        }
    }

    override fun fetchUserProfile(slideShare: SlideShare): UserProfile {
        return UserProfile.EMPTY
    }

    override fun updateStatus(slideShare: SlideShare, message: String) {
        // This operation is not supported
    }
}
