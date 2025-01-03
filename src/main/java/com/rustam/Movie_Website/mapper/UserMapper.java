package com.rustam.Movie_Website.mapper;

import com.rustam.Movie_Website.dao.entity.BaseUser;
import com.rustam.Movie_Website.dao.entity.User;
import com.rustam.Movie_Website.dto.response.AdminRegisterResponse;
import com.rustam.Movie_Website.dto.response.UserRegisterResponse;
import com.rustam.Movie_Website.dto.response.UserUpdateResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface UserMapper {

    UserRegisterResponse toResponse(BaseUser baseUser);

    List<UserRegisterResponse> toResponses(List<BaseUser> baseUsers);

    UserUpdateResponse toUpdated(BaseUser baseUser);

    AdminRegisterResponse toAdminResponse(BaseUser user);
}
