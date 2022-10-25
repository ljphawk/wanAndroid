package com.ljp.repository.network.base


/*
 *@创建者       L_jp
 *@创建时间     2022/6/16 13:19.
 *@描述
 */


open class ApiResponse<T> constructor(
    open val errorCode: Int = -1,
    open val errorMsg: String? = null,
    open val data: T? = null,
){
     fun isSuccessful(): Boolean {
        return errorCode == 0
    }
}


class ApiSuccessResponse<T>(override val data: T?) : ApiResponse<T>()

data class ApiFailedResponse<T>(override val errorCode: Int, override val errorMsg: String) :
    ApiResponse<T>()

class ApiEmptyResponse<T> : ApiResponse<T>()