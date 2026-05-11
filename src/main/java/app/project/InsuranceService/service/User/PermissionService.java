package app.project.InsuranceService.service.User;

import app.project.InsuranceService.dto.request.PermissionRequest;
import app.project.InsuranceService.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void delete(String permission);
}
