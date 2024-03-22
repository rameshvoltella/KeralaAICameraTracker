package com.ramzmania.aicammvd.errors

import com.ramzmania.aicammvd.errors.mapper.ErrorMapper
import javax.inject.Inject

/**
 * Created by AhmedEltaher
 */

class ErrorManager @Inject constructor(private val errorMapper: ErrorMapper) : ErrorUseCase {
    override fun getError(errorCode: Int): Error {
        return Error(code = errorCode, description = errorMapper.errorsMap.getValue(errorCode))
    }
}
