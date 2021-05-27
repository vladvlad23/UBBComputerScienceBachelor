--version 6
CREATE PROCEDURE AddCandidateKey (@doOrUndo BIT) AS

    IF @doOrUndo=1
        ALTER TABLE AcademicEvent
        ADD CONSTRAINT oneEventPerDay UNIQUE (AcademicEventDate)
    ELSE
        ALTER TABLE AcademicEvent
        DROP CONSTRAINT oneEventPerDay

GO

exec AddCandidateKey @doOrUndo = 0