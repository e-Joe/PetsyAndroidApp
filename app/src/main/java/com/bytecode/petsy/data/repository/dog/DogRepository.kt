package com.bytecode.petsy.data.repository.dog

import androidx.annotation.VisibleForTesting
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.dog.DogEntity
import com.bytecode.petsy.data.model.local.user.UserEntity
import javax.inject.Inject

class DogRepository
@Inject
constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val dao: DogDao
) {
    suspend fun saveDog(dogEntity: DogEntity) = dao.insert(dogEntity)

    suspend fun saveDogs(dogs: List<DogEntity>) = dao.insert(dogs)

    suspend fun getDogs(ownerId: Long) = dao.getDogs(ownerId)
}