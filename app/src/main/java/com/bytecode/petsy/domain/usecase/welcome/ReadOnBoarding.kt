package com.bytecode.petsy.domain.usecase.welcome

import androidx.annotation.VisibleForTesting
import com.bytecode.framework.usecase.ReturnUseCase
import com.bytecode.petsy.data.repository.welcome.WelcomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadOnBoarding @Inject constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val repository: WelcomeRepository
) : ReturnUseCase<Unit, Boolean>() {

    override suspend fun execute(params: Unit): Flow<Boolean> {
        return repository.readOnBoardingState()
    }
}