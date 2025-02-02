public  abstract  class Sporcu {
    private String sporcuIsim;
    private String sporcuTakim;
    private String imagePath;

    //parametreli constructer
public Sporcu(String sporcuIsim, String sporcuTakim) {
    this.sporcuIsim = sporcuIsim;
    this.sporcuTakim = sporcuTakim;
    this.imagePath = imagePath;
}
//parametresiz constructer
public  Sporcu(){
this.sporcuIsim = null;
this.sporcuTakim = null;
}

abstract void sporcuPuaniGoster();
//kart puanını gösterir;


//Getter-Setter
    public String getSporcuIsim() {
        return sporcuIsim;
    }
    public void setSporcuIsim(String sporcuIsim) {
    this.sporcuIsim = sporcuIsim;
    }
    public String getSporcuTakim() {
    return sporcuTakim;
    }
    public void setSporcuTakim(String sporcuTakim) {
    this.sporcuTakim = sporcuTakim;
    }

    public String toString() {
        return "Sporcu[Ad:" + this.getSporcuIsim() + ", Takım:" + this.getSporcuTakim() + "]";
    }

    public String getImagePath() {
        return imagePath;
    }

    public static void main(String[] args) {

    }



}
