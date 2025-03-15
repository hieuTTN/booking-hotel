package com.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TienIchController {

    @GetMapping("/tien-ich")
    public String danhmuc() {
        return "admin/tienich";
    }
}
