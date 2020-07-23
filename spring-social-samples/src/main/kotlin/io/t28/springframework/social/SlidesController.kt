package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.GetSlideshowOptions
import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.Slideshow
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
}
