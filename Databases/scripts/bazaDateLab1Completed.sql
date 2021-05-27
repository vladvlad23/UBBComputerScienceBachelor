CREATE TABLE Department(
	DepartmentId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	DepartmentName varchar(50) NOT NULL
	);

CREATE TABLE Professor(
	ProfessorId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	FirstName varchar(50) NOT NULL,
	LastName varchar(50) NOT NULL,
	DomainOfInterest varchar(50),
	DepartmentId int FOREIGN KEY REFERENCES Department(DepartmentID)
	);

CREATE TABLE StudentGroup (
	StudentGroupId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	ProfessorId int NOT NULL FOREIGN KEY REFERENCES Professor(ProfessorId),
	GroupYear int NOT NULL,
	GroupNumber int NOT NULL
	);
	
CREATE TABLE Student ( 
	StudentId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	StudentFirstName varchar(50) NOT NULL,
	StudentLastName varchar(50) NOT NULL,
	ParentInitial varchar(3) NOT NULL,
	GroupId int FOREIGN KEY REFERENCES StudentGroup(StudentGroupId)
	);

CREATE TABLE Classroom(
		ClassroomId int NOT NULL PRIMARY KEY IDENTITY(1,1),
		ClassroomAddress varchar(50) NOT NULL,
		ClassroomFeatures varchar(50) NOT NULL
	);

CREATE TABLE Course (
	CourseId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	ProfessorId int FOREIGN KEY REFERENCES Professor(ProfessorId),
	CourseDay varchar(20) NOT NULL,
	ClassroomId int FOREIGN KEY REFERENCES Classroom(ClassroomId)
);

CREATE TABLE Exam(
	ExamId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	ExamName varchar(50),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId)
);

CREATE TABLE ExamResults(
	ExamResultsId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	GradeNormalSession int,
	GradeRetakeSession int,
	ExamId int FOREIGN KEY REFERENCES Exam(ExamId),
	StudentId int FOREIGN KEY REFERENCES Student(StudentId)
);

CREATE TABLE Homework(
	HomeworkId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId),
	Grade int,
	HomeworkName varchar(50),
	Deadline date NOT NULL
);

CREATE TABLE Attendance(
	AttendanceId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	StudentId int FOREIGN KEY REFERENCES Student(StudentId),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId),
	AttendanceDate date NOT NULL
);

CREATE TABLE CourseEnrollment(
	CourseEnrollmentId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	CourseId int NOT NULL FOREIGN KEY REFERENCES Course(CourseId),
	StudentId int NOT NULL FOREIGN KEY REFERENCES Student(StudentId)
);

ALTER TABLE Course ADD TimeOfCourse varchar(10);
ALTER TABLE Course ADD CourseName varchar(10) NOT NULL;
ALTER TABLE Student ALTER COLUMN ParentInitial varchar(3) NULL; /* treat case where orphan */
ALTER TABLE Professor ADD Salary int;