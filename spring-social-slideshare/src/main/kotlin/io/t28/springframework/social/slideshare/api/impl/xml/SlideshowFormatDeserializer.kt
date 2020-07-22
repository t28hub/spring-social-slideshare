package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Deserializer to convert string representation of format
 */
internal object SlideshowFormatDeserializer : JsonDeserializer<Slideshow.Format>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Slideshow.Format {
        if (parser.currentToken != VALUE_STRING) {
            throw JsonParseException(parser, "Expected token to be $VALUE_STRING, but was ${parser.currentToken}")
        }

        val value = parser.valueAsString
        return Slideshow.Format.values().firstOrNull { format ->
            format.name.equals(value, true)
        } ?: Slideshow.Format.UNKNOWN
    }
}
