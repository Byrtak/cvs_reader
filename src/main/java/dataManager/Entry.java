package dataManager;

public class Entry {
    private final String ico_Firmy, nazev_firmy, adresa_firmy, email_zamestance, jmeno_zamestance, prijmeni_zamestance, datum_aktualizace;

    public Entry(String ico_Firmy, String nazev_firmy, String adresa_firmy, String email_zamestance, String jmeno_zamestance, String prijmeni_zamestance, String datum_aktualizace) {
        this.ico_Firmy = ico_Firmy;
        this.nazev_firmy = nazev_firmy;
        this.adresa_firmy = adresa_firmy;
        this.email_zamestance = email_zamestance;
        this.jmeno_zamestance = jmeno_zamestance;
        this.prijmeni_zamestance = prijmeni_zamestance;
        this.datum_aktualizace = datum_aktualizace;
    }

    public String getIco_Firmy() {
        return ico_Firmy;
    }

    public String getNazev_firmy() {
        return nazev_firmy;
    }

    public String getAdresa_firmy() {
        return adresa_firmy;
    }

    public String getEmail_zamestance() {
        return email_zamestance;
    }

    public String getJmeno_zamestance() {
        return jmeno_zamestance;
    }

    public String getPrijmeni_zamestance() {
        return prijmeni_zamestance;
    }

    public String getDatum_aktualizace() {
        return datum_aktualizace;
    }

    public boolean theSame(Entry entry) {
        return this.ico_Firmy.equals(entry.getIco_Firmy()) &&
                this.nazev_firmy.equals(entry.getNazev_firmy()) &&
                this.adresa_firmy.equals(entry.getAdresa_firmy()) &&
                this.email_zamestance.equals(entry.getEmail_zamestance()) &&
                this.jmeno_zamestance.equals(entry.getJmeno_zamestance()) &&
                this.prijmeni_zamestance.equals(entry.getPrijmeni_zamestance()) &&
                this.datum_aktualizace.equals(entry.getDatum_aktualizace());
    }
}
