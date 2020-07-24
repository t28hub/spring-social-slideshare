package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import io.t28.springframework.social.slideshare.api.Tag

/**
 * Annotated mixin to add annotations to [Tag]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "Tag")
interface TagMixin {
    @get:JacksonXmlProperty(localName = "Count", isAttribute = true)
    val count: Int

    @get:JacksonXmlProperty(localName = "InnerText")
    val name: String
}
