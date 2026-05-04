package app.project.InsuranceService.service.User.impl;

import app.project.InsuranceService.dto.request.UserCreationRequest;
import app.project.InsuranceService.dto.request.UserUpdateRequest;
import app.project.InsuranceService.dto.response.UserResponse;
import app.project.InsuranceService.entity.User;
import app.project.InsuranceService.exception.AppException;
import app.project.InsuranceService.exception.ErrorCode;
import app.project.InsuranceService.mapper.UserMapper;
import app.project.InsuranceService.repository.UserRepository;
import app.project.InsuranceService.service.User.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;


    @Override
    public UserResponse createUser(UserCreationRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTS);
        }

        User user = userMapper.toUser(request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse getUserById(String userId) {
        return userMapper.toUserResponse(userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toUserResponse).toList();
    }

    @Override
    public UserResponse updateUser(UserUpdateRequest request, String userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userMapper.updateUser(request, user);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }
}
