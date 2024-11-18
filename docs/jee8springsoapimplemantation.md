# 5. Implementing Spring Web Services for SOAP - Maven Dependencies

## Spring Web Services Introduction

The topic of Spring Web Services is vast and can easily fill an entire book. In this guide, we'll provide a concise introduction and practical example to get you started. For more comprehensive information, refer to the [official Spring Web Services documentation](https://docs.spring.io/spring-ws/docs/current/reference/html).

## Required Maven Dependencies

To begin, you need to include the necessary Maven dependencies in your `pom.xml` file:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.ws</groupId>
        <artifactId>spring-ws-core</artifactId>
        <version>3.1.1.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
        <scope>provided</scope>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.3.10</version>
    </dependency>
</dependencies>
```


### References

1. [Spring Web Services Documentation](https://docs.spring.io/spring-ws/docs/current/reference/)
2. [Spring Framework Reference Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)
3. [Jakarta EE Documentation](https://jakarta.ee/specifications/)

---
