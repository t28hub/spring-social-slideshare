package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.databind.JsonMappingException
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
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class SlideshowFormatDeserializerTest {
    private lateinit var mapper: XmlMapper

    @BeforeAll
    fun setup() {
        mapper = XmlMapper.Builder(ObjectMappers.xmlMapper())
            .addMixIn(Xml::class.java, XmlMixin::class.java)
            .build()
    }

    @ParameterizedTest(name = "should convert to {1} when content is {0}")
    @MethodSource("provideFormat")
    fun `should convert string representation to enum`(string: String, expected: Slideshow.Format) {
        // Arrange
        @Language("xml")
        val content = """
            <Root>
              <Format>$string</Format>
            </Root>
        """.trimIndent()

        // Act
        val parsed = mapper.readValue<Xml>(content)

        // Assert
        assertEquals(parsed.format, expected)
    }

    @Test
    fun `should throw exception if value is not string`() {
        // Arrange
        @Language("xml")
        val content = """
            <Root>
              <Format>
                <Value>pptx</Value>
              </Format>
            </Root>
        """.trimIndent()

        // Assert
        assertThrows<JsonMappingException> {
            // Act
            mapper.readValue<Xml>(content)
        }
    }

    @Suppress("unused")
    companion object {
        @JvmStatic
        fun provideFormat(): Stream<Arguments> {
            return Stream.of(
                arguments("pptx", Slideshow.Format.PPTX),
                arguments("ppt", Slideshow.Format.PPT),
                arguments("pdf", Slideshow.Format.PDF),
                arguments("odp", Slideshow.Format.ODP),
                arguments("doc", Slideshow.Format.DOC),
                arguments("pot", Slideshow.Format.POT),
                arguments("txt", Slideshow.Format.TXT),
                arguments("rdf", Slideshow.Format.RDF),
                arguments("key", Slideshow.Format.UNKNOWN),
                arguments("", Slideshow.Format.UNKNOWN)
            )
        }
    }

    data class Xml(val format: Slideshow.Format)

    @Suppress("unused")
    interface XmlMixin {
        @get:JacksonXmlProperty(localName = "Format")
        @get:JsonDeserialize(using = SlideshowFormatDeserializer::class)
        val format: Slideshow.Format
    }
}
