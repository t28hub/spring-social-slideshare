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

import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.Tag
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users", produces = [APPLICATION_JSON_VALUE])
class UsersController(private val slideShare: SlideShare) {
    @GetMapping("/{user}/favorites")
    fun getFavorites(@PathVariable user: String): List<Favorite> {
        return slideShare.userOperations().getUserFavorites(user)
    }

    @GetMapping("/{user}/contacts")
    fun getContacts(
        @PathVariable user: String,
        @RequestParam(defaultValue = "0") offset: Int,
        @RequestParam(defaultValue = "10") limit: Int
    ): List<Contact> {
        val options = GetUserContactsOptions(
            offset = offset,
            limit = limit
        )
        return slideShare.userOperations().getUserContacts(user, options)
    }

    @GetMapping("/tags")
    fun getTags(): List<Tag> {
        return slideShare.userOperations().getUserTags()
    }
}
