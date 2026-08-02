package in.pavan.ecom_mcp.dto.user_dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserRequest(
        @NotBlank(message = "Name should not be null")
        String name,
        @NotBlank(message = "Email should not be null")
        @Email(message = "Provide a valid email")
        String email,
        @NotBlank(message = "Phone number should not be null")
        String phoneNo

){
}
