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
package io.t28.springframework.social.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.GetUserContactsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.Slideshows
import io.t28.springframework.social.slideshare.api.Tag
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Users"])
@RestController
@RequestMapping("/users", produces = [APPLICATION_JSON_VALUE])
class UsersController(private val slideShare: SlideShare) {
    @ApiOperation(
        value = "List user's slides",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a list of user's slides"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/{user}/slides")
    fun getSlides(
        @ApiParam("The user name", required = true)
        @PathVariable user: String,
        @ApiParam("Offset of the list to be returned", required = true)
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @ApiParam("Limit of the list to be returned", required = true)
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @ApiParam("Whether to return detailed information")
        @RequestParam(defaultValue = "false") detailed: Boolean,
        @ApiParam("Whether to exclude tags")
        @RequestParam(defaultValue = "false") uncovered: Boolean
    ): Slideshows {
        val options = GetSlideshowsOptions(
            offset = offset,
            limit = limit,
            detailed = detailed,
            unconverted = uncovered
        )
        return slideShare.slideshowOperations()
            .getSlideshowsByUser(user, options)
    }

    @ApiOperation(
        value = "List user's favorited slides",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a list of user's favorited slides"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/{user}/favorites")
    fun getFavorites(
        @ApiParam("The user name", required = true)
        @PathVariable user: String
    ): List<Favorite> {
        return slideShare.userOperations().getUserFavorites(user)
    }

    @ApiOperation(
        value = "List user's contacts",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a list of user's contacts"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/{user}/contacts")
    fun getContacts(
        @ApiParam("The user name", required = true)
        @PathVariable user: String,
        @ApiParam("Offset of the list to be returned", required = true)
        @RequestParam(defaultValue = "0") offset: Int,
        @ApiParam("Limit of the list to be returned", required = true)
        @RequestParam(defaultValue = "10") limit: Int
    ): List<Contact> {
        val options = GetUserContactsOptions(
            offset = offset,
            limit = limit
        )
        return slideShare.userOperations().getUserContacts(user, options)
    }

    @ApiOperation(
        value = "List user's tags",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a list of user's tags"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/tags")
    fun getTags(): List<Tag> {
        return slideShare.userOperations().getUserTags()
    }
}
