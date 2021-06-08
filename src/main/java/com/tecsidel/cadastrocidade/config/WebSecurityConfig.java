package com.tecsidel.cadastrocidade.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
//	@Autowired
//	private DataSource dataSource;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
				.password("$2a$10$PGdec3oUQy3fY//qZilsxuoNFBWRXXV5scR52hxgVFE6K.zzxMCVu").roles("ADMIN").and()
				.withUser("user").password("$2a$10$RXrUdMWknTn3nmDiXFkdN./.82DzJVsOGCffF9joarjkyuBHXY7kG")
				.roles("USER");

		/*
		 * BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); String
		 * rawPassword = "user"; String encodedPassword = encoder.encode(rawPassword);
		 * System.out.println(encodedPassword);
		 */
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest().authenticated().and()
				.formLogin(form -> form.loginPage("/login").defaultSuccessUrl("/cadastro", true).permitAll())
				.logout(logout -> {
					logout.logoutUrl("/logout").logoutSuccessUrl("/cadastro");
				}).csrf().disable();
	}
}