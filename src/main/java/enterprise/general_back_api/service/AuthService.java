package enterprise.general_back_api.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import enterprise.general_back_api.auth.LoginRequest;
import enterprise.general_back_api.auth.RegisterRequest;
import enterprise.general_back_api.auth.TokenResponse;
import enterprise.general_back_api.token.Token;
import enterprise.general_back_api.token.TokenRepository;
import enterprise.general_back_api.user.Role;
import enterprise.general_back_api.user.User;
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

        public TokenResponse authenticate(LoginRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
                User user = userRepository.findByEmail(request.getEmail())
                                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
                String JwtTokenAccess = jwtService.generateAccessToken(user);
                String JwtTokenRefresh = jwtService.generateRefreshToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, JwtTokenAccess);
                return TokenResponse.builder()
                                .accessToken(JwtTokenAccess)
                                .refreshToken(JwtTokenRefresh)
                                .build();
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

}
