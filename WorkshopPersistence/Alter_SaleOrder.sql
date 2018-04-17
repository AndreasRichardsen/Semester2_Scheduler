use WesternCompany;

ALTER TABLE SaleOrder
ADD CONSTRAINT fk_SoIn
foreign key(invoiceNo) references Invoice(invoiceNo)