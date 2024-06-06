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
    deadline TIME NOT NULL,
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

