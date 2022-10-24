package com.ljp.respository.network.livadata.listener

import androidx.lifecycle.LiveData
import com.ljp.respository.network.livadata.Resource

interface IResourceLiveData<R> {

    fun asLiveData(): LiveData<Resource<R>>
}