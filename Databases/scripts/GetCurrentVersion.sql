CREATE PROCEDURE GetCurrentVersion (@version INT OUTPUT) AS 
    SELECT @version = VersionNumber
    FROM DatabaseVersion
GO