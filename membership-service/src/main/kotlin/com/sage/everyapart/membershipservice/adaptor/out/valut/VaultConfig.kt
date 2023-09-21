package com.sage.everyapart.membershipservice.adaptor.out.valut

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.authentication.ClientAuthentication
import org.springframework.vault.client.VaultEndpoint
import org.springframework.vault.core.VaultTemplate
import org.springframework.vault.support.VaultToken

@Configuration
class VaultConfig(
    @Value("\${spring.cloud.vault.token}")
    val  vaultToken:String,

    @Value("\${spring.cloud.vault.scheme}")
    val vaultScheme:String,

    @Value("\${spring.cloud.vault.host}")
    val vaultHost:String,

    @Value("\${spring.cloud.vault.port}")
    val vaultPort:Int
) {

    @Bean
    fun vaultTemplate():VaultTemplate {

        val endpoint = VaultEndpoint.create(vaultHost, vaultPort)
        endpoint.scheme = vaultScheme

        return VaultTemplate(endpoint, {VaultToken.of(vaultToken) } as ClientAuthentication)
    }


}
