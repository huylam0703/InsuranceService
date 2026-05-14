package app.project.InsuranceService.service.User;

import app.project.InsuranceService.dto.request.User.UserCreationRequest;
import app.project.InsuranceService.dto.request.User.UserUpdateRequest;
import app.project.InsuranceService.dto.response.User.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse createUser(UserCreationRequest request);

    UserResponse getUserById(String userId);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(UserUpdateRequest request, String userId);

    void deleteUser(String userId);

    public UserResponse getMyInfo();
}
