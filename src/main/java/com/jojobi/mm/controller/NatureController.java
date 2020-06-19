package com.jojobi.mm.controller;

import com.jojobi.mm.service.NatureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NatureController {

    private final NatureService natureService;

    public NatureController(NatureService natureService) {
        this.natureService = natureService;
    }

    @GetMapping("/nature")
    public String getAllTopLevelNatures(Model model) {
        model.addAttribute("natures", natureService.getAllTopLevelNatures());
        return "natures";
    }
}
