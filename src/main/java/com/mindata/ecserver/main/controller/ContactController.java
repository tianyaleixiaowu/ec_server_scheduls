package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.ContactManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/21.
 */
@RestController
public class ContactController {
    @Resource
    private ContactManager contactManager;

    @RequestMapping("/completeProvince")
    public Object completeProvince() {
        contactManager.completeAreaCode();
        return "补齐完毕";
    }
}
