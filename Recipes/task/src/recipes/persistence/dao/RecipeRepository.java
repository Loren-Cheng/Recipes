package recipes.persistence.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;
import recipes.entity.Recipe;

import java.util.List;

@Component
public interface RecipeRepository extends JpaRepository<Recipe, Long>, PagingAndSortingRepository<Recipe, Long> {

    @Query(value = "SELECT * FROM recipe  WHERE category = Upper(?1) ORDER BY DATE desc ",
            nativeQuery = true)
    public List<Recipe> findAllByCategoryIs(String category);


    @Query(value = "SELECT * FROM recipe WHERE name like %?1% ORDER BY DATE desc ",
            nativeQuery = true)
    public List<Recipe> findAllByNameContainingIgnoreCase(String name);
}
