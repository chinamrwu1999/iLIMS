package com.amswh.iLIMS.framework.security.service;

import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.RsaKeyUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;

public class JoseJWTService {

    public String generateJWToken(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size is 2048 bits
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            JwtClaims claims = new JwtClaims();
            claims.setIssuer("issuer");
            claims.setAudience("audience");
            claims.setExpirationTimeMinutesInTheFuture(10); // Token expires in 10 minutes
            claims.setGeneratedJwtId();
            claims.setIssuedAtToNow();
            claims.setNotBeforeMinutesInThePast(2); // Token is not valid before 2 minutes from now
            claims.setSubject("subject");

            JsonWebSignature jws = new JsonWebSignature();
            jws.setPayload(claims.toJson());
            jws.setKey(keyPair.getPrivate());
            // jws.setKeyIdHeaderValue(new RsaKeyUtil().computeThumbprint(keyPair.getPublic())); // Optional key ID
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

            // Sign the JWT
            String jwt = jws.getCompactSerialization();
        }catch (Exception err){
            err.printStackTrace();
        }
    }


    public void parseToken(String token){
        try {

            // Assume you have obtained or retrieved the RSA KeyPair (public and private keys)
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size is
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                    .setVerificationKey(keyPair.getPublic())
                    .build();

            // Parse and verify the JWT
            JwtClaims claims = jwtConsumer.processToClaims(token);

            // Print out the claims
            System.out.println("Issuer: " + claims.getIssuer());
            System.out.println("Subject: " + claims.getSubject());
            System.out.println("Audience: " + claims.getAudience());
            System.out.println("Expiration: " + claims.getExpirationTime());
            System.out.println("Issued at: " + claims.getIssuedAt());
        }catch(Exception err){
            err.printStackTrace();
        }
    }

    public void KeyPairGenerate(){
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048); // Key size is
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            byte [] privateKey=keyPair.getPrivate().getEncoded();
            byte [] publicKey=keyPair.getPublic().getEncoded();
            writeToFile("publicKey.pub", keyPair.getPublic().getEncoded());

            // Write private key to file
            writeToFile("privateKey.key", keyPair.getPrivate().getEncoded());
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, byte[] content) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        fos.write(content);
        fos.close();
    }


}
