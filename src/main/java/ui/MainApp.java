package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.text.NumberFormat;
import java.util.Locale;

import dao.FilmDAO;
import dao.TiketDAO;
import dao.UserDAO;
import model.Film;
import model.Tiket;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MainApp extends JFrame {
    private String currentUser;

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

    private JPanel currentPanel;

    public MainApp() {
        setTitle("Cinema App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Mulai dengan LoginPage
        showLoginPage();
    }

    public void showLoginPage() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = new LoginPage(this);
        add(currentPanel);
        revalidate();
        repaint();
    }

    public void showRegistrationPage() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = new RegistrationPage(this);
        add(currentPanel);
        revalidate();
        repaint();
    }

    public void showMenuPage() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = new MenuPage(this);
        add(currentPanel);
        revalidate();
        repaint();
    }

    public void showTicketPage() {
        if (currentPanel != null) {
            remove(currentPanel);
        }
        currentPanel = new TicketPage(this); // Pindah ke TicketPage
        add(currentPanel);
        revalidate();
        repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}


class LoginPage extends JPanel {
    public LoginPage(MainApp mainApp) {
        setLayout(new BorderLayout());

        // Left Panel (Welcome Section)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(33, 37, 41)); // Dark gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("Welcome to Cinema!");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(welcomeLabel, gbc);

        JLabel infoLabel = new JLabel("<html>Book tickets easily with your<br>personal account.</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        leftPanel.add(infoLabel, gbc);

        JButton signUpButton = new JButton("SIGN UP");
        styleButton(signUpButton, new Color(0, 153, 76));
        gbc.gridy = 2;
        leftPanel.add(signUpButton, gbc);

        // Right Panel (Login Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(15, 15, 15, 15);

        JLabel loginLabel = new JLabel("Login to Your Account");
        loginLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        loginLabel.setForeground(new Color(0, 123, 255));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(loginLabel, rightGbc);

        // Username Field
        JTextField usernameField = new JTextField(20);
        styleTextField(usernameField, "Username");
        rightGbc.gridy = 1;
        rightPanel.add(usernameField, rightGbc);

        // Password Field
        JPasswordField passwordField = new JPasswordField(20);
        styleTextField(passwordField, "Password");
        rightGbc.gridy = 2;
        rightPanel.add(passwordField, rightGbc);

        // Buttons
        JButton loginButton = new JButton("LOGIN");
        styleButton(loginButton, new Color(0, 123, 255));
        rightGbc.gridy = 3;
        rightGbc.gridwidth = 1;
        rightPanel.add(loginButton, rightGbc);

        JButton registerButton = new JButton("REGISTER");
        styleButton(registerButton, new Color(0, 153, 76));
        rightGbc.gridx = 1;
        rightPanel.add(registerButton, rightGbc);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerSize(0); // Remove divider
        splitPane.setDividerLocation(450);
        splitPane.setEnabled(false);
        add(splitPane);

        // Action Listeners
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            UserDAO userDAO = new UserDAO();
            User user = userDAO.loginUser(username, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful! Welcome " + user.getUsername());
                mainApp.setCurrentUser(user.getUsername()); // Simpan nama pengguna yang login
                mainApp.showMenuPage(); // Pindah ke MenuPage
            } else {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        });



        registerButton.addActionListener(e -> mainApp.showRegistrationPage());
        signUpButton.addActionListener(e -> mainApp.showRegistrationPage());
    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new PlaceholderFocusListener(placeholder));
    }

    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }
}

class RegistrationPage extends JPanel {
    public RegistrationPage(MainApp mainApp) {
        setLayout(new BorderLayout());

        // Left Panel (Sign In Section)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(33, 37, 41)); // Dark gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel welcomeLabel = new JLabel("Already Have an Account?");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(welcomeLabel, gbc);

        JLabel infoLabel = new JLabel("<html>Sign in and continue exploring<br>our movie collections.</html>");
        infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        gbc.gridy = 1;
        leftPanel.add(infoLabel, gbc);

        JButton signInButton = new JButton("SIGN IN");
        styleButton(signInButton, new Color(0, 123, 255));
        gbc.gridy = 2;
        leftPanel.add(signInButton, gbc);

        // Right Panel (Registration Form)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);
        GridBagConstraints rightGbc = new GridBagConstraints();
        rightGbc.insets = new Insets(15, 15, 15, 15);

        JLabel createLabel = new JLabel("Create Your Account");
        createLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        createLabel.setForeground(new Color(0, 153, 76));
        rightGbc.gridx = 0;
        rightGbc.gridy = 0;
        rightGbc.gridwidth = 2;
        rightPanel.add(createLabel, rightGbc);

        // Fields
        JTextField nameField = new JTextField(20);
        styleTextField(nameField, "Name");
        rightGbc.gridy = 1;
        rightPanel.add(nameField, rightGbc);

        JTextField emailField = new JTextField(20);
        styleTextField(emailField, "Email");
        rightGbc.gridy = 2;
        rightPanel.add(emailField, rightGbc);

        JPasswordField passwordField = new JPasswordField(20);
        styleTextField(passwordField, "Password");
        rightGbc.gridy = 3;
        rightPanel.add(passwordField, rightGbc);

        // Sign Up Button
        JButton signUpButton = new JButton("SIGN UP");
        styleButton(signUpButton, new Color(0, 153, 76));
        rightGbc.gridy = 4;
        rightPanel.add(signUpButton, rightGbc);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerSize(0); // Remove divider
        splitPane.setDividerLocation(450);
        splitPane.setEnabled(false);
        add(splitPane);

        // Action Listeners
        signInButton.addActionListener(e -> mainApp.showLoginPage());
        signUpButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User user = new User();
            user.setUsername(name);
            user.setEmail(email);
            user.setPassword(password);

            UserDAO userDAO = new UserDAO();
            boolean isRegistered = userDAO.registerUser(user);

            if (isRegistered) {
                JOptionPane.showMessageDialog(this, "Account Created Successfully!");
                mainApp.showMenuPage(); // Pindah ke MenuPage
            } else {
                JOptionPane.showMessageDialog(this, "Registration Failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


    }

    private void styleTextField(JTextField field, String placeholder) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        field.setText(placeholder);
        field.setForeground(Color.GRAY);
        field.addFocusListener(new PlaceholderFocusListener(placeholder));
    }

    private void styleButton(JButton button, Color color) {
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }
}

class MenuPage extends JPanel {
    private MainApp mainApp;

    // Tambahkan formatter Rupiah
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public MenuPage(MainApp mainApp) {
        this.mainApp = mainApp; // Simpan referensi MainApp
        setLayout(new BorderLayout());

        // Left Panel (Navigation / Quick Info)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(33, 37, 41)); // Dark gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel menuTitleLabel = new JLabel("Cinema Menu");
        styleLabel(menuTitleLabel, 24, Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(menuTitleLabel, gbc);

        JButton logoutButton = new JButton("Logout");
        styleButton(logoutButton, new Color(220, 53, 69));
        gbc.gridy = 1;
        leftPanel.add(logoutButton, gbc);

        logoutButton.addActionListener(e -> mainApp.showLoginPage());

        JButton ticketPageButton = new JButton("View Tickets");
        styleButton(ticketPageButton, new Color(0, 123, 255));
        gbc.gridy = 2; // Di bawah tombol Logout
        leftPanel.add(ticketPageButton, gbc);

        // Tambahkan ActionListener untuk membuka TicketPage
        ticketPageButton.addActionListener(e -> mainApp.showTicketPage());

        // Right Panel (Film Cards)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Available Films");
        styleLabel(headerLabel, 24, new Color(0, 153, 76));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(headerLabel, BorderLayout.NORTH);

        // Film Cards Container (diubah untuk tampilan horizontal)
        JPanel cardContainer = new JPanel();
        cardContainer.setLayout(new BoxLayout(cardContainer, BoxLayout.X_AXIS)); // Layout horizontal
        cardContainer.setBackground(Color.WHITE);
        cardContainer.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        FilmDAO filmDAO = new FilmDAO();
        List<Film> films = filmDAO.getAllFilms();
// Tambahkan jarak antar card (optional)
        for (Film film : films) {
            JPanel filmCard = createFilmCard(film);
            cardContainer.add(filmCard);
            cardContainer.add(Box.createRigidArea(new Dimension(20, 0))); // Spacer horizontal
        }

// Atur JScrollPane dengan kebijakan scroll horizontal dan non-vertikal
        JScrollPane scrollPane = new JScrollPane(cardContainer,
                JScrollPane.VERTICAL_SCROLLBAR_NEVER,  // Tidak tampilkan vertical scrollbar
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); // Tampilkan horizontal scrollbar saat dibutuhkan
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Smooth scrolling horizontal

        rightPanel.add(scrollPane, BorderLayout.CENTER);


        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerSize(0); // Remove divider
        splitPane.setDividerLocation(250); // Adjust the size of the left panel
        splitPane.setEnabled(false);
        add(splitPane);
    }

    private JPanel createFilmCard(Film film) {
        JPanel filmCard = new JPanel();
        filmCard.setLayout(new BorderLayout());
        filmCard.setBackground(Color.WHITE);
        filmCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        // Ubah ukuran film card menjadi lebih kecil, misalnya 200 x 350
        filmCard.setPreferredSize(new Dimension(200, 350));

        // Load Banner Image
        String imagePath = "/images/" + film.getJudul().toLowerCase().replace(" ", "_") + ".jpg";
        ImageIcon bannerIcon = null;
        try {
            bannerIcon = new ImageIcon(getClass().getResource(imagePath));
        } catch (Exception e) {
            System.err.println("Image not found for: " + film.getJudul());
        }

        JLabel bannerLabel = new JLabel();
        if (bannerIcon != null) {
            // Sesuaikan ukuran gambar agar proporsional, misalnya 200 x 120
            Image scaledImage = bannerIcon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
            bannerLabel.setIcon(new ImageIcon(scaledImage));
        } else {
            bannerLabel.setText("No Image");
            bannerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        bannerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Film Details (Title, Genre, Duration, Price, Description)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(film.getJudul(), SwingConstants.CENTER);
        styleLabel(titleLabel, 16, new Color(33, 37, 41)); // Ubah ukuran font jika perlu

        JLabel genreLabel = new JLabel("Genre: " + film.getGenre(), SwingConstants.LEFT);
        styleLabel(genreLabel, 12, Color.DARK_GRAY);

        JLabel durationLabel = new JLabel("Duration: " + film.formatDurasi(), SwingConstants.LEFT);
        styleLabel(durationLabel, 12, Color.DARK_GRAY);

        // Format harga menjadi Rupiah
        String formattedPrice = CURRENCY_FORMATTER.format(film.getHarga());
        JLabel priceLabel = new JLabel("Price: " + formattedPrice, SwingConstants.LEFT);
        styleLabel(priceLabel, 12, new Color(0, 123, 255));

        // Description
        JLabel descriptionLabel = new JLabel("<html>" + film.getDeskripsi() + "</html>", SwingConstants.LEFT);
        styleLabel(descriptionLabel, 10, Color.GRAY);
        descriptionLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer
        detailsPanel.add(genreLabel);
        detailsPanel.add(durationLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer
        detailsPanel.add(descriptionLabel);

        // Book Button
        JButton bookButton = new JButton("Book Now");
        styleButton(bookButton, new Color(0, 123, 255));
        bookButton.setPreferredSize(new Dimension(180, 35)); // Sesuaikan ukuran tombol
        bookButton.addActionListener(e -> {
            String jumlahTiket = JOptionPane.showInputDialog("Enter ticket quantity:");
            try {
                int tiket = Integer.parseInt(jumlahTiket);
                if (tiket <= 0) {
                    JOptionPane.showMessageDialog(this, "Invalid ticket quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double totalPrice = tiket * film.getHarga();

                // Simpan tiket ke database
                TiketDAO tiketDAO = new TiketDAO();
                Tiket tiketObj = new Tiket();
                tiketObj.setNamaPembeli(mainApp.getCurrentUser()); // Gunakan nama pengguna dari mainApp
                tiketObj.setFilmId(film.getId());
                tiketObj.setJumlahTiket(tiket);
                tiketObj.setHargaTotal(totalPrice);

                boolean isBooked = tiketDAO.insertTiket(tiketObj);
                if (isBooked) {
                    JOptionPane.showMessageDialog(this, String.format("Booked %d tickets for %s.\nTotal: %s", tiket, film.getJudul(), CURRENCY_FORMATTER.format(totalPrice)));
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to book ticket.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tambahkan komponen ke film card
        filmCard.add(bannerLabel, BorderLayout.NORTH);
        filmCard.add(detailsPanel, BorderLayout.CENTER);
        filmCard.add(bookButton, BorderLayout.SOUTH);

        return filmCard;
    }


    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void styleLabel(JLabel label, int fontSize, Color color) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(color);
    }
}


class TicketPage extends JPanel {
    private MainApp mainApp;

    // Deklarasi variabel formatter Rupiah sebagai static final
    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public TicketPage(MainApp mainApp) {
        this.mainApp = mainApp;
        setLayout(new BorderLayout());

        // Left Panel (Navigation / Info)
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(33, 37, 41)); // Dark gray
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;

        JLabel titleLabel = new JLabel("Your Tickets");
        styleLabel(titleLabel, 24, Color.WHITE);
        gbc.gridy = 0;
        leftPanel.add(titleLabel, gbc);

        JButton backButton = new JButton("Back to Menu");
        styleButton(backButton, new Color(0, 123, 255));
        gbc.gridy = 1;
        leftPanel.add(backButton, gbc);

        // Action: Back to MenuPage
        backButton.addActionListener(e -> mainApp.showMenuPage());

        // Right Panel (Ticket List)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        JLabel headerLabel = new JLabel("Your Tickets");
        styleLabel(headerLabel, 20, new Color(0, 153, 76));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rightPanel.add(headerLabel, BorderLayout.NORTH);

        // Ubah layout container tiket agar tampil vertikal
        JPanel ticketContainer = new JPanel();
        ticketContainer.setLayout(new BoxLayout(ticketContainer, BoxLayout.Y_AXIS)); // Layout vertikal
        ticketContainer.setBackground(Color.WHITE);
        ticketContainer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Ambil data tiket dari database
        TiketDAO tiketDAO = new TiketDAO();
        List<Tiket> tiketList = tiketDAO.getAllTiket();

        for (Tiket tiket : tiketList) {
            if (tiket.getNamaPembeli().equals(mainApp.getCurrentUser())) {
                JPanel tiketCard = createTiketCard(tiket);
                ticketContainer.add(tiketCard);
                ticketContainer.add(Box.createRigidArea(new Dimension(0, 10))); // Spacer vertikal antar tiket
            }
        }

        // Atur JScrollPane dengan scrollbar vertikal
        JScrollPane scrollPane = new JScrollPane(ticketContainer,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,  // Scrollbar vertikal muncul jika diperlukan
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);     // Tidak tampilkan horizontal scrollbar
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling

        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Split Pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        splitPane.setDividerSize(0); // Remove divider
        splitPane.setDividerLocation(250); // Set left panel width
        splitPane.setEnabled(false);
        add(splitPane);
    }

    private JPanel createTiketCard(Tiket tiket) {
        JPanel tiketCard = new JPanel();
        tiketCard.setLayout(new BorderLayout());
        tiketCard.setBackground(Color.WHITE);
        tiketCard.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        tiketCard.setPreferredSize(new Dimension(200, 150)); // Ukuran tiket card

        // Ambil judul film berdasarkan ID
        FilmDAO filmDAO = new FilmDAO();
        Film film = filmDAO.getFilmById(tiket.getFilmId());
        String filmTitle = film != null ? film.getJudul() : "Unknown Film";

        // Tambahkan detail tiket
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(Color.WHITE);
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding

        JLabel titleLabel = new JLabel(filmTitle, SwingConstants.CENTER);
        styleLabel(titleLabel, 14, new Color(33, 37, 41)); // Font lebih kecil

        JLabel jumlahTiketLabel = new JLabel("Jumlah Tiket: " + tiket.getJumlahTiket(), SwingConstants.LEFT);
        styleLabel(jumlahTiketLabel, 12, Color.DARK_GRAY);

        // Format harga menggunakan formatter Rupiah
        String formattedPrice = CURRENCY_FORMATTER.format(tiket.getHargaTotal());
        JLabel totalHargaLabel = new JLabel("Total Harga: " + formattedPrice, SwingConstants.LEFT);
        styleLabel(totalHargaLabel, 12, Color.DARK_GRAY);

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Spacer kecil
        detailsPanel.add(jumlahTiketLabel);
        detailsPanel.add(totalHargaLabel);

        // Tombol "Print Now"
        JButton printButton = new JButton("Print Now");
        styleButton(printButton, new Color(0, 123, 255));
        printButton.setPreferredSize(new Dimension(150, 25)); // Ukuran tombol lebih kecil
        printButton.addActionListener(e -> {
            try {
                // Ambil user_id berdasarkan nama_pembeli
                UserDAO userDAO = new UserDAO();
                User user = userDAO.getUserByUsername(tiket.getNamaPembeli());
                if (user == null) {
                    JOptionPane.showMessageDialog(this, "User not found for: " + tiket.getNamaPembeli(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tentukan lokasi folder "print" di dalam src/main/java
                String folderPath = System.getProperty("user.dir") + "/src/main/java/print";
                File folder = new File(folderPath);

                // Pastikan folder "print" ada, jika tidak maka buat
                if (!folder.exists() && !folder.mkdirs()) {
                    JOptionPane.showMessageDialog(this, "Failed to create directory: " + folderPath, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Tentukan nama file
                String fileName = tiket.getNamaPembeli() + "_ticket_" + tiket.getId() + ".txt";
                File file = new File(folder, fileName);

                // Format harga menggunakan formatter Rupiah
                String formattedPriceForFile = CURRENCY_FORMATTER.format(tiket.getHargaTotal());

                // Buat file teks dan tulis informasi tiket
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("===== Ticket Details =====\n");
                    writer.write("User ID: " + user.getUserId() + "\n");
                    writer.write("Ticket ID: " + tiket.getId() + "\n");
                    writer.write("Film: " + filmTitle + "\n");
                    writer.write("Nama Pembeli: " + tiket.getNamaPembeli() + "\n");
                    writer.write("Jumlah Tiket: " + tiket.getJumlahTiket() + "\n");
                    writer.write("Total Harga: " + formattedPriceForFile + "\n");
                    writer.write("==========================\n");
                }

                // Tampilkan pesan sukses
                JOptionPane.showMessageDialog(this, "Ticket has been saved to:\n" + file.getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Failed to save ticket: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        tiketCard.add(detailsPanel, BorderLayout.CENTER); // Hanya detail
        tiketCard.add(printButton, BorderLayout.SOUTH);

        return tiketCard;
    }

    private void styleLabel(JLabel label, int fontSize, Color color) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, fontSize));
        label.setForeground(color);
    }

    private void styleButton(JButton button, Color color) {
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }
}


// Helper Class for Placeholder Management
class PlaceholderFocusListener implements java.awt.event.FocusListener {
    private final String placeholder;

    public PlaceholderFocusListener(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void focusGained(java.awt.event.FocusEvent e) {
        JTextField field = (JTextField) e.getComponent();
        if (field.getText().equals(placeholder)) {
            field.setText("");
            field.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(java.awt.event.FocusEvent e) {
        JTextField field = (JTextField) e.getComponent();
        if (field.getText().isEmpty()) {
            field.setText(placeholder);
            field.setForeground(Color.GRAY);
        }
    }
}