package com.huongnguyen.dto.response;

import java.util.List;

public record LoginResponse(String token,
                            String type,
                            String refreshToken,
                            String email,
                            List<String> roles
) { }
