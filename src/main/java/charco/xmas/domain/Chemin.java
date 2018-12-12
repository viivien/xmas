package charco.xmas.domain;

import java.util.Objects;

public class Chemin {
    private Direction direction;

    public Chemin(Direction direction) {
        this.direction = direction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chemin chemin = (Chemin) o;
        return direction == chemin.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction);
    }
}
