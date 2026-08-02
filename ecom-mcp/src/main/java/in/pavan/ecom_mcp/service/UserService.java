package in.pavan.ecom_mcp.service;


import in.pavan.ecom_mcp.dto.user_dto.UserRequest;
import in.pavan.ecom_mcp.dto.user_dto.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);
    List<UserResponse> getAllUsers();
    UserResponse getUserById(UUID userId);
    UserResponse updateUser(UUID userId, UserRequest userRequest);
    public UserResponse deleteUser(UUID userId);

}
