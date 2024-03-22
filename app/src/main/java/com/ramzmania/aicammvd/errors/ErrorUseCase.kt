package com.ramzmania.aicammvd.errors


interface ErrorUseCase {
    fun getError(errorCode: Int): Error
}
