package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

/**
 * Deserializer to convert numeric type to boolean type
 */
internal object NumericBooleanDeserializer : JsonDeserializer<Boolean>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): Boolean {
        return when (parser.valueAsString) {
            "0" -> false
            "1" -> true
            else -> throw JsonParseException(parser, "'${parser.valueAsString}' cannot deserialize as Boolean")
        }
    }
}
