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

import io.t28.springframework.social.entity.ErrorResponse
import io.t28.springframework.social.slideshare.api.InvalidParameterException
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.TOO_MANY_REQUESTS
import org.springframework.http.HttpStatus.UNAUTHORIZED
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.http.ResponseEntity
import org.springframework.social.ApiException
import org.springframework.social.InvalidAuthorizationException
import org.springframework.social.OperationNotPermittedException
import org.springframework.social.RateLimitExceededException
import org.springframework.social.ResourceNotFoundException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
object ApiExceptionHandler {
    @ExceptionHandler(InvalidParameterException::class)
    fun handle(exception: InvalidParameterException): ResponseEntity<ErrorResponse> {
        return handle(BAD_REQUEST, exception)
    }

    @ExceptionHandler(InvalidAuthorizationException::class)
    fun handle(exception: InvalidAuthorizationException): ResponseEntity<ErrorResponse> {
        return handle(UNAUTHORIZED, exception)
    }

    @ExceptionHandler(OperationNotPermittedException::class)
    fun handle(exception: OperationNotPermittedException): ResponseEntity<ErrorResponse> {
        return handle(FORBIDDEN, exception)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handle(exception: ResourceNotFoundException): ResponseEntity<ErrorResponse> {
        return handle(NOT_FOUND, exception)
    }

    @ExceptionHandler(RateLimitExceededException::class)
    fun handle(exception: RateLimitExceededException): ResponseEntity<ErrorResponse> {
        return handle(TOO_MANY_REQUESTS, exception)
    }

    @ExceptionHandler(ApiException::class)
    fun handle(exception: ApiException): ResponseEntity<ErrorResponse> {
        return handle(BAD_REQUEST, exception)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handle(exception: RuntimeException): ResponseEntity<ErrorResponse> {
        return handle(INTERNAL_SERVER_ERROR, exception)
    }

    private fun handle(status: HttpStatus, exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(status)
            .contentType(APPLICATION_JSON)
            .body(ErrorResponse("${exception.message}"))
    }
}
