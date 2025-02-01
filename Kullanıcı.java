import java.rmi.server.LogStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.*;


public class Kullanıcı extends  Oyuncu {

    public Kullanıcı(String oyuncuID, String oyuncuAdi, int Skor) {
        super(oyuncuID,oyuncuAdi,Skor);
    }

    private final static Logger LOGGER =
            Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    // Oyuncu sınıfının parametresiz constructor'ı çağrılıyor
    public Kullanıcı() {
        super();
    }


    //kullanıcı kart seçme olayı..
    @Override
    public Sporcu kartSec() {
        Scanner scanner=new Scanner(System.in);
        System.out.println("-----------------Kullancı kart seçiyor-----------------");
        System.out.println(" Seçim aralığı :    0 - " +(getKartListesi().size()-1) +  "  ) "); //index bazında;
        int index=scanner.nextInt();//kullancı kendi istediği kartı seçsin diye;
        //seçilen kart , kartlistesinden çıkarıldı!

        LOGGER.log(Level.INFO, String.valueOf(index));
        return    getKartListesi().remove(index);

    }

    //Getter-Setter

    @Override
    public void setOyuncuID(String oyuncuID) {
        super.setOyuncuID(oyuncuID);
    }

    @Override
    public void setOyuncuAdi(String oyuncuAdi) {
        super.setOyuncuAdi(oyuncuAdi);
    }

//    @Override
//    public void setKartListesi(ArrayList<String> kartListesi) {
//        super.setKartListesi(kartListesi);
//    }

    @Override
    public String getOyuncuID() {
        return super.getOyuncuID();
    }

    @Override
    public String getOyuncuAdi() {
        return super.getOyuncuAdi();
    }

//    @Override
//    public ArrayList<String> getKartListesi() {
//        return super.getKartListesi();
//    }

    @Override
    public void setSkor(int Skor) {
        super.setSkor(Skor);
    }

    @Override
    public int getSkor() {
        return super.getSkor();
    }

}
