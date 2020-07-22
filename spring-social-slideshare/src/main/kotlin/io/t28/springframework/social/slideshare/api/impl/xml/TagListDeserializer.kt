package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Deserializer to convert empty tag list
 */
internal object TagListDeserializer : JsonDeserializer<List<Slideshow.Tag>>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): List<Slideshow.Tag> {
        if (parser.currentToken == VALUE_STRING) {
            return getNullValue(context)
        }
        return parser.readValueAs(object : TypeReference<List<Slideshow.Tag>>() {})
    }

    override fun getNullValue(context: DeserializationContext): List<Slideshow.Tag> {
        return emptyList()
    }
}
