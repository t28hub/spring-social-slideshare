package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.readValue
import io.t28.springframework.social.slideshare.api.Slideshow
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagListDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMappers.xmlMapper())
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
        assertEquals(deserialized.tags.size, 3)
        assertEquals(deserialized.tags[0].name, "jvm")
        assertEquals(deserialized.tags[1].name, "java")
        assertEquals(deserialized.tags[2].name, "kotlin")
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
        assertEquals(deserialized.tags.size, 0)
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
        assertEquals(deserialized.tags.size, 0)
    }

    data class Xml(val tags: List<Slideshow.Tag>)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Tags")
        @get:JsonDeserialize(using = TagListDeserializer::class)
        val tags: List<Slideshow.Tag>
    }
}
