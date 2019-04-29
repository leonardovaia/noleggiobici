package com.noleggiobiciclette;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import lombok.extern.slf4j.Slf4j;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Bean // BCrypt --> password hashing function designed by Niels Provos and David Mazières
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.jdbcAuthentication().dataSource(dataSource)
        .authoritiesByUsernameQuery("select mail as USERNAME,  ROLE from utenti where mail=?")
        .usersByUsernameQuery("select mail AS USERNAME, password, enabled  from utenti where mail=?")
        .passwordEncoder(passwordEncoder());
    }

   
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            //Allows users to authenticate with HTTP Basic authentication
            .httpBasic()
            .and()
            //Ensures that any request to our application requires the user to be authenticated
            .authorizeRequests()
                .antMatchers("/api/biciclette/**").permitAll()
                .antMatchers("/api/noleggi/**").authenticated()
                .antMatchers("/api/stazioni/**").hasAuthority("ADMIN")
                .antMatchers("/api/utenti/**").hasAuthority("ADMIN")
                .anyRequest().fullyAuthenticated() 
                
            .and()
           //Allows users to authenticate with form based login
                .formLogin()
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(new AuthenticationSuccessHandler(){
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
                                response.setContentType("application/json");
                                response.getOutputStream().print("{"  
                                    + "\"username\": \"" + authentication.getName()  + "\","
                                    + "\"role\"    : \"" + authentication.getAuthorities()  + "\"}");
                                response.setStatus(200);
                        }
                    } )
                    .failureHandler(new AuthenticationFailureHandler(){
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                org.springframework.security.core.AuthenticationException exception) throws IOException, ServletException {
                            response.setStatus(403);
                        }
                    })
            .and()
                 .logout()
                    .permitAll()
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
            .and()
            // solo per vedere le api con swagger
            // .authorizeRequests().anyRequest().permitAll()
            // .and()
            .csrf().disable() // Cross-site request forgery
            .cors().disable() // Cross-Origin Resource Sharing 
            .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
    }
	@Bean
	public FilterRegistrationBean corsFilterBean() {
        final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Collections.singletonList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		// setAllowCredentials(true) is important, otherwise:
		// The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
		configuration.setAllowCredentials(true);
		// setAllowedHeaders is important! Without it, OPTIONS preflight request
		// will fail with 403 Invalid CORS request
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		FilterRegistrationBean corsFilter = new FilterRegistrationBean(new CorsFilter(source));
		corsFilter.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return corsFilter;
	}
}