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

import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.readValue
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class NumericBooleanDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMapperHolder.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @Test
    fun `should deserialize 0 to false`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Supported>0</Supported>
            </Root>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.supported, false)
    }

    @Test
    fun `should deserialize 1 to true`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Supported>1</Supported>
            </Root>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.supported, true)
    }

    @Test
    fun `should throw Exception if value is neither 0 nor 1`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Supported>2</Supported>
            </Root>
            """.trimIndent()

        // Assert
        assertThrows<JsonMappingException> {
            // Act
            mapper.readValue<Xml>(content)
        }
    }

    @Test
    fun `should throw Exception if value is missing`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Supported></Supported>
            </Root>
            """.trimIndent()

        // Assert
        assertThrows<JsonMappingException> {
            // Act
            mapper.readValue<Xml>(content)
        }
    }

    data class Xml(val supported: Boolean)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Supported")
        @get:JsonDeserialize(using = NumericBooleanDeserializer::class)
        val supported: Boolean
    }
}
