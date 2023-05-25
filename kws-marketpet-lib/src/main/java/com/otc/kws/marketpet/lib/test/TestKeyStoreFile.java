package com.otc.kws.marketpet.lib.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class TestKeyStoreFile 
{
	static String KEY_STORE_FILE = "key-store.jks";
	static String KEY_STORE_TYPE = "pkcs12";
	static String PASSWORD = "Ez@work.Open-Gate.Operation_2022";
	static String SECRET_KEY_ALIAS = "encryption.secret-key";
	
	static String ALGORITHM = "PBKDF2WithHmacSHA256";
	static String SECRET_KEY = "bbcfb15b-4269-422d-bc53-a7136bb2cf24";
	static String SALT = "Ezwork@freewillsolutions.com_2020";
	static String CIPHER_TRANSFORMATION = "AES";
	
	
	public static void main(String[] args) throws Exception
	{
		SecretKey generatedSecretKey = createSecretKey();
		
		SecretKey loadedSecretKey = loadSecretKey();
		
		String rawData = "admin@ezwork";
		String encryptedData = encrypt(loadedSecretKey, rawData);
		String decryptedData = decrypt(loadedSecretKey, "YWRtaW5Aa3dzMTIzNGhlbGxvcHJvdG8=");
		
		System.out.println("rawData => " + rawData);
		System.out.println("encryptedData => " + encryptedData);
		System.out.println("decryptedData => " + decryptedData);
	}
	
	protected static SecretKey createSecretKey() throws Exception
	{
		KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
		
		ks.load(null, PASSWORD.toCharArray());
		
		KeyStore.ProtectionParameter password = new KeyStore.PasswordProtection(PASSWORD.toCharArray());
		
		SecretKey generatedSecretKey = generateSecretKey();
		KeyStore.SecretKeyEntry secretKeyEntry = new KeyStore.SecretKeyEntry(generatedSecretKey);
		ks.setEntry(SECRET_KEY_ALIAS, secretKeyEntry, password);
		
		try (FileOutputStream fos = new FileOutputStream(KEY_STORE_FILE)) {
		    ks.store(fos, PASSWORD.toCharArray());
		}
		
		return generatedSecretKey;
	}
	
	protected static SecretKey loadSecretKey() throws Exception
	{
		KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
		ks.load(new FileInputStream(KEY_STORE_FILE), PASSWORD.toCharArray());
		return (SecretKey) ks.getKey(SECRET_KEY_ALIAS, PASSWORD.toCharArray());
	}
	
	protected static SecretKey generateSecretKey() throws Exception
	{
	    SecretKeyFactory factory = SecretKeyFactory.getInstance(ALGORITHM);
	    KeySpec spec = new PBEKeySpec(SECRET_KEY.toCharArray(), SALT.getBytes(), 65536, 256);
	    SecretKey secret = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), CIPHER_TRANSFORMATION);
	    return secret;
	}
	
	protected static String encrypt(SecretKey secretKey, String rawData) throws Exception
	{
        Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encrypted = cipher.doFinal(rawData.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encrypted);
	}
	
	protected static String decrypt(SecretKey secretKey, String encryptedData) throws Exception
	{
		Cipher cipher = Cipher.getInstance(CIPHER_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(encryptedData.getBytes(StandardCharsets.UTF_8))));
	}
}