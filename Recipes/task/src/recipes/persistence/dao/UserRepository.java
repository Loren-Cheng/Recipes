package recipes.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import recipes.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "select * from user where email = ?1 ", nativeQuery = true)
    public User findUserByEmail(String email);
}
