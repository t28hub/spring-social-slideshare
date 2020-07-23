package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.SlideShareTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(SlideShareProperties::class)
class SlideShareConfiguration(
    @Autowired private val properties: SlideShareProperties
) {
    @Bean
    fun slideShare(): SlideShare {
        return SlideShareTemplate(apiKey = properties.apiKey, sharedSecret = properties.sharedSecret)
    }
}
