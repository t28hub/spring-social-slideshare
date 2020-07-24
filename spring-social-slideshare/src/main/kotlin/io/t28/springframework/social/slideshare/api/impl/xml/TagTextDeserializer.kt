package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken.VALUE_STRING
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Deserializer to split tag text
 */
internal object TagTextDeserializer : JsonDeserializer<List<String>>() {
    private const val DELIMITER = ","

    override fun deserialize(parser: JsonParser, context: DeserializationContext): List<String> {
        if (parser.currentToken != VALUE_STRING) {
            return getNullValue(context)
        }

        val text = parser.valueAsString
        return text.split(DELIMITER).filter { tag ->
            // The split method will return a List containing an empty string if the text is empty.
            tag.isNotEmpty()
        }
    }

    override fun getNullValue(context: DeserializationContext): List<String> {
        return emptyList()
    }
}
