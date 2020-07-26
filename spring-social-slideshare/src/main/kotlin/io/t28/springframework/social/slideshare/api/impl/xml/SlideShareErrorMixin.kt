package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import io.t28.springframework.social.slideshare.api.SlideShareError

/**
 * Annotated mixin to add annotations to [SlideShareError]
 */
@JsonIgnoreProperties(ignoreUnknown = true)
interface SlideShareErrorMixin {
    @get:JacksonXmlProperty(localName = "Message")
    val message: MessageMixin

    /**
     * Annotated mixin to add annotations to [SlideShareError.Message]
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    interface MessageMixin {
        @get:JacksonXmlProperty(localName = "ID", isAttribute = true)
        @get:JsonDeserialize(using = ErrorTypeDeserializer::class)
        val id: SlideShareError.ErrorType

        @get:JacksonXmlProperty(localName = "InnerText")
        val text: String
    }
}
