-- noinspection SqlWithoutWhereForFile


--view all the students
CREATE VIEW viewStudents AS
    SELECT S.StudentFirstName,S.StudentLastName
    FROM Student as S
GO


-- view all students that failed exams and the exams
CREATE VIEW viewFailedStudents AS
    SELECT S.StudentFirstName,S.StudentLastName,E.ExamName
    FROM Student AS S, ExamResults as ER, Exam as E
    WHERE ER.GradeNormalSession<5 or ER.GradeRetakeSession<5 and ER.ExamId = E.ExamId
GO



--select students that failed in the normal session, but succeeded in the retake session and group them by group id

CREATE VIEW getRedeemedStudents AS
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
GO

CREATE Table SchoolClub(
    SchoolClubId   INT PRIMARY KEY IDENTITY (1,1),
    SchoolClubName VARCHAR(100) NOT NULL
)

CREATE TABLE SchoolClubActivity (
    ID INT PRIMARY KEY IDENTITY(1, 1),
    SchoolClubId INT FOREIGN KEY REFERENCES SchoolClub(SchoolClubId),
    SchoolClubActivityName VARCHAR(100)
)

CREATE OR ALTER PROCEDURE AddToTables (@tableName VARCHAR(100), @testId INT, @noOfRows INT, @position INT) AS --PROCEDURE ADDS TO TABLES A TABLE IF IT HAS NOT BEEN ADDED BEFORE
    BEGIN
        IF (SELECT count(1)
            FROM TestTables
            WHERE TableID =
                  (SELECT T.TableID
                   FROM Tables as T
                   WHERE T.Name = @tableName
                  ) ) = 0 -- i.e the table name does not exist in the testTables
            BEGIN
                INSERT INTO Tables(Name) VALUES (@tableName)
                DECLARE @tableId INT;
                SET @tableId = (SELECT T.TableID from Tables AS T WHERE Name = @tableName)

                PRINT CAST(@testId AS VARCHAR(5)) + ' ' +  CAST( @tableId AS VARCHAR(5))

                IF (SELECT COUNT(1)
                    FROM TestTables AS T
                    WHERE T.Position = @position) = 1
                    BEGIN
                        RAISERROR (13001,-1,-1,'Position already occupied');
                    END
                ELSE
                    INSERT INTO TestTables(TestId,TableID,NoOfRows,Position) VALUES (@testId,@tableId,@noOfRows,@position)-- insert into testtables the test and the data associated to the table
            END
        ELSE
            RAISERROR (13001,-1,-1,'View already there');
    END
GO

CREATE PROCEDURE AddToViews (@viewName VARCHAR(100), @testId INT) AS -- PROCEDURE ADDS TO VIEWS A VIEW IF IT HAS NOT BEEN ADDED BEFORE
    BEGIN
        PRINT 'Adding view to Views '  + @viewName
        IF (SELECT count(1)
            FROM TestViews
            WHERE ViewID =
                  (Select V.ViewID
                    FROM Views as V
                    WHERE V.Name = @viewName)) = 0 -- i.e the view name does not exist in the testViews
            BEGIN
                INSERT INTO Views(Name) VALUES (@viewName)
                DECLARE @viewId INT;
                SET @viewId = (
                    SELECT V.ViewID
                    FROM Views AS V
                    WHERE Name = @viewName
                    )
                INSERT INTO TestViews(TestID, ViewID) VALUES (@testId,@viewId); --insert into testviews the view and the test associated with it
            END
        ELSE
            RAISERROR (1000,-1,-1,'View already there');
    end
GO

CREATE OR ALTER PROCEDURE InsertForTable (@tableName VARCHAR(40)) AS
    BEGIN
        DECLARE @index INT = 0;

        DECLARE @NoOfRows INT;
        SET @NoOfRows =  --get number of rows for current table
            (SELECT NoOfRows
            FROM TestTables WHERE TableID = (
                SELECT TableID
                FROM Tables
                WHERE Name = @tableName
                ))


        IF @tableName = 'SchoolClub'
        BEGIN
            WHILE @index < @NoOfRows
            BEGIN
                INSERT INTO SchoolClub(SchoolClubName) VALUES ('RandomSchoolClub' + CAST((FLOOR(RAND() * (100-1)+1)) AS VARCHAR(10)))
                SET @index = @index+1;
                PRINT CAST(@index as VARCHAR(20)) + CAST(@index as VARCHAR(20))
            END
        END


        IF @tableName = 'SchoolClubActivity'
        BEGIN
            WHILE @index < @NoOfRows
            BEGIN
                 --A NEWID() is generated for each row and the table is then sorted by it. The first record is returned (i.e. the record with the "lowest" GUID). So we generate a random school club id
                INSERT INTO SchoolClubActivity(SchoolClubId,SchoolClubActivityName) VALUES ((SELECT SchoolClubId FROM (SELECT TOP 1 * FROM SchoolClub ORDER BY NEWID()) AS RandomSchoolClub) ,
                                                                                            'RandClubActiv' + CAST((FLOOR(RAND() * (100-1)+1)) AS VARCHAR(100)))
                SET @index = @index+1;
            END
        END

        IF @tableName = 'AcademicEvent'
        BEGIN
            WHILE @index < @NoOfRows
            BEGIN
                INSERT INTO AcademicEvent(AcademicEventName, AcademicEventDate, CoordinatorProfessorId) VALUES ('RandomAcademicName' + CAST((FLOOR(RAND() * (100-1)+1)) AS VARCHAR(10)),
                                                                                                                DATEADD(DAY, ABS(CHECKSUM(NEWID()) % 3650),'2000-01-01'),
                                                                                                                (SELECT ProfessorId FROM (SELECT TOP 1 * FROM Professor ORDER BY NEWID()) AS RandomProfessor))
                SET @index = @index+1;
                PRINT CAST(@index as VARCHAR(20)) + CAST(@index as VARCHAR(20))
            END
        END


    END
GO

CREATE OR ALTER PROCEDURE ExecuteTest (@testName VARCHAR(30)) AS
BEGIN
    PRINT (@testName)
    if @testName = 'test academic entities'
    BEGIN
        DECLARE @startTestTime datetime;
        DECLARE @endTestTime datetime;
        SET @startTestTime = GETDATE();

        INSERT INTO TestRuns(Description,StartAt) VALUES ('Addition (100 rows) to test SchoolClubs, SchoolClubActivity and AcademicEvent',@startTestTime)
        DECLARE @testId INT;

        DECLARE @testRunId INT = (
            SELECT TOP 1 TestRunID
            FROM TestRuns
            WHERE Description = 'Addition (100 rows) to test SchoolClubs, SchoolClubActivity and AcademicEvent'
            ORDER BY TestRunID DESC
            )

        SET @testId = (
            SELECT testId
            FROM Tests as TR
            WHERE TR.Name = @testName
            )

        DECLARE @firstTableName  VARCHAR(100) = 'AcademicEvent';
        DECLARE @secondTableName  VARCHAR(100) = 'SchoolClubActivity';
        DECLARE @thirdTableName  VARCHAR(100) = 'SchoolClub';

        exec AddToTables @tableName = 'AcademicEvent', @testId = @testId, @noOfRows = 100, @position = 1;
        exec AddToTables @tableName = 'SchoolClubActivity', @testId = @testId, @noOfRows = 100, @position = 2;
        exec AddToTables @tableName = 'SchoolClub', @testId = @testId, @noOfRows = 100, @position = 3;

        --must clear tables like this as we cannot do delete from @variable because (as i can see at least) the statement is evaluated before execution so it doesn't work. (i can provide examples if needed)
        delete from AcademicEvent; --delete position 1
        delete from SchoolClubActivity; -- delete position 2
        delete from SchoolClub; -- delete position 3

        PRINT 'WORKS'

        --go through all positions descending as to do all the required operations
        DECLARE @currentPosition INT; --current position of insertion
        DECLARE @startTime datetime; -- starting time for the test
        DECLARE @endTime datetime; -- end time for the test
        DECLARE @currentTableName VARCHAR(100); -- the table name in which we insert
        DECLARE @currentTableId INT;
        DECLARE @TablePositionCursor AS CURSOR; -- the cursor
        SET @TablePositionCursor = CURSOR FOR -- get all the test tables sorted by position descendingly so we can reinsert in reverse order
            SELECT TT.Position
            FROM TestTables as TT
            ORDER BY TT.Position DESC;
        OPEN @TablePositionCursor;
        FETCH NEXT FROM @TablePositionCursor INTO @currentPosition;
        WHILE @@FETCH_STATUS = 0
            BEGIN
                PRINT 'Inserting test for position' + CAST(@currentPosition AS VARCHAR(100))

                SET @currentTableId = (SELECT TT.TableID
                        FROM TestTables AS TT
                        WHERE TT.Position = @currentPosition
                        )
                SET @currentTableName = ( --select current table name
                    SELECT T.Name
                    FROM Tables AS T
                    WHERE T.TableID = @currentTableId
                    );

                SET @startTime = GETDATE();

                exec InsertForTable @tableName= @currentTableName; --execute test

                SET @endTime = GETDATE();

                PRINT 'INSERTING INTO TEST RUN TABLES FOR' + @currentTableName

                INSERT INTO TestRunTables(TestRunID,TableId, StartAt, EndAt) VALUES (@testRunId,@currentTableId,@startTime,@endTime);--insert results

                FETCH NEXT FROM @TablePositionCursor INTO @currentPosition

            END
        CLOSE @TablePositionCursor;
        DEALLOCATE @TablePositionCursor;

        exec AddToViews @viewName = 'viewStudents', @testId = @testId;
        exec AddToViews @viewName = 'getRedeemedStudents', @testId = @testId;
        exec AddToViews @viewName = 'viewFailedStudents', @testId = @testId;



        DECLARE @currentViewId INT;
        DECLARE @ViewCursor AS CURSOR;
        DECLARE @currentViewName NVARCHAR(100);
        declare @necessaryQuery nvarchar(100) = 'select * from ';
        SET @ViewCursor = CURSOR FOR -- get all the test views
            SELECT TV.ViewID
            FROM TestViews as TV
        OPEN @ViewCursor;
        FETCH NEXT FROM @ViewCursor INTO @currentViewId;
        WHILE @@FETCH_STATUS = 0
            BEGIN
                PRINT 'Testing view ' + CAST(@currentViewId AS VARCHAR(10))
                SET @currentViewName = ( --select current view name
                    SELECT V.Name
                    FROM Views AS V
                    WHERE V.ViewID = @currentViewId);

                SET @startTime = GETDATE();

                exec (@necessaryQuery + @currentViewName)

                SET @endTime = GETDATE();

                INSERT INTO TestRunViews VALUES (@testRunId,@currentViewId,@startTime,@endTime);

                FETCH NEXT FROM @ViewCursor INTO @currentViewId
            END
        CLOSE @ViewCursor;
        DEALLOCATE @ViewCursor;

        SET @endTestTime = GETDATE();

        UPDATE TestRuns
        SET EndAt = @endTestTime
        WHERE Description = 'Addition (100 rows) to test SchoolClubs, SchoolClubActivity and AcademicEvent' and StartAt=@startTestTime
    END
END
GO

CREATE OR ALTER PROCEDURE ClearAllTestTables AS
    BEGIN
        DELETE FROM TestRuns
        DELETE FROM TestRunTables
        DELETE FROM TestRunViews
        DELETE FROM TestViews
        DELETE FROM TestTables
        DELETE FROM Tables
        DELETE FROM Views
        DELETE FROM Tests
    END
GO

exec ClearAllTestTables;

INSERT INTO Tests VALUES ('test academic entities');
exec ExecuteTest @testName = 'test academic entities'


select * from SchoolClub
select * from SchoolClubActivity
SELECT * from AcademicEvent


select * from Tests;

select * from TestRuns
select * from tables
select * from Views
select * from TestTables
select * from TestViews
select * from TestRunTables
select * from TestRunViews
