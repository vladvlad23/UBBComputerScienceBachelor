--2 procedures. 1 will add a column and one will remove a column
--version 2
CREATE PROCEDURE AddCreditsToCourses (@doOrUndo BIT) AS
    IF @doOrUndo=1
        ALTER TABLE Course
        ADD ECTSNumber int;
    ELSE
        ALTER TABLE Course
        DROP COLUMN ECTSNumber;
GO

exec AddCreditsToCourses @doOrUndo = 0

