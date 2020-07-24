package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.t28.springframework.social.slideshare.api.Favorite

/**
 * Annotated mixin to add annotations to [Favorite]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "favorite")
interface FavoriteMixin {
    @get:JacksonXmlProperty(localName = "slideshow_id")
    val slideshowId: String

    @get:JacksonXmlProperty(localName = "tag_text")
    @get:JsonDeserialize(using = TagTextDeserializer::class)
    val tags: List<String>
}
