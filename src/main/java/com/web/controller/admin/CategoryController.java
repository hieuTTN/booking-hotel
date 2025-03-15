package com.web.controller.admin;

import com.web.entity.Category;
import com.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/danhmuc")
    public String danhmuc() {
        return "admin/danhmuc";
    }

    @PostMapping(value = {"/addcategory"})
    public String addcategory(@RequestParam String name, @RequestParam(required = false) Long id, RedirectAttributes redirectAttributes) {
        Category category = new Category();
        category.setName(name);
        category.setId(id);
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("message", "Thêm danh mục thành công!");
        return "redirect:/admin/danhmuc";
    }

    @GetMapping(value = {"/deletecategory"})
    public String deletecategory(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryRepository.deleteById(id);
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Danh mục đã có liên kết, không thể xóa!");
        }
        redirectAttributes.addFlashAttribute("message", "Xóa danh mục thành công!");
        return "redirect:/admin/danhmuc";
    }
}
