package edu.pes.laresistencia.util

import java.io.File
import java.lang.Exception
import java.util.*

// More info on:
// http://javasampleapproach.com/spring-framework/spring-boot/kotlin-spring-boot/kotlin-resttemplateclient-sendrecieve-base64-fileimage-springboot-server
class KotlinUtilBase64ByteArrayImage {
    companion object {
        fun getByteArrayImg(filePath: String): ByteArray? {
            var byteArray: ByteArray
            try {
                byteArray = File(filePath).readBytes()
            }
            catch (e: Exception) {
                return null
            }
            return byteArray
        }

        fun saveBase64Img(base64Str: String?, pathFile: String): Unit {
            val imageByteArray = Base64.getDecoder().decode(base64Str)
            File(pathFile).writeBytes(imageByteArray)
        }
    }
}
