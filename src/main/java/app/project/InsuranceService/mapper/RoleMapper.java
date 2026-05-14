package app.project.InsuranceService.mapper;

import app.project.InsuranceService.dto.request.Auth.RoleRequest;
import app.project.InsuranceService.dto.response.Auth.RoleResponse;
import app.project.InsuranceService.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);

}
