package com.oguzhanturkmen.kotlinulkelerapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseViewModel(application: Application): AndroidViewModel(application),CoroutineScope {
    //korotinin ne yapacağını söylüyoruz
    //job: iş oluşturur ve arka planda yapar
    private val job = Job()
    override val coroutineContext: CoroutineContext
    //bunu dersek öncelikle işini yap sonra main threade dön
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}