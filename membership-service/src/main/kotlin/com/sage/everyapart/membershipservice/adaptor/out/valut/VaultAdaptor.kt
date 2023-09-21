package com.sage.everyapart.membershipservice.adaptor.out.valut

import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultKeyValueOperationsSupport
import org.springframework.vault.core.VaultTemplate

@Configuration
class VaultAdaptor(
    vaultTemplate: VaultTemplate
) {

    val encryptor:AESProvider

    init {
        val ops = vaultTemplate.opsForKeyValue("kv-v1/data/ecrypt", VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)

        val key = ops.get("dbkey")?.data?.get("key") as? String ?: throw RuntimeException("key 가 null 입니다.")

        this.encryptor= AESProvider(key)

    }

    fun encrypt(plainText:String):String {

        try {
            return encryptor.encrypt(plainText)
        } catch (e:Exception) {
            throw RuntimeException()
        }

    }

    fun decrypt(encryptedText:String): String {

        try {
            return encryptor.decrypt(encryptedText)
        } catch (e:Exception) {
            throw RuntimeException()
        }


    }



}
