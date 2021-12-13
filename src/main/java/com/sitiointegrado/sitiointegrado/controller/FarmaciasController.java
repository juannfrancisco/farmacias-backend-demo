package com.sitiointegrado.sitiointegrado.controller;

import com.sitiointegrado.sitiointegrado.model.JWTResponse;
import com.sitiointegrado.sitiointegrado.model.TokenRequest;
import com.sitiointegrado.sitiointegrado.model.TokenResponse;
import com.sitiointegrado.sitiointegrado.model.User;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RestController
public class FarmaciasController {


    @GetMapping("login")
    public ResponseEntity<Void> loginClaveUnica(){

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080/openid/authorize/?client_id=wd&redirect_uri=http://localhost:4200&response_type=asd&scope=asd&state=sad"))
                .build();

    }

    @GetMapping("logout")
    public ResponseEntity<Void> logout(){
        return ResponseEntity
                .status(HttpStatus.FOUND)
                .location(URI.create("http://localhost:8080/openid/authorize/?client_id=wd&redirect_uri=http://localhost:4200&response_type=asd&scope=asd&state=sad"))
                .build();
    }


    @GetMapping("clave-unica")
    public ResponseEntity<JWTResponse> claveUnica(
            @RequestParam( name ="code", required = false)  String code,
            @RequestParam( name ="state", required = false)  String state
    ){

        /**

        // Llamada a clave unica /openid/token
        RestTemplate restTemplate = new RestTemplate();
        String tokenUrl = "http://localhost:8080/openid/token";
        TokenRequest request = new TokenRequest( "","", "", "", code, state );
        ResponseEntity<TokenResponse> response = restTemplate.postForEntity(tokenUrl, request, TokenResponse.class);

        System.out.println( "LLamada al access token"+ response.getBody().getAccess_token() );


        // Llamada a clave unica /openid/userInfo
        RestTemplate restTemplate2 = new RestTemplate();
        String userInfoUrl = "http://localhost:8080/openid/userinfo";
        // TODO: PAsar Header Autorization el token,
        ResponseEntity<User> responseUser = restTemplate2.postForEntity(userInfoUrl, null, User.class);

         System.out.println( "User info"+ responseUser.getBody().getName().getNombres() );
         **/

        //String token = createJWT("SOMEID1234", "JWT Demo", "Andrew", 800000);
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
       return new ResponseEntity<>( new JWTResponse(token), HttpStatus.OK);
    }

    private static String SECRET_KEY = "oeRaYY7Wo24sDqKSX3IM9ASGmdGPmkTd9jo1QTy4b7P9Ze5_9hKolVX8xNrQDcNRfVEdTZNOuOyqEGhXEbdJI-ZQ19k_o9MI0y3eZN2lp9jow55FfXMiINEdt1XR85VipRLSOkT6kSpzs2x-jbLDiz9iFVzkd81YKxMgPA7VfZeQUm4n-mOmnWMaVX30zGFU4L3oPBctYKkl4dYfqYWqRNfrgPJVi5DGFjywgxx0ASEiJHtV72paI3fDR2XwlSkyhhmY-ICjCRmsJN4fX1pdoL8a18-aQrvyu4j0Os6dVPYIoPvvY0SAZtWYKHfM15g7A3HD4cVREf9cUsprCRK93w";

    public static String createJWT(String id, String issuer, String subject, long ttlMillis) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = Base64.getDecoder().decode(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis > 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }



}
