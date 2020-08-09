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

package io.t28.springframework.social.entity

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Slide update request")
data class UpdateSlideRequest(
    @ApiModelProperty("New title of the slide")
    val title: String? = null,
    @ApiModelProperty("New description of the slide")
    val description: String? = null,
    @ApiModelProperty("New tags of the slide")
    val tags: List<String>? = null,
    @ApiModelProperty("Whether to set private or not")
    val private: Boolean? = null,
    @ApiModelProperty("Whether to generate a secret URL")
    val generateSecretUrl: Boolean? = null,
    @ApiModelProperty("Whether to allow embed")
    val allowEmbed: Boolean? = null,
    @ApiModelProperty("Whether to share the slide to contacts if private")
    val shareWithContacts: Boolean? = null
)
