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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.SearchResults
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Annotated mixin to add annotations to [SearchResults]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface SearchResultsMixin {
    @get:JacksonXmlProperty(localName = "Meta")
    val meta: SearchResults.Meta

    @get:JacksonXmlElementWrapper(useWrapping = false)
    @get:JacksonXmlProperty(localName = "Slideshow")
    val slideshows: List<Slideshow>

    /**
     * Annotated mixin to add annotations to [SearchResults.Meta]
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    interface MetaMixin {
        @get:JacksonXmlProperty(localName = "Query")
        val query: String

        @get:JacksonXmlProperty(localName = "ResultOffset")
        val resultOffset: Int?

        @get:JacksonXmlProperty(localName = "NumResults")
        val numResults: Int

        @get:JacksonXmlProperty(localName = "TotalResults")
        val totalResults: Int
    }
}
