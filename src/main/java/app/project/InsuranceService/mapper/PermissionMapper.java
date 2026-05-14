package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Auth.PermissionRequest;
import app.project.InsuranceService.dto.response.Auth.PermissionResponse;
import app.project.InsuranceService.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
