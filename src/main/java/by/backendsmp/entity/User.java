package by.backendsmp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "userName", unique = true, nullable = false)
    private String userName;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", unique = true, nullable = false)
    private String password;

    @Column(name = "birthDate", nullable = false)
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate birthDate;

    @Column(name = "createdAt",  nullable = false, columnDefinition = "TIMESTAMP(0)")
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @Column(name = "userAvatar")
    private String userAvatar;

    @Column(name = "profileAvatar")
    private String profileAvatar;

    @Column(name = "streamKey")
    private String streamKey;

    @Column(name = "profileDescription")
    private String description;

    //Потрібно додати індексацію
    // Користувач як стрімер — на нього підписуються
    @OneToMany(mappedBy = "streamer", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Subscription> followers = new HashSet<>();

    // Користувач як підписник — він підписується на інших
    @OneToMany(mappedBy = "follower", cascade = CascadeType.ALL,  orphanRemoval = true)
    private Set<Subscription> following = new HashSet<>();

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
