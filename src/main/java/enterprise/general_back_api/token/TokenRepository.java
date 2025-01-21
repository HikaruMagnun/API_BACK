package enterprise.general_back_api.token;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TokenRepository extends JpaRepository<Token, Long> {

    @Query(value = """
            select t from Token t inner join User u on t.user.id = u.id
            where u.id = :id and (t.isExpired = false or t.isRevoked = false)
            """)
    List<Token> findAllValidTokenByUser(Long id);
}
