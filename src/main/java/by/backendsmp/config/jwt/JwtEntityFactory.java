package by.backendsmp.config.jwt;

import by.backendsmp.entity.User;

public class JwtEntityFactory {
    public static JwtEntity create(User user) {
        return new JwtEntity(
                user.getId(),
                user.getUserName(),
                user.getPassword());
    }
}
