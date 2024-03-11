INSERT INTO Position(
    NAME
)
VALUES
    ('Manager'),
    ('Receptionist')
;

INSERT INTO Staff (
    FirstName, LastName, Email, phoneNumber, PositionID
)
VALUES 
    ('Nithisha', 'Sathishkumar', 'nithis13@uw.edu', '4253640364', (SELECT ID FROM Position WHERE Name = 'Manager')),
    ('Sahong', 'Song', 'song25@uw.edu', '3465816381', (SELECT ID FROM Position WHERE Name = 'Receptionist')),
    ('Andy', 'Hoang', 'andyHoang@gmail.com', '7463856291', (SELECT ID FROM Position WHERE Name = 'Receptionist')),
    ('Shaun', 'Cushman', 'shaun_mc@gmail.com', '6356485261', (SELECT ID FROM Position WHERE Name = 'Manager')),
    ('Shyam', 'Ramesh', 'shyam.aws@gmail.com', '3745237452', (SELECT ID FROM Position WHERE Name = 'Manager')),
    ('Iliya', 'Belyak', 'iliyab@yahoo.com', '7836472749', (SELECT ID FROM Position WHERE Name = 'Receptionist')),
    ('Anna', 'Rivas', 'annayu683@yahoo.com', '8746372637', (SELECT ID FROM Position WHERE Name = 'Receptionist')),
    ('Nasheeta', 'Lott', 'bbqhdho@yahoo.com', '1234567890', (SELECT ID FROM Position WHERE Name = 'Receptionist'))
;

insert into state values('AL','Alabama'), ('AK','Alaska'), ('AZ','Arizona'), 
('AR','Arkansas'), ('CA','California'), ('CO','Colorado'), ('CT','Connecticut'), 
('DE','Delaware'), ('FL','Florida'), ('GA','Georgia'), ('HI','Hawaii'), 
('ID', 'Idaho'), ('IL','Illinois'), ('IN','Indiana'), ('IA','Iowa'), 
('KS','Kansas'), ('KY','Kentucky'), ('LA','Lousiana'), ('ME','Maine'), 
('MD','Maryland'), ('MA','Massachusetts'), ('MI','Michigan'), ('MN','Minnesota'), ('MS','Mississippi'), ('MO','Missouri'), ('MT','Montana'), ('NE','Nebraska'), 
('NV','Nevada'), ('NH','New Hampshire'), ('NJ','New Jersey'), 
('NM','New Mexico'), ('NY','New York'), ('NC','North Carolina'), 
('ND','North Dakota'), ('OH','Ohio'), ('OK','Oklahoma'), ('OR','Oregon'), 
('PA','Pennsylvania'), ('RI','Rhode Island'), ('SC','South Carolina'),
('SD','South Dakota'), ('TN','Tennessee'), ('TX','Texas'), ('UT','Utah'), 
('VT','Vermont'), ('VA','Virginia'), ('WA','Washington'), ('WV','West Virginia'), ('WI','Wisconsin'), ('WY','Wyoming');

insert into phonetype values('M','mobile'), ('H','home');

insert into guest(firstname, lastname, guestnum, email, address1, city, stateid) values('F1','L1','G10FPK', '1@1.com','address11', 'seattle', 'WA'),
('F2','L2','G10FSK', '2@2.com','address12', 'orlando', 'FL'),
('Jack','Sparrow','G00PWN','POTC@gmail.com','1212 Black Pearl Rd.','Tortuga','FL'),
('George','Lucas','G01KFM','StarWars@disney.com','123 1234 St.','Space','HI'),
('Steven','Spielberg','G09FSK','Hollywood@gmail.com','12 1656 Rd.','Hollywood','CA'),
('Robin','Hood','G03KDF','money@yahoo.com','123 Forest St.','Sherwood','OH'),
('Spider','Man','G02DKN','marvel@gmail.com','1234 Web St.','Brooklyn','NY'),
('Tony','Stark','G06KPF','stark@yahoo.com','address97','Malibu','CA');

insert into phone(guestid,phonetypeid,phonenumber) 
values(1,'M','12345678'), (2,'H','12345672'), (3,'M','325624209'), (4,'H','1323564'),
(5,'M','2143567'), (6,'M','12263423'), (7,'M','76342145'), (8,'H','8676453');

insert into room(roomnumber,capacity,priceperday) 
values('A1001',2,150), 
	('A1002',4,280),
	('A1003',2,150),
	('A1004',1,100),
	('A1005',8,500),
	('A1006',10,800),
	('A1007',4,300),
	('A1008',1,120),
	('A1009',2,200),
	('A1010',6,300),
	('A1011',2,900),
	('A1012',4,360),
	('A1013',6,500),
	('A1014',2,1200);

insert into paymenttype values('CC','credit card'), ('CS','cash');

INSERT INTO Payment(PaymentTypeID,PaymentDate)
VALUES('CC','2022-02-17'),('CC','2022-04-19'),('CC','2022-08-04');

INSERT INTO Reservation(reservationNum, numberOfGuest, paymentID, amount,
	createTime, guestID, staffID)
VALUES(
	'R00BEV3LNHQXE2',4,null,1960,'2022-01-01',8,null),
	('R0FKSJMFLJFJ59',8,1,30300,'2022-02-17',3,1),
	('R0QEWRTRTJYHFD',4,2,18000,'2022-04-19',4,3),
	('R078FFGJ5CGBSO',2,3,34800,'2022-08-04',1,5),
	('R0HGDFSCDSCVFD',1,null,1800,'2023-02-03',7,null);

INSERT INTO Booking(ReservationID,RoomID,CiDate,CoDate)
VALUES
	(1,2,'2024-02-01','2024-02-08'),
	(2,7,'2024-05-06','2024-06-06'),
	(2,3,'2024-05-06','2024-06-06'),
	(2,9,'2024-05-06','2024-06-06'),
	(3,12,'2024-08-20','2024-10-10'),
	(4,14,'2024-06-20','2024-07-19'),
	(5,8,'2024-02-10','2024-02-25');
