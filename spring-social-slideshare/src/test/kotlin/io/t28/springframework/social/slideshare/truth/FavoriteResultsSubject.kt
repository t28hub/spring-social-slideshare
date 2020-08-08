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
package io.t28.springframework.social.slideshare.truth

import com.google.common.truth.FailureMetadata
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
import io.t28.auto.truth.AutoSubject
import io.t28.springframework.social.slideshare.api.FavoriteResults

fun assertThat(actual: FavoriteResults): FavoriteResultsSubject {
    return assertAbout(FavoriteResultsSubject.FACTORY).that(actual)
}

/**
 * Truth subject for [FavoriteResults]
 */
@AutoSubject(FavoriteResults::class)
class FavoriteResultsSubject(
    metadata: FailureMetadata,
    actual: FavoriteResults
) : AutoFavoriteResultsSubject(metadata, actual) {
    companion object {
        val FACTORY = Factory<FavoriteResultsSubject, FavoriteResults> { metadata, actual ->
            FavoriteResultsSubject(metadata, actual)
        }
    }
}
