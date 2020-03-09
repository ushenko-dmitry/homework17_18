package ru.mail.dimaushenko.webmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = {"ru.mail.dimaushenko.service", "ru.mail.dimaushenko.repository"})
@PropertySource({"classpath:/db/request/request.properties"})
public class AppConfig {

}
