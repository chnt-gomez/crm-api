package com.vmartinez.crm.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DummyController {

    @GetMapping("/test")
    public String triggerAdvicer() {
        return "OK";
    }
}
