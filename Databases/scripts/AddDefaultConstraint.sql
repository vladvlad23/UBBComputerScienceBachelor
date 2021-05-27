
--version 4
CREATE PROCEDURE AddDefaultConstraint (@doOrUndo BIT) AS

    IF @doOrUndo=1
        ALTER TABLE Classroom
        ADD CONSTRAINT featureConstraint DEFAULT NULL for ClassroomFeatures;
    ELSE
        ALTER TABLE Classroom
        DROP CONSTRAINT featureConstraint;
GO

EXEC AddDefaultConstraint @doOrUndo=0

