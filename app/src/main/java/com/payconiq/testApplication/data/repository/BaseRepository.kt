package com.payconiq.testApplication.data.repository

import com.payconiq.testApplication.utils.NetworkResult

abstract class BaseRepository {

    companion object {
        private const val UNAUTHORIZED = "Unauthorized"
        private const val NOT_FOUND = "Not found"
        const val SOMETHING_WRONG = "Something went wrong"

        fun <T : Any> handleSuccess(data: T): NetworkResult<T> {
            return NetworkResult.Success(data)
        }

        fun <T : Any> handleException(code: Int): NetworkResult<T> {
            val exception = getErrorMessage(code)
            return NetworkResult.Error(Exception(exception))
        }

        private fun getErrorMessage(httpCode: Int): String {
            return when (httpCode) {
                401 -> UNAUTHORIZED
                404 -> NOT_FOUND
                else -> SOMETHING_WRONG
            }
        }
    }
}