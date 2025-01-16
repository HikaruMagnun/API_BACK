package enterprise.general_back_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import enterprise.general_back_api.entity.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {

}
