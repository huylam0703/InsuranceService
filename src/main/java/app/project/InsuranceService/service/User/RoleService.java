package app.project.InsuranceService.service.User;

import app.project.InsuranceService.dto.request.RoleRequest;
import app.project.InsuranceService.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAllRoles();

}
