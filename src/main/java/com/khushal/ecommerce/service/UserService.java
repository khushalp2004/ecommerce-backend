package com.khushal.ecommerce.service;

import com.khushal.ecommerce.dto.UserProfileResponse;

public interface UserService {
    UserProfileResponse getCurrentUserProfile(String email);
}
