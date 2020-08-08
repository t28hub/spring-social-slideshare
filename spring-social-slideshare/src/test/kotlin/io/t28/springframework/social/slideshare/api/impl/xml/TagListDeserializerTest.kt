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
import io.t28.springframework.social.slideshare.api.Slideshow
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagListDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMapperHolder.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @Test
    fun `should deserialize non-empty Tags`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Xml>
              <Tags>
                <Tag Count="1" Owner="1">jvm</Tag>
                <Tag Count="2" Owner="1">java</Tag>
                <Tag Count="4" Owner="0">kotlin</Tag>
              </Tags>
            </Xml>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).hasSize(3)
        assertThat(deserialized.tags[0].name).isEqualTo("jvm")
        assertThat(deserialized.tags[1].name).isEqualTo("java")
        assertThat(deserialized.tags[2].name).isEqualTo("kotlin")
    }

    @Test
    fun `should deserialize empty Tags`() {
        // Arrange
        @Language("xml")
        val content =
            """
            <Xml>
              <Tags> </Tags>
            </Xml>
            """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).isEmpty()
    }

    @Test
    fun `should deserialize to empty list if Tags is missing`() {
        // Act
        @Language("xml")
        val content =
            """
            <Xml>
            </Xml>
            """.trimIndent()
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertThat(deserialized.tags).isEmpty()
    }

    data class Xml(val tags: List<Slideshow.Tag>)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Tags")
        @get:JsonDeserialize(using = TagListDeserializer::class)
        val tags: List<Slideshow.Tag>
    }
}
