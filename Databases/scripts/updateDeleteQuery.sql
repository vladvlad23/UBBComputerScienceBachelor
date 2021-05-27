/*
	what are the Professors that don't have any courses?


Delete:

	1. Delete the students that have the same last name "Andreescu"
	and parent initial W as they have moved to another city (uses And)
	2. Delete the Courses "actuarial science","mathematical economics" ,"operation research" and 
	"abstract logic"
	3. Also delete the courses that have the form "Abstract ..." for the same reason
	as the Applied Mathematics and Abstract Mathematics department will be inactive
	3. Delete all the courses

Updates:

	1.Update all the courses from "Monday" to "Friday" as Monday will be dedicated to extra curricular 
	activities
	2.change all the professors from the applied mathematics and abstract mathematics
	department to the just mathematics department as the former 2 will be inactive for a while.
	3. All classes without any features will be given a Projector. (uses null)
	4. Update exam results where course is etc and grades are between 2 and 5 
	be also failed in the retake (no retake for this)



*/

UPDATE Course SET CourseDay='Friday' WHERE CourseDay='Monday';
UPDATE Professor SET DepartmentId='1' WHERE DepartmentId='7' OR DepartmentId='8';
UPDATE Classroom SET ClassroomFeatures='Projector' WHERE ClassroomFeatures is NULL;
UPDATE ExamResults SET GradeRetakeSession=GradeNormalSession WHERE GradeNormalSession BETWEEN 1 and 4;

DELETE FROM Student WHERE StudentLastName='Andreescu' and ParentInitial='W';
DELETE FROM Course WHERE CourseName IN ('Actuarial Science','Mathematical Economics' ,'Operation Research', 'Abstract Logic');
DELETE FROM Course WHERE CourseName LIKE 'abstract%';  /* i should test this again */


/* 
/* 
