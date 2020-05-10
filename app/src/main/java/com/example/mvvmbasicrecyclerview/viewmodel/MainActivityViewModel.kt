package com.example.mvvmbasicrecyclerview.viewmodel

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvmbasicrecyclerview.model.User
import com.example.mvvmbasicrecyclerview.repository.UserRepository


class MainActivityViewModel : ViewModel() {

    var alUserLiveData= MutableLiveData<ArrayList<User>>()
    var isUpdatingLiveData= MutableLiveData<Boolean>()
    var userRepository: UserRepository?=null

    init{
        if(userRepository==null){
            userRepository=UserRepository.getInstance()
        }

        if(alUserLiveData.value!!.size==0)
        alUserLiveData=userRepository!!.getUsers()
    }

    fun getUsers(): LiveData <ArrayList<User>>{
        return alUserLiveData
    }

    fun addNewValue(newUser:User){
        isUpdatingLiveData.value=true
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun onPostExecute(aVoid: Void?) {
                super.onPostExecute(aVoid)
                var alCurrentUsers = alUserLiveData.value

                //add new user in current list
                alCurrentUsers!!.add(newUser)
                alUserLiveData.postValue(alCurrentUsers)
                isUpdatingLiveData.postValue(false)
            }
            override fun doInBackground(vararg params: Void?): Void? {
                try {
                    Thread.sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                return null
            }
        }.execute()

    }

    fun getIsUpdating(): MutableLiveData<Boolean>? {
        return isUpdatingLiveData
    }

}