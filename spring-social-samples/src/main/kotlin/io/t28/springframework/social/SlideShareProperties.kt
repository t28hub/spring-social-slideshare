package io.t28.springframework.social

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "spring.social.slideshare")
data class SlideShareProperties(
    val apiKey: String,
    val sharedSecret: String
)
