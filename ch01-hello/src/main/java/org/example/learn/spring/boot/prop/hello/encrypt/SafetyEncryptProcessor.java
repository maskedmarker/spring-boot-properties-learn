package org.example.learn.spring.boot.prop.hello.encrypt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.util.StringUtils;

import java.util.Base64;
import java.util.Properties;

public class SafetyEncryptProcessor implements EnvironmentPostProcessor {

    public static final String PREFIX = "ENC(";
    public static final String SUFFIX = ")";

    public static final String BIZ_CONFIG_PWD = "biz.config.pwd";
    public static final String PASSWORD_ENABLE_ENCRYPT = "password.enable.encrypt";
    public static final String PASSWORD_ENABLE_ENCRYPT_ENABLE = "true";

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String enableEncrypt = environment.getProperty(PASSWORD_ENABLE_ENCRYPT);
        if (!PASSWORD_ENABLE_ENCRYPT_ENABLE.equals(enableEncrypt)) {
            return;
        }

        String password = environment.getProperty(BIZ_CONFIG_PWD);
        if (StringUtils.isEmpty(password)) {
            return;
        }

        byte[] decode = Base64.getDecoder().decode(password);
        byte[] messageDe;
        try {
            messageDe = RSAEncryptDecrypt.decrypt(decode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Properties properties = new Properties();
        properties.setProperty(BIZ_CONFIG_PWD, new String(messageDe));
        PropertiesPropertySource propertiesPropertySource = new PropertiesPropertySource(BIZ_CONFIG_PWD, properties);
        environment.getPropertySources().addFirst(propertiesPropertySource);
    }
}