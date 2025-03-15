package com.web.controller.admin;

import com.web.entity.User;
import com.web.enums.Role;
import com.web.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TaiKhoanAdmin {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = {"/tai-khoan"}, method = RequestMethod.GET)
    public String danhMucGet(Model model, @RequestParam(value = "role", required = false) Role role) {
        if(role == null){
            model.addAttribute("taiKhoanList", userRepository.findAll());
        }
        else{
            model.addAttribute("taiKhoanList", userRepository.findByRole(role));
        }
        model.addAttribute("roleSelect",role);
        return "admin/taikhoan";
    }

    @PostMapping("/update-role")
    public String updateRole(@RequestParam Long idUser, @RequestParam Role role) {
        User user = userRepository.findById(idUser).get();
        user.setRole(role);
        userRepository.save(user);
        return "redirect:tai-khoan?update-role-success=true";
    }


    @PostMapping("/lockOrUnlockUser")
    public String deleteCategory(RedirectAttributes redirectAttributes, HttpServletRequest request, @RequestParam("id") Long id){
        User user = userRepository.findById(id).get();
        String mess = "";
        if(user.getActived() == true){
            user.setActived(false);
            userRepository.save(user);
            mess = "Khóa tài khoản thành công";
        }
        else{
            user.setActived(true);
            userRepository.save(user);
            mess = "Mở khóa tài khoản thành công";
        }
        redirectAttributes.addFlashAttribute("message", mess);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }
}
