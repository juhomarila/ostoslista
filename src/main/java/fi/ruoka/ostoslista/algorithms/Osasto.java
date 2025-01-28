package fi.ruoka.ostoslista.algorithms;

import java.util.ArrayList;
import java.util.List;

public class Osasto {

    private int id;
    private String nimi;

    public Osasto(int id, String nimi) {
        this.id = id;
        this.nimi = nimi;
    }

    public String getNimi() {
        return nimi;
    }

    public int getId() {
        return id;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Osasto{" +
                "id=" + id +
                ", nimi='" + nimi + '\'' +
                '}';
    }

    public static List<Osasto> getAllOsastot() {
        List<Osasto> osastot = new ArrayList<>();
        osastot.add(new Osasto(1, "Urheilutarvikkeet"));
        osastot.add(new Osasto(2, "Elektroniikka"));
        osastot.add(new Osasto(3, "Kengät"));
        osastot.add(new Osasto(4, "Vaatteet"));
        osastot.add(new Osasto(5, "Kirjat/Askartelu"));
        osastot.add(new Osasto(6, "Lelut"));
        osastot.add(new Osasto(7, "Patterit/Lamput/Auto"));
        osastot.add(new Osasto(8, "Eläintarvikkeet"));
        osastot.add(new Osasto(9, "Taloustarvikkeet"));
        osastot.add(new Osasto(10, "Kauneudenhoito"));
        osastot.add(new Osasto(11, "Pesuaineet"));
        osastot.add(new Osasto(12, "Pyyhkeet/Lakanat"));
        osastot.add(new Osasto(13, "Perunat"));
        osastot.add(new Osasto(14, "Yrtit"));
        osastot.add(new Osasto(15, "Hedelmät"));
        osastot.add(new Osasto(16, "Vihannekset"));
        osastot.add(new Osasto(17, "Leivät"));
        osastot.add(new Osasto(18, "Lihat"));
        osastot.add(new Osasto(19, "Maitotuotteet"));
        osastot.add(new Osasto(20, "Kinkut"));
        osastot.add(new Osasto(21, "Ruokamakkarat"));
        osastot.add(new Osasto(22, "Voit"));
        osastot.add(new Osasto(23, "Juustot"));
        osastot.add(new Osasto(24, "Kananmunat"));
        osastot.add(new Osasto(25, "Jugurtit"));
        osastot.add(new Osasto(26, "Pastakastikkeet"));
        osastot.add(new Osasto(27, "Pastat/Riisit"));
        osastot.add(new Osasto(28, "TexMex"));
        osastot.add(new Osasto(29, "Suola/Sokeri"));
        osastot.add(new Osasto(30, "Ketsupit/Sinapit"));
        osastot.add(new Osasto(31, "Mausteet"));
        osastot.add(new Osasto(32, "Säilykkeet"));
        osastot.add(new Osasto(33, "Kahvi/tee/kaakao"));
        osastot.add(new Osasto(34, "Juomat"));
        osastot.add(new Osasto(35, "Karkit"));
        osastot.add(new Osasto(36, "Sipsit"));
        osastot.add(new Osasto(37, "Pähkinät ja urheilujuomat"));
        osastot.add(new Osasto(38, "Jäätelöt"));
        osastot.add(new Osasto(39, "Kalapakasteet"));
        osastot.add(new Osasto(40, "Pizzapakasteet"));
        osastot.add(new Osasto(41, "Vihannespakasteet"));
        osastot.add(new Osasto(42, "Juhlakortit"));
        osastot.add(new Osasto(43, "Puutarha"));
        osastot.add(new Osasto(44, "Muut"));
        return osastot;
    }
}
