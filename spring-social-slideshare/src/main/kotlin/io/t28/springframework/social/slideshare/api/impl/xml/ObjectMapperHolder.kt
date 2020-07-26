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

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlFactory
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

/**
 * Instance holder to use single instance of [ObjectMapper]
 */
object ObjectMapperHolder {
    // https://github.com/FasterXML/jackson-module-kotlin/issues/138#issuecomment-405087827
    private val xmlMapper = Jackson2ObjectMapperBuilder()
        .createXmlMapper(true)
        .factory(XmlFactory.builder().nameForTextElement("InnerText").build())
        .modules(KotlinModule(), SlideShareModule())
        .simpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        .build<XmlMapper>()

    /**
     * Retrieve an instance of [XmlMapper]
     *
     * @return [XmlMapper]
     */
    fun xmlMapper(): XmlMapper {
        return xmlMapper
    }
}
