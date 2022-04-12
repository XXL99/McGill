-- Include your INSERT SQL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the INSERT statements for the tables with foreign key references
--    ONLY AFTER the parent tables!

-- This is only an example of how you add INSERT statements to this file.
--   You may remove it.
INSERT INTO Father (ID, name, address, birthday, profession, email, phone#, health_id, bloodtype)
VALUES (1515,'Bianca Fowler','Ap #523-5448 Fermentum Road','1994-06-12','teacher','metus@yahoo.edu','1-366-994-3805',427124886,'A'),
  (7315,'Barclay Peck','5297 Ante Road','1997-05-04','engineer','augue.ut.lacus@outlook.net','1-695-966-2057',42222143,'O'),
  (2925,'Garrett Riggs','Ap #730-3781 Natoque Rd.','1996-08-22','doctor','mauris.blandit.enim@protonmail.com','1-341-563-3617',442821364,'AB'),
  (2880,'Halee Duke','Ap #358-9980 Mattis St.','1998-04-30','unemployed','eget.mollis.lectus@icloud.couk','1-857-717-2855',433064208,'O'),
  (9840,'Cora Frank','696-4700 Class St.','1996-11-25','manager','odio@protonmail.edu','1-756-707-7527',473097931,'B');

INSERT INTO Mother (health_id, Phone#, address, name, bloodtype, birthday, profession, email)
VALUES
    (71532933,'1-373-774-2077','Ap #471-2602 Tempus Av.','Tara Potts','A','1993-10-10','teacher','at@hotmail.edu'),
    (45163724,'1-613-211-4666','618-9092 Risus St.','Candice Bruce','B','1995-12-14','professor','egestas.blandit@hotmail.net'),
    (41584645,'1-340-885-3211','Ap #464-2177 Ipsum. Street','Kiayada Stanley','O','1997-07-15','engineer','aliquam.ultrices@yahoo.net'),
    (8389969,'1-657-834-1684','P.O. Box 228, 4229 Inceptos Rd.','Rina Hurley','O','1998-03-17','doctor','ornare.facilisis@protonmail.net'),
    (294969047,'1-553-966-1443','701-4705 Ultricies Street','Victoria Gutierrez','A','1998-12-29','journalist','nisl.sem.consequat@yahoo.edu');

INSERT INTO Institution (email, address, type, phone#, Web, name)
VALUES
    ('mi.tempor@yahoo.org','P.O. Box 863, 5333 Fusce St.','Community clinic','1-898-452-7430','https://www.example.com/?bedroom=branch,','Amet Consectetuer Clinic'),
    ('ultrices@protonmail.edu','P.O. Box 249, 9470 Urna, Rd.','Community clinic','1-247-565-3269','http://www.example.com/airplane.htm','Tellus Aenean Clinic'),
    ('nunc.laoreet.lectus@google.ca','552-9902 Amet Rd.','Birthing center','1-442-325-7903','http://www.example.net/,','Quam Dignissim Pharetra Center'),
    ('nulla@aol.org','915-8533 Porta St.','Community clinic','1-174-266-5796','https://amount.example.com/afterthought.html','Ipsum Nunc Clinc'),
    ('enim.commodo@google.edu','P.O. Box 104, 6004 Eu Av.','Birthing center','1-291-254-1572','http://example.com/?bone=bit#art','Lac-Saint-Louis');

INSERT INTO MidWife(ID, name, phone#, email, Institution_email)
VALUES
    (4138,'Marion Girard','1-471-515-8856','ligula.aenean@protonmail.com','enim.commodo@google.edu'),
    (8335,'Iliana Marsh','1-804-552-8143','ac.facilisis@aol.edu','enim.commodo@google.edu'),
    (9772,'Zelda Zimmerman','1-452-668-5888','et.magnis@protonmail.com','mi.tempor@yahoo.org'),
    (9229,'Tamekah Colon','1-434-515-7157','est.nunc@icloud.org','enim.commodo@google.edu'),
    (8745,'Ava Reyes','1-583-288-1112','in@hotmail.couk','ultrices@protonmail.edu');

INSERT INTO Pregnancy (MotherHealthID, ExpTimeFrame, NumOfTimes , #ofBabies, ExpDate, finExpDate, LastMenPeriod, ifInvite, FatherID, birthAt, homeBirth)
VALUES
    (294969047,'2021-01-02',1,2,'2021-01-12','2021-01-12','2020-01-02','true',1515,'enim.commodo@google.edu','false'),
    (294969047,'2022-06-21',2,1,'2022-06-26','2022-06-21','2021-07-21','true',1515,'enim.commodo@google.edu','false'),
    (41584645,'2021-05-04',1,1,'2021-05-05','2021-05-05','2020-08-04','true',2925,NULL,'true'),
    (8389969,'2021-06-10',2,1,'2021-06-20','2021-06-20','2020-08-10','true',2880,'nunc.laoreet.lectus@google.ca','false'),
    (71532933,'2022-06-14',2,2,'2022-06-25','2022-06-25','2021-07-14','true',9840,'nunc.laoreet.lectus@google.ca','false');

INSERT INTO Info_session (MidwifeID, date, Time, language)
VALUES
    (4138,'2022-01-25','14:45:00','English'),
    (8335,'2021-01-20','12:30:00','English'),
    (9772,'2020-09-17','12:20:00','French'),
    (9229,'2021-01-22','09:25:00','English'),
    (8745,'2022-01-06','11:45:00','French');

INSERT INTO Registered (MotherHealthID, ExpTimeFrame, MidwifeID, date, time)
VALUES
    (294969047,'2021-01-02',9772,'2020-09-17','12:20:00'),
    (294969047,'2022-06-21',8745,'2022-01-06','11:45:00'),
    (41584645,'2021-05-04',9229,'2021-01-22','09:25:00'),
    (8389969,'2021-06-10',8335,'2021-01-20','12:30:00'),
    (71532933,'2022-06-14',4138,'2022-01-25','14:45:00');

INSERT INTO Attend (MotherHealthID, ExpTimeFrame, MidwifeID, date, time, IfInterested)
VALUES
    (294969047,'2021-01-02',9772,'2020-09-17','12:20:00','true'),
    (294969047,'2022-06-21',8745,'2022-01-06','11:45:00','true'),
    (41584645,'2021-05-04',9229,'2021-01-22','09:25:00','true'),
    (8389969,'2021-06-10',8335,'2021-01-20','12:30:00','true'),
    (71532933,'2022-06-14',4138,'2022-01-25','14:45:00','true');

INSERT INTO Assignment (assignID, PrimaryMF, BackMF, MotherHealthID, ExpTimeFrame)
VALUES
    (1493,8745,8335,294969047,'2021-01-02'),
    (8178,8335,9772,294969047,'2022-06-21'),
    (5658,9772,9229,41584645,'2021-05-04'),
    (7864,9229,4138,8389969,'2021-06-10'),
    (2017,4138,8745,71532933,'2022-06-14');

INSERT INTO Appointments (ID, Time, Date, AssignID)
VALUES
    (1249,'14:36:00','2020-09-02',8178),
    (2157,'12:56:00','2022-03-21',2017),
    (6666,'11:44:00','2021-01-04',5658),
    (7954,'13:17:00','2021-02-10',7864),
    (2365,'07:43:00','2022-03-24',2017);

INSERT INTO Note (appID, TimeStamp, Date)
VALUES
    (1249,'14:40:44','2020-09-02'),
    (2157,'12:58:06','2022-03-21'),
    (6666,'11:55:11','2021-01-04'),
    (7954,'13:20:01','2021-02-10'),
    (2365,'07:50:20','2022-03-24');

INSERT INTO Technician (ID, Phone#, name)
VALUES
    (4801,'(655) 624-7141','Hop Allison'),
    (8237,'(802) 405-7462','Deborah Mcguire'),
    (3389,'(352) 751-4742','Angela Tillman'),
    (2310,'(372) 647-5317','Xaviera Newman'),
    (7172,'1-336-777-3571','Felicia Madden');

INSERT INTO Lab (ID, name, address)
VALUES
    (2897,'Aliquam Iaculis Associates','P.O. Box 818, 9125 Praesent Avenue'),
    (5077,'Pellentesque Ultricies Inc.','761-8885 Morbi Rd.'),
    (6810,'Ornare Foundation','844-5169 Ullamcorper Rd.'),
    (6822,'Euismod Est Foundation','2239 A, Rd.'),
    (9079,'At Consulting','488-6912 Eget St.');

INSERT INTO Test (ID, preDate, sampleDate, labDate, Type, result, TechID, labID)
VALUES
    (5534,'2020-09-02','2020-09-12','2020-09-22','blood iron test','65 mcg/dL. Positive',4801,2897),
    (4148,'2022-03-21','2022-03-20','2022-04-10','blood type test','O',8237,2897),
    (2554,'2021-01-04','2021-02-04','2021-02-14','blood iron test','70 mcg/dL. Positive',3389,5077),
    (9687,'2021-02-10','2021-04-10','2021-04-10','blood iron test','110 mcg/dL. Positive',2310,6810),
    (9711,'2022-03-24','2022-05-24','2022-05-25','blood pressure test','Low: 80; High:110; Good',7172,9079);

INSERT INTO Prescribe (testID, appID, ifmadeByMF)
VALUES
    (5534,1249,'true'),
    (4148,2157,'true'),
    (2554,6666,'false'),
    (9687,7954,'false'),
    (9711,2365,'false');

INSERT INTO applyTestOn (testID, MotherHealthID, ExpTimeFrame)
VALUES
    (5534,294969047,'2021-01-02'),
    (4148,294969047,'2022-06-21'),
    (2554,41584645,'2021-05-04'),
    (9687,8389969,'2021-06-10'),
    (9711,71532933,'2022-06-14');

INSERT INTO Babies (MotherHealthID, ExpTimeFrame, Time, birthdate, bloodtype, name, gender)
VALUES
    (294969047,'2021-01-02','12:03:38','2021-01-12','O','Asher Gordon','female'),
    (294969047,'2021-01-02','12:04:40','2021-01-12','O','Cameron Gordon','female'),
    (41584645,'2021-05-04','05:39:26','2021-06-04','AB','Gail Massey','female'),
    (8389969,'2021-06-10','02:07:48','2021-06-12','O',NULL,'male'),
    (71532933,'2022-06-14','05:12:00','2022-06-18','B','Emery Henson','female'),
    (71532933,'2022-06-14','05:15:05','2022-06-18','A','Logan Henson','male');






