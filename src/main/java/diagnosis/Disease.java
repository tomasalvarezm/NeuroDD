package diagnosis;

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
        return "Disease{" +
                "name='" + name + '\'' +
                ", index_disease=" + score +
                '}';
    }
}
