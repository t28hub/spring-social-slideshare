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
import com.google.common.truth.StringSubject
import com.google.common.truth.Subject.Factory
import com.google.common.truth.Truth.assertAbout
import io.t28.auto.truth.AutoSubject
import io.t28.springframework.social.slideshare.api.Slideshow

fun assertThat(actual: Slideshow): SlideshowSubject {
    return assertAbout(SlideshowSubject.FACTORY).that(actual)
}

/**
 * Truth subject for [Slideshow].
 */
@Suppress("unused")
@AutoSubject(Slideshow::class)
class SlideshowSubject(
    metadata: FailureMetadata,
    private val actual: Slideshow
) : AutoSlideshowSubject(metadata, actual) {
    fun description(): StringSubject {
        return check("description").that(actual.description)
    }

    fun embed(): StringSubject {
        return check("embed").that(actual.embed)
    }

    fun status(): AutoStatusSubject {
        return check("status").about(StatusSubject.FACTORY).that(actual.status)
    }

    fun format(): AutoFormatSubject {
        return check("format").about(FormatSubject.FACTORY).that(actual.format)
    }

    fun download(): AutoDownloadSubject {
        return check("download").about(DownloadSubject.FACTORY).that(actual.download)
    }

    companion object {
        val FACTORY = Factory<SlideshowSubject, Slideshow> { metadata, actual ->
            SlideshowSubject(metadata, actual)
        }
    }

    /**
     * Truth subject for [Slideshow.Status].
     */
    @AutoSubject(Slideshow.Status::class)
    object StatusSubject {
        val FACTORY = Factory<AutoStatusSubject, Slideshow.Status> { metadata, actual ->
            AutoStatusSubject(metadata, actual)
        }
    }

    /**
     * Truth subject for [Slideshow.Format].
     */
    @AutoSubject(Slideshow.Format::class)
    object FormatSubject {
        val FACTORY = Factory<AutoFormatSubject, Slideshow.Format> { metadata, actual ->
            AutoFormatSubject(metadata, actual)
        }
    }

    /**
     * Truth subject for [Slideshow.Download].
     */
    @AutoSubject(Slideshow.Download::class)
    object DownloadSubject {
        val FACTORY = Factory<AutoDownloadSubject, Slideshow.Download> { metadata, actual ->
            AutoDownloadSubject(metadata, actual)
        }
    }
}
