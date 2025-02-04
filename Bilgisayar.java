import java.util.ArrayList;
import java.util.Random;
public class Bilgisayar extends  Oyuncu{

    public Bilgisayar(String oyuncuID, String oyuncuAdi, int Skor){
        super(oyuncuID,oyuncuAdi,Skor);
    }

    // Oyuncu sınıfının parametresiz constructor'ı çağrılıyor
    public Bilgisayar(){
  super();


    }

    //Bilgisayar random olarak
    //aldığı kartlar arasından yine random kart seçerek ortaya koyacaktır.  -> tamamen random kart seçilecek bilgiseyarda;
    @Override
    public Sporcu kartSec() {
//        System.out.println("--------------------Bilgiseyar sınıfına özgü kart seçme -------------------------------");
        Random random = new Random();
        int choosen_index=random.nextInt(getKartListesi().size());
       return getKartListesi().remove(choosen_index);           //random seçilen kart çıkarıldı...


    }



    //Getter-Setter

    @Override
    public int getSkor() {
        return super.getSkor();
    }



    @Override
    public String getOyuncuAdi() {
        return super.getOyuncuAdi();
    }

    @Override
    public String getOyuncuID() {
        return super.getOyuncuID();
    }



    @Override
    public void setOyuncuAdi(String oyuncuAdi) {
        super.setOyuncuAdi(oyuncuAdi);
    }

    @Override
    public void setOyuncuID(String oyuncuID) {
        super.setOyuncuID(oyuncuID);
    }

    @Override
    public void setSkor(int Skor) {
        super.setSkor(Skor);
    }


}

