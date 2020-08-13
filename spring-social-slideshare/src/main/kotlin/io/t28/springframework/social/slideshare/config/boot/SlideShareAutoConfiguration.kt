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
package io.t28.springframework.social.slideshare.config.boot

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import io.t28.springframework.social.slideshare.connect.SlideShareConnectionFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.autoconfigure.AutoConfigureBefore
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import org.springframework.context.annotation.ScopedProxyMode
import org.springframework.social.autoconfigure.SocialAutoConfigurerAdapter
import org.springframework.social.autoconfigure.SocialWebAutoConfiguration
import org.springframework.social.config.annotation.EnableSocial
import org.springframework.social.config.annotation.SocialConfigurerAdapter
import org.springframework.social.connect.ConnectionFactory
import org.springframework.social.connect.ConnectionRepository
import org.springframework.web.context.WebApplicationContext

/**
 * Auto configuration for SlideShare
 */
@Configuration
@ConditionalOnClass(SocialConfigurerAdapter::class, SlideShareConnectionFactory::class)
@ConditionalOnProperty(prefix = "spring.social.slideshare", name = ["apikey"])
@AutoConfigureBefore(SocialWebAutoConfiguration::class)
@AutoConfigureAfter(WebMvcAutoConfiguration::class)
class SlideShareAutoConfiguration {
    /**
     * [SocialConfigurerAdapter] implementation for SlideShare
     *
     * @param properties The properties for SlideShare.
     */
    @Configuration
    @EnableSocial
    @EnableConfigurationProperties(SlideShareProperties::class)
    @ConditionalOnWebApplication
    class SlideShareConfigurerAdapter(
        @Autowired private val properties: SlideShareProperties
    ) : SocialAutoConfigurerAdapter() {
        @Bean
        @Scope(scopeName = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.INTERFACES)
        @ConditionalOnMissingBean
        fun slideShare(repository: ConnectionRepository): SlideShare? {
            val connection = repository.findPrimaryConnection(SlideShare::class.java)
            return connection?.api
                ?: SlideShareTemplate(properties.apiKey, properties.sharedSecret, properties.credentials)
        }

        override fun createConnectionFactory(): ConnectionFactory<*> {
            return SlideShareConnectionFactory(
                apiKey = properties.apiKey,
                sharedSecret = properties.sharedSecret,
                credentials = properties.credentials
            )
        }
    }
}
