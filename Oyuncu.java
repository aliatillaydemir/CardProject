import java.sql.SQLOutput;
import java.util.ArrayList;


//Oyuncu classımız abstract class oldu!
public abstract class  Oyuncu {
    private String oyuncuID;
    private String oyuncuAdi;
    private int Skor;
    private ArrayList<Sporcu> kartListesi; //-> arraylistimiz var sporcu objesi depoluyor ismi de kartlistesi;


    //parametreli constructer
    public Oyuncu(String oyuncuID, String oyuncuAdi, int Skor) {
        this.oyuncuID = oyuncuID;
        this.oyuncuAdi = oyuncuAdi;
        this.Skor = Skor;
        this.kartListesi = new ArrayList<Sporcu>();


    }

    //parametresiz constructer
    public Oyuncu() {
        this.oyuncuID = "";
        this.oyuncuAdi = "";
        this.Skor = 0;
        this.kartListesi = new ArrayList<>();
    }


    //oyuncunun skorunu göstermek:
    public void SkorGoster() {
        System.out.println((getOyuncuAdi() + " Skoru: " + getSkor()));
    }


    //her oyuncu için->bilgiseyar ve kullanıcı classları için(polimorfizmle alınan classslar için)
    public abstract Sporcu kartSec();


    //Getter-Setter

    public String getOyuncuID() {
        return oyuncuID;
    }

    public void setOyuncuID(String oyuncuID) {
        this.oyuncuID = oyuncuID;
    }

    //fonksiyon arraylist döndürüyor ve arraylist içersinde sporcu objesini depoluyor!---> önemli
    public ArrayList<Sporcu> getKartListesi() {
        return this.kartListesi;
    }


    public int getSkor() {
        return Skor;
    }

    public void setSkor(int Skor) {
        this.Skor = Skor;
    }

    public String getOyuncuAdi() {
        return oyuncuAdi;
    }

    public void setOyuncuAdi(String oyuncuAdi) {
        this.oyuncuAdi = oyuncuAdi;

    }
}