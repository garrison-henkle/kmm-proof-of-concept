package com.you.components.utils

import java.io.File
import java.io.IOException

actual class CFile actual constructor(filename: String, androidFilesDirPath: String?){
    private var openedFile: File = File("$androidFilesDirPath/$filename")

    actual fun write(text: String): Boolean = try{
        openedFile.outputStream().use{ stream ->
            stream.bufferedWriter().use{ writer ->
                writer.write(text)
            }
        }
        true
    } catch (ex: IOException){
        false
    }

    actual fun readAll(): String? = try{
        openedFile.let{ file ->
            file.inputStream().use{ stream ->
                stream.bufferedReader().use{ reader ->
                    reader.readText()
                }
            }
        }
    } catch(ex: IOException){
        null
    }

    actual companion object{
        actual fun exists(filename: String, androidFilesDirPath: String?): Boolean =
            File(androidFilesDirPath?.let{ "$it/$filename" } ?: filename).exists()
    }
}