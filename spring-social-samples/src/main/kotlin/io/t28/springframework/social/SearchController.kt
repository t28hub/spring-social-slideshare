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
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.SearchSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Api(tags = ["Search"])
@RestController
@RequestMapping("/search", produces = [APPLICATION_JSON_VALUE])
class SearchController(private val slideShare: SlideShare) {
    @Suppress("LongParameterList")
    @ApiOperation(
        value = "Search slides",
        produces = APPLICATION_JSON_VALUE
    )
    @ApiResponses(
        ApiResponse(code = 200, message = "Returns matched slides"),
        ApiResponse(code = 400, message = "Bad request"),
        ApiResponse(code = 401, message = "Unauthorized"),
        ApiResponse(code = 500, message = "Internal server error")
    )
    @GetMapping
    fun search(
        @ApiParam("Search query", required = true)
        @RequestParam q: String,
        @ApiParam("Page number of search results")
        @RequestParam(defaultValue = "1") page: Int,
        @ApiParam("Search results per page")
        @RequestParam(defaultValue = "10") perPage: Int,
        @ApiParam("The language of slides")
        @RequestParam(defaultValue = "ALL") language: SearchSlideshowsOptions.Language,
        @ApiParam("Sorts the search results")
        @RequestParam(defaultValue = "RELEVANCE") sort: SearchSlideshowsOptions.Sort,
        @ApiParam("The time period of restricting search")
        @RequestParam(defaultValue = "ANY") uploadDate: SearchSlideshowsOptions.UploadDate,
        @ApiParam("The file type of slides")
        @RequestParam(defaultValue = "ALL") fileType: SearchSlideshowsOptions.FileType,
        @ApiParam("The search type")
        @RequestParam(defaultValue = "TEXT") searchType: SearchSlideshowsOptions.SearchType,
        @ApiParam("Whether to return detailed information")
        @RequestParam(defaultValue = "false") detailed: Boolean
    ): SearchResults {
        val options = SearchSlideshowsOptions(
            page = page,
            perPage = perPage,
            language = language,
            sort = sort,
            uploadDate = uploadDate,
            fileType = fileType,
            what = searchType,
            detailed = detailed
        )
        return slideShare.slideshowOperations().searchSlideshows(q, options)
    }
}
