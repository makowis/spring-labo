package com.makowis.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class HelloControllerTests {

    @Autowired
    private HelloController controller;


    @Test void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void  helloTest() {
        var name = "World";
        assertThat(controller.hello(name)).isEqualTo(String.format("Hello %s!",name));
    }

}
