package by.backendsmp.entity.users;

import by.backendsmp.entity.Subscription;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class UserMainPageResponse {
    private String userName;
    private String description;
    private LocalDateTime createdDate;

    private String userAvatar;
    private String profileAvatar;

    private List<String> followers;
    private List<String>  streamers;

    private int followersCount;
    private int streamersCount;
}
