package com.ramzmania.aicammvd.data

import com.ramzmania.aicammvd.data.dto.cameralist.CameraDataResponse

// A generic class that contains data and status about loading this data.
sealed class Resource<T>(
        val data: T? = null,
        val errorCode: Int? = null,
        val instanceIdentifier:String?=null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)
    class LoadingInstance<T>(instanceIdentifier: String) : Resource<T>(null,0,instanceIdentifier)
    class SuccessInstance<T>(data: T,instanceIdentifier: String?) : Resource<T>(data,instanceIdentifier =instanceIdentifier )
    class DataErrorInstance<T>(errorCode: Int,instanceIdentifier: String?) : Resource<T>(null, errorCode, instanceIdentifier =instanceIdentifier)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$errorCode]"
            is Loading<T> -> "Loading"
            is LoadingInstance<T> -> instanceIdentifier!!
            is SuccessInstance<*> -> "Success[data=$data instanceIdentifier=$instanceIdentifier]"
            is DataErrorInstance<T> -> "Error[exception=$errorCode instanceIdentifier=$instanceIdentifier]"
        }
    }


}
