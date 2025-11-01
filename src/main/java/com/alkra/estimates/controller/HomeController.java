package com.alkra.estimates.controller;

import com.alkra.estimates.model.ProductType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("productTypes", ProductType.values());
        return "index";
    }
}
