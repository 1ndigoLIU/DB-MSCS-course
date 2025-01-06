drop schema if exists HW4_Applications;
create schema HW4_Applications;
use HW4_Applications;

create table User (
  userID int primary key auto_increment,
  username varchar(30) not null,
  firstName varchar(50) not null,
  lastName varchar(50) not null,
  email varchar(50),
  constraint uq_User_username unique (username)
);

create table Applicant (
  userID int primary key,
  program enum('masters', 'phd') not null,
  essay text,
  constraint fk_Applicant_userID foreign key (userID)
    references User(userID)
    on delete cascade
    on update cascade
);

create table Reviewer (
  userID int primary key,
  program enum('masters', 'phd') not null,
  constraint fk_Reviewer_userID foreign key (userID)
    references User(userID)
    on delete cascade
    on update cascade
);

create table LetterWriter (
  userID int primary key,
  university varchar(255) not null,
  constraint fk_LetterWriter_userID foreign key (userID)
    references User(userID)
    on delete cascade
    on update cascade
);

create table Degree (
  degreeID int primary key auto_increment,
  applicantID int not null,
  degreeType enum('bachelors', 'masters', 'phd') not null,
  grantingInstitution varchar(255) not null,
  subject varchar(255) not null,
  dateGranted date not null,
  constraint fk_Degree_applicantID foreign key (applicantID)
    references Applicant(userID)
    on delete cascade
    on update cascade
);

create table RecLetter (
  authorID int,
  applicantID int,
  dateReceived date not null,
  body text not null,
  constraint pk_RecLetter primary key (authorID, applicantID),
  constraint fk_RecLetter_authorID foreign key (authorID)
    references LetterWriter(userID)
    on delete cascade
    on update cascade,
  constraint fk_RecLetter_applicantID foreign key (applicantID)
    references Applicant(userID)
    on delete cascade
    on update cascade
);

create table Rating (
  reviewerID int,
  applicantID int,
  rating int not null,
  constraint pk_Rating primary key (reviewerID, applicantID),
  constraint fk_Rating_reviewerID foreign key (reviewerID)
    references Reviewer(userID)
    on delete cascade
    on update cascade,
  constraint fk_Rating_applicantID foreign key (applicantID)
    references Applicant(userID)
    on delete cascade
    on update cascade
);
