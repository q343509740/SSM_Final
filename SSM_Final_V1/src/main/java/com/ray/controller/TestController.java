package com.ray.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Ray
 * @date 2018/7/14 0014
 */
@Controller
public class TestController {

    @RequestMapping("test")
    public String test(){
        return "test";
    }
}
