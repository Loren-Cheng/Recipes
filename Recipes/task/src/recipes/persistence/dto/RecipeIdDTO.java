package recipes.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipeIdDTO {
    private long id;

    @Override
    public String toString() {
        return "RecipeIdDTO{" +
                "id=" + id +
                '}';
    }
}
