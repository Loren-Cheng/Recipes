package recipes.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import recipes.entity.Recipe;
import recipes.persistence.dto.RecipeIdDTO;

import java.util.List;

public interface RecipeService {

    ResponseEntity<RecipeIdDTO> save(Recipe recipe);

    Recipe findRecipeById(long theId);

    ResponseEntity<Integer> deleteById(long theId);

    List<Recipe> findAllByCategoryIs(String theCategory);

    List<Recipe> findAllByNameContainingIgnoreCase(String theName);
}
