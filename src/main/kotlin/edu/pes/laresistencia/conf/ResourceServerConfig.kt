package edu.pes.laresistencia.conf

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices


@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {
    @Autowired
    private lateinit var tokenServices: ResourceServerTokenServices

    @Value("\${security.jwt.resource-ids}")
    private lateinit var resourceIds: String

    override fun configure(resources: ResourceServerSecurityConfigurer) {
        resources.resourceId(resourceIds).tokenServices(tokenServices)
    }

    override fun configure(http: HttpSecurity) {
        http.requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**", "/api-docs/**").permitAll()
                .antMatchers("/ws", "/ws/**").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/**/photo").permitAll()
                .antMatchers("/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/swagger-resources/**", "/v2/api-docs").permitAll()
                .antMatchers("/**").authenticated()
    }
}