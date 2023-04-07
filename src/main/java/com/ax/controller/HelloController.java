package com.ax.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * className: HelloController
 * description:
 *
 * @author: axiang
 * date: 2023/4/5 15:25
 */
@RestController
public class HelloController {

    @RequestMapping("hello")
    public String hello() {
        return "hello";
    }
}
