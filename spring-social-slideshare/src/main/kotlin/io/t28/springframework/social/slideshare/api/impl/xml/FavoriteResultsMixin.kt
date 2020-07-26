package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.FavoriteResults

/**
 * Annotated mixin to add annotations to [FavoriteResults]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface FavoriteResultsMixin {
    @get:JacksonXmlProperty(localName = "SlideshowID")
    val id: String
}
