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

import io.swagger.annotations.Api
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.t28.springframework.social.slideshare.api.EditSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.Slideshows
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Slides"])
@RestController
@RequestMapping("/slides", consumes = [APPLICATION_JSON_VALUE], produces = [APPLICATION_JSON_VALUE])
class SlidesController(private val slideShare: SlideShare) {
    @ApiOperation(
        value = "Get a slide",
        notes = "Authentication required if the slide is private",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a slide information"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "Slide not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/{id}")
    fun get(
        @ApiParam("The slide ID", required = true)
        @PathVariable id: String,
        @ApiParam("Whether to return detailed information")
        @RequestParam(defaultValue = "false") detailed: Boolean,
        @ApiParam("Whether to exclude tags")
        @RequestParam(defaultValue = "false") excludeTags: Boolean
    ): Slideshow {
        val options = GetSlideshowOptions(
            excludeTags = excludeTags,
            detailed = detailed
        )
        return slideShare.slideshowOperations()
            .getSlideshowById(id = id, options = options)
    }

    @ApiOperation(
        value = "Update a slide",
        notes = "Authorization required",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns an updated slide information"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "Slide not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @PatchMapping("/{id}")
    fun update(
        @ApiParam("The slide ID", required = true)
        @PathVariable id: String,
        @ApiParam("New slide information", required = true)
        @RequestBody body: UpdateSlideRequest
    ): Slideshow {
        val options = EditSlideshowOptions(
            title = body.title,
            description = body.description,
            tags = body.tags
        )
        val updated = slideShare.slideshowOperations().editSlideshow(id, options)
        return slideShare.slideshowOperations().getSlideshowById(updated.id)
    }

    @ApiOperation(
        value = "Delete a slide",
        notes = "Authorization required"
    )
    @ApiResponses(
        ApiResponse(code = 204, message = "No content"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "Slide not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @DeleteMapping("/{id}")
    fun delete(
        @ApiParam("The slide ID", required = true)
        @PathVariable id: String
    ) {
        slideShare.slideshowOperations()
            .deleteSlideshow(id)
    }

    @ApiOperation(
        value = "List slides by tag",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns a list of slides"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 404, message = "User not found"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping("/tags/{tag}")
    fun getByTag(
        @ApiParam("The tag name", required = true)
        @PathVariable tag: String,
        @ApiParam("Offset of the list to be returned", required = true)
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @ApiParam("Limit of the list to be returned", required = true)
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @ApiParam("Whether to return detailed information")
        @RequestParam(required = false, defaultValue = "false") detailed: Boolean
    ): Slideshows {
        val options = GetSlideshowsOptions(
            offset = offset,
            limit = limit,
            detailed = detailed
        )
        return slideShare.slideshowOperations()
            .getSlideshowsByTag(tag, options)
    }

    @ApiModel("Slide update request")
    data class UpdateSlideRequest(
        @ApiModelProperty("New title of the slide")
        val title: String? = null,
        @ApiModelProperty("New description of the slide")
        val description: String? = null,
        @ApiModelProperty("New tags of the slide")
        val tags: List<String>? = null,
        @ApiModelProperty("Whether to set private or not")
        val private: Boolean? = null,
        @ApiModelProperty("Whether to generate a secret URL")
        val generateSecretUrl: Boolean? = null,
        @ApiModelProperty("Whether to allow embed")
        val allowEmbed: Boolean? = null,
        @ApiModelProperty("Whether to share the slide to contacts if private")
        val shareWithContacts: Boolean? = null
    )
}
