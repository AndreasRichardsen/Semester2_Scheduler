use dmai0916_202617;

CREATE TABLE [Shift](
sNumber int Not null, 
sDate Date Not null,
sStartTime varchar(99) Null,
sLocation varchar(99) Null,
shiftType varchar(99) Not null,
estimatedShiftLength int Null,
employeeId int Null,
PRIMARY KEY (sNumber, sDate, shiftType));