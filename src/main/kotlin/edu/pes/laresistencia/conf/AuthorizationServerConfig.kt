package edu.pes.laresistencia.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import java.util.logging.Logger


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {
    private val logger = Logger.getLogger(AuthorizationServerConfig::class.toString())

    @Value("\${security.jwt.client-id}")
    private lateinit var clientId: String

    @Value("\${security.jwt.client-secret}")
    private lateinit var clientSecret: String

    @Value("\${security.jwt.grant-type}")
    private lateinit var grantType: String

    @Value("\${security.jwt.scope-read}")
    private lateinit var scopeRead: String

    @Value("\${security.jwt.scope-write}")
    private var scopeWrite = "write"

    @Value("\${security.jwt.resource-ids}")
    private lateinit var resourceIds: String

    @Autowired
    private lateinit var tokenStore: TokenStore

    @Autowired
    private lateinit var accessTokenConverter: JwtAccessTokenConverter

    @Autowired
    private lateinit var authenticationManager: AuthenticationManager


    override fun configure(clients: ClientDetailsServiceConfigurer) {
        logger.info("clientId is $clientId")
        logger.info("clientSecrect is $clientSecret")
        clients.inMemory()
            .withClient(clientId)
            .secret(clientSecret)
            .authorizedGrantTypes(grantType)
            .scopes(scopeRead, scopeWrite)
            .resourceIds(resourceIds)
            .accessTokenValiditySeconds(60 * 60 * 24 * 7 * 4 * 5)
    }

    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
        val enhancerChain = TokenEnhancerChain()

        enhancerChain.setTokenEnhancers(listOf(accessTokenConverter))

        endpoints.tokenStore(tokenStore)
            .accessTokenConverter(accessTokenConverter)
            .tokenEnhancer(enhancerChain)
            .authenticationManager(authenticationManager)
    }
}