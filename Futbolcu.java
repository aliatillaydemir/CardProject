public class Futbolcu extends Sporcu {
    private int penaltı;
    private  int serbestAtis;
    private int kaleciKarsiKarsiya;
    private boolean kartKullanildiMi;




    //parametreli constructer;
    public Futbolcu(String futbolcuAdi, String futbolcuTakim,int penaltı,int serbestAtis,int kaleciKarsiKarsiya) {
        super(futbolcuAdi,futbolcuTakim);
        this.penaltı=penaltı;
        this.serbestAtis=serbestAtis;
        this.kaleciKarsiKarsiya=kaleciKarsiKarsiya;
        this.kartKullanildiMi=false;
    }
    //parametresiz constructer;
    public Futbolcu(){
        super("","");
        this.penaltı=0;
        this.serbestAtis=0;
        this.kaleciKarsiKarsiya=0;
        this.kartKullanildiMi=false;

    }

    @Override
    public void sporcuPuaniGoster() {
    int toplam=serbestAtis+kaleciKarsiKarsiya+penaltı;
        System.out.println(getSporcuTakim() +"penaltı "+penaltı +"serbestAtis "+serbestAtis +"kaleciKarsiKarsiya "+kaleciKarsiKarsiya +" atarak toplam :" +toplam + "puan topladı." );
    }


    //Getter-Setter

    @Override
    public String getSporcuIsim() {
        return super.getSporcuIsim();
    }

    @Override
    public String getSporcuTakim() {
        return super.getSporcuTakim();
    }

    @Override
    public void setSporcuIsim(String sporcuIsim) {
        super.setSporcuIsim(sporcuIsim);
    }

    @Override
    public void setSporcuTakim(String sporcuTakim) {
        super.setSporcuTakim(sporcuTakim);
    }

    public boolean isKartKullanildiMi() {
        return kartKullanildiMi;
    }

    public int getKaleciKarsiKarsiya() {
        return kaleciKarsiKarsiya;
    }

    public int getPenaltı() {
        return penaltı;
    }

    public int getSerbestAtis() {
        return serbestAtis;
    }

    public void setKaleciKarsiKarsiya(int kaleciKarsiKarsiya) {
        this.kaleciKarsiKarsiya = kaleciKarsiKarsiya;
    }

    public void setKartKullanildiMi(boolean kartKullanildiMi) {
        this.kartKullanildiMi = kartKullanildiMi;
    }

    public void setPenaltı(int penaltı) {
        this.penaltı = penaltı;
    }

    public void setSerbestAtis(int serbestAtis) {
        this.serbestAtis = serbestAtis;
    }




}



