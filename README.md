
# Setup

Library side gradle setup
```groovy
plugins {
    //...
    id 'maven-publish'  // *1 add maven publish plugin
    //...
}

dependencies {
    //..
    
    // Add basic spring boot libraries
    implementation group: 'org.springframework.boot', name: 'spring-boot-autoconfigure', version: '2.7.3'
    
    // Add @PostConstruct to class path
    implementation group: 'javax.annotation', name: 'javax.annotation-api', version: '1.3.2'
    //..
}

java {
    withSourcesJar() // output source code
    withJavadocJar() // output with java doc
}

publishing {
    publications {
        // publish with basic maven setup
        mavenJava(MavenPublication) {
            from components.java
        }
    }
}

```

## publish with command

For local build and publish (test only)
```shell
gradle clena build publishToMavenLocal 
```

For production publish
```shell
gradle clena build publish 
```

## Library user
```groovy

repositories {
    //...
    mavenLocal() //only in case local maven is needed
    //...
}
implementation group: 'org.example', name: 'spring-autoconfig-example', version: '1.0-SNAPSHOT'
```

# Spring simple bean creation
The bean **SpringSimpleBean** 
```java

@Component
public class SpringSimpleBean {

    @PostConstruct
    public void init(){
        System.out.printf("[%s] created%n", SpringSimpleBean.class.getSimpleName());
    }
}
```

There are cases the bean will be loaded into the library user project.\
**[ See git tag: stage/01-version1 ]**

### 1. If component scan is defined
```java
@SpringBootApplication(scanBasePackages="org.example")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
```

### 2. If autowired
```java
@SpringBootApplication()
public class Main {
    @Autowired
    SpringSimpleBean springSimpleBean;
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
```

# Spring auto configuration bean creation
**[ See tag: stage/02-custom-enable-annotation ]** \
General @EnableXXXX annotation bean configuration approach is achieved by below step
1. Remove the @Component annotation on target classes 
    ```java
    public class SpringSimpleBean {
        @PostConstruct
        public void init(){
            System.out.printf("[%s] created%n", SpringSimpleBean.class.getSimpleName());
        }
    }
    ```
2. Create Configuration class
    ```java
    public class EnableSpringSimpleBeanConfig {
        @Bean
        public SpringSimpleBean springSimpleBean(){
            return new SpringSimpleBean();
        }
    }
    ```
3. Create @EnableXXXX annotation \
   The key point is add the **EnableSpringSimpleBeanConfig** into @Import annotation. \
   So that when the library user include the @EnableSpringSimpleBean in their spring boot application. \
   The bean configuration in the class **EnableSpringSimpleBeanConfig** is created when load. 
    ```java
    @Retention(RUNTIME)
    @Target(TYPE)
    @Import(EnableSpringSimpleBeanConfig.class)
    public @interface EnableSpringSimpleBean {
    }
    ```
4. Include the @EnableXXXX in library user project
```java
// This is the library user project, not the spring-autoconfig-example
@SpringBootApplication
@EnableSpringSimpleBean 
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }
}
```