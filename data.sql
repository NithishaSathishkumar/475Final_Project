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

insert into state values('WA','Washington'), ('FL','Florida');
insert into phonetype values('M','mobile'), ('H','home');
insert into guest(firstname, lastname, guestnum, email, address1,city,stateid) values('F1','L1','1111', '1@1.com','address11', 'seattle', 'WA'),('F2','L2','2222', '2@2.com','address12', 'orlando', 'FL');
insert into phone(guestid,phonetypeid,phonenumber) values(1,'M','12345678'), (2,'H','12345672');

insert into room(roomnumber,capacity,priceperday) values('1001',2, 123), ('1002',4, 150),('1003',2, 118);

insert into paymenttype values('CC','credit card'), ('cs','cash');