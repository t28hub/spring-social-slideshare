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

import com.google.common.truth.Truth.assertThat
import io.t28.springframework.social.slideshare.api.SlideShare
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.getBean
import org.springframework.boot.context.properties.source.ConfigurationPropertySources
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.social.UserIdSource
import org.springframework.social.autoconfigure.SocialWebAutoConfiguration
import org.springframework.social.connect.ConnectionFactoryLocator
import org.springframework.social.connect.ConnectionRepository
import org.springframework.social.connect.UsersConnectionRepository
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext

@Suppress("CAST_NEVER_SUCCEEDS")
internal class SlideShareAutoConfigurationTest {
    private lateinit var context: AnnotationConfigWebApplicationContext

    @AfterEach
    fun `tear down`() {
        if (::context.isInitialized) {
            context.close()
        }
    }

    @Test
    fun `should register social beans`() {
        // Arrange & Act
        context = AnnotationConfigWebApplicationContext().apply {
            TestPropertyValues.of(
                "spring.social.slideshare.apiKey:TEST_API_KEY",
                "spring.social.slideshare.sharedSecret:TEST_SHARED_SECRET"
            ).applyTo(this)
            ConfigurationPropertySources.attach(environment)
            register<SlideShareAutoConfiguration>()
            register<SocialWebAutoConfiguration>()
            refresh()
        }

        // Assert
        with(context) {
            assertThat(getBean<UsersConnectionRepository>()).isNotNull()
            assertThat(getBean<ConnectionRepository>()).isNotNull()
            assertThat(getBean<ConnectionFactoryLocator>()).isNotNull()
            assertThat(getBean<UserIdSource>()).isNotNull()
            assertThat(getBean<SlideShare>()).isNotNull()
        }
    }

    @Test
    fun `should register social beans if username and password are set`() {
        // Arrange & Act
        context = AnnotationConfigWebApplicationContext().apply {
            TestPropertyValues.of(
                "spring.social.slideshare.apiKey:TEST_API_KEY",
                "spring.social.slideshare.sharedSecret:TEST_SHARED_SECRET",
                "spring.social.slideshare.username:TEST_USERNAME",
                "spring.social.slideshare.password:TEST_PASSWORD"
            ).applyTo(this)
            ConfigurationPropertySources.attach(environment)
            register<SlideShareAutoConfiguration>()
            register<SocialWebAutoConfiguration>()
            refresh()
        }

        // Assert
        with(context) {
            assertThat(getBean<UsersConnectionRepository>()).isNotNull()
            assertThat(getBean<ConnectionRepository>()).isNotNull()
            assertThat(getBean<ConnectionFactoryLocator>()).isNotNull()
            assertThat(getBean<UserIdSource>()).isNotNull()
        }
    }

    @Test
    fun `should not register social beans if properties are not set`() {
        // Arrange & Act
        context = AnnotationConfigWebApplicationContext().apply {
            register<SlideShareAutoConfiguration>()
            register<SocialWebAutoConfiguration>()
            refresh()
        }

        // Assert
        val exception = assertThrows<NoSuchBeanDefinitionException> {
            context.getBean<SlideShare>()
        }
        assertThat(exception).hasMessageThat().contains("'io.t28.springframework.social.slideshare.api.SlideShare'")
    }

    private inline fun <reified T : Any> AnnotationConfigWebApplicationContext.register() {
        register(T::class.java)
    }
}
