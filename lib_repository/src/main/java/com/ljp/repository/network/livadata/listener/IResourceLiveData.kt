package com.ljp.repository.network.livadata.listener

import androidx.lifecycle.LiveData
import com.ljp.repository.network.livadata.Resource

interface IResourceLiveData<R> {

    fun asLiveData(): LiveData<Resource<R>>
}