package com.gradution.chao.graductiondesign.config;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class ErrorPageConfig implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {

        if("404".equals(String.valueOf(status))){
            return new ModelAndView("/error404");
        }else if("400".equals(String.valueOf(status))){
            return new ModelAndView("/error400");
        }else if("401".equals(String.valueOf(status))){
            return new ModelAndView("/error401");
        }else if("500".equals(String.valueOf(status))){
            return new ModelAndView("/error500");
        }else{
            return new ModelAndView("/error404");
        }
    }

}
