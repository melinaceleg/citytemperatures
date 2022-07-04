package com.candoit.weather.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Custom Controller that handles error request from url.
 *
 */
@Controller
public class UrlErrorController  implements ErrorController {


    @RequestMapping("/error")
    @ResponseBody
    ModelAndView error(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        String uri = String.valueOf(request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        String code = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
        String msg = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        mav.addObject(uri);
        mav.addObject(code);
        mav.addObject(msg);
        return mav;
    }

}
