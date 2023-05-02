package com.bytecode.framework.usecase


abstract class AsyncUseCase<in Params, ReturnType> where ReturnType : Any {

    protected abstract suspend fun execute(params: Params)

    suspend operator fun invoke(params: Params) = execute(params)
}