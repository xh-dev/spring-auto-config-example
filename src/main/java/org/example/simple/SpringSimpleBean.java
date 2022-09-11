package org.example.simple;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class SpringSimpleBean {

    @PostConstruct
    public void init(){
        System.out.printf("[%s] created%n", SpringSimpleBean.class.getSimpleName());
    }
}
