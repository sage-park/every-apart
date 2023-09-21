package com.sage.everyapart.membershipservice.adaptor.out.valut

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class AESProvider(key: String) {

    val secretKey:SecretKeySpec

    val ALGORITM = "AES"
    val TRANSFORMATION = "AES/ECB/PKCS5Padding" // ECB mode, PKCS5Padding


    init {
        this.secretKey = SecretKeySpec(key.toByteArray(), ALGORITM)
    }


    fun encrypt(plainText: String): String {

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        val encryptedBytes = cipher.doFinal(plainText.toByteArray())

        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(encryptedText: String): String {

        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, secretKey)

        val decodedBytes = Base64.getDecoder().decode(encryptedText)
        val decryptedBytes = cipher.doFinal(decodedBytes)

        return String(decryptedBytes)
    }

}
