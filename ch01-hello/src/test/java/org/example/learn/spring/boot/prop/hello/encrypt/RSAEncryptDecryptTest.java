package org.example.learn.spring.boot.prop.hello.encrypt;

import org.junit.jupiter.api.Test;

import java.util.Base64;

public class RSAEncryptDecryptTest {

    @Test
    public void test0() throws Exception {
        String plainText = "hello";
        byte[] message = plainText.getBytes();
        System.out.println("原始字符串为:" + new String(message));

        byte[] messageEn = RSAEncryptDecrypt.encrypt(message, RSAEncryptDecrypt.publicKey);
        String encryptPassword = new String(Base64.getEncoder().encode(messageEn));
        System.out.println("加密后的Base64字符串为:" + encryptPassword);

        String str = new String(Base64.getEncoder().encode(messageEn));
        byte[] decode = Base64.getDecoder().decode(str);
        byte[] messageDe = RSAEncryptDecrypt.decrypt(decode);
        System.out.println("还原后的字符串为:" + new String(messageDe));
    }
}