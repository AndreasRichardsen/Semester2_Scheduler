use dmai0916_202617;

CREATE TABLE Employee(
eId int Not Null, 
eFirstName varchar(99) Not Null,
eLastName varchar(99) Not Null,
ePhoneNumber varchar(99) Not Null,
eEmail varchar(99) Not Null,
eMinWorkHours int Not Null,
eMaxWorkHours int Not Null,
eTypeOfEmployee varchar(99) Not null,
PRIMARY KEY (eId));