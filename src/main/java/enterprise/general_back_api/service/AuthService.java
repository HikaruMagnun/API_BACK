package enterprise.general_back_api.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import enterprise.general_back_api.dto.LoginRequest;
import enterprise.general_back_api.dto.RegisterRequest;
import enterprise.general_back_api.dto.TokenResponse;
import enterprise.general_back_api.entity.Token;
import enterprise.general_back_api.entity.User;
import enterprise.general_back_api.repository.TokenRepository;
import enterprise.general_back_api.repository.UserRepository;
import enterprise.general_back_api.util.user_auth.Role;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponse register(RegisterRequest request) {
        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .dni(request.dni())
                .role(Role.CLIENT)
                .build();
        User userSaved = userRepository.save(user);
        String JwtTokenAccess = jwtService.generateAccessToken(user);
        String JwtTokenRefresh = jwtService.generateRefreshToken(user);
        saveUserToken(userSaved, JwtTokenAccess);
        return TokenResponse.builder()
                .accessToken(JwtTokenAccess)
                .refreshToken(JwtTokenRefresh)
                .build();
    }

    public TokenResponse login(LoginRequest request) {
        authenticationManager.authenticate(request);
    }

    public TokenResponse refresh(String authentication) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'refresh'");
    }

    private void saveUserToken(User user, String jwtToken) {
        final Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

}
