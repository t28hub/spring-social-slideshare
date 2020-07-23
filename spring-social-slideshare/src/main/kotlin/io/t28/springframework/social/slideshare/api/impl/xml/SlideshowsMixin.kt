package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.Slideshow

@JsonIgnoreProperties(ignoreUnknown = true)
interface SlideshowsMixin {
    @get:JacksonXmlProperty(localName = "Name")
    val name: String

    @get:JacksonXmlProperty(localName = "Count")
    val count: Int

    @get:JacksonXmlElementWrapper(useWrapping = false)
    @get:JacksonXmlProperty(localName = "Slideshow")
    val slideshows: List<Slideshow>
}
