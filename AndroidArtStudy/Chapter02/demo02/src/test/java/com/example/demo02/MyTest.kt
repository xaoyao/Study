package com.example.demo02

import org.junit.Test
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/**
 * Created by liu on 2016/11/24 0024.
 */
class MyTest {

    @Test
    fun test01(){
        val user=User(0,"jake",true)
        val out = ObjectOutputStream(FileOutputStream("cache.txt"))
        out.writeObject(user)
        out.close()
    }
    @Test
    fun test02(){
        val inp=ObjectInputStream(FileInputStream("cache.txt"))
        val user=inp.readObject() as User
        inp.close()
        println(user.userName)
    }

    @Test
    fun test03(){
        println("He he")
    }

}