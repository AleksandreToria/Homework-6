package com.example.homework6

class Users {
    private val userList: ArrayList<UserInformation> = ArrayList()
    private var removedUserCount: Int = 0

    fun userExists(email: String): Boolean {
        return userList.any { it.email == email }
    }

    fun addUser(userInfo: UserInformation) {
        userList.add(userInfo)
    }

    fun userCount(): Int {
        return userList.count()
    }

    fun removeUser(userInfo: UserInformation) {
        val user = userFinder(userInfo.email)
        userList.remove(user)
        removedUserCount++
    }

    fun usersRemoved(): Int {
        return removedUserCount
    }

    fun userFinder(email: String): UserInformation {
        return userList.first() { it.email == email }
    }

    fun update(user: UserInformation) {
        val userUpdate = userFinder(user.email)
        userUpdate.firstName = user.firstName
        userUpdate.lastName = user.lastName
        userUpdate.age = user.age
    }
}