package br.com.testeatlantico.emaildispatcher.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

@Configuration
public class OAuth2Config {

    private PasswordEncoder encoder = new BCryptPasswordEncoder();

    public static final String CLIENT_ID = "client_service";
    public static final String CLIENT_SECRET = "service123";
    public static final String TOKEN_URL = "http://localhost:8080/oauth/token";

    private static final Logger LOGGER = LoggerFactory.getLogger(OAuth2Config.class);

    @Bean
    protected OAuth2ProtectedResourceDetails oauth2Resource(){
        ClientCredentialsResourceDetails clientCredentialsResourceDetails = new ClientCredentialsResourceDetails();
        clientCredentialsResourceDetails.setAccessTokenUri(TOKEN_URL);
        clientCredentialsResourceDetails.setClientId(CLIENT_ID);
        clientCredentialsResourceDetails.setClientSecret(encoder.encode(CLIENT_SECRET));
        clientCredentialsResourceDetails.setGrantType("client_credentials");
        //clientCredentialsResourceDetails.setAuthenticationScheme(AuthenticationScheme.header);
        return clientCredentialsResourceDetails;
    }

    @Bean
    public OAuth2RestTemplate oAuth2RestTemplate(){
        AccessTokenRequest atr = new DefaultAccessTokenRequest();
        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(oauth2Resource(),new DefaultOAuth2ClientContext(atr));
        oAuth2RestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        return oAuth2RestTemplate;
    }
}
