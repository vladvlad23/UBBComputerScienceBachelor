--version 7
CREATE PROCEDURE AddForeignKey (@doOrUndo BIT) AS

    IF @doOrUndo=1
        ALTER TABLE AcademicEvent
        ADD CONSTRAINT coordinatorProfessor FOREIGN KEY (CoordinatorProfessorId) REFERENCES Professor(ProfessorId)
    ELSE
        ALTER TABLE AcademicEvent
        DROP CONSTRAINT coordinatorProfessor

GO

exec AddForeignKey @doOrUndo = 0