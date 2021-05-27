--we don't really need to know the number of ECTS credits, but we do need to know however if the credits are mandatory or facultative (1, respectively 0), so we change the data type to BIT
--version 3
CREATE PROCEDURE ModifyCredits (@doOrUndo BIT) AS

    IF @doOrUndo=1
        ALTER TABLE Course
        ALTER COLUMN ECTSNumber BIT
    ELSE
        ALTER TABLE Course
        ALTER Column ECTSNumber INT
GO

exec ModifyCredits @doOrUndo = 0


