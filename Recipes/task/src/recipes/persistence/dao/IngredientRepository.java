package recipes.persistence.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import recipes.entity.Ingredient;

@Component
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
}
