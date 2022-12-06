package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    @Column(name = "id", nullable = false)
    private long id;

    @Column
    @JsonProperty
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Column
    @JsonProperty
    @NotNull
    @NotEmpty
    @NotBlank
    private String category;

    @Column
    @UpdateTimestamp
    @DateTimeFormat
    private LocalDateTime date;

    @Column
    @JsonProperty
    @NotNull
    @NotEmpty
    @NotBlank
    private String description;

    @OneToMany
    @JoinColumn(name = "recipe_id")
    @NotNull
    @NotEmpty
    private List<Ingredient> ingredients = new LinkedList<>();

    @OneToMany
    @JoinColumn(name = "recipe_id")
    @NotNull
    @NotEmpty
    private List<Direction> directions = new LinkedList<>();

    @Column
    @JsonIgnore
    private String email;

    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", ingredients=" + ingredients +
                ", directions=" + directions +
                ", email='" + email + '\'' +
                '}';
    }
}
