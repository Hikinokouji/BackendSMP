package by.backendsmp.repository;


import by.backendsmp.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User save(User user);

    Optional<User> findUserByUserName(String userName);

    @Query("""
            SELECT DISTINCT u FROM User u
            LEFT JOIN FETCH u.followers f
            LEFT JOIN FETCH f.follower
            LEFT JOIN FETCH u.following ff
            LEFT JOIN FETCH ff.streamer
            WHERE u.userName = :userName
        """)
    Optional<User> customFindUserWithRelations(@Param("userName") String userName);

    @Query("SELECT u.id FROM User u WHERE u.userName = :userName")
    Long customFindUserIdByUserName(String userName);


    //User getUserById(Long id);
}
