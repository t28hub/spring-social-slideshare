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
package io.t28.springframework.social.slideshare.api

/**
 * SlideShare API Error.
 *
 * - [API Error Codes](https://www.slideshare.net/developers/documentation#error_codes)
 *
 * @param message The error message.
 */
class SlideShareError(val message: Message) {
    /**
     * API error message.
     *
     * @param id The error type.
     * @param text The error message text.
     */
    data class Message(val id: ErrorType, val text: String)

    /**
     * The type of API error.
     *
     * @param code The error code.
     */
    enum class ErrorType(val code: Int) {
        UNKNOWN_ERROR(-1),
        NO_API_KEY_PROVIDED(0),
        FAILED_API_VALIDATION(1),
        FAILED_USER_AUTHENTICATION(2),
        MISSING_TITLE(3),
        MISSING_FILE_FOR_UPLOAD(4),
        BLANK_TITLE(5),
        SLIDESHOW_FILE_IS_NOT_RESOURCE_OBJECT(6),
        INVALID_EXTENSION(7),
        FILE_SIZE_TOO_BIG(8),
        SLIDESHOW_NOT_FOUND(9),
        USER_NOT_FOUND(10),
        GROUP_NOT_FOUND(11),
        NO_TAG_PROVIDED(12),
        TAG_NOT_FOUND(13),
        REQUIRED_PARAMETER_MISSING(14),
        SEARCH_QUERY_CANNOT_BE_BLANK(15),
        INSUFFICIENT_PERMISSIONS(16),
        INCORRECT_PARAMETERS(17),
        ACCOUNT_ALREADY_LINKED(70),
        NO_LINKED_ACCOUNT_FOUND(71),
        USER_NOT_CREATED(72),
        INVALID_APPLICATION_ID(73),
        LOGIN_ALREADY_EXISTS(74),
        EMAIL_ALREADY_EXISTS(75),
        ACCOUNT_EXCEEDED_DAILY_LIMIT(99),
        YOUR_ACCOUNT_HAS_BEEN_BLOCKED(100)
    }
}
