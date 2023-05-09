package com.example.userservicedemo.security;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private String jwtSecret = "a97090370373569707f5c77d360501ced1e02d75ac99ed665fa8891606e94181";
    private long jwtExpirationDate = 3600000;
    private KeyPair keyPair;

    public JwtTokenProvider() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        this.keyPair = keyPairGenerator.generateKeyPair();
    }

//     generate JWT token
    public String generateToken(Authentication authentication) throws JOSEException {
        String username = authentication.getName();
        return generateTokenFromUsername(username);
    }

    public String generateTokenFromUsername(String username) throws JOSEException {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("myApp")
                .expirationTime(expireDate);

        SignedJWT signedJWT = new SignedJWT(
                new JWSHeader.Builder(JWSAlgorithm.RS256).keyID("myKey").build(),
                builder.build());

        JWSSigner signer = new RSASSASigner(keyPair.getPrivate());
        signedJWT.sign(signer);

        JWEHeader jweHeader = new JWEHeader.Builder(JWEAlgorithm.RSA_OAEP_256, EncryptionMethod.A256GCM)
                .contentType("JWT")
                .build();

        JWEObject jweObject = new JWEObject(jweHeader, new Payload(signedJWT));
        JWEEncrypter encrypter = new RSAEncrypter((RSAPublicKey) keyPair.getPublic());
        jweObject.encrypt(encrypter);

        return jweObject.serialize();

    }

    // get username from Jwt token
    public String getUsername(String token) throws ParseException, JOSEException {

        JWEObject jweObject = JWEObject.parse(token);
        jweObject.decrypt(new RSADecrypter(keyPair.getPrivate()));
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
        return signedJWT.getJWTClaimsSet().getSubject();
    }

    // validate Jwt token
    public boolean validateToken(String token) throws ParseException, JOSEException {
        JWEObject jweObject = JWEObject.parse(token);
        jweObject.decrypt(new RSADecrypter(keyPair.getPrivate()));
        SignedJWT signedJWT = jweObject.getPayload().toSignedJWT();
        return signedJWT.verify(new RSASSAVerifier((RSAPublicKey) keyPair.getPublic()));
    }

}
