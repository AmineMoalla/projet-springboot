package com.iit.services;

import com.iit.entities.Notification;
import com.iit.repositories.NotificationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }      

    public List<Notification> getAll() {
        return repository.findAll();
    }

    public Optional<Notification> getById(Long id) {
        return repository.findById(id);
    }

    public Notification save(Notification notification) {
        return repository.save(notification);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
    
    
    public boolean existsById(Long id) {
        return repository.existsById(id);
    }
}
