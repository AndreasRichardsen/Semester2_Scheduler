use WesternCompany;

create table Discount
(discountType varchar(99) not null,
discountAmount decimal(10, 2),
primary key (discountType));

create table Customer
(customerId int,
name varchar(99) not null,
c_address varchar(99) not null,
zipcode varchar(99) not null,
city varchar(99) not null,
phoneNo varchar(20) not null,
email varchar(99) not null,
customerType varchar(99) not null,
discountType varchar(99) not null,
primary key (customerId),
foreign key (discountType) references Discount(discountType));

create table SaleOrder
(saleOrderId int not null,
so_date date not null,
deliveryStatus varchar(99) not null,
deliveryDate date not null,
customerId int not null,
invoiceNo int not null,
primary key (saleOrderId),
foreign key (customerId) references Customer(customerId));

create table Invoice
(invoiceNo int not null,
saleOrderId int not null,
paymentDate date not null,
primary key (invoiceNo),
foreign key (saleOrderId) references SaleOrder(saleOrderId));

create table Warehouse
(warehouseId int not null,
name varchar(99) not null,
capacity decimal(10, 2),
warehouseType varchar(99) not null,
primary key (warehouseId));

create table Product
(productId int not null,
name varchar(99) not null,
countryOfOrigin varchar(99) not null,
p_description varchar(99) not null,
productType varchar(99) not null,
stock int not null,
warehouseId int not null,
primary key (productId),
foreign key (warehouseId) references Warehouse(warehouseId));

create table SaleOrderLine
(productId int not null,
saleOrderId int not null,
quantity int not null,
typeOfPurchase varchar(99) not null,
primary key (productId, saleOrderId),
foreign key (productId) references Product(productId),
foreign key (saleOrderId) references SaleOrder(saleOrderId));


create table Clothing
(productId int not null,
size varchar(99) not null,
colour varchar(99) not null,
primary key (productId),
foreign key (productId) references Product(productId));

create table Equipment
(productId int not null,
e_type varchar(99) not null,
primary key (productId),
foreign key (productId) references Product(productId));

create table GunReplicas
(productId int not null,
calibre varchar(99) not null,
material varchar(99) not null,
primary key (productId),
foreign key (productId) references Product(productId));

create table SalePrice
(productId int not null,
startDate date not null,
salePrice decimal(10, 2),
rentPrice decimal(10, 2),
primary key (productId, startDate),
foreign key (productId) references Product(productId));



create table Remote_W
(warehouseId int not null,
licenseNo varchar(99) not null,
primary key (warehouseId),
foreign key (warehouseId) references Warehouse(warehouseId));

create table Fixed
(warehouseId int not null,
f_address varchar(99) not null,
zipcode varchar(99) not null,
city varchar(99) not null,
primary key (warehouseId),
foreign key (warehouseId) references Warehouse(warehouseId));



