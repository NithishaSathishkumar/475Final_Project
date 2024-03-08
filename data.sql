INSERT INTO Position(
    NAME
)
VALUES
    ('Manager'),
    ('Receptionist')
;

INSERT INTO Staff (
    FirstName, LastName, StaffNum, Email, phoneNumber, PositionID
)
VALUES 
    ('Nithisha', 'Sathishkumar', '22954', 'nithis13@uw.edu', '4253640364', 1),
    ('Sahong', 'Song', '21125', 'song25@uw.edu', '3465816381', 2),
    ('Andy', 'Hoang', '21235', 'andyHoang@gmail.com', '7463856291', 2),
    ('Shaun', 'Cushman', '27531', 'shaun_mc@gmail.com', '6356485261', 1),
    ('Shyam', 'Ramesh', '56372', 'shyam.aws@gmail.com', '3745237452', 1),
    ('Iliya', 'Belyak', '12345', 'iliyab@yahoo.com', '7836472749', 2),
    ('Anna', 'Rivas', '87642', 'annayu683@yahoo.com', '8746372637', 2),
    ('Nasheeta', 'Lott', '67845', 'bbqhdho@yahoo.com', '1234567890', 2)
;

insert into state values('WA','Washington'), ('FL','Florida');
insert into phonetype values('M','mobile'), ('H','home');
insert into guest(firstname, lastname, guestnum, email, address1,city,stateid) values('F1','L1','1111', '1@1.com','address11', 'seattle', 'WA'),('F2','L2','2222', '2@2.com','address12', 'orlando', 'FL');
insert into phone(guestid,phonetypeid,phonenumber) values(1,'M','12345678'), (2,'H','12345672');

insert into room(roomnumber,capacity,priceperday) values('1001',2, 123), ('1002',4, 150),('1003',2, 118);

insert into paymenttype values('CC','credit card'), ('cs','cash');