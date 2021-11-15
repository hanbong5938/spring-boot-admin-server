package com.springboot.admin.global.security

import de.codecentric.boot.admin.server.config.AdminServerProperties
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.security.web.csrf.CookieCsrfTokenRepository

@Configuration
class SecurityConfig : WebSecurityConfigurerAdapter() {

    var adminServerProperties = AdminServerProperties();

    override fun configure(http: HttpSecurity?) {
        val contextPath = this.adminServerProperties.contextPath

        val successHandler = SavedRequestAwareAuthenticationSuccessHandler()
        successHandler.setTargetUrlParameter("redirectTo")
        successHandler.setDefaultTargetUrl("/")

        http!!.authorizeRequests()
            .antMatchers("$contextPath/assets/**").permitAll()
            .antMatchers("$contextPath/login").permitAll()
            .antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("$contextPath/login").successHandler(successHandler)
            .and()
            .logout().logoutUrl("$contextPath/logout")
            .and()
            .httpBasic()
            .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            .ignoringAntMatchers(
                "$contextPath/instances",
                "$contextPath/actuator/**"
            )
    }
}
