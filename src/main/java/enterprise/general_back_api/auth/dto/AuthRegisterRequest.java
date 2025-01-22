package enterprise.general_back_api.auth.dto;

/**
 * DTO for the register request.
 */
public record AuthRegisterRequest(
                String username,
                String email,
                String password,
                String dni) {
}