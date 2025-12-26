package com.iit.controllersRest;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.iit.entities.Notification;
import com.iit.repositories.NotificationRepository;
import com.iit.services.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationRestController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public List<Notification> getAll() {
        return notificationService.getAll();
    }

    @GetMapping("/{id}")
    public Notification getById(@PathVariable Long id) {
        return notificationService.getById(id).orElse(null);
    }

    @PostMapping("/")
    public Notification save(@RequestBody Notification n) {
        return notificationService.save(n);
    }

    /*@PutMapping("/")
    public Notification update(@RequestBody Notification n) {
        return notificationRepos.save(n);
    }*/
    
    @PutMapping("/")
    public Notification update(@RequestBody Notification notification) {
        if (notification.getId() == null ||
            !notificationService.existsById(notification.getId())) {
            throw new RuntimeException("Notification non trouvée");
        }
        return notificationService.save(notification);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    	notificationService.delete(id);
    }
}

