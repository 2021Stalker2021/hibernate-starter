package com.dmdev.dto;

import com.dmdev.entity.PersonalInfo;
import com.dmdev.entity.Role;

public record UserCreateDto(PersonalInfo personalInfo,
                            String userName,
                            String info,
                            Role role,
                            Integer companyId) {
}
