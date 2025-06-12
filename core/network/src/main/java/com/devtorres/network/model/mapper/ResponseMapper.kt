package com.devtorres.network.model.mapper

interface ResponseMapper<Domain, Response> {
    fun asDomain(response: Response): Domain
}