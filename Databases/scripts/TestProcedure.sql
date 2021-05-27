CREATE PROCEDURE TestProcedure AS
    select * from Department;
    select * from Professor;
    select * from StudentGroup;
    select * from Student;
    select * from Classroom;
    select * from Course;
    select * from Exam;
    select * from ExamResults;
    select * from Homework;
    select * from Attendance;
    select * from Course;
GO

Exec TestProcedure