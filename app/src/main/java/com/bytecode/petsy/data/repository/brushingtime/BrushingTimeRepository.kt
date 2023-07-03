package com.bytecode.petsy.data.repository.brushingtime

import androidx.annotation.VisibleForTesting
import com.bytecode.petsy.data.local.dao.BrushingTimeDao
import com.bytecode.petsy.data.local.dao.DogDao
import com.bytecode.petsy.data.local.dao.UserDao
import com.bytecode.petsy.data.model.local.brushing.BrushingTimeEntity
import com.bytecode.petsy.data.model.local.dog.DogEntity
import com.bytecode.petsy.data.model.local.user.UserEntity
import javax.inject.Inject

class BrushingTimeRepository
@Inject
constructor(
    @get:VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    internal val dao: BrushingTimeDao
) {
    suspend fun saveBrushingTime(brushingTimeEntity: BrushingTimeEntity) =
        dao.insert(brushingTimeEntity)

    suspend fun saveBrushingTimes(times: List<BrushingTimeEntity>) = dao.insert(times)

    suspend fun getTimesForUser(ownerId: Long) = dao.getTimesForUser(ownerId)

    suspend fun getTimesForDog(dogId: Long) = dao.getTimesForDog(dogId)

    suspend fun deleteTimesForDog(dogId: Long) = dao.deleteTimesForDog(dogId)

    suspend fun deleteTimesForUser(userId: Long) = dao.deleteTimesForUser(userId)


}