package diagnosis;

import java.util.Objects;

public class Disease {
    private String name;
    public float score;


    public Disease(String name) {
        this.name = name;
        this.score = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return name + "probability : " + score + "%\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disease disease = (Disease) o;
        return Objects.equals(name, disease.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
