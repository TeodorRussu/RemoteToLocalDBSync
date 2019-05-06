package entity;

import java.util.Objects;

/**
 * Client entity
 */
public class Client {

    //fields
    private String denumire;
    private String adresa;
    private String localitate;
    private String judet;
    private String tara;

    //constructors
    public Client(String denumire, String adresa, String localitate, String judet, String tara) {
        this.denumire = denumire;
        this.adresa = adresa;
        this.localitate = localitate;
        this.judet = judet;
        this.tara = tara;
    }

    public Client(String firstname, String lastname, String company, String address_1, String address_2, String city, String postcode, String zone, String country) {
        this.tara = country.trim();
        this.judet = zone.trim();

        if (judet.isEmpty()) {
            judet = city.trim();
        }
        this.localitate = city.trim();
        String SPACE = " ";
        if (postcode != null && !postcode.trim().isEmpty()) {
            localitate = localitate + SPACE + postcode;
        }

        this.adresa = address_1.trim();
        if (address_2 != null && !address_2.trim().isEmpty()) {
            adresa = adresa + SPACE + address_2.trim();
        }

        if (company != null && !company.trim().isEmpty()) {
            this.denumire = company;
        } else {
            this.denumire = firstname.trim() + SPACE + lastname.trim();
        }
    }

    //getters

    public String getDenumire() {
        return denumire;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getLocalitate() {
        return localitate;
    }

    public String getJudet() {
        return judet;
    }

    public String getTara() {
        return tara;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(denumire, client.denumire) &&
                Objects.equals(adresa, client.adresa) &&
                Objects.equals(localitate, client.localitate) &&
                Objects.equals(judet, client.judet) &&
                Objects.equals(tara, client.tara);
    }

    @Override
    public int hashCode() {
        return denumire.hashCode() * 7 + adresa.hashCode() * 119 + localitate.hashCode() * 23 + judet.hashCode() * 37 +
                tara.hashCode() * 91;
    }

    @Override
    public String toString() {
        return "Client{" +
                "denumire='" + denumire + '\'' +
                ", tara='" + tara + '\'' +
                '}';
    }
}
