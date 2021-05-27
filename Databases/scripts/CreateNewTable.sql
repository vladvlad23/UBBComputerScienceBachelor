--version 5
CREATE PROCEDURE CreateTable (@doOrUndo BIT) AS

    IF @doOrUndo=1
        CREATE TABLE AcademicEvent (
            AcademicEventName varchar(20) NOT NULL,
            AcademicEventDate date NOT NULL,
            CoordinatorProfessorId int
        )
    ELSE
        DROP TABLE AcademicEvent
GO

EXEC CreateTable @doOrUndo=0