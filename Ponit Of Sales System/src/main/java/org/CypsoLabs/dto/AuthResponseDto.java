package org.CypsoLabs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.List;

@Data
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType="Bearer";
    private List<String> roles;

    public AuthResponseDto(String accessToken ,String refreshToken,List<String> roles) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;

    }
}
