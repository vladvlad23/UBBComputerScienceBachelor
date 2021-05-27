/*

SELECT
1. We must get all the people in the school to organize them for a charitable event
Get all the names of the professors and of the students (union)
2. We must get all the students that failed in the first OR the second session of the Algebra Final exam

b.
We need to put all students that failed both statistics exam in a study group. get them all (intersect)

Get all	the courses that take place in the last 2 days of the week as some students need to go on a vacation 


c.
We need to see how many students lost credits because of statistics only 
all students that failed both their Statistics exam, but failed neither algebra exams (except)


Select all the student except the first 2 groups as they have already participated in a
required activity(not in)

d.
select all the students that have results on the exams they have enrolled for (didn't skip exams)
and the data related to the exams

select all the names of the professors that have assigned homework for their courses and the 
homework name

select all classrooms and their courses including classrooms that don't have a course to them

select all courses and their homeworks including courses with no homeworks


e.
select all students first and last name where the students don't have passing grades in the algebra exam 

select the course names where we can find people that tried to cheat (got a grade 1) during both
retake and normal session 


f.
We need to give prizes to best students. 
select students that passed both algebra and statistics(EXIST SELECT from ExamRes where grades >5) 
and top 3 them

we need to find what professors have at least one course so we check the professors where
at least a course has their professor id


g. get all professors that have an above average salary (need to introduce salaries first)
SELECT Name
from ( 
select avg(salary) as AverageSalary
from professor ) as Budget, Professor as P
where budget.averageSalary < P.Salary

get all students from group 2 that failed exams

h. 
select students that failed in the normal session, but succeeded in the retake session and group 
them by group id

select students grouped by group id that sustained the second statistics exam

select all exams grouped by id having the average normal session grade greater than 5

select all exam results grouped by student id which have been failed

i.

check if there are any exams where the student Popescu failed

We need all exam ids where the students belong to the second group

We need the full name of the student that has taken the biggest grade in the normal session 
in the first statistics exam

We need the full name of the student that has the worst grade average of the Algebra Exams





(good for nested?) Get all the students who ar enrolled in a specific course
		4.Select all students which have names derivated from Alex as it is their name birthday
	and we send them an email by id (uses in operator)
		5. Students must participate to a conference and they are too many for us to send at once
	so we will send them alphabetically. Select all the students whose last name begins
	with 'a' to get the first series. 
	*/

	/*a*/
SELECT P.FirstName,P.LastName
FROM Professor AS P
UNION
SELECT S.StudentFirstName,S.StudentLastName
FROM Student as S

SELECT E.StudentId
FROM ExamResults as E
WHERE E.ExamId = 4 and (E.GradeNormalSession<5 or E.GradeRetakeSession<5)


/*b*/
SELECT E.StudentId
FROM ExamResults AS E
WHERE E.ExamId = 1 AND E.GradeNormalSession<5
INTERSECT
SELECT E.StudentId
FROM ExamResults AS E
WHERE E.ExamId = 2 AND E.GradeNormalSession<5


SELECT C.CourseName
FROM Course AS C
WHERE C.CourseDay IN ('Saturday','Sunday')

/*c*/
SELECT E.StudentId
FROM ExamResults as E
WHERE E.ExamResultsId IN(
	SELECT E.ExamResultsId
	FROM ExamResults AS E
	WHERE E.ExamId = 1 AND E.GradeNormalSession < 5 and E.GradeRetakeSession < 5
	UNION
	SELECT E.StudentId
	FROM ExamResults as E
	WHERE E.ExamId = 2 AND E.GradeNormalSession < 5 AND E.GradeRetakeSession < 5
)
EXCEPT 
SELECT E.StudentId
FROM ExamResults AS E
WHERE E.ExamId = 3 AND E.ExamId = 4 AND E.GradeNormalSession > 5 AND E.GradeRetakeSession > 5

SELECT S.StudentFirstName,S.StudentLastName
FROM STUDENT AS S
WHERE S.GroupId NOT IN (1,2)

-- d)
SELECT S.StudentFirstName, S.StudentLastName, ER.GradeNormalSession,E.ExamName,C.CourseName -- joining 2 many to many relationships
FROM Student AS S 
INNER JOIN ExamResults ER on 
	S.StudentId = ER.StudentId 
INNER JOIN Exam E ON
	ER.ExamId = E.ExamId
INNER JOIN CourseEnrollment CE ON
	E.CourseId = CE.CourseId
INNER JOIN Course C ON
	C.CourseId = CE.CourseId

SELECT P.FirstName, P.LastName, C.CourseName, H.HomeworkName -- join 3 tables
FROM Professor as P
FULL JOIN Course C ON
	P.ProfessorId = C.ProfessorId
FULL JOIN Homework H ON
	H.CourseId = C.CourseId

SELECT C.CourseName, CL.ClassroomAddress -- classrooms and their courses including classrooms that don't have a course to them
FROM Course C
RIGHT JOIN Classroom CL ON
	C.ClassroomId = CL.ClassroomId

SELECT  C.CourseName,H.HomeworkName-- courses and their homeworks including courses with no homeworks
FROM Course C
LEFT JOIN Homework H ON
	H.CourseId = C.CourseId





-- e)
SELECT S.StudentFirstName,S.StudentLastName
FROM Student AS S
WHERE S.StudentId IN (
	SELECT E.StudentId
	FROM ExamResults AS E
	WHERE E.ExamId = 3 and E.ExamId = 4 AND E.GradeNormalSession < 5 AND E.GradeRetakeSession < 5
)
/*
SELECT 
FROM Student as S
WHERE S.StudentId IN (
	SELECT C.StudentId
	FROM CourseEnrollment AS C
	WHERE C.CourseId 

	*/
SELECT C.CourseName
FROM Course as C
WHERE C.CourseId IN (
	SELECT E.CourseId
	FROM Exam as E
	WHERE E.ExamId IN (
		SELECT ER.ExamId
		FROM ExamResults AS ER
		WHERE ER.GradeNormalSession = 1 and ER.GradeRetakeSession = 1 
	)
)

-- f)

SELECT TOP 3 S.StudentFirstName,S.StudentLastName
FROM Student as S
WHERE EXISTS (
	SELECT ER.StudentId
	FROM ExamResults AS ER
	WHERE ER.GradeNormalSession > 5 or ER.GradeRetakeSession > 5 
)

SELECT P.FirstName,P.LastName
FROM Professor as P
WHERE EXISTS (
	SELECT *
	FROM Course as C
	WHERE C.ProfessorId is not NULL
	)

-- g)

SELECT P2.FirstName,P2.LastName
FROM ( 
	SELECT avg(P1.Salary) AS AverageSalary
	FROM professor AS P1) AS Budget, Professor AS P2
WHERE Budget.averageSalary < P2.Salary


SELECT GroupTwo.StudentFirstName, GroupTwo.StudentLastName
FROM (
	SELECT *
	FROM Student AS S1
	WHERE S1.GroupId = 2) AS GroupTwo, ExamResults as E
WHERE E.GradeNormalSession < 5 AND E.GradeRetakeSession < 5

-- h)

SELECT S2.StudentFirstName, S2.StudentLastName
FROM (
	SELECT S.StudentId
	FROM Student AS S
	GROUP BY S.GroupId,S.StudentId
	HAVING S.StudentId IN (
		SELECT ER.StudentId
		FROM ExamResults AS ER
		WHERE ER.GradeNormalSession <5)
) AS Res,Student S2
WHERE Res.StudentId = S2.StudentId

SELECT S2.StudentFirstName, S2.StudentLastName
FROM(
	SELECT S.StudentId
	FROM Student AS S
	GROUP BY S.GroupId, S.StudentId
	HAVING S.StudentId IN (
		SELECT ER.StudentId
		FROM ExamResults AS ER
		WHERE ER.ExamId = 2
	)
) AS Res, Student S2
WHERE Res.StudentId = S2.StudentId

SELECT ER.ExamResultsId
FROM ExamResults as ER
GROUP BY ER.ExamId, ER.ExamResultsId
HAVING AVG(ER.GradeNormalSession) > 5

SELECT *
FROM (
	SELECT ER.StudentId
	FROM ExamResults AS ER
	GROUP BY  ER.StudentId, ER.GradeNormalSession
	HAVING ER.GradeNormalSession < 5 ) AS Res, ExamResults as ER
WHERE Res.StudentId = Er.StudentId


-- i )

SELECT ER.ExamId 
FROM ExamResults AS ER
WHERE ER.StudentId = ANY (Select S.StudentId FROM Student AS S WHERE S.StudentLastName = 'Popescu') 
AND ER.GradeNormalSession < 5

SELECT ER.ExamId
FROM ExamResults AS ER
WHERE ER.StudentId NOT IN (Select S.StudentId FROM Student AS S WHERE S.StudentLastName <> 'Popescu') 

SELECT ER.ExamId
FROM ExamResults AS ER
WHERE ER.StudentId = ANY (Select S.StudentId FROM Student AS S WHERE S.GroupId = 2)

SELECT ER.ExamId
FROM ExamResults AS ER
WHERE ER.StudentId NOT IN (SELECT S.StudentId FROM Student AS S Where S.GroupId <> 2)


SELECT S.StudentFirstName, S.StudentLastName 
FROM Student AS S, ExamResults AS ER
WHERE ER.ExamId = 1  AND GradeNormalSession > ALL (
	SELECT ER2.GradeNormalSession 
	FROM ExamResults AS ER2
	WHERE ER2.ExamId = 1
	)

	
SELECT S.StudentFirstName, S.StudentLastName 
FROM Student AS S, ExamResults AS ER
WHERE ER.ExamId = 1 or ER.ExamId = 2 AND GradeNormalSession > ALL (
	SELECT AVG(ER2.GradeNormalSession)
	FROM ExamResults AS ER2
	WHERE ER2.ExamId = 4 OR ER2.ExamId = 2
)




