package recipes.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import recipes.entity.Direction;

@Component
public interface DirectionRepository extends JpaRepository<Direction,Long> {
}
