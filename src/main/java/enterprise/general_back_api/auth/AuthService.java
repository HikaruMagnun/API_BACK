package enterprise.general_back_api.auth;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import enterprise.general_back_api.auth.dto.AuthLoginRequest;
import enterprise.general_back_api.auth.dto.AuthRegisterRequest;
import enterprise.general_back_api.auth.dto.AuthResponse;
import enterprise.general_back_api.token.Token;
import enterprise.general_back_api.token.TokenRepository;
import enterprise.general_back_api.token.jwt.JWTService;
import enterprise.general_back_api.user.Role;
import enterprise.general_back_api.user.User;
import enterprise.general_back_api.user.UserRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

        private final TokenRepository tokenRepository;
        private final PasswordEncoder passwordEncoder;
        private final UserRepository userRepository;
        private final JWTService jwtService;
        private final AuthenticationManager authenticationManager;

        public AuthResponse register(AuthRegisterRequest request) {
                User user = User.builder()
                                .user_name(request.username())
                                .email(request.email())
                                .password(passwordEncoder.encode(request.password()))
                                .dni(request.dni())
                                .role(Role.CLIENT)
                                .build();
                User userSaved = userRepository.save(user);
                String jwtTokenAccess = jwtService.generateAccessToken(user);
                String jwtTokenRefresh = jwtService.generateRefreshToken(user);
                saveUserToken(userSaved, jwtTokenAccess);
                return AuthResponse.builder()
                                .accessToken(jwtTokenAccess)
                                .refreshToken(jwtTokenRefresh)
                                .build();
        }

        public AuthResponse authenticate(AuthLoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String jwtTokenAccess = jwtService.generateAccessToken(user);
                String jwtTokenRefresh = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, jwtTokenAccess);
                return AuthResponse.builder()
                                .accessToken(jwtTokenAccess)
                                .refreshToken(jwtTokenRefresh)
                                .build();
        }

        private void saveUserToken(User user, String jwtToken) {
                Token token = Token.builder()
                                .user(user)
                                .token(jwtToken)
                                .tokenType(Token.TokenType.BEARER)
                                .isExpired(false)
                                .isRevoked(false)
                                .build();
                tokenRepository.save(token);
        }

        private void revokeAllUserTokens(User user) {
                List<Token> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getID());
                if (!validUserTokens.isEmpty()) {
                        validUserTokens.forEach(token -> {
                                token.setIsExpired(true);
                                token.setIsRevoked(true);
                        });
                        tokenRepository.saveAll(validUserTokens);
                }
        }

        public AuthResponse refresh(String authentication) {
                @NotNull(message = "Token is required")
                String token = jwtService.extractToken(authentication);
                @NotNull(message = "Token is required")
                String email = jwtService.extractEmail(token);
                User user = userRepository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + email));
                if (!jwtService.isTokenValid(token, user)) {
                        throw new IllegalArgumentException("Invalid token");
                }

                String JwtTokenAccess = jwtService.generateAccessToken(user);
                String JwtTokenRefresh = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, JwtTokenAccess);
                return AuthResponse.builder()
                                .accessToken(JwtTokenAccess)
                                .refreshToken(JwtTokenRefresh)
                                .build();
        }

}
