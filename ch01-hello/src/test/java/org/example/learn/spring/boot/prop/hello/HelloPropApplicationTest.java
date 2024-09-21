package org.example.learn.spring.boot.prop.hello;

import org.example.learn.spring.boot.prop.hello.config.BizConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HelloPropApplicationTest {

    @Autowired
    BizConfig bizConfig;

    @Test
    public void test0() {
        String pwd = bizConfig.getPwd();
        System.out.println("pwd = " + pwd);
    }
}