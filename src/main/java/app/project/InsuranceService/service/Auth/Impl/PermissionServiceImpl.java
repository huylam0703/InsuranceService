package app.project.InsuranceService.service.Auth.Impl;

import app.project.InsuranceService.dto.request.Auth.PermissionRequest;
import app.project.InsuranceService.dto.response.Auth.PermissionResponse;
import app.project.InsuranceService.entity.Permission;
import app.project.InsuranceService.mapper.PermissionMapper;
import app.project.InsuranceService.repository.PermissionRepository;
import app.project.InsuranceService.service.Auth.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public PermissionResponse create(PermissionRequest request) {
        Permission permission = permissionMapper.toPermission(request);

        permission = permissionRepository.save(permission);

        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public List<PermissionResponse> getAllPermissions() {
        var permissions = permissionRepository.findAll();

        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(String permission) {
        permissionRepository.deleteById(permission);
    }
}
