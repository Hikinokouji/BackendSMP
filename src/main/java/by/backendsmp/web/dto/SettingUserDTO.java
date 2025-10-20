package by.backendsmp.web.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SettingUserDTO {
    private String userName;
    private String email;
    private String streamKey;
    private String description;
}
