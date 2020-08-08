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
import com.google.common.truth.Subject
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
import io.t28.auto.truth.AutoSubject
import io.t28.springframework.social.slideshare.api.Contact

fun assertThat(actual: Contact): ContactSubject {
    return assertAbout(ContactSubject.FACTORY).that(actual)
}

/**
 * [Subject] for making assertions about [Contact].
 */
@AutoSubject(Contact::class)
class ContactSubject(
    metadata: FailureMetadata,
    actual: Contact
) : AutoContactSubject(metadata, actual) {
    companion object {
        val FACTORY = Factory<ContactSubject, Contact> { metadata, actual ->
            ContactSubject(metadata, actual)
        }
    }
}
