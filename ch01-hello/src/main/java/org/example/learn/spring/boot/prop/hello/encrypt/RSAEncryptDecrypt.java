package org.example.learn.spring.boot.prop.hello.encrypt;

import java.util.Base64;
import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSAEncryptDecrypt {
    // 公钥
    public static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqfkYJn8BHttyGrQYcoXTZK8Za" +
            "MVUkqhEdFIyWZhnUQz6Jr299IkBCrbTnKcQTn3ekyQl5/F+j2p8YuNQDBJiq46T/srI+zh1XIaTIKJoOI8M4ploKOztJ8IP" +
            "L9ucdnv/tEdGDD9kY5JILa5DQnem9HkaS55kGJX6Oet6CRiNekwiG4+K61SjyfqxuLcqm7v2gH2nvTnkci9FLwtErpdF4uT" +
            "Mv6LB46Z9Hpg5iVsHDbwnwqOCfirgwdalJQTy+jcn9UqqNTsneREOxTtt9JQEnsDE/4UIPAYiU6cQDRJ5YrXR56LYC/0g55" +
            "KpMVnMIJgS5Y/YVt66QHtUTCVmmSgxAQIDAQAB";

    // 私钥
    private static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCp+" +
            "RgmfwEe23IatBhyhdNkrxloxVSSqER0UjJZmGdRDPomvb30iQEKttOcpxBOfd6TJCXn8X6Panxi41AMEmKrjpP+" +
            "ysj7OHVchpMgomg4jwzimWgo7O0nwg8v25x2e/+0R0YMP2RjkkgtrkNCd6b0eRpLnmQYlfo563oJGI16TCIbj4rrVKPJ+" +
            "rG4tyqbu/aAfae9OeRyL0UvC0Sul0Xi5My/osHjpn0emDmJWwcNvCfCo4J+" +
            "KuDB1qUlBPL6Nyf1Sqo1Oyd5EQ7FO230lASewMT/hQg8BiJTpxANEnlitdHnotgL/SDnkqkxWcwgmBLlj" +
            "9hW3rpAe1RMJWaZKDEBAgMBAAECggEBAIHEloaVglJ/sf7nLp8IwxrkgB64QVhytUillKFItOBxau53A" +
            "HaYvr3iVW8NMWrruClYeMQ7YKe34d1RtMRyqPhXs2/cfFMoiJmqeNt6gt1jga/i9V4BfRJUm2mrXiorg" +
            "06s97LUFx3aCdcua1Vsqn+NkeDXvY3zuwXLXPFi2GjcRBnwZL9S2i8mxCSChWy8ePjYW34bJ7viTQ7hf" +
            "Hz5VNA0hqMQrmgzUADq5xelnhQKsEWBaHHhLTKGYF0yNmRJ7zQJkiSncxIrzD5dvRH88Ms2nJ7lJlb1F" +
            "YEpZ8URlK63ofOskvcpvXFH3g7yvi6j9rCRFAL/i2UnP+lFimQ+bs0CgYEA7ZdVrSGMsKRwaX7+lmCTu" +
            "5cR6V74ohZKyDyzp8PcOBxvPmjY5tIULK++12xyOpsMHAwAoKfRedRrvS9vm525g5A+fioALXE74/d2A7" +
            "E/psO6hO89W5AcA91wMTZXLLGZ8JD0LBQp3u4dNGyyZLpDMTgM1WUsted1mOn2msRkK68CgYEAtySKlvaL" +
            "8Rw70LMMYZUUJ6BbUsR85aJHZ3JPsisM01PgEgcJiZHfThVZHqUgEU0noqRDzIVVKk9C1Gqx+t6F3JqU8" +
            "Cewop+nIKboLH5vMr+7OqM5hXLSx9g8Mm779k9P4+u7Kvq4/fY38d7kVayr3TVb5g0jEo1ugsHonwMsKk8Cg" +
            "YBa791/EqRCx+2us0jGTdi9qCjW5d7MSzP8SB+LSs/zOg7qGD9MuYO3Rt0Inx1piQathXqIAzOOKdvC4XEaYtgqnv8" +
            "MUw8WVYzSyFiHOURfk/LEBr25WgMfB5Z1f5MGLEP7a7/JTz5ncUQEWMY+/3vQTt+6narrRNgh2wrkWd7tSQKBgGIs" +
            "YWpZUVz3UI0oXbu1iW9Qg4PTtkv2eKZYXaZZc2+ZJ6UiRpeLLZQS14oY5B7CKDwEKB/rXWLnyCBL7YpYXJOL/cjazd" +
            "HvGUzki9LGF9+xbbEaLEx/58OfA23ZlpFLpLy98cAxVJc2tHigje/rNtnGr7ObWTCpxhKr1YHf1n37AoGBAI64k/lV4" +
            "FJn57cJnIZPp1jjapxSouxO063Z5BTYVsA6/cqXgbNPt1S8TnZRaRB2qtVrN5otRuueO6GIRJf6XTXvwxLV4xQAA/Z5" +
            "g2q2gelK7/ChYRdVn6Fwtnwx4iPBkH6FK+eQ0E8BfcJ5f6XPWp8N7ZpaZrd+dyc3r7uW78gG";

    //加密
    public static byte[] encrypt(byte[] str, String publicKey) throws Exception {
        // base64编码的公钥
        byte[] decoded = Base64.getDecoder().decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA")
                .generatePublic(new X509EncodedKeySpec(decoded));
        // RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        return cipher.doFinal(str);
    }

    //解密
    public static byte[] decrypt(byte[] str) throws Exception {
        // base64编码的私钥
        byte[] decoded = Base64.getDecoder().decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA")
                .generatePrivate(new PKCS8EncodedKeySpec(decoded));
        // RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        return cipher.doFinal(str);
    }
}