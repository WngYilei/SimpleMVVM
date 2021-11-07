package com.xl.xl_base.tool.util

import android.util.Base64
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

/**
 * 描述: 加密工具
 *
 * author zys
 * create by 2021/4/27
 */
@Suppress("SpellCheckingInspection")
object EncryptUtil {

    private const val AES_MODE = "AES/CBC/PKCS5Padding"
    private const val PWD_KEY = "!@#\$CEWQ6789asdf&*()+{:<kks"
    private const val PWD_IV = ">:{+-pl,MKO)*IK<mju7%OKDMEWSJ"


    /**
     * 密码登录 用pkcs7 加密
     */
    fun encodePkcs7(str: String?): String {
        if (str == null) return ""
        return try {
            val cipher: Cipher = Cipher.getInstance(AES_MODE)
            cipher.init(
                Cipher.ENCRYPT_MODE,
                SecretKeySpec(PWD_KEY.md5Bytes(), "AES"),
                IvParameterSpec(PWD_IV.md5Bytes())
            )
            val bytes: ByteArray = cipher.doFinal(str.toByteArray(StandardCharsets.UTF_8))
            Base64.encodeToString(bytes, Base64.DEFAULT).trimEnd('\n')
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

    private fun String.md5Bytes(): ByteArray = try {
        MessageDigest.getInstance("MD5")
            .digest(this.toByteArray(StandardCharsets.UTF_8))
    } catch (e: Exception) {
        e.printStackTrace()
        "".toByteArray(StandardCharsets.UTF_8)
    }


    private val HEX_DIGITS = charArrayOf(
        '0', '1', '2', '3', '4',
        '5', '6', '7', '8', '9',
        'a', 'b', 'c', 'd', 'e', 'f'
    )

    /**
     * 计算MD5
     */
    fun encodeMD5(vararg inputs: String): String {
        val content = inputs.joinToString("")
        try {
            val btInput = content.toByteArray()
            val mdInst = MessageDigest.getInstance("MD5")
            mdInst.update(btInput)
            val md = mdInst.digest()
            val str = CharArray(md.size * 2)
            var k = 0
            var i = 0
            do {
                val byte0 = md[i]
                str[k++] = HEX_DIGITS[byte0.toInt().ushr(4) and 0xf]
                str[k++] = HEX_DIGITS[byte0.toInt() and 0xf]
                i++
            } while (i < md.size)
            return String(str)
        } catch (e: Exception) {
            return ""
        }

    }
}