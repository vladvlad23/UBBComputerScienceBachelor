exec sp_helpindex 'SchoolClub'


declare @index INT = 0;
while @index<100
BEGIN
    INSERT INTO Classroom(ClassroomAddress, ClassroomFeatures, ClassroomCapacity) VALUES ('RandomAddress' + CAST((FLOOR(RAND() * (100-1)+1)) AS VARCHAR(10)),
                                                                                          'RandomFeature' + CAST((FLOOR(RAND() * (100-1)+1)) AS VARCHAR(10)),
                                                                                          (FLOOR(RAND() * (100-1)+1)));
    SET @index = @index+1;
end


select * from SchoolClub;
select * from SchoolClubActivity;
SELECT * from Classroom;

create nonclustered index ix_SchoolClub_Name ON SchoolClub (SchoolClubName);
create nonclustered index ix_SchoolClub_Members ON SchoolClub (NumberOfMembers,SchoolClubName);
select * from SchoolClub where SchoolClubId=1561; --cl index seek


select * from SchoolClub where SchoolClubId=100; -- cl index seek. Just go on the tree until school club id is found.
select * from SchoolClub; -- cl index scan because it's easier to scan data in the order it is physically stored
select DISTINCT(SchoolClub.SchoolClubName) from SchoolClub; --ncl index scan because we only need to use the schoolclub name index to retrieve all data
select SchoolClubName FROM SchoolClub where NumberOfMembers=56; -- ncl index seek

-- Write a query on table Tb (my case Classroom) with a WHERE clause of the form WHERE b2 = value and analyze its execution plan.
-- Create a nonclustered index that can speed up the query. Recheck the query’s execution plan (operators, SELECT’s estimated subtree cost).

select Classroom.ClassroomFeatures from Classroom where ClassroomCapacity = 97; -- total cost is 0.0033975

create nonclustered index ix_Clasroom_Capacity on Classroom (ClassroomCapacity,ClassroomFeatures) GO; --now total cost is 0.0032831. If i had 100000 more records, this would actually have been relevant


--c. Create a view that joins at least 2 tables. Check whether existing indexes are helpful; if not, reassess existing indexes / examine the cardinality of the tables.
SELECT SchoolClub.SchoolClubName from SchoolClub INNER JOIN SchoolClubActivity SCA on SchoolClub.SchoolClubId = SCA.SchoolClubId where SCA.SchoolClubActivityName = 'RandClubActiv92';
-- ^ is currently cl index scan and cl index seek.
-- it can be made better by changing the cl index scan to ncl index seek. To do this, we create a new index

create nonclustered index ix_SchoolClubActivity_NameAndId on SchoolClubActivity (SchoolClubActivityName,SchoolClubId);

SELECT SchoolClub.SchoolClubName from SchoolClub INNER JOIN SchoolClubActivity SCA on SchoolClub.SchoolClubId = SCA.SchoolClubId where SCA.SchoolClubActivityName = 'RandClubActiv92';





