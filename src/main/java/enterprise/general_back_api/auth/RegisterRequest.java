package enterprise.general_back_api.auth;

/**
 * DTO for the register request.
 */
public record RegisterRequest(
        String username,
        String email,
        String password,
        String dni) {
}