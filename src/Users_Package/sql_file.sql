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
select * from hostels;
drop table hostels;

CREATE TABLE feedbacks (
    id INT AUTO_INCREMENT PRIMARY KEY,
    hostel_id INT NOT NULL,
    message TEXT NOT NULL,
    FOREIGN KEY (hostel_id) REFERENCES hostels(id)
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


