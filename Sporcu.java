public  abstract  class Sporcu {
    private String sporcuIsim;
    private String sporcuTakim;
    protected String imagePath;

    //parametreli constructer
public Sporcu(String sporcuIsim, String sporcuTakim, String imagePath) {
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


    //this -> şuanki çalıştırılan nesne futbolcu türünde mi yoksa basketbolcu türünde mi diye kontrol ediyor;
    public String toString() {
        if (this instanceof Futbolcu) { //-> şu anki çalıştıırlan nesne Futbolcu mu?
            Futbolcu f = (Futbolcu) this; // Downcasting işlemi
            System.out.println("-----------------------------------------------------");
            return "Futbolcu\n[Ad: " + f.getSporcuIsim() + "\n Takım: " + f.getSporcuTakim() +
                    "\n Penaltı: " + f.getPenaltı() +
                    "\n Serbest Atış: " + f.getSerbestAtis() +
                    "\n Kaleci Karşı Karşıya: " + f.getKaleciKarsiKarsiya() + "]";
        } else if (this instanceof Basketbolcu) { //-> şu anki çalıştıırlan nesne Basketbolcu mu?
            Basketbolcu b = (Basketbolcu) this; // Downcasting işlemi
            System.out.println("-----------------------------------------------------");
            return "Basketbolcu\n[Ad: " + b.getSporcuIsim() + "\n Takım: " + b.getSporcuTakim() +
                    "\n İkilik: " + b.getIkilik() +
                    "\n Üçlük: " + b.getUcluk() +
                    "\n Serbest Atış: " + b.getSerbestAtis() + "]";
        }
        return "Sporcu[Ad: " + this.getSporcuIsim() + ", Takım: " + this.getSporcuTakim() + "]";
    }


    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public static void main(String[] args) {

    }




}
