📚 Library Management System (Java & MySQL)

Library Management System ini adalah aplikasi berbasis Java (OOP) dengan arsitektur DAO + MVC yang terhubung ke database MySQL.
Aplikasi ini berjalan melalui console/terminal, dimulai dari halaman login dan dilanjutkan ke menu utama untuk melakukan berbagai operasi perpustakaan.

✨ Fitur Utama
🔐 1. Login

Pengguna masuk menggunakan contoh (Case sensitive):
a) Nama: Joseph
b) Email: joe@mail.com
Login akan memverifikasi data pengguna dari tabel users.

📘 2. Lihat Semua Buku

Menampilkan seluruh daftar buku, baik yang tersedia maupun yang sedang dipinjam.
Output menampilkan:
a) Book ID
b) Title
c) Author
d) Category

🔍 3. Cari Buku Berdasarkan Judul

Pengguna dapat memasukkan keyword judul → sistem mencari buku yang mengandung keyword tersebut.

📖 4. Meminjam Buku

Pengguna memasukkan Book ID

Sistem akan:
a) Mengecek apakah buku tersedia
b) Mengubah status buku menjadi Borrowed
c) Mencatat peminjaman di tabel loans
d) Menetapkan loan_date & due_date (7 hari dari tanggal pinjam)

🔄 5. Mengembalikan Buku

Pengguna memasukkan Book ID

Sistem:
a) Mencari loan aktif dengan Book ID yang dimasukan
b) Mengisi return_date dengan tanggal hari ini
c) Menghitung denda otomatis (jika terlambat): fine = max(DATEDIFF(return_date, due_date), 0) * 3
d) Mengubah status buku menjadi Available
e) Menampilkan jumlah denda (jika ada)

🧾 6. Melihat Riwayat Peminjaman

Sistem menampilkan seluruh riwayat peminjaman milik pengguna:
1) Loan ID
2) Book ID
3) Loan Date
4) Due Date
5) Return Date
6) Fine (jika ada)

🚪 7. Exit

Keluar dari program.

🗂️ Struktur Proyek
src/
 ├── model/
 │    ├── user.java
 │    ├── book.java
 │    └── loan.java
 │
 ├── DAO/
 │    ├── UserDAO.java
 │    ├── BookDAO.java
 │    └── LoanDAO.java
 │
 ├── util/
 │    └── dbConnection.java
 │
 ├── Main.java
 │
database/
 └── library_db.sql

 🛢️ Database Setup (MySQL)

1️. Buat database:
    CREATE DATABASE librarydb;
    USE librarydb;
2️. Buat tabel users, books, dan loans (Bisa dilakukan dengan mengimport file librarydb.sql ke MySQL). 
3️. Jalankan aplikasi.

▶️ Cara Menjalankan Program

1. Clone repository: git clone https://github.com/yourusername/library-management-system.git
2. Import project ke IDE (IntelliJ / Eclipse)
3. Tambahkan MySQL Connector di folder lib/
4. Pastikan file dbConnection.java sudah benar:
     url = "jdbc:mysql://localhost:3306/librarydb";
     username = "root";
     password = "yourpassword";
5. Jalankan Main.java

🧱 Teknologi yang Digunakan

-> Java 17
-> OOP Principles (SOLID)
-> DAO Pattern
-> MySQL Database
-> JDBC
-> MVC Architecture (Model + DAO + Console UI)

📌 Catatan Tambahan

a) Semua operasi database telah ditangani menggunakan PreparedStatement untuk menghindari SQL Injection.
b) Peminjaman & pengembalian menggunakan transaction handling (commit/rollback) untuk menjaga konsistensi data.
c) Struktur project mudah dikembangkan untuk fitur tambahan seperti admin panel atau GUI di masa depan.

🤝 Kontribusi

Pull request terbuka untuk siapa saja yang ingin memperbaiki bug atau menambahkan fitur baru.
