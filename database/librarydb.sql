CREATE DATABASE IF NOT EXISTS librarydb;
USE librarydb;

CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100),
    author VARCHAR(100),
    category VARCHAR(100),
    status BOOLEAN
);

CREATE TABLE loans (
    loan_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    book_id INT,
    loan_date DATE,
    due_date DATE,
    return_date DATE,
    fine INT
);

INSERT INTO users (name, email) VALUES
('Louis', 'Louis@mail.com'),
('Joseph', 'joe@mail.com'),
('Jerry', 'Jerry@mail.com');

INSERT INTO books (title, author, category, status) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', 'Classic', FALSE),
('To Kill a Mockingbird', 'Harper Lee', 'Classic', FALSE),
('1984', 'George Orwell', 'Dystopian', FALSE),
('The Hobbit', 'J.R.R. Tolkien', 'Fantasy', FALSE),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 'Fantasy', FALSE),
('A Game of Thrones', 'George R. R. Martin', 'Fantasy', FALSE),
('The Catcher in the Rye', 'J.D. Salinger', 'Classic', FALSE),
('The Alchemist', 'Paulo Coelho', 'Inspirational', FALSE),
('Atomic Habits', 'James Clear', 'Self-Help', FALSE),
('Sapiens: A Brief History of Humankind', 'Yuval Noah Harari', 'History', FALSE),
('Rich Dad Poor Dad', 'Robert T. Kiyosaki', 'Financial', FALSE),
('Clean Code', 'Robert C. Martin', 'Programming', FALSE),
('Introduction to Algorithms', 'Thomas H. Cormen', 'Programming', FALSE),
('The Pragmatic Programmer', 'Andrew Hunt & David Thomas', 'Programming', FALSE),
('Design Patterns: Elements of Reusable Object-Oriented Software', 'Gang of Four', 'Programming', FALSE);