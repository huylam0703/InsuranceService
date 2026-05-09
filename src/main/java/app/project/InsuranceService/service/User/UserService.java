package app.project.InsuranceService.service.User;

import app.project.InsuranceService.dto.request.UserCreationRequest;
import app.project.InsuranceService.dto.request.UserUpdateRequest;
import app.project.InsuranceService.dto.response.UserResponse;
import app.project.InsuranceService.entity.User;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getUserById(String userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UserUpdateRequest request, String userId);

    void deleteUser(String userId);

    public UserResponse getMyInfo();
}
