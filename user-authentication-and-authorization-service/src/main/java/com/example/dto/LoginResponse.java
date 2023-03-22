package com.example.dto;

import com.example.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public record LoginResponse(
        User user,
        String accessToken,
        String refreshToken
) {
}
