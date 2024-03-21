package diagnosis;

public class Symptom {

    private String name;

    public Symptom(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "name='" + name + '\'' +
                '}';
    }
}
