package com.iit.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iit.entities.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{

}
