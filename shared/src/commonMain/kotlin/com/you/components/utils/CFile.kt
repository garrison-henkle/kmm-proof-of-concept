package com.you.components.utils

expect class CFile(filename: String, androidFilesDirPath: String? = null){
    fun write(text: String): Boolean
    fun readAll(): String?

    companion object{
        fun exists(filename: String, androidFilesDirPath: String? = null): Boolean
    }
}