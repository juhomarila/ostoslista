package fi.ruoka.ostoslista.algorithms;

public class Tuote {
    private String nimi;
    private Osasto osasto;
    private double hinta;

    public Tuote(String nimi, Osasto osasto, double hinta) {
        this.nimi = nimi;
        this.osasto = osasto;
        this.hinta = hinta;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public Osasto getOsasto() {
        return osasto;
    }

    public void setOsasto(Osasto osasto) {
        this.osasto = osasto;
    }

    public double getHinta() {
        return hinta;
    }

    public void setHinta(double hinta) {
        this.hinta = hinta;
    }

    @Override
    public String toString() {
        return "Tuote{" +
                "nimi='" + nimi + '\'' +
                ", osasto='" + osasto.getNimi() + '\'' +
                ", hinta=" + hinta +
                '}';
    }   
    
}
