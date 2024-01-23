package edu.vassar.cmpu203.resolveandroid.model;

import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Map;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class AuthKey implements Serializable {
    private String salt;
    private String key;

    public AuthKey() {
    }

    public AuthKey(@NonNull String password) {
        this(AuthKey.generateSalt(), password);
    }

    private AuthKey(@NonNull String salt, @NonNull String password) {
        this.salt = salt;
        this.key = AuthKey.generateKey(salt, password);
    }

    public static AuthKey fromMap(Map<String, Object> map){
        AuthKey authKey = new AuthKey();
        authKey.salt = (String) map.get("salt");
        authKey.key = (String) map.get("key");

        return authKey;
    }

    public String getSalt() {
        return this.salt;
    }

    public String getKey() {
        return this.key;
    }

    public boolean validatePassword(String password) {
        AuthKey oauthKey = new AuthKey(this.salt, password);
        return this.key.equals(oauthKey.key);
    }

    private static final int SALT_LEN = 20;

    private static String generateSalt() {
        byte[] salt = new byte[SALT_LEN];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            Log.e("NextGenPos", "Error generating authentication salt", e);
        }
        String saltStr = Base64.getEncoder().encodeToString(salt);
        return saltStr;
    }

    private static final int KEY_LEN = 40;
    private static final int NITERS = 64000;

    private static String generateKey(String salt, String password) {
        String hashStr = null;
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] saltBytes = Base64.getDecoder().decode(salt);
            char[] passChars = password.toCharArray();
            PBEKeySpec spec = new PBEKeySpec(passChars, saltBytes, NITERS, KEY_LEN * Byte.SIZE);
            byte[] hashBytes = skf.generateSecret(spec).getEncoded();
            hashStr = Base64.getEncoder().encodeToString(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            Log.e("NextGenPos", "Error generating authentication key", e);
        }
        return hashStr;
    }
}
