package model;

public class Tiket {
    private int id; // Unique identifier for the ticket
    private String namaPembeli; // Name of the buyer
    private int filmId; // ID of the associated film
    private int jumlahTiket; // Number of tickets
    private double hargaTotal; // Total price of the tickets

    // Default constructor
    public Tiket() {
    }

    // Constructor with fields
    public Tiket(int id, String namaPembeli, int filmId, int jumlahTiket, double hargaTotal) {
        this.id = id;
        this.namaPembeli = namaPembeli;
        this.filmId = filmId;
        this.jumlahTiket = jumlahTiket;
        this.hargaTotal = hargaTotal;
    }

    // Getter and Setter Methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaPembeli() {
        return namaPembeli;
    }

    public void setNamaPembeli(String namaPembeli) {
        this.namaPembeli = namaPembeli;
    }

    public int getFilmId() {
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public int getJumlahTiket() {
        return jumlahTiket;
    }

    public void setJumlahTiket(int jumlahTiket) {
        this.jumlahTiket = jumlahTiket;
    }

    public double getHargaTotal() {
        return hargaTotal;
    }

    public void setHargaTotal(double hargaTotal) {
        this.hargaTotal = hargaTotal;
    }

    @Override
    public String toString() {
        // Return a formatted string representing the ticket
        return String.format("Tiket{id=%d, namaPembeli='%s', filmId=%d, jumlahTiket=%d, hargaTotal=%.2f}",
                id, namaPembeli, filmId, jumlahTiket, hargaTotal);
    }
}
