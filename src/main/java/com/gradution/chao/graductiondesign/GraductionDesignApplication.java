package com.gradution.chao.graductiondesign;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@Configuration
@SpringBootApplication
public class GraductionDesignApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraductionDesignApplication.class, args);
    }


    /**
     * 上传文件配置
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize(DataSize.parse("20480000KB"));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("20480000KB"));

        return factory.createMultipartConfig();
    }


}
