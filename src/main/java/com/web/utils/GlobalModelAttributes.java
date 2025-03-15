package com.web.utils;

import com.web.entity.User;
import com.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributes {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserUtils userUtils;

    @ModelAttribute
    public void addGlobalAttributes(Model model) {
        model.addAttribute("danhMucListGlobal", categoryRepository.findAll());
        User user = userUtils.getUserWithAuthority();
    }
}
