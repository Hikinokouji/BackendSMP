package by.backendsmp.controller.auth;

import by.backendsmp.service.auth.SettingService;
import by.backendsmp.web.dto.EditSettingDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sec")
@RequiredArgsConstructor
public class ProfilesController {
    private final SettingService settingService;

    @PutMapping("/data")
    public ResponseEntity<?> updateProfile(@RequestHeader("Authorization") String token,
                                                @RequestBody EditSettingDTO dto) {
        return settingService.editProfile(token, dto);
    }
}
