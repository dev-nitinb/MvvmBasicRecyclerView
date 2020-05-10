package com.example.mvvmbasicrecyclerview.repositorie

import androidx.lifecycle.MutableLiveData
import com.example.mvvmbasicrecyclerview.model.User

class UserRepository {
    var alUser= ArrayList<User>()

    companion object{
        private var instance:UserRepository?=null

        fun getInstance():UserRepository{
            if(instance==null){
                instance= UserRepository()
            }
            return instance!!
        }
    }

    fun getUsers(): MutableLiveData<ArrayList<User>> {
        setUsers()

        var userLiveData=MutableLiveData<ArrayList<User>>()
        userLiveData.value=alUser
        return userLiveData
    }

    fun setUsers() {
        alUser.add(User("AAA"))
        alUser.add(User("BBB"))
        alUser.add(User("CCC"))
        alUser.add(User("DDD"))
        alUser.add(User("EEE"))
    }
}