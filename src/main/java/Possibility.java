import java.util.Objects;

public class Possibility {
    String name;
    int occurence;

    public Possibility(String name) {
        this.name = name;
        this.occurence = 0;
    }

    public void increment(){
        this.occurence++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Possibility that = (Possibility) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
