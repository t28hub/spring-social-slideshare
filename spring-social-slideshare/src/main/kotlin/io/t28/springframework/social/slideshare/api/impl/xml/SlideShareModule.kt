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
package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.databind.Module
import com.fasterxml.jackson.databind.module.SimpleModule
import io.t28.springframework.social.slideshare.api.Contact
import io.t28.springframework.social.slideshare.api.Favorite
import io.t28.springframework.social.slideshare.api.FavoriteResults
import io.t28.springframework.social.slideshare.api.FavoriteState
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.SlideShareError
import io.t28.springframework.social.slideshare.api.Slideshow
import io.t28.springframework.social.slideshare.api.Slideshows
import io.t28.springframework.social.slideshare.api.Tag
import io.t28.springframework.social.slideshare.api.UpdateResults

/**
 * Jackson module for setting up mixin annotations on SlideShare data classes.
 */
class SlideShareModule : SimpleModule("SlideShareModule") {
    override fun setupModule(context: SetupContext) {
        with(context) {
            // mixin annotations for [SlideshowOperations]
            setMixInAnnotations<Slideshow, SlideshowMixin>()
            setMixInAnnotations<Slideshow.Tag, SlideshowMixin.TagMixin>()
            setMixInAnnotations<Slideshow.RelatedSlideshowId, SlideshowMixin.RelatedSlideshowIdMixin>()
            setMixInAnnotations<Slideshows, SlideshowsMixin>()
            setMixInAnnotations<SearchResults, SearchResultsMixin>()
            setMixInAnnotations<SearchResults.Meta, SearchResultsMixin.MetaMixin>()
            setMixInAnnotations<UpdateResults, UpdateResultsMixin>()

            // mixin annotations for [UserOperations]
            setMixInAnnotations<Contact, ContactMixin>()
            setMixInAnnotations<Favorite, FavoriteMixin>()
            setMixInAnnotations<Tag, TagMixin>()

            // mixin annotations for [FavoriteOperation]
            setMixInAnnotations<FavoriteResults, FavoriteResultsMixin>()
            setMixInAnnotations<FavoriteState, FavoriteStateMixin>()

            // mixin annotations for API errors
            setMixInAnnotations<SlideShareError, SlideShareErrorMixin>()
            setMixInAnnotations<SlideShareError.Message, SlideShareErrorMixin.MessageMixin>()
        }
    }

    companion object {
        /**
         * Extension function for [Module.SetupContext.setMixInAnnotations].
         *
         * @param TARGET the type of target class or interface.
         * @param MIXIN the type of mixin class or interface.
         */
        internal inline fun <reified TARGET, reified MIXIN> SetupContext.setMixInAnnotations() {
            setMixInAnnotations(TARGET::class.java, MIXIN::class.java)
        }
    }
}
