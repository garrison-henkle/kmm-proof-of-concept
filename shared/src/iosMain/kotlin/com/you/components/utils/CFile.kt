package com.you.components.utils

import platform.Foundation.*

actual class CFile actual constructor(filename: String, androidFilesDirPath: String?){
    private val filePath = directory.stringByAppendingPathComponent(filename)

    @Suppress("CAST_NEVER_SUCCEEDS")
    actual fun write(text: String): Boolean = (text as NSString).writeToFile(
        path = filePath,
        atomically = true,
        encoding = NSUTF8StringEncoding,
        error = null
    )

    actual fun readAll(): String? = NSString.stringWithContentsOfFile(
        path = filePath,
        encoding = NSUTF8StringEncoding,
        error = null
    )

    actual companion object{
        private val directory by lazy{
            NSSearchPathForDirectoriesInDomains(
                directory = NSDocumentDirectory,
                domainMask = NSUserDomainMask,
                expandTilde = true
            ).first() as NSString
        }

        actual fun exists(filename: String, androidFilesDirPath: String?): Boolean{
            val absPath = directory.stringByAppendingPathComponent(filename)
            return NSFileManager.defaultManager.fileExistsAtPath(absPath)
        }
    }
}