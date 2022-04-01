package com.payconiq.testApplication.utils

object AppEnum {

    enum class API_CALL_STATUS(val data: String) {
        SUCCESS("SUCCESS"),
        ERROR("ERROR"),
        LOADING("LOADING");

        companion object {
            fun fromString(value: String): API_CALL_STATUS {
                return values().first { it.data == value }
            }
        }
    }


    enum class ERROR_MESSAGE(val data: String) {
        DATA_NOT_FOUND("Data not found..."),
        LOCATION_NULL("User did not set location"),
        BIO_NULL("User did not set any bio yet.");

        companion object {
            fun fromString(value: String): ERROR_MESSAGE {
                return values().first { it.data == value }
            }
        }
    }
}