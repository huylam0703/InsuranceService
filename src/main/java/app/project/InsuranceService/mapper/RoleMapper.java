package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.PermissionRequest;
import app.project.InsuranceService.dto.request.RoleRequest;
import app.project.InsuranceService.dto.response.PermissionResponse;
import app.project.InsuranceService.dto.response.RoleResponse;
import app.project.InsuranceService.entity.Permission;
import app.project.InsuranceService.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
