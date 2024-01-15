package com.example.tbc_events.domain.use_case

import com.example.tbc_events.data.common.Resource
import com.example.tbc_events.domain.repository.IDeleteUsersRepository
import com.example.tbc_events.presentation.model.UserItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//class DeleteUsersUseCase @Inject constructor() {
//
//    suspend operator fun invoke(
//        users: List<UserItem>
//    ): Flow<Resource<List<UserItem>>> {
//        var updatedList = users
//
//        updatedList = updatedList.filterNot { it.isSelected }
//
//        return flow {
//            emit(Resource.Success(updatedList))
//        }
//    }
//}


// This version is with Fake delete api and won't delete items.
// Uncomment the above example for it to work and comment this one.
class DeleteUsersUseCase @Inject constructor(private val repository: IDeleteUsersRepository) {

    suspend operator fun invoke(users: List<UserItem>) {
        for (i in users){
            if (i.isSelected) repository.deleteUser(i.id)
        }
    }
}