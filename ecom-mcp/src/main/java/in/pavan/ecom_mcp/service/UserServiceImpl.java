package in.pavan.ecom_mcp.service;

import in.pavan.ecom_mcp.dto.user_dto.UserRequest;
import in.pavan.ecom_mcp.dto.user_dto.UserResponse;
import in.pavan.ecom_mcp.exceptions.UserAlreadyExistsException;
import in.pavan.ecom_mcp.exceptions.UserNotExistsException;
import in.pavan.ecom_mcp.model.User;
import in.pavan.ecom_mcp.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepo userRepo;
    public UserServiceImpl(UserRepo userRepo){
        this.userRepo=userRepo;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        if(userRepo.existsByEmail(userRequest.email())){
            throw new UserAlreadyExistsException("User already exists. User other email");
        }
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());
        user.setPhoneNo(userRequest.phoneNo());
        user = userRepo.save(user);
        return UserResponse.fromEntity(user);
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.findAll().stream().map(UserResponse::fromEntity).toList();
    }

    @Override
    public UserResponse getUserById(UUID userId) {
       User user = userRepo.findById(userId)
               .orElseThrow(()->new UserNotExistsException("No user exists with this user ID"));
       return UserResponse.fromEntity(user);
    }

    @Override
    public UserResponse updateUser(UUID userId, UserRequest userRequest) {

        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user ID"));
        user.setPhoneNo(userRequest.phoneNo());
        user.setName(userRequest.name());
        user.setEmail(userRequest.email());

        user = userRepo.save(user);

        return UserResponse.fromEntity(user);
    }

    @Override
    public UserResponse deleteUser(UUID userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(()->new UserNotExistsException("No user exists with this user ID"));
        userRepo.deleteById(userId);
        return UserResponse.fromEntity(user);
    }
}
