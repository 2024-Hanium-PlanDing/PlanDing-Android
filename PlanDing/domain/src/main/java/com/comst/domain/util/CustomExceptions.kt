package com.comst.domain.util

import java.io.IOException

class AccountNotFoundException(e: Throwable?, val url: String? = null) : IOException(e)
class ServerNotFoundException(e: Throwable?, val url: String? = null) : IOException(e)
class InternalServerErrorException(e: Throwable?, val url: String? = null) : IOException(e)
class BadRequestException(e: Throwable?, val url: String? = null) : IOException(e)
class ReAuthenticationRequiredException(e: Throwable?, val url: String? = null) : IOException(e)

class BadGatewayException(e: Throwable?, val url: String? = null) : IOException(e)