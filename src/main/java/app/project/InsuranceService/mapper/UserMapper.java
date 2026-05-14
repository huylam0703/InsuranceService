package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.User.UserCreationRequest;
import app.project.InsuranceService.dto.request.User.UserUpdateRequest;
import app.project.InsuranceService.dto.response.User.UserResponse;
import app.project.InsuranceService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(UserUpdateRequest request,@MappingTarget User user);
}
