# 🎬 Sistem Pemesanan Tiket Bioskop

Aplikasi desktop berbasis Java untuk sistem pemesanan tiket bioskop dengan antarmuka GUI menggunakan Java Swing.

## 📋 Deskripsi

Aplikasi ini memungkinkan pengguna untuk:
- Mendaftar dan login ke sistem
- Melihat daftar film yang tersedia
- Memesan tiket film
- Melihat riwayat pemesanan
- Mencetak tiket dalam format text file

## 🏗️ Arsitektur

Proyek ini menggunakan arsitektur **MVC (Model-View-Controller)**:

```
src/main/java/
├── model/          # Entity classes (Film, Tiket, User)
├── dao/            # Data Access Objects untuk database operations
├── koneksi/        # Database connection management
└── ui/             # User Interface dengan Java Swing
```

## 🗄️ Database Schema

Database menggunakan **MySQL/MariaDB** dengan 3 tabel:

### Table: `film`
```sql
- id (INT, PRIMARY KEY, AUTO_INCREMENT)
- judul (VARCHAR)
- genre (VARCHAR)
- durasi (INT) -- dalam menit
- harga (DOUBLE)
- deskripsi (TEXT)
```

### Table: `user`
```sql
- user_id (INT, PRIMARY KEY, AUTO_INCREMENT)
- username (VARCHAR)
- password (VARCHAR)
- email (VARCHAR)
```

### Table: `tiket`
```sql
- id (INT, PRIMARY KEY, AUTO_INCREMENT)
- nama_pembeli (VARCHAR)
- film_id (INT, FOREIGN KEY -> film.id)
- jumlah_tiket (INT)
- harga_total (DOUBLE)
```

## 🚀 Cara Menjalankan

### Prerequisites
- Java 23 atau lebih tinggi
- Maven 3.6+
- MySQL/MariaDB Server
- MySQL Connector/J 8.0.33

### Langkah-langkah

1. **Clone repository**
   ```bash
   git clone <repository-url>
   cd tiket-bioskop
   ```

2. **Setup Database**
   ```bash
   mysql -u root -p < bioskop.sql
   ```

3. **Konfigurasi Database**

   Edit file `src/main/java/koneksi/Koneksi.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/bioskop";
   private static final String USER = "root";
   private static final String PASS = "your_password"; // sesuaikan password
   ```

4. **Build Project**
   ```bash
   mvn clean install
   ```

5. **Run Application**
   ```bash
   mvn exec:java -Dexec.mainClass="ui.MainApp"
   ```

## 🎨 Fitur Aplikasi

### 1. Login Page
- Login dengan username dan password
- Validasi credentials dari database
- Link ke halaman registrasi

### 2. Registration Page
- Registrasi akun baru
- Validasi input (nama, email, password)
- Otomatis login setelah registrasi berhasil

### 3. Menu Page (Film List)
- Tampilan daftar film dalam bentuk cards
- Informasi film: banner, judul, genre, durasi, harga (IDR), deskripsi
- Fitur booking tiket dengan input jumlah
- Format harga dalam Rupiah (Rp)
- Scroll horizontal untuk browsing film

### 4. Ticket Page
- Riwayat pemesanan tiket user
- Detail setiap tiket: film, jumlah, total harga
- Fitur **Print Ticket** - menyimpan tiket ke file `.txt`
- File disimpan di: `src/main/java/print/{username}_ticket_{id}.txt`

## 🔧 Teknologi yang Digunakan

- **Java 23** - Core programming language
- **Java Swing** - GUI framework
- **Maven** - Build automation & dependency management
- **MySQL Connector/J 8.0.33** - JDBC driver
- **MySQL/MariaDB** - Database management system

## 📦 Dependencies

```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.33</version>
</dependency>
```

## 📁 Struktur Proyek

```
tiket-bioskop/
├── src/
│   └── main/
│       └── java/
│           ├── dao/
│           │   ├── FilmDAO.java       # CRUD operations untuk Film
│           │   ├── TiketDAO.java      # CRUD operations untuk Tiket
│           │   └── UserDAO.java       # CRUD operations untuk User
│           ├── koneksi/
│           │   └── Koneksi.java       # Database connection
│           ├── model/
│           │   ├── Film.java          # Film entity
│           │   ├── Tiket.java         # Tiket entity
│           │   └── User.java          # User entity
│           ├── ui/
│           │   └── MainApp.java       # Main application & all UI pages
│           └── print/                 # Generated ticket files (gitignored)
├── bioskop.sql                        # Database schema & sample data
├── pom.xml                            # Maven configuration
└── README.md                          # Project documentation
```

## 💡 Fitur Khusus

### Format Mata Uang
Aplikasi menggunakan format **Rupiah (IDR)** dengan `NumberFormat`:
```java
NumberFormat.getCurrencyInstance(new Locale("id", "ID"))
```
Output: `Rp100.000,00`

### Database Connection Pool
Menggunakan singleton pattern untuk koneksi database yang efisien.

### Ticket Printing
Tiket yang dicetak berisi:
- User ID
- Ticket ID
- Nama Film
- Nama Pembeli
- Jumlah Tiket
- Total Harga (format Rupiah)

## ⚠️ Catatan Keamanan

**PENTING**: Aplikasi ini dibuat untuk tujuan pembelajaran. Beberapa hal yang perlu diperbaiki untuk production:

1. **Password Storage**: Password disimpan dalam plaintext. Gunakan hashing (BCrypt, Argon2) untuk produksi.
2. **SQL Injection**: Meskipun sudah menggunakan PreparedStatement, tetap perlu validasi input lebih ketat.
3. **Credentials**: Database credentials hardcoded. Gunakan environment variables atau config files.
4. **Authentication**: Tidak ada session management atau token-based auth.

## 🔐 Rekomendasi Keamanan

Untuk production-ready application:
- Implementasi password hashing (BCrypt)
- Gunakan environment variables untuk credentials
- Tambahkan input validation dan sanitization
- Implementasi proper session management
- Tambahkan logging untuk audit trail
- Gunakan HTTPS untuk komunikasi

## 📝 Database Sample Data

Database sudah dilengkapi dengan sample data:

**Films:**
- Avatar (Sci-Fi, 162 min, Rp100.000)
- Inception (Action, 148 min, Rp120.000)
- Frozen (Animation, 102 min, Rp80.000)
- The Godfather (Drama, 175 min, Rp150.000)

**Test User:**
- Username: `TEST`
- Password: `123`
