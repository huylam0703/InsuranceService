package app.project.InsuranceService.service.Auth;

import app.project.InsuranceService.dto.request.Auth.PermissionRequest;
import app.project.InsuranceService.dto.response.Auth.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void delete(String permission);
}
