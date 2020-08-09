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
package io.t28.springframework.social.config

import io.t28.springframework.social.controller.FavoritesController
import io.t28.springframework.social.controller.SearchController
import io.t28.springframework.social.controller.SlidesController
import io.t28.springframework.social.controller.UsersController
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors.any
import springfox.documentation.builders.RequestHandlerSelectors.withClassAnnotation
import springfox.documentation.spi.DocumentationType.SWAGGER_2
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger.web.DocExpansion
import springfox.documentation.swagger.web.ModelRendering
import springfox.documentation.swagger.web.OperationsSorter
import springfox.documentation.swagger.web.TagsSorter
import springfox.documentation.swagger.web.UiConfiguration
import springfox.documentation.swagger.web.UiConfigurationBuilder
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
@ComponentScan(
    basePackageClasses = [
        SlidesController::class,
        SearchController::class,
        UsersController::class,
        FavoritesController::class
    ]
)
class SwaggerConfiguration {
    @Bean
    fun api(): Docket {
        return Docket(SWAGGER_2)
            .consumes(setOf(APPLICATION_JSON_VALUE))
            .produces(setOf(APPLICATION_JSON_VALUE))
            .apiInfo(
                ApiInfoBuilder().apply {
                    title("Slideshare Demo API")
                    description("Demo REST API using spring-social-slideshare")
                    version("v1")
                }.build()
            )
            .select()
            .apis(withClassAnnotation(RestController::class.java))
            .paths(any())
            .build()
    }

    @Bean
    fun uiConfig(): UiConfiguration {
        return UiConfigurationBuilder.builder()
            .deepLinking(true)
            .displayOperationId(false)
            .defaultModelsExpandDepth(1)
            .defaultModelExpandDepth(1)
            .defaultModelRendering(ModelRendering.EXAMPLE)
            .displayRequestDuration(false)
            .docExpansion(DocExpansion.NONE)
            .filter(false)
            .maxDisplayedTags(null)
            .operationsSorter(OperationsSorter.ALPHA)
            .showExtensions(false)
            .showCommonExtensions(false)
            .tagsSorter(TagsSorter.ALPHA)
            .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
            .validatorUrl(null)
            .build()
    }
}
