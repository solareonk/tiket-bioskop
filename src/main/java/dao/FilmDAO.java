package dao;

import koneksi.Koneksi;
import model.Film;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FilmDAO {

    /**
     * Get all films from the database.
     *
     * @return List of Film objects.
     */
    public List<Film> getAllFilms() {
        List<Film> list = new ArrayList<>();
        String sql = "SELECT * FROM film";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Film film = new Film();
                film.setId(rs.getInt("id"));
                film.setJudul(rs.getString("judul"));
                film.setGenre(rs.getString("genre"));
                film.setDurasi(rs.getInt("durasi"));
                film.setHarga(rs.getDouble("harga"));
                film.setDeskripsi(rs.getString("deskripsi"));
                list.add(film);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching films: " + e.getMessage());
        }
        return list;
    }

    /**
     * Get a film by its ID.
     *
     * @param filmId ID of the film to fetch.
     * @return Film object if found, otherwise null.
     */
    public Film getFilmById(int filmId) {
        String sql = "SELECT * FROM film WHERE id = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, filmId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Film film = new Film();
                    film.setId(rs.getInt("id"));
                    film.setJudul(rs.getString("judul"));
                    film.setGenre(rs.getString("genre"));
                    film.setDurasi(rs.getInt("durasi"));
                    film.setHarga(rs.getDouble("harga"));
                    film.setDeskripsi(rs.getString("deskripsi"));
                    return film;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching film by ID: " + e.getMessage());
        }
        return null;
    }
}
