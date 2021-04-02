package cn.tosptream.data.ctrl;

import cn.tosptream.data.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebCtrl {
    @Autowired
    private WebService service;

    @GetMapping("/all")
    public String home(Model model) {
        model.addAttribute("configs", service.getAll());

        return "home";
    }

}
