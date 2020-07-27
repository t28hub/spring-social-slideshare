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
package io.t28.springframework.social.slideshare.api

/**
 * Optional parameters for editing slideshows.
 *
 * - [SlideshowOperations.editSlideshow]
 *
 * @param title The slideshow title.
 * @param description The slideshow description.
 * @param tags The slideshow tags.
 * @param private Whether or not to set slideshow private.
 * @param generateSecretUrl Whether or not to generate a secret URL.
 * @param allowEmbed Whether or not to allow embedding the slideshow.
 * @param shareWithContacts Whether or not the contacts on SlideShare can view the slideshow.
 */
data class EditSlideshowOptions(
    val title: String? = null,
    val description: String? = null,
    val tags: List<String>? = null,
    val private: Boolean? = null,
    val generateSecretUrl: Boolean? = null,
    val allowEmbed: Boolean? = null,
    val shareWithContacts: Boolean? = null
)
