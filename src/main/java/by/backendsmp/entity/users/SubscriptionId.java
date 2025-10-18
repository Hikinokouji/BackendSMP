package by.backendsmp.entity.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionId implements Serializable {
    private Long follower;
    private Long streamer;
}
