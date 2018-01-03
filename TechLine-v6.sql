use master 
go

IF EXISTS(select * from sys.databases where name='TechLine')
DROP DATABASE TechLine

create database TechLine
go

use TechLine
go

--Status 0: FALSE, 1: TRUE

create table Categories
(
	categoryId varchar(10) primary key,
	categoryName nvarchar(100),
	categoryDesc nvarchar(500),
	categoryStatus bit,
	categoryIcon varchar(200)
)
go

create table ProductTypes
(
	typeId varchar(10) primary key,
	categoryId varchar(10) foreign key references Categories(categoryId),
	typeName nvarchar(50) unique,
	typeDesc nvarchar(300),
	typeIcon varchar(200),
	typeStatus bit 
)
go

create table Users
(
	userId varchar(30) primary key,
	[password] varchar(20),
	email varchar(50),
	fullname nvarchar(100),
	phone varchar(20),
	[role] varchar(20), --select box for seller or customer
	userStatus bit --manage user is banned or not.
)
go

create table Customers
(
	userId varchar(30) foreign key references Users(userId) primary key,
	dob varchar(50),
	gender varchar(10),
	[address] nvarchar(1000),
	point int
)
go

create table Seller
(
	userId varchar(30) foreign key references Users(userId) primary key,
	storeName nvarchar(1000),
	identityCard varchar(20), -- CMND -- unseen on Customer
	approvedDate varchar(50), -- CMND approved date -- unseen on Customer
	approvedPlace nvarchar(100), -- CMND approved place -- unseen on Customer
	storeAddress nvarchar(100), -- unseen on Customer
	storeIcon varchar(200), 
	storeSummary nvarchar(1000)
)
go

create table Brands
(
	brandId varchar(10) primary key,
	brandName nvarchar(100),
	brandIcon varchar(200),
	brandStatus bit 
)
go

create table Products
(
	productId varchar(10) primary key,
	typeId varchar(10) foreign key references ProductTypes(typeId),
	userId varchar(30) foreign key references Users(userId),
	brandId varchar(10) foreign key references Brands(brandId),
	productName nvarchar(500),
	productDesc nvarchar(3500),
	productSummary nvarchar(1000),
	productPrice float,
	productUnit nvarchar(50),
	productWeight	float,
	productWidth	float,
	productHeigth	float,
	productLength	float,
	productQuantity int,
	productImage varchar(4000),
	productDiscount int,
	productRating float,
	isApproved bit,
	datePosted datetime not null default getdate(),
	productStatus bit
)
go



create table ProductRating 
(
	productId varchar(10) foreign key references Products(productId),
	ratingPoint int,
	[count] int,
	primary key (productId, ratingPoint)
)
go

create table ProductsEditHistory
(
	productId varchar(10) foreign key references Products(productId),
	[version] int,
	productName nvarchar(500),
	productPrice float,
	productDiscount int,
	editTime datetime not null default getdate(),
	primary key([version], productId)
)
go

create table ProductsComment
(
	commentID varchar(10) primary key,
	userId varchar(30) foreign key references Users(userId),
	productId varchar(10) foreign key references Products(productId),
	commentContent nvarchar(3000),
	commentStatus bit
)
go

create table OrderMaster
(
	orderMId	varchar(10) primary key,
	userId varchar(30) foreign key references Users(userId),
	orderTotalPrice float,
	DeliveryPrice float,
	orderNote	nvarchar(1000),
	orderStatus	varchar(10), --Processing , Cancelled , Delievery , Done
	dateOrdered	datetime not null default getdate()
)
go

create table OrderDetails
(
	orderMId	varchar(10) foreign key references OrderMaster(orderMId),
	productId varchar(10) foreign key references Products(productId),
	quantity	int,
	primary key(orderMId,productId)
)
go

create table OrderAddress
(
	orderMId	varchar(10) foreign key references OrderMaster(orderMid) primary key,
	userId varchar(30) foreign key references Users(userId),
	orderAddressLat float,
	orderAddressLng	float,
	orderAddressDetail	nvarchar(1000),
	orderPhone varchar(20)
)
go




