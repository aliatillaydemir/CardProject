public  class  Basketbolcu extends  Sporcu {
    private int ikilik;
    private int ucluk;
    private int serbestAtis;
    private boolean kartKullanildiMi;


    //parametreli constructer;
    public Basketbolcu(int ikilik, int ucluk, int serbestAtis, String basketbolcuAdi, String basketbolcuTakim, String imagePath) {
        super(basketbolcuAdi, basketbolcuTakim, imagePath);
        this.ikilik = ikilik;
        this.ucluk = ucluk;
        this.serbestAtis = serbestAtis;
        this.kartKullanildiMi = false;

    }

    //parametresiz constructer
    public Basketbolcu() {
        super("", "", "");
        this.ikilik = 0;
        this.ucluk = 0;
        this.serbestAtis = 0;
        this.kartKullanildiMi = false;
    }

    @Override
    public void sporcuPuaniGoster() {
        int toplam = ucluk + ikilik + serbestAtis;
        System.out.println(getSporcuTakim() + "ikilik " + ikilik + "ucluk " + ucluk + "serbestAtis " + serbestAtis + " atarak toplam :" + toplam + "puan topladı.");
    }

    //Getter-Setter

    @Override
    public void setSporcuTakim(String sporcuTakim) {
        super.setSporcuTakim(sporcuTakim);
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }

    @Override
    public String getSporcuTakim() {
        return super.getSporcuTakim();
    }

    @Override
    public void setSporcuIsim(String sporcuIsim) {
        super.setSporcuIsim(sporcuIsim);
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }

    @Override
    public String getSporcuIsim() {
        return super.getSporcuIsim();
    }

    public int getIkilik() {
        return ikilik;
    }

    public void setIkilik(int ikilik) {
        this.ikilik = ikilik;
    }

    public int getUcluk() {
        return ucluk;
    }

    public void setUcluk(int ucluk) {
        this.ucluk = ucluk;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public String getImagePath() {
        return super.getImagePath();
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;


    }
}
