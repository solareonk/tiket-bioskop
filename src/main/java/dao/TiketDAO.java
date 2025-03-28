package dao;

import koneksi.Koneksi;
import model.Tiket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TiketDAO {

    /**
     * Get all tickets from the database.
     *
     * @return List of Tiket objects.
     */
    public List<Tiket> getAllTiket() {
        List<Tiket> list = new ArrayList<>();
        String sql = "SELECT * FROM tiket";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tiket tiket = new Tiket();
                tiket.setId(rs.getInt("id"));
                tiket.setNamaPembeli(rs.getString("nama_pembeli"));
                tiket.setFilmId(rs.getInt("film_id"));
                tiket.setJumlahTiket(rs.getInt("jumlah_tiket"));
                tiket.setHargaTotal(rs.getDouble("harga_total"));
                list.add(tiket);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching tickets: " + e.getMessage());
        }
        return list;
    }

    /**
     * Insert a new ticket into the database.
     *
     * @param tiket Tiket object to be inserted.
     * @return True if insertion is successful, otherwise false.
     */
    public boolean insertTiket(Tiket tiket) {
        String sql = "INSERT INTO tiket (nama_pembeli, film_id, jumlah_tiket, harga_total) VALUES (?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tiket.getNamaPembeli());
            ps.setInt(2, tiket.getFilmId());
            ps.setInt(3, tiket.getJumlahTiket());
            ps.setDouble(4, tiket.getHargaTotal());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error inserting ticket: " + e.getMessage());
        }
        return false;
    }

    /**
     * Get a ticket by its ID.
     *
     * @param tiketId ID of the ticket to fetch.
     * @return Tiket object if found, otherwise null.
     */
    public Tiket getTiketById(int tiketId) {
        String sql = "SELECT * FROM tiket WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, tiketId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Tiket tiket = new Tiket();
                    tiket.setId(rs.getInt("id"));
                    tiket.setNamaPembeli(rs.getString("nama_pembeli"));
                    tiket.setFilmId(rs.getInt("film_id"));
                    tiket.setJumlahTiket(rs.getInt("jumlah_tiket"));
                    tiket.setHargaTotal(rs.getDouble("harga_total"));
                    return tiket;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching ticket by ID: " + e.getMessage());
        }
        return null;
    }
}
