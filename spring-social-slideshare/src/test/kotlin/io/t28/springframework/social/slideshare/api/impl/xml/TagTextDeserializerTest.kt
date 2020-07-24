package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.module.kotlin.readValue
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class TagTextDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMappers.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @Test
    fun `should split text to list`() {
        // Arrange
        @Language("xml")
        val content = """
            <Root>
              <Tags>Java,Groovy,Scala,Kotlin</Tags>
            </Root>
        """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.tags.size, 4)
        assertEquals(deserialized.tags[0], "Java")
        assertEquals(deserialized.tags[1], "Groovy")
        assertEquals(deserialized.tags[2], "Scala")
        assertEquals(deserialized.tags[3], "Kotlin")
    }

    @Test
    fun `should return empty list if value is empty`() {
        // Arrange
        @Language("xml")
        val content = """
            <Root>
              <Tags></Tags>
            </Root>
        """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.tags.size, 0)
    }

    @Test
    fun `should return empty list if tag is missing`() {
        // Arrange
        @Language("xml")
        val content = """
            <Root>
            </Root>
        """.trimIndent()

        // Act
        val deserialized = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(deserialized.tags.size, 0)
    }

    data class Xml(val tags: List<String>)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Tags")
        @get:JsonDeserialize(using = TagTextDeserializer::class)
        val tags: List<String>
    }
}
