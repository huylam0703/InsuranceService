package app.project.InsuranceService.service.Auth;

import app.project.InsuranceService.dto.request.Auth.RoleRequest;
import app.project.InsuranceService.dto.response.Auth.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAllRoles();

}
