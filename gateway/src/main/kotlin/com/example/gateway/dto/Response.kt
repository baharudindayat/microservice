package com.example.gateway.dto

import com.fasterxml.jackson.annotation.JsonInclude



@JsonInclude(JsonInclude.Include.NON_NULL)
data class Response<T> (
     var status: Int,

     var message: String,

     var data: T? = null,

     var errorList: List<String?>? = null,

     var pageNumber: Int? = null,

     var pageSize: Int? = null,

     var totalPage: Int? = null,

     var totalData: Long? = null
)