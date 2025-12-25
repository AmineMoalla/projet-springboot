package com.iit.controllersThymleaf;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import com.iit.entities.Notification;
import com.iit.repositories.NotificationRepository;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private NotificationRepository notificationRepos;

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("notifications", notificationRepos.findAll());
        return "notifications"; // templates/notifications.html
    }

    @GetMapping("/form")
    public String formNotification(Model model) {
        model.addAttribute("notification", new Notification());
        return "formNotification"; // templates/formNotification.html
    }

    @PostMapping("/save")
    public String save(@Valid Notification n, BindingResult br) {
        if (br.hasErrors()) return "formNotification";
        notificationRepos.save(n);
        return "confirmation";
    }

    @GetMapping("/edit")
    public String edit(Model model, @RequestParam Long id) {
        Notification n = notificationRepos.findById(id).orElse(null);
        model.addAttribute("notification", n);
        return "editNotification"; // templates/editNotification.html
    }

    @PostMapping("/update")
    public String update(@Valid Notification n, BindingResult br) {
        if (br.hasErrors()) return "editNotification";
        notificationRepos.save(n);
        return "confirmation";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        notificationRepos.deleteById(id);
        return "redirect:index";
    }
}
