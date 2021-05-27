--version 8
CREATE PROCEDURE AddPrimaryKey (@doOrUndo BIT) AS

    IF @doOrUndo=1
    BEGIN
        ALTER TABLE AcademicEvent
        ADD CONSTRAINT uniqueAcademicNameAndDate PRIMARY KEY (AcademicEventName,AcademicEventDate)
    END
    ELSE
        ALTER TABLE AcademicEvent
        DROP CONSTRAINT uniqueAcademicName
GO
EXEC AddPrimaryKey @doOrUndo = 0