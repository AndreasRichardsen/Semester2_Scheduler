ALTER TABLE dbo.[Shift] 
ADD CONSTRAINT FK_Workload_Shift_Cascade 
FOREIGN KEY (sDate) REFERENCES Workload(Date) 
ON DELETE CASCADE

ALTER TABLE dbo.[Shift]
ADD CONSTRAINT FK_Employee_Shift
FOREIGN KEY (employeeId) REFERENCES Employee(eId)
ON DELETE SET NULL

ALTER TABLE dbo.HoursWorked
ADD CONSTRAINT FK_HoursWorked_Shift
FOREIGN KEY (employeeId) REFERENCES Employee(eId)
ON DELETE CASCADE


ALTER TABLE dbo.DatesUnavailable
ADD CONSTRAINT FK_DatesUnavailable_Shift
FOREIGN KEY (employeeId) REFERENCES Employee(eId)
ON DELETE CASCADE


ALTER TABLE dbo.DaysOfWeekUnavailable
ADD CONSTRAINT FK_DaysOfWeekUnavailable_Shift
FOREIGN KEY (employeeId) REFERENCES Employee(eId)
ON DELETE CASCADE

ALTER TABLE dbo.DaysOfWeekPreferredOff
ADD CONSTRAINT FK_DaysOfWeekPreferredOff_Shift
FOREIGN KEY (employeeId) REFERENCES Employee(eId)
ON DELETE CASCADE
