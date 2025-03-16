package com.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class RoomController {

    @GetMapping("/room")
    public String room() {
        return "admin/room";
    }

    @GetMapping("/add-room")
    public String addRoom() {
        return "admin/addroom";
    }
}
