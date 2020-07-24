package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.t28.springframework.social.slideshare.api.Contact

/**
 * Annotated mixin to add annotations to [Contact]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Contact")
interface ContactMixin {
    @get:JacksonXmlProperty(localName = "Username")
    val username: String

    @get:JacksonXmlProperty(localName = "NumSlideshows")
    val numSlideshows: Int

    @get:JacksonXmlProperty(localName = "NumComments")
    val numComments: Int
}
