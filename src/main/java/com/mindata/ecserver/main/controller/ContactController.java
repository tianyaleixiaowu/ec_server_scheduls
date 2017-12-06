package com.mindata.ecserver.main.controller;

import com.mindata.ecserver.main.manager.ContactManager;
import com.mindata.ecserver.main.service.EsContactService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wuweifeng wrote on 2017/11/21.
 */
@RequestMapping("/contact")
@RestController
public class ContactController {
    @Resource
    private ContactManager contactManager;
    @Resource
    private EsContactService esContactService;

    @RequestMapping("/completeProvince")
    public Object completeProvince() {
        contactManager.completeAreaCode();
        return "补齐完毕";
    }

    /**
     * 删除Contact
     */
    @RequestMapping("/delete")
    public Object deleteContact(String ids) {
        esContactService.deleteByIds(ids);
        return contactManager.deleteByIds(ids);
    }
}
