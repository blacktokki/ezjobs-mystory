package com.ezjobs.mystory.config;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.ezjobs.mystory.security.*;
 
@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = {"com.ezjobs.*"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    AuthProvider authProvider;
    
    @Autowired
    private CaptchaAuthenticationDetailsSource captchaWebAuthenticationDetailsSource;
    
    @Autowired
    private AuthFailureHandler authFailureHandler;
 
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    
    @Autowired
    OAuth2SuccessHandler oAuth2SuccessHandler;
    
    @Autowired
    CustomAccessDeniedHandler customAccessDeniedHandler;
    
    @Override
    public void configure(WebSecurity web) {
        // 허용되어야 할 경로들
        web.ignoring().antMatchers("/webjars/**", 
                                   "/css/**",
                                   "/image/**",
                                   "/js/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
 
        // 로그인 설정
        http.authorizeRequests()
            // USER, ADMIN으로 권한 분리 유알엘 정의
            .antMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
            .antMatchers("/resume/**",
            			 "/board/write/**",
            			 "/user/password/change/**").hasAuthority("ROLE_USER")
            .antMatchers("/user/password/change/**").not().hasAuthority("ROLE_SOCIAL")
            .antMatchers("/user/join/social/**").authenticated()
            .antMatchers("/**").permitAll()
        .and()
            .oauth2Login()
            .loginPage("/user/login")
            .defaultSuccessUrl("/")
            .successHandler(oAuth2SuccessHandler)
            .authorizationEndpoint()
            .baseUri("/user/oauth2/authorization")
            .and()
        .and()
            // 로그인 페이지 및 성공 url, handler 그리고 로그인 시 사용되는 id, password 파라미터 정의
            .formLogin()
            .authenticationDetailsSource(captchaWebAuthenticationDetailsSource)
            .loginPage("/user/login")
            .defaultSuccessUrl("/")
            .successHandler(authSuccessHandler)
            .failureHandler(authFailureHandler)
            .usernameParameter("id")
            .passwordParameter("loginPw")
        .and()
            // 로그아웃 관련 설정
            .logout().logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
            .logoutSuccessUrl("/")
            .invalidateHttpSession(true)
        .and()
            // csrf 사용유무 설정
            // csrf 설정을 사용하면 모든 request에 csrf 값을 함께 전달해야한다.
            .csrf()
        .and()
            // 로그인 프로세스가 진행될 provider
            .authenticationProvider(authProvider)
            .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
    }
    
    
    @Bean
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties, @Value("${custom.oauth2.kakao.client-id}") String kakaoClientId) {
        List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
                .map(client -> getRegistration(oAuth2ClientProperties, client))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        /*
        registrations.add(CustomOAuth2Provider.KAKAO.getBuilder("kakao")
                .clientId(kakaoClientId)
                .clientSecret("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .jwkSetUri("test") //필요없는 값인데 null이면 실행이 안되도록 설정되어 있음
                .build());
        */
        return new InMemoryClientRegistrationRepository(registrations);
    }
    
    private ClientRegistration getRegistration(OAuth2ClientProperties clientProperties, String client) {
        if ("google".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("google");
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .scope("email", "profile")
                    .build();
        }

        if ("facebook".equals(client)) {
            OAuth2ClientProperties.Registration registration = clientProperties.getRegistration().get("facebook");
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(registration.getClientId())
                    .clientSecret(registration.getClientSecret())
                    .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                    .scope("email")
                    .build();
        }
        return null;
    }
}

