package com.smh.PostBlogWebApp.controller;

import com.smh.PostBlogWebApp.exception.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("${server.error.path:${error.path:/error}}")
public class CustomErrorController implements ErrorController {

    private final ErrorAttributes errorAttributes;
    private final boolean isDebug;

    @Autowired
    public CustomErrorController(ErrorAttributes errorAttributes,
                                 @Value("${debug:false}") String isDebug) {
        this.errorAttributes = errorAttributes;
        this.isDebug=isDebug.equals("true");
    }

    @RequestMapping
    public ErrorResponse response(HttpServletRequest request, HttpServletResponse response){
        return new ErrorResponse(HttpStatus.valueOf(response.getStatus()),getErrorAttributes(request));
    }

    private Map<String,Object> getErrorAttributes(HttpServletRequest request){
        return errorAttributes.getErrorAttributes(new ServletWebRequest(request), getErrorAttributeOptions());
    }

    private ErrorAttributeOptions getErrorAttributeOptions(){
        if(isDebug){
            return ErrorAttributeOptions.of(ErrorAttributeOptions.Include.values());
        }
        return ErrorAttributeOptions.defaults();
    }

    @Override
    @Deprecated
    public String getErrorPath() {
        return null;
    }

}
