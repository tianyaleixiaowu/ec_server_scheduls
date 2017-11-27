package com.mindata.ecserver.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

/**
 * @author hanliqiang wrote on 2017/11/27
 */
@Controller
public class CoordinateController {

    @GetMapping("/baidu")
    public String baidu(String address) throws IOException, InterruptedException {
        return "baidu";
    }
}
