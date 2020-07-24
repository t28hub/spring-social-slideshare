package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.databind.module.SimpleModule
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.Slideshows
import io.t28.springframework.social.slideshare.api.UpdateResults

/**
 * Jackson module for setting up mixin annotations on SlideShare data classes.
 */
class SlideShareModule : SimpleModule("SlideShareModule") {
    override fun setupModule(context: SetupContext) {
        with(context) {
            setMixInAnnotations<Slideshow, SlideshowMixin>()
            setMixInAnnotations<Slideshow.Tag, SlideshowMixin.TagMixin>()
            setMixInAnnotations<Slideshow.RelatedSlideshowId, SlideshowMixin.RelatedSlideshowIdMixin>()
            setMixInAnnotations<Slideshows, SlideshowsMixin>()
            setMixInAnnotations<SearchResults, SearchResultsMixin>()
            setMixInAnnotations<SearchResults.Meta, SearchResultsMixin.MetaMixin>()
            setMixInAnnotations<UpdateResults, UpdateResultsMixin>()
            setMixInAnnotations<Favorite, FavoriteMixin>()
        }
    }

    companion object {
        /**
         * Extension function for [SetupContext.setMixInAnnotations].
         *
         * @param TARGET the type of target class or interface
         * @param MIXIN the type of mixin class or interface
         */
        internal inline fun <reified TARGET, reified MIXIN> SetupContext.setMixInAnnotations() {
            setMixInAnnotations(TARGET::class.java, MIXIN::class.java)
        }
    }
}
