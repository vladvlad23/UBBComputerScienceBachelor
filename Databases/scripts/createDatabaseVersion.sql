CREATE TABLE DatabaseVersion (
    VersionNumber INT
)

INSERT INTO DatabaseVersion Values(1)

CREATE PROCEDURE GetCurrentVersion (@version INT OUTPUT) AS
    SELECT @version = VersionNumber
    FROM DatabaseVersion
go


CREATE PROCEDURE RollForward (@currentVersion INT) AS
    print 'rolling forwards from '
    print @currentVersion
    if @currentVersion = 1
    BEGIN
        exec AddCreditsToCourses @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 2
    END
    else if @currentVersion = 2
    BEGIN
        exec ModifyCredits @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 3
    END
    else if @currentVersion = 3
    BEGIN
        exec AddDefaultConstraint @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 4
    END
    else if @currentVersion = 4
    BEGIN
        exec CreateTable @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 5
    END
    else if @currentVersion = 5
    BEGIN
        exec AddCandidateKey @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 6
    END
    else if @currentVersion = 6
    BEGIN
        exec AddForeignKey @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 7
    END
    else if @currentVersion = 7
    BEGIN
        exec AddPrimaryKey @doOrUndo = 1
        UPDATE DatabaseVersion SET VersionNumber = 8
    END
GO

CREATE PROCEDURE RollBackwards (@currentVersion INT) AS
    print 'rolling backwards from '
    print @currentVersion
    if @currentVersion = 2
    BEGIN
        exec AddCreditsToCourses @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 1
    END
    else if @currentVersion = 3
    BEGIN
        exec ModifyCredits @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 2
    END
    else if @currentVersion = 4
    BEGIN
        exec AddDefaultConstraint @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 3
    END
    else if @currentVersion = 5
    BEGIN
        exec CreateTable @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 4
    END
    else if @currentVersion = 6
    BEGIN
        exec AddCandidateKey @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 5
    END
    else if @currentVersion = 7
    BEGIN
        exec AddForeignKey @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 6
    END
    else if @currentVersion = 8
    BEGIN
        exec AddPrimaryKey @doOrUndo = 0
        UPDATE DatabaseVersion SET VersionNumber = 7
    END
GO

CREATE PROCEDURE GoToVersion (@targetVersion INT) AS
    DECLARE @CURRENT_VERSION INT
    SELECT @CURRENT_VERSION = VersionNumber FROM DatabaseVersion

    print @targetVersion

    if @targetVersion <1 or @targetVersion>8
        	RAISERROR ( 'Whoops, an error occurred.',1,1)
    else
        BEGIN
        WHILE @targetVersion < @CURRENT_VERSION
        BEGIN
            print 'rolling backwards'
            exec RollBackwards @currentVersion =  @CURRENT_VERSION;
            SELECT @CURRENT_VERSION = VersionNumber  FROM DatabaseVersion
        END
        WHILE @targetVersion > @CURRENT_VERSION
        BEGIN
            print 'rolling forward'
            exec RollForward @currentVersion = @CURRENT_VERSION;
            SELECT @CURRENT_VERSION = VersionNumber  FROM DatabaseVersion
        END
    END
go




exec GoToVersion @targetVersion = 5;


select * from DatabaseVersion

