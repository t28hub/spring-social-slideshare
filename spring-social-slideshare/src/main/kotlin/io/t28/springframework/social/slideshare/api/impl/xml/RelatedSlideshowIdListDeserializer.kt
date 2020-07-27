/*
 * Copyright 2020 Tatsuya Maki
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import io.t28.springframework.social.slideshare.api.Slideshow

/**
 * Deserializer to convert empty related slideshow ID list.
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
