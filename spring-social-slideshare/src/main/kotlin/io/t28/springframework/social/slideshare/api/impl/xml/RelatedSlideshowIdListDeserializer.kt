package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Deserializer to convert empty related slideshow ID list
 */
internal object RelatedSlideshowIdListDeserializer : JsonDeserializer<List<Slideshow.RelatedSlideshowId>>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): List<Slideshow.RelatedSlideshowId> {
        if (parser.currentToken == JsonToken.VALUE_STRING) {
            return getNullValue(context)
        }
        return parser.readValueAs(object : TypeReference<List<Slideshow.RelatedSlideshowId>>() {})
    }

    override fun getNullValue(context: DeserializationContext): List<Slideshow.RelatedSlideshowId> {
        return emptyList()
    }
}
