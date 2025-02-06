package com.dicoding.core.data.local.preferences.utils

import android.util.Base64
import java.nio.charset.StandardCharsets
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec


object EncryptionHelper {
    private const val ALGORITHM = "AES/GCM/NoPadding"
    private const val TAG_LENGTH = 128
    private const val IV_SIZE = 12

    fun encryptData(plainText: String, secretKey: SecretKey): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeToString(iv + encryptedBytes, Base64.DEFAULT)
    }

    fun decryptData(encryptedText: String, secretKey: SecretKey): String {
        val decodedBytes = Base64.decode(encryptedText, Base64.DEFAULT)
        val iv = decodedBytes.copyOfRange(0, IV_SIZE)
        val encryptedBytes = decodedBytes.copyOfRange(IV_SIZE, decodedBytes.size)

        val cipher = Cipher.getInstance(ALGORITHM)
        val spec = GCMParameterSpec(TAG_LENGTH, iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, spec)

        return String(cipher.doFinal(encryptedBytes), StandardCharsets.UTF_8)
    }
}
