package recipes.api;

import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import recipes.entity.Direction;
import recipes.entity.Ingredient;
import recipes.entity.Recipe;
import recipes.exception.ExceptionInRecipe;
import recipes.persistence.dao.DirectionRepository;
import recipes.persistence.dao.IngredientRepository;
import recipes.persistence.dao.RecipeRepository;
import recipes.persistence.dto.RecipeIdDTO;

import java.util.List;
import java.util.Optional;

@Service("Recipe")
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final DirectionRepository directionRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, DirectionRepository directionRepository, IngredientRepository ingredientRepository) {
        this.recipeRepository = recipeRepository;
        this.directionRepository = directionRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    @Synchronized
    public ResponseEntity<RecipeIdDTO> save(Recipe recipe) {
        RecipeIdDTO recipeIdDto = new RecipeIdDTO();
        List<Direction> directions = recipe.getDirections();
        List<Ingredient> ingredients = recipe.getIngredients();
        directionRepository.saveAll(directions);
        ingredientRepository.saveAll(ingredients);
        long id = recipeRepository.save(recipe).getId();
        recipeIdDto.setId(id);
        return new ResponseEntity(recipeIdDto, HttpStatus.OK);
    }

    @Override
    public Recipe findRecipeById(long theId) {
        Optional<Recipe> result = recipeRepository.findById(theId);
        Recipe theRecipe = null;
        if (result.isPresent()) {
            theRecipe = result.get();
        } else {
            throw new ExceptionInRecipe.RecipeNotFoundException();
        }
        return theRecipe;
    }

    @Override
    public ResponseEntity<Integer> deleteById(long theId) {
        recipeRepository.deleteById(theId);
        return new ResponseEntity(204, HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Recipe> findAllByCategoryIs(String theCategory){
        return recipeRepository.findAllByCategoryIs(theCategory);
    }

    @Override
    public List<Recipe> findAllByNameContainingIgnoreCase(String theName){
        return recipeRepository.findAllByNameContainingIgnoreCase(theName);
    }
}
