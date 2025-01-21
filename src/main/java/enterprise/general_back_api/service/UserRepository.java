package enterprise.general_back_api.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import enterprise.general_back_api.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String Username);
}
