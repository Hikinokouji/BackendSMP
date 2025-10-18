package by.backendsmp.controller.unauth;

import by.backendsmp.entity.users.UserMainPageResponse;
import by.backendsmp.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(@RequestParam("user") String userName){
        Optional<UserMainPageResponse> userMainPageResponse = userService.searchUserPage(userName);
        if(!userMainPageResponse.isPresent()){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        return ResponseEntity.ok(userMainPageResponse);
    }

//    @PostMapping("/follower")
//    public ResponseEntity<?> follower(@RequestParam("streamerName") String followerOnStreamerName,
//                                      @RequestParam("followerName") String followerItName){
//
//        return ResponseEntity.ok("msg");
//    }
}
