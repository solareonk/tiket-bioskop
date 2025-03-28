package model;

public class Film {
    private int id;
    private String judul;
    private String genre;
    private int durasi; // Durasi dalam menit
    private double harga;
    private String deskripsi;

    public Film() {
    }

    public Film(int id, String judul, String genre, int durasi, double harga, String deskripsi) {
        this.id = id;
        this.judul = judul;
        this.genre = genre;
        this.durasi = durasi;
        this.harga = harga;
        this.deskripsi = deskripsi;
    }

    // Getter and Setter Methods
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDurasi() {
        return durasi;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    /**
     * Format duration from minutes to hours and minutes.
     *
     * @return Formatted duration string.
     */
    public String formatDurasi() {
        int jam = durasi / 60;
        int menit = durasi % 60;
        return jam + " jam " + menit + " menit";
    }

    @Override
    public String toString() {
        // Return film title for dropdowns or logs
        return this.judul;
    }
}
