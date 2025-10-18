package by.backendsmp.controller.auth;

import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sec")
public class ProfilesRedaction {

    @PutMapping("/data")
    public ResponseEntity<String> updateProfile(){
        return ResponseEntity.ok().body("success");
    }
}
