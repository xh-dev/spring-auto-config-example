package org.example.simple;

import org.springframework.context.annotation.Bean;


/**
 * Auto configuration for simple spring bean
 */
//@Configuration
public class EnableSpringSimpleBeanConfig {
    @Bean
    public SpringSimpleBean springSimpleBean(){
        return new SpringSimpleBean();
    }
}
