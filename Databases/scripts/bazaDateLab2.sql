CREATE TABLE Department(
	DepartmentId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	DepartmentName varchar(50) NOT NULL
	);

CREATE TABLE Professor(
	ProfessorId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	Name varchar(50) NOT NULL,
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
	StudentName varchar(50) NOT NULL,
	ParentInitial varchar(3) NOT NULL,
	GroupId int FOREIGN KEY REFERENCES StudentGroup(StudentGroupId)
	);

CREATE TABLE Classroom(
		ClassroomId int NOT NULL PRIMARY KEY IDENTITY(1,1),
		ClassroomAddress varchar(50) NOT NULL
	);

CREATE TABLE Course (
	CourseId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	ProfessorId int FOREIGN KEY REFERENCES Professor(ProfessorId),
	CourseDay varchar(20) NOT NULL,
	ClassroomId int FOREIGN KEY REFERENCES Classroom(ClassroomId)
);

CREATE TABLE Exam(
	ExamId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId)
);

CREATE TABLE ExamResults(
	ExamResultsId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	GradeNormalSession int,
	GradeRetakeSession int,
	StudentId int FOREIGN KEY REFERENCES Student(StudentId)
);

CREATE TABLE Homework(
	HomeworkId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId),
	Grade int,
	Deadline date NOT NULL
);

CREATE TABLE Attendance(
	AttendanceId int NOT NULL PRIMARY KEY IDENTITY(1,1),
	StudentId int FOREIGN KEY REFERENCES Student(StudentId),
	CourseId int FOREIGN KEY REFERENCES Course(CourseId),
	AttendanceDate date NOT NULL
);