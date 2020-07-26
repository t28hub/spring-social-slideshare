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

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.readValue
import io.t28.springframework.social.slideshare.api.Slideshow
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RelatedSlideshowIdListDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMapperHolder.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @Test
    fun `should deserialize non-empty RelatedSlideshows`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Xml>
              <RelatedSlideshows>
                <RelatedSlideshowID rank="1">100000</RelatedSlideshowID>
                <RelatedSlideshowID rank="2">100001</RelatedSlideshowID>
                <RelatedSlideshowID rank="3">100002</RelatedSlideshowID>
              </RelatedSlideshows>
            </Xml>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.relatedSlideshows.size, 3)
        assertEquals(deserialized.relatedSlideshows[0].id, "100000")
        assertEquals(deserialized.relatedSlideshows[1].id, "100001")
        assertEquals(deserialized.relatedSlideshows[2].id, "100002")
    }

    @Test
    fun `should deserialize empty RelatedSlideshows`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Xml>
              <RelatedSlideshows> </RelatedSlideshows>
            </Xml>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertTrue(deserialized.relatedSlideshows.isEmpty())
    }

    @Test
    fun `should deserialize to empty list if RelatedSlideshows is missing`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Xml>
            </Xml>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertTrue(deserialized.relatedSlideshows.isEmpty())
    }

    data class Xml(var relatedSlideshows: List<Slideshow.RelatedSlideshowId> = emptyList())

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "RelatedSlideshows")
        @get:JsonDeserialize(using = RelatedSlideshowIdListDeserializer::class)
        val relatedSlideshows: List<Slideshow.RelatedSlideshowId>
    }
}
