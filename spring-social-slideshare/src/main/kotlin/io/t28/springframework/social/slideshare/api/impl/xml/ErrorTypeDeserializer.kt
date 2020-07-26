package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.SlideShareError.ErrorType

/**
 * Deserializer to convert error ID to [ErrorType]
 */
object ErrorTypeDeserializer : JsonDeserializer<ErrorType>() {
    override fun deserialize(parser: JsonParser, context: DeserializationContext): ErrorType {
        if (parser.currentToken != JsonToken.VALUE_STRING) {
            throw context.wrongTokenException(parser, String::class.java, JsonToken.VALUE_STRING, "ID")
        }

        val value = parser.getValueAsInt(ErrorType.UNKNOWN_ERROR.code)
        return ErrorType.values().firstOrNull { type ->
            value == type.code
        } ?: ErrorType.UNKNOWN_ERROR
    }
}
