package com.security.basic.config;

import com.security.basic.service.serviceimpl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.List;

@Configuration
public class SecurityConfig {


    // step 2 define url for rest api communication
    // for making endpoints whitelist (public) or blacklist (private)
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf->csrf.disable()).authorizeHttpRequests(req->
                req.requestMatchers("/app/**").permitAll()).build();
    }

    // step 3

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        // return authenticationConfiguration.getAuthenticationManager();
        // return new ProviderManager(authenticationProvider());
        return new ProviderManager(List.of(authenticationProvider()));
    }

    // here authenticationProvider method requires one of default authentication providers which
    // spring security provides lets say DaoAuthenticationProvider

    // write password encoder which we can pass to DaoAuthenticationProvider
    // and that password encoder will be used to encode password when user login

    // when someone do login request from react or any api-client  then there login request object is coming to first
    // security filter chain after that authentication filter validate or check with the help
    // of UsernamePasswordAuthenticationFilter that the incoming
    // request has filled fields of username and password if this filed is not filled then
    // authentication filter reject that request without any further processing and if request
    // is valid then from security filter chain or authentication filter the request comes to
    // controller layer here in controller the login method receive login request object
    // after from controller the login request object is coming to service layer still the login
    // request is in object form now when we have to split this incoming valid login request to
    // username and password and then pass this username and password to UsernamePasswordAuthenticationToken
    // and then authenticationManager validate this token with help of authenticationProvider
    // it means that after passing username and password to UsernamePasswordAuthenticationToken
    // this will return object of UsernamePasswordAuthenticationToken class now we have to
    // autowired authentication manager and call .authenticate() method to pass this object for authentication
    // now this means that this UserNameAuthenticationToken object is now comes under scope of authentication manger and authentication manager interface is used as field for class AuthenticationConfiguration so
    // indirectly this object of UserNameAuthenticationToken object comes under scope of AuthenticationConfiguration
    // now control transfer will go for config package where with the help of AuthenticationConfiguration class which provide getAuthenticationManager method with the help of this method we will get authentication manager which holds UserNameAuthenticationToken object with the help .authenticate() method in service layer
    // now authentication manager are using provider manager and provider manager using authentication provider namely DaoAuthenticationProvider which will take two parameter generally UserDetailsService and PasswordEncoder
    // here UserDetailsService is an interface which is implemented by custom class say UserDetailsServiceImpl this class uses one built method of spring security .loadUserByUsername() which has one default argument of username
    // now we are using findByUserName(incoming username from loadUserByUsername) with the help of this we can fetch that complete row of the user because
    // username is unique that's why it is capable to separately distinguish between different row
    // after getting that row object which is type of UserDetails is transferred to DaoAuthenticationProvider in place of UserDetailsService and other parameter which is
    // PasswordEncoder this will receive incoming login password from network lets say from react as a login request
    // now what this authentication provider do that it will take the password which is stored in db corresponding to that username and it internally matches that db password from incoming password from react
    // if that password matches then it means user is authenticated .( so it means that authentication manager uses authentication provider to validate incoming request from frontend )
    // now after authentication control transfer again back to service layer from config package .
    // and in service layer after getting authentication object from authentication manager we will check this object is valid or not if valid then we will generate jwt token and return this jwt token to client side and client side will store this jwt token in local storage or cookie and in future request to server side it will send this jwt token in header of request with key "Authorization" and value "Bearer <jwt token>" then server side will validate this jwt token by extracting this token and then validate it with help of authentication manager and authentication provider and if token is valid then server side will return response to client side else it will reject this request and return error response to client side

    // bean for password encoder
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // fetch user object from database
    @Bean
    UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }



}
