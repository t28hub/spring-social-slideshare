package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.GetSlideshowsOptions
import io.t28.springframework.social.slideshare.api.SearchOptions
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.Slideshows
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/slides", produces = [APPLICATION_JSON_VALUE])
class SlidesController(private val slideShare: SlideShare) {
    @GetMapping("/{id}")
    fun get(
        @PathVariable id: String,
        @RequestParam(defaultValue = "false") detailed: Boolean,
        @RequestParam(defaultValue = "false") excludeTags: Boolean
    ): Slideshow {
        val options = GetSlideshowOptions(
            excludeTags = excludeTags,
            detailed = detailed
        )
        return slideShare.slideshowOperations()
            .getSlideshowById(id = id, options = options)
    }

    @GetMapping("/search")
    fun search(
        @RequestParam q: String,
        @RequestParam(defaultValue = "1") page: Int,
        @RequestParam(defaultValue = "10") perPage: Int,
        @RequestParam(defaultValue = "ALL") language: SearchOptions.Language,
        @RequestParam(defaultValue = "RELEVANCE") sort: SearchOptions.Sort,
        @RequestParam(defaultValue = "ANY") uploadDate: SearchOptions.UploadDate,
        @RequestParam(defaultValue = "ALL") fileType: SearchOptions.FileType,
        @RequestParam(defaultValue = "TEXT") searchType: SearchOptions.SearchType,
        @RequestParam(defaultValue = "false") detailed: Boolean
    ): SearchResults {
        val options = SearchOptions(
            page = page,
            perPage = perPage,
            language = language,
            sort = sort,
            uploadDate = uploadDate,
            fileType = fileType,
            what = searchType,
            detailed = detailed
        )
        return slideShare.slideshowOperations()
            .searchSlideshows(q, options)
    }

    @GetMapping("/tags/{tag}/slides")
    fun getByTag(
        @PathVariable tag: String,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
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

    @GetMapping("/users/{user}/slides")
    fun getByUser(
        @PathVariable user: String,
        @RequestParam(required = false, defaultValue = "0") offset: Int,
        @RequestParam(required = false, defaultValue = "10") limit: Int,
        @RequestParam(defaultValue = "false") detailed: Boolean,
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
}
