package com.hileclient.friend

object FriendManager {
    private val friends = mutableListOf<String>()

    fun addFriend(name: String) {
        if (!friends.contains(name)) friends.add(name)
    }

    fun removeFriend(name: String) {
        friends.remove(name)
    }

    fun isFriend(name: String) = friends.contains(name)
    fun getFriends() = friends.toList()
}
