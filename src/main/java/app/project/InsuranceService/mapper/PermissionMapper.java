package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.PermissionRequest;
import app.project.InsuranceService.dto.request.UserCreationRequest;
import app.project.InsuranceService.dto.request.UserUpdateRequest;
import app.project.InsuranceService.dto.response.PermissionResponse;
import app.project.InsuranceService.dto.response.UserResponse;
import app.project.InsuranceService.entity.Permission;
import app.project.InsuranceService.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
