package io.t28.springframework.social.slideshare.api.impl.xml

import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

object ObjectMappers {
    // https://github.com/FasterXML/jackson-module-kotlin/issues/138#issuecomment-405087827
    private val xmlMapper = Jackson2ObjectMapperBuilder()
        .createXmlMapper(true)
        .factory(XmlFactory.builder().nameForTextElement("InnerText").build())
        .modules(KotlinModule(), SlideShareModule())
        .simpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        .build<XmlMapper>()

    fun xmlMapper(): XmlMapper {
        return xmlMapper
    }
}

