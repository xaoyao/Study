package com.example.demo02

import java.io.Serializable

/**
 * Created by liu on 2016/11/24 0024.
 */
data class User(var userId: Int, var userName: String, var isMale: Boolean) : Serializable {
}