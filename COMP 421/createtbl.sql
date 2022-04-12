-- Include your create table DDL statements in this file.
-- Make sure to terminate each statement with a semicolon (;)

-- LEAVE this statement on. It is required to connect to your database.
CONNECT TO cs421;

-- Remember to put the create table ddls for the tables with foreign key references
--    ONLY AFTER the parent tables has already been created.

-- This is only an example of how you add create table ddls to this file.
--   You may remove it.
CREATE TABLE Father
(
    ID         INTEGER PRIMARY KEY NOT NULL ,
    name       VARCHAR(30),
    address    VARCHAR(200),
    birthday   DATE,
    profession VARCHAR(30),
    email      VARCHAR(255) UNIQUE NOT NULL ,
    phone#     VARCHAR(20),
    health_id  INTEGER, --assume the id composed of only integers
    bloodtype  VARCHAR(10)
);
CREATE TABLE Mother
(
    health_id  INTEGER PRIMARY KEY NOT NULL ,
    Phone#     VARCHAR(20),
    address    VARCHAR(200),
    name       VARCHAR(30),
    bloodtype  VARCHAR(10),
    birthday   DATE,
    profession VARCHAR(30),
    email      VARCHAR(255) UNIQUE NOT NULL
);
CREATE TABLE Institution
(
    email   VARCHAR(255) PRIMARY KEY NOT NULL ,
    address VARCHAR(200) NOT NULL,
    type    VARCHAR(50)  NOT NULL,
    phone#  VARCHAR(20),
    Web     VARCHAR(500),
    name    VARCHAR(30) UNIQUE NOT NULL
);
CREATE TABLE MidWife
(
    ID                INTEGER PRIMARY KEY NOT NULL,
    name              VARCHAR(30),
    phone#            VARCHAR(20),
    email             VARCHAR(255) UNIQUE NOT NULL ,
    Institution_email VARCHAR(255) NOT NULL,
    FOREIGN KEY (Institution_email) REFERENCES institution
);
CREATE TABLE Pregnancy
(
    MotherHealthID INTEGER NOT NULL ,
    ExpTimeFrame   DATE NOT NULL ,
    NumOfTimes     INTEGER NOT NULL DEFAULT 1,
    #ofBabies      INTEGER,
    ExpDate        DATE,
    finExpDate     DATE,
    LastMenPeriod  DATE,
    ifInvite       BOOLEAN,
    FatherID       INTEGER,
    birthAt        VARCHAR(200),
    homeBirth      BOOLEAN NOT NULL DEFAULT true,
    PRIMARY KEY (MotherHealthID, ExpTimeFrame),
    FOREIGN KEY (MotherHealthID) REFERENCES Mother,
    FOREIGN KEY (FatherID) REFERENCES Father,
    FOREIGN KEY (birthAt) REFERENCES Institution
);
CREATE TABLE Info_session
(
    MidwifeID INTEGER NOT NULL ,
    date      DATE NOT NULL ,
    time      TIME NOT NULL ,
    language  VARCHAR(20),
    PRIMARY KEY (MidwifeID, date, time),
    FOREIGN KEY (MidwifeID) REFERENCES MidWife
);
CREATE TABLE Registered
(
    MotherHealthID INTEGER NOT NULL ,
    ExpTimeFrame   DATE NOT NULL ,
    MidwifeID      INTEGER NOT NULL ,
    date           DATE NOT NULL ,
    time           TIME NOT NULL ,
    PRIMARY KEY (MotherHealthID,ExpTimeFrame,MidwifeID,date,time),
    FOREIGN KEY (MotherHealthID,ExpTimeFrame) REFERENCES Pregnancy,
    FOREIGN KEY (MidwifeID,date,time) REFERENCES Info_session
);
CREATE TABLE Attend
(
    MotherHealthID INTEGER NOT NULL ,
    ExpTimeFrame   DATE NOT NULL ,
    MidwifeID      INTEGER NOT NULL ,
    date           DATE NOT NULL ,
    time           TIME NOT NULL ,
    ifInterested   BOOLEAN,
    PRIMARY KEY (MotherHealthID,ExpTimeFrame,MidwifeID,date,time),
    FOREIGN KEY (MotherHealthID,ExpTimeFrame) REFERENCES Pregnancy,
    FOREIGN KEY (MidwifeID,date,time) REFERENCES Info_session
);
CREATE TABLE Assignment
(
    assignID       INTEGER NOT NULL,
    PrimaryMF      INTEGER NOT NULL,
    BackMF         INTEGER NOT NULL,
    MotherHealthID INTEGER NOT NULL,
    ExpTimeFrame   DATE NOT NULL,
    PRIMARY KEY (assignID),
    FOREIGN KEY (PrimaryMF) REFERENCES MidWife (ID),
    FOREIGN KEY (BackMF) REFERENCES MidWife (ID),
    FOREIGN KEY (MotherHealthID, ExpTimeFrame) REFERENCES Pregnancy
);
CREATE TABLE Appointments
(
    ID       INTEGER PRIMARY KEY NOT NULL ,
    Time     TIME,
    Date     DATE,
    assignID INTEGER NOT NULL ,
    FOREIGN KEY (assignID) REFERENCES Assignment
);
CREATE TABLE Note
(
    appID     INTEGER NOT NULL ,
    TimeStamp TIME NOT NULL ,
    Date      DATE NOT NULL,
    PRIMARY KEY (appID, TimeStamp),
    FOREIGN KEY (appID) REFERENCES Appointments
);
CREATE TABLE Technician
(
    ID     INTEGER PRIMARY KEY NOT NULL ,
    Phone# VARCHAR(20),
    name   VARCHAR(30)
);
CREATE TABLE Lab
(
    ID      INTEGER PRIMARY KEY NOT NULL ,
    name    VARCHAR(30),
    address VARCHAR(500)
);
CREATE TABLE Test
(
    ID         INTEGER PRIMARY KEY NOT NULL ,
    preDate    DATE NOT NULL ,
    sampleDate DATE,
    labDate    DATE ,
    Type       VARCHAR(30) NOT NULL,
    result     VARCHAR(500),
    TechID     INTEGER,
    labID      INTEGER,
    CHECK ( labDate>preDate ),
    FOREIGN KEY (TechID) REFERENCES Technician,
    FOREIGN KEY (labID) REFERENCES Lab
);
CREATE TABLE Prescribe
(
    testID     INTEGER NOT NULL ,
    appID      INTEGER NOT NULL ,
    ifmadeByMF BOOLEAN NOT NULL ,
    PRIMARY KEY (testID, appID),
    FOREIGN KEY (testID) REFERENCES Test,
    FOREIGN KEY (appID) REFERENCES Appointments
);
CREATE TABLE applyTestOn
(
    testID         INTEGER NOT NULL ,
    MotherHealthID INTEGER NOT NULL ,
    ExpTimeFrame   DATE NOT NULL ,
    PRIMARY KEY (testID, MotherHealthID, ExpTimeFrame),
    FOREIGN KEY (testID) REFERENCES Test,
    FOREIGN KEY (MotherHealthID, ExpTimeFrame) REFERENCES Pregnancy
);
CREATE TABLE Babies
(
    MotherHealthID INTEGER NOT NULL ,
    ExpTimeFrame   DATE NOT NULL ,
    Time           TIME NOT NULL ,
    birthdate      DATE NOT NULL ,
    bloodtype      VARCHAR(10) NOT NULL,
    name           VARCHAR(30),
    gender         VARCHAR(10) NOT NULL,
    PRIMARY KEY (MotherHealthID, ExpTimeFrame, Time, birthdate),
    FOREIGN KEY (MotherHealthID, ExpTimeFrame) REFERENCES Pregnancy
);

















