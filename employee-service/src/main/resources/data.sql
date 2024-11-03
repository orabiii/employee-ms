-- Insert dummy data into Branch table
INSERT INTO Branch (id, name) VALUES (1, 'Head Office');
INSERT INTO Branch (id, name) VALUES (2, 'Branch A');
INSERT INTO Branch (id, name) VALUES (3, 'Branch B');

-- Insert dummy data into Employee table
INSERT INTO Employee (id, name, national_id, age, branch_id) VALUES
(1, 'John Doe', '30010101234567', 24, 1),
(2, 'Jane Smith', '29712311876543', 27, 2),
(3, 'Mike Brown', '30807122345678', 16, 3),
(4, 'Anna Johnson', '29204052345671', 32, 1),
(5, 'Chris Lee', '30505151234562', 19, 2);

ALTER TABLE employee ALTER COLUMN id RESTART WITH 6;