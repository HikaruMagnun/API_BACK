package enterprise.general_back_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import enterprise.general_back_api.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String Username);
}
