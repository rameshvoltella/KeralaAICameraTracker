package com.ramzmania.aicammvd.errors

/**
 * Created by AhmedEltaher
 */

class Error(val code: Int, val description: String) {
    constructor(exception: Exception) : this(code = DEFAULT_ERROR, description = exception.message
            ?: "")
}

const val NO_INTERNET_CONNECTION = -1
const val NETWORK_ERROR = -2
const val DEFAULT_ERROR = -3
const val PASS_WORD_ERROR = -101
const val USER_NAME_ERROR = -102
const val CHECK_YOUR_FIELDS = -103
const val SEARCH_ERROR = -104
const val EMAIL_ERROR = -105
const val CONFIRM_PASSWORD_ERROR = -106
const val MOBILE_ERROR = -107
const val EMAIL_PASSWORD_ERROR = -108
const val EMAIL_ALREADY_REGISTERED_ERROR = -109
const val GOOGLE_SIGNIN_ERROR = -110
const val SERVER_ERROR = 404
const val SEARCH_TEXT_EMPTY_ERROR = -111
const val CATEGORY_SELECTION_ERROR = -112
const val EXIT_CATEGORY_MSG = -113
const val NO_SEATS_LEFT_ERROR = -114
const val SOMETHING_WENT_WRONG = -115
const val NOTE_SAVING_ERROR = -116
const val NOTES_WRITING_PERMISSION_DENIED = -117
const val NOTES_SAVED = -118
const val FEED_ERROR = -119
const val NEWS_DATE_LIST_ERROR = -120

