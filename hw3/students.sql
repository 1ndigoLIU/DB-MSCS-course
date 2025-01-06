DROP SCHEMA IF EXISTS HW3_Students;
CREATE SCHEMA HW3_Students;
USE HW3_Students;

-- CREATE TABLE statements go here
CREATE TABLE Person (
	idNumber INT,
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Person_idNumber PRIMARY KEY (idNumber)
);

CREATE TABLE Student (
	idNumber INT,
    enrollmentDate DATE NOT NULL,
    CONSTRAINT pk_Student_idNumber PRIMARY KEY (idNumber),
    CONSTRAINT fk_Student_idNumber FOREIGN KEY (idNumber)
		REFERENCES Person(idNumber)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Department(
	`name` VARCHAR(255),
    CONSTRAINT pk_Department_name PRIMARY KEY (`name`)
);

CREATE TABLE Faculty (
	idNumber INT,
    department VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Faculty_idNumber PRIMARY KEY (idNumber),
    CONSTRAINT fk_Faculty_idNumber FOREIGN KEY (idNumber)
		REFERENCES Person(idNumber)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Faculty_department FOREIGN KEY (department)
		REFERENCES Department(`name`)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Message (
	messageID INT AUTO_INCREMENT,
    sender INT NOT NULL,
    recipient INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    body VARCHAR(255) NOT NULL,
    CONSTRAINT pk_Message_messageID PRIMARY KEY (messageID),
    CONSTRAINT fk_Message_sender FOREIGN KEY (sender)
		REFERENCES Person(idNumber)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Message_recipient FOREIGN KEY (recipient)
		REFERENCES Person(idNumber)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Course (
	catalogNumber VARCHAR(6),
    department VARCHAR(255) NOT NULL,
    `description` VARCHAR(255) NOT NULL,
    lecturer INT,
    CONSTRAINT pk_Course_catalogNumber PRIMARY KEY (catalogNumber),
    CONSTRAINT fk_Course_department FOREIGN KEY (department)
		REFERENCES Department(`name`)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Course_lecturer FOREIGN KEY (lecturer)
		REFERENCES Faculty(idNumber)
        ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Registration (
	studentID INT,
    course VARCHAR(6),
    grade INT,
    CONSTRAINT pk_Registration_studentID_course PRIMARY KEY (studentID, course),
    CONSTRAINT fk_Registration_studentID FOREIGN KEY (studentID)
		REFERENCES Student(idNumber)
        ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_Registration_course FOREIGN KEY (course)
		REFERENCES Course(catalogNumber)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- Problem 1:
INSERT INTO Person (idNumber, firstName, lastName) 
	VALUES (2475343, 'John', 'Smith');
INSERT INTO Student (idNumber, enrollmentDate)
	VALUES (2475343, '2023-09-01');

-- Problem 2:
INSERT INTO Department (`name`)
	VALUES ('Computer Science CS');
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5200', 'Computer Science CS', 'Introduces relational database management systems as a class of software systems. Prepares students to be sophisticated users of database management systems.', NULL);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2475343, 'CS5200', NULL);

-- Add extra records for testing queries
INSERT INTO Person (idNumber, firstName, lastName) 
	VALUES (2311676, 'Xihao', 'Liu');
INSERT INTO Student (idNumber, enrollmentDate)
	VALUES (2311676, '2024-09-01');
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5010', 'Computer Science CS', 'Introduces modern program design paradigms. Starts with functional program design, introducing the notion of a design recipe.', NULL);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2311676, 'CS5010', 99);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2311676, 'CS5200', 97);
INSERT INTO Department (`name`)
	VALUES ('English');
INSERT INTO Person (idNumber, firstName, lastName) 
	VALUES (1000001, 'David', 'Leon');
INSERT INTO Faculty (idNumber, department)
	VALUES (1000001, 'English');
INSERT INTO Person (idNumber, firstName, lastName) 
	VALUES (1000002, 'Rider', 'Cook');
INSERT INTO Faculty (idNumber, department)
	VALUES (1000002, 'English');
INSERT INTO `hw3_students`.`message` (`messageID`, `sender`, `recipient`, `title`, `body`) VALUES ('1', '1000001', '2311676', 'a', 'a');
INSERT INTO `hw3_students`.`message` (`messageID`, `sender`, `recipient`, `title`, `body`) VALUES ('2', '1000001', '2475343', 'a', 'a');
INSERT INTO `hw3_students`.`message` (`messageID`, `sender`, `recipient`, `title`, `body`) VALUES ('3', '1000001', '1000002', 'a', 'a');
INSERT INTO `hw3_students`.`message` (`messageID`, `sender`, `recipient`, `title`, `body`) VALUES ('4', '2475343', '1000001', 'c', 'a');
INSERT INTO `hw3_students`.`message` (`messageID`, `sender`, `recipient`, `title`, `body`) VALUES ('5', '2311676', '1000002', 'c', 'a');
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5011', 'Computer Science CS', 'des.', 1000002);
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5012', 'Computer Science CS', 'des.', 1000002);
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5013', 'Computer Science CS', 'des.', 1000002);
INSERT INTO Course (catalogNumber, department, `description`, lecturer)
	VALUES ('CS5030', 'Computer Science CS', 'des.', 1000002);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2311676, 'CS5011', NULL);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2311676, 'CS5012', NULL);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2311676, 'CS5013', NULL);
INSERT INTO Registration (studentID, course, grade)
	VALUES (2475343, 'CS5013', NULL);

-- Problem 3:
SELECT Registration.studentID AS `ID#`, firstName, LastName
	FROM Registration INNER JOIN Person ON Registration.studentID = Person.idNumber
    WHERE Registration.course = 'CS5010';

-- Problem 4:
SELECT studentID, AVG(grade) AS average
	FROM Registration
	GROUP BY studentID;

-- Problem 5:
SELECT Faculty.idNumber AS idNumber, Person.firstName AS firstName, Person.lastName AS lastName, COUNT(Message.messageID) AS numberOfMessages
	FROM Person INNER JOIN Faculty USING (idNumber)
		LEFT OUTER JOIN Message ON Faculty.idNumber = Message.sender
	GROUP BY idNumber;

-- Problem 6:
SELECT course AS catalogNumber, COUNT(DISTINCT studentID) AS numberOfStudents
	FROM Registration
    GROUP BY catalogNumber
    ORDER BY numberOfStudents DESC
    LIMIT 5;
    
-- Problem 7:
SELECT studentID, firstName, lastName, course AS catalogNumber
	FROM Registration INNER JOIN Person ON Registration.studentID = Person.idNumber
    WHERE Registration.grade IS NULL;
    
-- Problem 8:
SELECT Faculty.idNumber AS idNumber, firstName, lastName, COUNT(catalogNumber) AS numberOfCourses
	FROM Person INNER JOIN Faculty ON Person.idNumber = Faculty.idNumber
		LEFT OUTER JOIN Course ON Faculty.idNumber = Course.lecturer
	GROUP BY Faculty.idNumber;
