package com.example.auth.dto

import com.fasterxml.jackson.annotation.JsonInclude
import lombok.Builder
import lombok.Data

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
data class GlobalResponse<T> (

    var status: Int,

    var message: String,

    var data: T? = null,

    var errorList: List<String?>? = null,

    var pageNumber: Int? = null,

    var pageSize: Int? = null,

    var totalPage: Int? = null,

    var totalData: Long? = null


)