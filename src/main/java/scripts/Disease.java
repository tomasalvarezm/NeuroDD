package scripts;

public class Disease {
    private String name;
    public int index_disease;

//    public String index_amyotrophicLateralSclerosis;
//    public String index_huntington;
//    public String index_multipleSclerosis;
//    public String index_myastheniaGravis;
//    public String index_parkinson;


    public Disease(String name) {
        this.name = name;
        this.index_disease = 0;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex_disease() {
        return index_disease;
    }

    public void setIndex_disease(int index_disease) {
        this.index_disease = index_disease;
    }
}
