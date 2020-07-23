package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.UpdateResults

/**
 * Annotated mixin to add annotations to [UpdateResults]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface UpdateResultsMixin {
    @get:JacksonXmlProperty(localName = "SlideShowID")
    val id: String
}
