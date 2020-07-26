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
package io.t28.springframework.social

import io.t28.springframework.social.slideshare.api.SlideShare
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/favorites", produces = [APPLICATION_JSON_VALUE])
class FavoritesController(private val slideShare: SlideShare) {
    @PutMapping("/{id}")
    fun favorite(@PathVariable id: String): ResponseEntity<Void> {
        slideShare.favoriteOperations().addFavorite(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}")
    fun checkFavorite(@PathVariable id: String): ResponseEntity<Void> {
        val state = slideShare.favoriteOperations().checkFavorite(id)
        return if (state.favorited) {
            ResponseEntity.ok().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
