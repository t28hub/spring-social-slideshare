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
import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Favorites"])
@RestController
@RequestMapping("/favorites", produces = [APPLICATION_JSON_VALUE])
class FavoritesController(private val slideShare: SlideShare) {
    @ApiOperation(
        value = "Favorite a slide",
        notes = "Authorization required"
    )
    @ApiResponses(
        ApiResponse(code = 204, message = "Request has succeeded"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "Slide not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @PutMapping("/{id}")
    fun favorite(
        @ApiParam("The slide ID", required = true)
        @PathVariable id: String
    ): ResponseEntity<Void> {
        slideShare.favoriteOperations().addFavorite(id)
        return ResponseEntity.noContent().build()
    }

    @ApiOperation(
        value = "Check if a slide is favorited",
        notes = "Authorization required"
    )
    @ApiResponses(
        ApiResponse(code = 204, message = "The slide is favorited"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "The slide is not favorited"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/{id}")
    fun check(
        @ApiParam("The slide ID", required = true)
        @PathVariable id: String
    ): ResponseEntity<Void> {
        val state = slideShare.favoriteOperations().checkFavorite(id)
        return if (state.favorited) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
