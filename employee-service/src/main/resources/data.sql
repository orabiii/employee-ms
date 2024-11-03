-- Insert dummy data into Branch table
INSERT INTO Branch (id, name) VALUES (1, 'Head Office');
INSERT INTO Branch (id, name) VALUES (2, 'Branch A');
INSERT INTO Branch (id, name) VALUES (3, 'Branch B');

-- Insert dummy data into Employee table
INSERT INTO Employee (id, name, national_id, age, branch_id) VALUES
(1, 'JOHN DOE', '30010101234567', 24, 1),
(2, 'JANE SMITH', '29712311876543', 27, 2),
(3, 'MIKE BROWN', '30807122345678', 16, 3),
(4, 'ANNA JOHNSON', '29204052345671', 32, 1),
(5, 'CHRIS LEE', '30505151234562', 19, 2);

ALTER TABLE employee ALTER COLUMN id RESTART WITH 6;