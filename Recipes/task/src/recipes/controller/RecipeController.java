package recipes.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import recipes.api.RecipeService;
import recipes.entity.Recipe;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@Validated
public class RecipeController {

    RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/api/recipe/new")
    @ResponseBody
    public Object addRecipe(@AuthenticationPrincipal UserDetails details, @RequestBody Recipe recipe) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(recipe));
//        System.out.println(objectMapper.writeValueAsString(details));
        try {
            recipe.setEmail(getLoggedInUser());
            return recipeService.save(recipe);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(400, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/api/recipe/{theId}")
    @ResponseBody
    public Recipe findById(@PathVariable long theId) {
        return recipeService.findRecipeById(theId);
    }

    @DeleteMapping("/api/recipe/{theId}")
    @ResponseBody
    @Synchronized
    public ResponseEntity<Integer> deleteOpportunities(@PathVariable long theId) {
        Recipe theRecipe = recipeService.findRecipeById(theId);
        Optional<Recipe> recipe = Optional.ofNullable(recipeService.findRecipeById(theId));
        if (theRecipe == null) {
            throw new RuntimeException("Recipe id not found - " + theId);
            // throw exception if null
        } else if (!getLoggedInUser().equals(theRecipe.getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only delete your own recipes.");
        }
        return recipeService.deleteById(theId);
    }

    @PutMapping("/api/recipe/{theId}")
    @ResponseBody
    @Synchronized
    public Object updateRecipe(@PathVariable Long theId, @Valid @RequestBody Recipe theRecipe) throws JsonProcessingException {
        theRecipe.setId(theId);
        Optional<Recipe> databaseRecipe = Optional.ofNullable(recipeService.findRecipeById(theId));
        if (!getLoggedInUser().equals(databaseRecipe.get().getEmail())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can only update your own recipes.");
        } else if (recipeService.findRecipeById(theId) != null) {
            theRecipe.setEmail(getLoggedInUser());
            recipeService.save(theRecipe);
            return new ResponseEntity<String>("No Content", HttpStatus.NO_CONTENT);
        } else {
            return HttpStatus.NOT_FOUND;
        }
    }


    @GetMapping(path = "/api/recipe/search", params = {"category"})
    @ResponseBody
    public List<Recipe> findAllByCategoryIs(final String category) {
        return recipeService.findAllByCategoryIs(category);
    }

    @GetMapping(path = "/api/recipe/search", params = {"name"})
    @ResponseBody
    public List<Recipe> findAllByNameContainingIgnoreCase(final String name) {
        return recipeService.findAllByNameContainingIgnoreCase(name);
    }

    public String getLoggedInUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}
