package com.stupidzhe.api.controller;

import com.stupidzhe.api.bean.ResultBean;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Mr.W on 2017/5/27.
 */
@RestController
public class RedirectErrorController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping(value = "/error")
    public ResultBean handleError() {
        return new ResultBean(false);
    }
}
