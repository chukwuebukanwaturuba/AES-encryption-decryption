package com.example.encryption;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

@RequiredArgsConstructor
@Service
public class AesServiceImpl implements AesService{

    private final AesRepo repo;
    public SecretKey generateKey(int n) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(n);
        return keyGenerator.generateKey();
    }
    public IvParameterSpec generateIv(){
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public  String encrypt(EncryptionRequest request)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, BadPaddingException, IllegalBlockSizeException
    {
        SecretKey key = generateKey(128);
			IvParameterSpec ivParameterSpec = generateIv();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, ivParameterSpec);
        byte[] cipherText = cipher.doFinal(request.getPassword().getBytes());
        EncryptEntity encryptEntity =  EncryptEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .secretKey(key)
                .ivParameterSpec(ivParameterSpec.getIV())
                .build();
        repo.save(encryptEntity);
        return Base64.getEncoder().encodeToString(cipherText);
    }
    @Transactional
    public  String decrypt( EncryptionRequest request)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException
            , BadPaddingException, IllegalBlockSizeException {
        EncryptEntity encryptEntity = repo.findByEmail(request.getEmail());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, encryptEntity.getSecretKey(),new IvParameterSpec(encryptEntity.getIvParameterSpec()));
        byte[] plainText= cipher.doFinal(Base64.getDecoder().decode(request.getPassword()));
        return new String(plainText);
    }


}
