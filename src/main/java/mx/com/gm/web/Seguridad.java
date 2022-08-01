package mx.com.gm.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration//al ser una clase de configuracion, es necesario utilizar este tag
@EnableWebSecurity//igual que este en aplicaciones web
public class Seguridad extends WebSecurityConfigurerAdapter {

@Autowired
private UserDetailsService userDS;

@Bean 
public BCryptPasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}

@Autowired
public void configurerGlobal(AuthenticationManagerBuilder build) throws Exception {
    build.userDetailsService(userDS).passwordEncoder(passwordEncoder());
}

    @Override //Esto permite al usuario que se le presenten o no opciones ejemplo que pueda o no eliminar un usuario
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/editar/**", "/agregar/**", "/eliminar/**")
                .hasRole("ADMIN")
                .antMatchers("/")
                .hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/errores/403");

    }
}
