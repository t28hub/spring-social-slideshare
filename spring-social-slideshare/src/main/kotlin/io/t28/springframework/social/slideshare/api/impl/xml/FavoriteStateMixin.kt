package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.FavoriteState

/**
 * Annotated mixin to add annotations to [FavoriteState]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface FavoriteStateMixin {
    @get:JacksonXmlProperty(localName = "SlideshowID")
    val id: String

    @get:JacksonXmlProperty(localName = "User")
    val user: String

    @get:JacksonXmlProperty(localName = "Favorited")
    val favorited: Boolean
}
