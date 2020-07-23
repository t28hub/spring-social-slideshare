package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.SlideShare
import io.t28.springframework.social.slideshare.api.impl.Credentials
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
        val credentials = if (properties.username.isNullOrEmpty() or properties.password.isNullOrEmpty()) {
            null
        } else {
            Credentials(username = properties.username!!, password = properties.password!!)
        }
        return SlideShareTemplate(apiKey = properties.apiKey, sharedSecret = properties.sharedSecret, credentials = credentials)
    }
}
