package com.you.components.utils

import co.touchlab.kermit.Logger
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

    actual fun createDirectories(): Boolean = NSFileManager.defaultManager.createDirectoryAtPath(
        path = filePath,
        withIntermediateDirectories = true,
        attributes = null,
        error = null
    )

    actual companion object{
        private val directory by lazy{
            Logger.i("You.com"){ "is debug: ${Platform.isDebugBinary}" }
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
