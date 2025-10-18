package by.backendsmp.entity;

import by.backendsmp.entity.users.SubscriptionId;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;


import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "subscriptions")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //На когось підписались
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "streamer_id")
    private User streamer;

    //Хто підписався
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    private LocalDateTime createdAt = LocalDateTime.now();
}
