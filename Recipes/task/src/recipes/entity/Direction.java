package recipes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Direction {

    @Id
    @Column(name = "id")
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonValue
    @NotNull
    @NotEmpty
    private String directions;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Recipe recipe;

    public Direction(String directions) {
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "Direction{" +
                "id=" + id +
                ", directions='" + directions + '\'' +
                '}';
    }
}
