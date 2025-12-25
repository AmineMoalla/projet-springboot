package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Notification;
import com.iit.repositories.NotificationRepository;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private NotificationRepository notificationRepos;

    @GetMapping("/")
    public List<Notification> getAll() {
        return notificationRepos.findAll();
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Long id) {
        return notificationRepos.findById(id).orElse(null);
    }

    @PostMapping("/")
    public Notification save(@RequestBody Notification n) {
        return notificationRepos.save(n);
    }

    @PutMapping("/")
    public Notification update(@RequestBody Notification n) {
        return notificationRepos.save(n);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        notificationRepos.deleteById(id);
    }
}

