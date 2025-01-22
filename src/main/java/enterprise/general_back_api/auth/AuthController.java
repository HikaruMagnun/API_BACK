package enterprise.general_back_api.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import enterprise.general_back_api.auth.dto.AuthLoginRequest;
import enterprise.general_back_api.auth.dto.AuthRegisterRequest;
import enterprise.general_back_api.auth.dto.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody(required = true) AuthRegisterRequest request) {
        AuthResponse authresponse = authService.register(request);
        return ResponseEntity.ok(authresponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponse> authenticate(@Valid @RequestBody(required = true) AuthLoginRequest request) {
        AuthResponse authresponse = authService.authenticate(request);
        return ResponseEntity.ok(authresponse);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authenticationHeader) {
        AuthResponse authResponse = authService.refresh(authenticationHeader);
        return ResponseEntity.ok(authResponse);
    }

}
