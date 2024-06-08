use sda_project_final_db;
create database sda_project_final_db;
drop database sda_project_final_db;

CREATE TABLE hostels (
    id INT  PRIMARY KEY Auto_increment,
    name VARCHAR(100) NOT NULL,
	password VARCHAR(100) NOT NULL, 
    location VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    number_of_one_bed_rooms INT NOT NULL,
    number_of_two_bed_rooms INT NOT NULL,
    laundry_service bit NOT NULL,
    mess_service bit NOT NULL
);


CREATE TABLE rooms (
    id INT PRIMARY KEY  Auto_increment,
    hostel_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    availability bit NOT NULL,
    no_guests int not NULL,
    capacity int not NULL,
    price double not NULL,
    room_no int not NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);


CREATE TABLE beds (
    id INT  PRIMARY KEY  Auto_increment,
    room_id INT NOT NULL,
   
    available bit NOT NULL,
    FOREIGN KEY (room_id) REFERENCES rooms(id)
);

CREATE TABLE double_seater_beds (
    bed_id INT PRIMARY KEY,
    FOREIGN KEY (bed_id) REFERENCES beds(id)
);

CREATE TABLE single_seater_beds (
    bed_id INT PRIMARY KEY,
    FOREIGN KEY (bed_id) REFERENCES beds(id)
);


CREATE TABLE Service (
    id INT PRIMARY KEY Auto_increment,
    hostel_id INT NOT NULL,
   
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);

CREATE TABLE mess_systems (
    id INT  PRIMARY KEY Auto_increment,
    Service_id INT NOT NULL,
    menu_item VARCHAR(100) NOT NULL,
    FOREIGN KEY (Service_id) REFERENCES Service(id)
);

CREATE TABLE laundary_services (
    id INT  PRIMARY KEY Auto_increment,
    Service_id INT NOT NULL,
    laundry_name VARCHAR(100) NOT NULL,
    FOREIGN KEY (Service_id) REFERENCES Service(id)
);


CREATE TABLE discounts (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_id INT NOT NULL,
    dls_code VARCHAR(20) NOT NULL,
    amount FLOAT NOT NULL,
    deadline Date NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);

CREATE TABLE feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_id INT NOT NULL,
    Description TEXT NOT NULL,
    Rating int not null,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
);



CREATE TABLE Payment (
    id INT AUTO_INCREMENT PRIMARY KEY,
    type VARCHAR(50) NOT NULL,
    challan VARCHAR(100),
    accNo VARCHAR(100)
);

CREATE TABLE CreditCard (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Payment(id)
);

CREATE TABLE Cash (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Payment(id)
);

CREATE TABLE Bank (
    id INT PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES Payment(id)
);

CREATE TABLE Students(
id int primary key auto_increment,
 email VARCHAR(100) NOT NULL,
 password VARCHAR(100) NOT NULL, 
  contact_number VARCHAR(20) NOT NULL,
  CNIC  VARCHAR(20) NOT NULL

);
CREATE TABLE items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    location VARCHAR(100) NOT NULL,
    item VARCHAR(100) NOT NULL,
    date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    description TEXT,
    item_id VARCHAR(50)
);
CREATE TABLE notifications (
    id INT AUTO_INCREMENT PRIMARY KEY,
    message VARCHAR(255) NOT NULL
);


SELECT * FROM hostels;
SELECT * FROM rooms;
SELECT * FROM beds;
SELECT * FROM double_seater_beds;
SELECT * FROM single_seater_beds;
SELECT * FROM Service;
SELECT * FROM mess_systems;
SELECT * FROM laundary_services;
SELECT * FROM discounts;
SELECT * FROM feedbacks;
INSERT INTO hostels (name, password, location, contact_number, number_of_one_bed_rooms, number_of_two_bed_rooms, laundry_service, mess_service)
VALUES 
('Greenwood Hostel', 'password123', '123 Main St, Springfield', '123-456-7890', 10, 5, 1, 1),
('Sunset Hostel', 'securePass1', '456 Oak St, Shelbyville', '234-567-8901', 8, 4, 0, 1),
('Riverside Hostel', 'myPass789', '789 Pine St, Capital City', '345-678-9012', 12, 6, 1, 0),
('Mountain View Hostel', 'mountain123', '101 Mountain Rd, Rivertown', '456-789-0123', 5, 3, 1, 1),
('Seaside Hostel', 'beachPass', '202 Seaside Ave, Beachtown', '567-890-1234', 7, 2, 0, 0);


-- Inserting dummy data into rooms table
INSERT INTO rooms (hostel_id, name, availability, no_guests, capacity, price, room_no)
VALUES
    (1, 'Room A', 1, 2, 2, 75.00, 101),
    (1, 'Room B', 1, 1, 1, 50.00, 102),
    (1, 'Room C', 0, 0, 1, 60.00, 103),
    (2, 'Room D', 1, 3, 3, 90.00, 201),
    (2, 'Room E', 0, 0, 1, 40.00, 202),
    (3, 'Room F', 1, 2, 2, 80.00, 301),
    (3, 'Room G', 1, 1, 1, 55.00, 302),
    (3, 'Room H', 0, 0, 1, 65.00, 303),
    (4, 'Room I', 1, 2, 2, 70.00, 401),
    (4, 'Room J', 1, 1, 1, 45.00, 402),
    (5, 'Room K', 1, 3, 3, 85.00, 501),
    (5, 'Room L', 0, 0, 1, 35.00, 502);


INSERT INTO Students (email, password, contact_number, CNIC)
VALUES
    ('student1@example.com', 'password1', '1234567890', '12345-6789012-3'),
    ('student2@example.com', 'password2', '9876543210', '98765-4321098-7');