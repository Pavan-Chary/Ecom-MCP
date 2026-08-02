package in.pavan.ecom_mcp.dto.user_dto;

import in.pavan.ecom_mcp.model.User;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String name,
        String email,
        String phoneNo
) {
    public static UserResponse fromEntity(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNo()
        );
    }
}
