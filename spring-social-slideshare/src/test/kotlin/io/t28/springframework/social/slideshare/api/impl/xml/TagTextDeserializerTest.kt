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
import com.google.common.truth.Truth.assertThat
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagTextDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMapperHolder.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @Test
    fun `should split text to list`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Tags>Java,Groovy,Scala,Kotlin</Tags>
            </Root>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).hasSize(4)
        assertThat(deserialized.tags[0]).isEqualTo("Java")
        assertThat(deserialized.tags[1]).isEqualTo("Groovy")
        assertThat(deserialized.tags[2]).isEqualTo("Scala")
        assertThat(deserialized.tags[3]).isEqualTo("Kotlin")
    }

    @Test
    fun `should return empty list if value is empty`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
              <Tags></Tags>
            </Root>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).isEmpty()
    }

    @Test
    fun `should return empty list if tag is missing`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Root>
            </Root>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).isEmpty()
    }

    data class Xml(val tags: List<String>)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Tags")
        @get:JsonDeserialize(using = TagTextDeserializer::class)
        val tags: List<String>
    }
}
