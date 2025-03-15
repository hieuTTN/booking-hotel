package com.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class HotelController {

    @GetMapping("/hotel")
    public String hotel() {
        return "admin/hotel";
    }

    @GetMapping("/add-hotel")
    public String addHotel() {
        return "admin/addhotel";
    }
}
