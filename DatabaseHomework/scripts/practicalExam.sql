IF OBJECT_ID('ROUTESTATIONS', 'U') IS NOT NULL
DROP TABLE RouteStations
IF OBJECT_ID('STATIONS', 'U') IS NOT NULL
DROP TABLE Stations
IF OBJECT_ID('ROUTES', 'U') IS NOT NULL
DROP TABLE Routes
IF OBJECT_ID('TRAINS', 'U') IS NOT NULL
DROP TABLE Trains
IF OBJECT_ID('TRAINTYPES', 'U') IS NOT NULL
DROP TABLE TrainTypes

DELETE FROM RouteStations
DELETE FROM Stations
DELETE FROM Routes
DELETE FROM Trains
DELETE FROM TrainTypes


CREATE TABLE TrainTypes(
                           TTID TINYINT PRIMARY KEY IDENTITY(1,1),
                           Description VARCHAR(300)
)



CREATE TABLE Trains(
    TID SMALLINT PRIMARY KEY IDENTITY(1,1),
    TName VARCHAR(300),
    TTID TINYINT REFERENCES TrainTypes(TTID)
)

CREATE TABLE Routes(
    RID SMALLINT PRIMARY KEY IDENTITY(1,1),
    RName VARCHAR(300) UNIQUE,
    TID SMALLINT REFERENCES Trains(TID)
)

CREATE TABLE Stations(
    SID SMALLINT PRIMARY KEY IDENTITY(1,1),
    SName VARCHAR(100) UNIQUE
)

CREATE TABLE RouteStations(
    RID SMALLINT REFERENCES Routes(RID),
    SID SMALLINT REFERENCES Stations(SID),
    ArrivalTime TIME,
    DepartureTime TIME,
    PRIMARY KEY (RID,SID)
)

CREATE OR ALTER PROC uspStationOnroute @RName VARCHAR(100), @SName VARCHAR(100), @ArrivalTime TIME, @DepartureTime TIME AS
BEGIN
    DECLARE @RID SMALLINT = (SELECT RID FROM Routes WHERE RName = @RName),
        @SID SMALLINT = (SELECT SID FROM Stations WHERE SName = @SName)
    IF @RID IS NULL OR @SID IS NULL
    BEGIN
        raiserror('NO SUCH STATION/ROUTE', 16, 1)
        RETURN -1
    END
    IF EXISTS (SELECT * FROM RouteStations WHERE RID = @RID AND SID = @SID)
        UPDATE RouteStations
        SET ArrivalTime = @ArrivalTime, DepartureTime = @DepartureTime
    ELSE
        INSERT RouteStations(RID,SID,ArrivalTime,DepartureTime) VALUES (@RID, @SID, @ArrivalTime,@DepartureTime)

END
GO

INSERT TrainTypes VALUES('regio'), ('interregio');
INSERT Trains VALUES('t1',1), ('t2',1), ('t3',1);
INSERT Routes VALUES('r1',1),('r2',2),('r3',3);
INSERT Stations VALUES('s1'),('s2'),('s3');

SELECT * FROM TrainTypes;
SELECT * FROM Trains;
SELECT * FROM Routes;
SELECT * FROM Stations;
SELECT * FROM RouteStations;

exec uspStationOnRoute 'r1', 's1', '6:00', '6:10';
exec uspStationOnRoute 'r1', 's2', '6:20', '6:30';
exec uspStationOnRoute 'r1', 's3', '6:40', '6:50';
exec uspStationOnRoute 'r2', 's3', '6:40', '6:50';

CREATE OR ALTER VIEW vRoutesWithAllStations AS
SELECT R.RName
FROM Routes R
WHERE NOT EXISTS
    (SELECT S.SID
    FROM Stations S
    EXCEPT
    SELECT RS.SID
    FROM RouteStations RS
    WHERE RS.RID = R.RID)
GO

SELECT * FROM vRoutesWithAllStations

CREATE OR ALTER FUNCTION ufStationsFilteredByNumOfRoutes (@R INT)
RETURNS TABLE
RETURN
    SELECT S.SNAME
    FROM STATIONS S
    WHERE S.SID IN
        (SELECT RS.SID
        FROM RouteStations RS
        GROUP BY RS.SID
        HAVING COUNT(*) >= @R)


SELECT * FROM RouteStations
SELECT * FROM ufStationsFilteredByNumOfRoutes(2)