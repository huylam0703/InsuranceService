package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.UserCreationRequest;
import app.project.InsuranceService.dto.request.UserUpdateRequest;
import app.project.InsuranceService.dto.response.UserResponse;
import app.project.InsuranceService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);

    void updateUser(UserUpdateRequest request,@MappingTarget User user);
}
