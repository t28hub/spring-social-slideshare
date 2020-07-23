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
