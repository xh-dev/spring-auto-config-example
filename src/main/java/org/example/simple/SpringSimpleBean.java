package org.example.simple;

import javax.annotation.PostConstruct;

/**
 * Simple Spring Bean
 */
public class SpringSimpleBean {

    /**
     * init method after bean creation
     */
    @PostConstruct
    public void init(){
        System.out.printf("[%s] created%n", SpringSimpleBean.class.getSimpleName());
    }
}
