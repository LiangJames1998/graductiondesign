package com.gradution.chao.graductiondesign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class FileConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/uploaded").addResourceLocations("file:D://IDEA-workplace/graduction-design/src/main/webapp/");
        super.addResourceHandlers(registry);
    }
}
