package com.ubaya.a3pilar_project.repository

import com.ubaya.a3pilar_project.data.UserDao
import com.ubaya.a3pilar_project.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun registerUser(user: User) = userDao.insertUser(user)
    suspend fun getUser(username: String) = userDao.getUserByUsername(username)
}
