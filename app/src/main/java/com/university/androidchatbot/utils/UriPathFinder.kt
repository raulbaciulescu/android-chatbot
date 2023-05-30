package com.university.androidchatbot.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class UriPathFinder {
    fun handleUri(context: Context, uri: Uri): String? {
        val type = when (context.contentResolver.getType(uri)) {
            "image/png" -> ".png"
            "image/jpg" -> ".jpg"
            "image/jpeg" -> ".jpg"
            "application/pdf" -> ".pdf"
            "application/msword" -> ".doc"
            "application/ms-doc" -> ".doc"
            "application/doc" -> ".doc"
            "text/plain" -> ".txt"
            "application/vnd.openxmlformats-officedocument.wordprocessingml.document" -> ".docx"
            else -> return null
        }
        val dir = File(context.cacheDir, "dir_name").apply { mkdir() }
        val file = File(dir, "${System.currentTimeMillis()}$type")
        copyStreamToFile(
            context.contentResolver.openInputStream(uri)!!, file.absoluteFile
        )

        return file.absolutePath
    }

    private fun copyStreamToFile(inputStream: InputStream, outputFile: File) {
        inputStream.use { input ->
            val outputStream = FileOutputStream(outputFile)
            outputStream.use { output ->
                val buffer = ByteArray(4 * 1024) // buffer size
                while (true) {
                    val byteCount = input.read(buffer)
                    if (byteCount < 0) break
                    output.write(buffer, 0, byteCount)
                }
                output.flush()
            }
        }
    }
}