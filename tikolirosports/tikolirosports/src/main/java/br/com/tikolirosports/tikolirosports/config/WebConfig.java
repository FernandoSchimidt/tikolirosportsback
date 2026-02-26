package br.com.tikolirosports.tikolirosports.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String caminho = System.getProperty("user.dir") + "/uploads/";

        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + caminho);
    }
}