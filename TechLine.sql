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
	categoryIcon varchar(100)
)
go

insert into Categories values('Cate01','Kitchen','Kitchen...',1,null),
								('Cate02','Bathroom','Bathroom...',1,null),
								('Cate03','Living Room','Living Room...',1,null),
								('Cate04','Laundry Room','Laundry Room...',1,null),
								('Cate05','Orther','Comming Soon...',1,null)
go

create table ProductTypes
(
	typeId varchar(10) primary key,
	categoryId varchar(10) foreign key references Categories(categoryId),
	typeName nvarchar(50) unique,
	typeDesc nvarchar(300),
	typeIcon varchar(100),
	typeStatus bit 
)
go

insert into ProductTypes values('Type01','Cate01','Gas Stove',N'Gray in harmony, eye-catching, bring luxury and elegance to every kitchen space.',null,1),
							  ('Type02','Cate01','Induction Cooker',N'Compact, easy to install and decorate for the kitchen. High power of 2000 W, quick cooking, energy saving.',null,1),
							  ('Type03','Cate01','Refrigerator',N'The eye-catching brown silver design combined with a solid rectangular shape will bring your interior space to a sophisticated, modern look.',null,1),
							  ('Type04','Cate02','Water heater',N'The design is extremely compact and stylish. Therefore, it easily highlights the bathroom space of the user.',null,1),
							  ('Type05','Cate02','Hair-dryer','Hair-dryer...',null,1),
							  ('Type06','Cate03','Tivi',N'Tivi................',null,1),
							  ('Type07','Cate03','Speaker',N'Speaker.................',null,1),
							  ('Type08','Cate04','Washing Machine',N'The very simple and familiar but practical design from the famous maker Aqua.',null,1),
							  ('Type09','Cate04','Clothes Iron',N'The combination of white and green looks graceful, cool eyes.',null,1)
go

create table Users
(
	userId varchar(10) primary key,
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
	userId varchar(10) foreign key references Users(userId) primary key,
	dob varchar(50),
	gender varchar(10),
	[address] nvarchar(1000),
	point int
)
go

create table Seller
(
	userId varchar(10) foreign key references Users(userId) primary key,
	storeName nvarchar(1000),
	identityCard varchar(20), -- CMND -- unseen on Customer
	approvedDate varchar(10), -- CMND approved date -- unseen on Customer
	approvedPlace nvarchar(20), -- CMND approved place -- unseen on Customer
	storeAddress nvarchar(100), -- unseen on Customer
	storeIcon varchar(100), 
	storeSummary nvarchar(1000)
)
go

create table Products
(
	productId varchar(10) primary key,
	typeId varchar(10) foreign key references ProductTypes(typeId),
	userId varchar(10) foreign key references Users(userId),
	productName nvarchar(50),
	productDesc nvarchar(3500),
	productSummary nvarchar(1000),
	productPrice float,
	productUnit nvarchar(50),
	productWeight	float,
	productWidth	float,
	productHeigth	float,
	productLength	float,
	productQuantity int,
	productImage varchar(200),
	productDiscount int,
	productRating float,
	isApproved bit,
	datePosted datetime not null default getdate(),
	productStatus bit
)
go

insert into Products values     ('P001','Type01',null,N'Sakura SA-650G',N'Modern design, luxurious, has two boilers to help cook at the same time, saving time, energy efficiency for housewives.',N'Modern design, luxurious, has two boilers to help cook at the same time, saving time, energy efficiency for housewives.',450,'Unit',5.0,8.0,34.0,65.0,8,'http://i.imgur.com/Cte4vxD.png',0,3.0,1,'20171204 10:34:09',1),
								('P002','Type01',null,N'Electrolux ETG728GKR',N'Black fashion, ladder-like design, users can be installed on high shelves, but still help to cook convenient, comfortable.',N'Black fashion, ladder-like design, users can be installed on high shelves, but still help to cook convenient, comfortable.',600,'Unit',10.0,14.8,41.8,71.7,15,'http://i.imgur.com/hGhVuMS.png',0,4.5,1,'20171224 12:34:09',1),
								('P003','Type01',null,N'Sakura SA-692SG',N'Easy to install in any kitchen space, luxurious colors, equipped with two buner to help cook convenience.',N'Easy to install in any kitchen space, luxurious colors, equipped with two buner to help cook convenience.',720,'Unit',6.0,8.5,38.6,69.7,20,'http://i.imgur.com/TPuQgDE.png',1,4.0,1,'20171124 12:34:09',1),
								('P004','Type02',null,N'Sunhouse SHD6146',N'Compact, easy to install and decorate for the kitchen. High power of 2000 W, quick cooking, energy saving.',N'Compact, easy to install and decorate for the kitchen. High power of 2000 W, quick cooking, energy saving.',1000,'Unit',3.0,6.0,38.0,26.0,15,'http://i.imgur.com/9KZC9Me.jpg',0,4.5,1,'20171024 12:34:09',1),
								('P005','Type02',null,N'Electrolux ETD29KC',N'Moving, preserving convenience, black luxury help to enhance the modern beauty of your kitchen space.',N'Moving, preserving convenience, black luxury help to enhance the modern beauty of your kitchen space.',320,'Unit',3.0,6.5,36.0,26.0,3,'http://i.imgur.com/L86jHR2.png',0,4.0,1,'20171220 12:34:09',1),
								('P006','Type02',null,N'Midea MI-T2112DA',N'An extremely compact size kitchen, users can move, store the kitchen easily, sophisticated kitchen design, beautiful, dots for modern living space.',N'An extremely compact size kitchen, users can move, store the kitchen easily, sophisticated kitchen design, beautiful, dots for modern living space.',500,'Unit',3.0,3.5,37.0,29.0,1,'http://i.imgur.com/XAAmjPc.png',10,2.0,1,'20171124 12:34:09',1),
								('P007','Type03',null,N'Sunhouse SHB9103MT',N'With a magnetic stove, an infrared cooktop for users to use with all the pots. A perfect combination product for those who love to experience both types of electric cookers.',N'With a magnetic stove, an infrared cooktop for users to use with all the pots. A perfect combination product for those who love to experience both types of electric cookers.',550,'Unit',5.0,8.0,40.0,70.0,15,'http://i.imgur.com/xE3GUxi.png',0,4.0,1,'20171127 12:34:09',1),
								('P008','Type03',null,N'Electrolux EHI727BA',N'Luxury design, wall-style design saves space',N'Luxury design, wall-style design saves space',700,'Unit',5,5.5,42,70,15,'http://i.imgur.com/x3RlNqF.png',0,4.5,1,'20171212 12:34:09',1),
								('P009','Type03',null,N'Sharp 165 litter SJ-174E-BS',N'The eye-catching brown silver design combined with a solid rectangular shape will bring your interior space to a sophisticated, modern look.',N'The eye-catching brown silver design combined with a solid rectangular shape will bring your interior space to a sophisticated, modern look.',1100,'Unit',33.0,127.5,56.0,53.2,15,'http://i.imgur.com/NT6STWu.png',0,1.0,1,'20171027 12:34:09',1),
								('P010','Type04',null,N'LG 189 litter GN-L205PS',N'Elegant and modern design with many useful technologies, promising to bring you the best moments of food processing.',N'Elegant and modern design with many useful technologies, promising to bring you the best moments of food processing.',1200,'Unit',43.0,140.0,58.5,55.5,6,'http://i.imgur.com/spFKHts.png',3,2.5,1,'20170927 12:34:09',1),
								('P011','Type04',null,N'Panasonic 135 litter NR-BJ158SSVN',N'Above the fridge freezer has just been launched in 2016 of the Panasonic manufacturer.',N'Above the fridge freezer has just been launched in 2016 of the Panasonic manufacturer.',1240,'Unit',28.0,112.5,58.4,52.6,15,'http://i.imgur.com/14HpOhy.png',0,4.0,1,'20171211 12:34:09',1),
								('P012','Type04',null,N'Electrolux EWE451AX-DWR',N'The design is extremely compact and stylish. Therefore, it easily highlights the bathroom space of the user.',N'The design is extremely compact and stylish. Therefore, it easily highlights the bathroom space of the user.',200,'Unit',3.7,34.0,9.5,24.0,15,'https://cdn.tgdd.vn/Products/Images/1962/71284/may-nuoc-nong-electrolux-ewe451ax-dwr-org-1.jpg',3,3.0,1,'20171221 12:34:09',1),
								('P013','Type05',null,N'Panasonic DH-3JL4VA',N'Compact design, suitable for any large or narrow bathroom space. At the same time, the body shell is made of durable Polyamide material, contributing to the beauty and convenience of cleaning products for users. The powerful power of up to 3500 W makes heating water faster, saving time and power consumption.',N'Compact design, suitable for any large or narrow bathroom space. At the same time, the body shell is made of durable Polyamide material, contributing to the beauty and convenience of cleaning products for users. The powerful power of up to 3500 W makes heating water faster, saving time and power consumption.',400,'Unit',1.6,38,9.3,19,1,'https://www.sosanhgia.com/images/product/12/may-nuoc-nong-panasonic-dh-3jl4va-1439611832.jpg',0,4,1,'20171220 12:34:09',1),
								('P014','Type05',null,N'Philips BHD002',N'Stylish design, elegant color with plastic cover less stain, easy to clean',N'Stylish design, elegant color with plastic cover less stain, easy to clean',500,'Unit',0.5,3.0,3.0,6.0,15,'https://cdn.tgdd.vn/Products/Images/1991/71648/philips-bhd0025.jpg',2,5.0,1,'20171209 12:34:09',1),
								('P015','Type05',null,N'Panasonic ND21-P645',N'Compact, eye-catching design with two elegant white violet colors',N'Compact, eye-catching design with two elegant white violet colors',1000,'Unit',200.0,0.6,0.8,1.2,15,'http://dienmaynguoiviet.vn/media/product/1434_0_may_say_toc_panasonic_eh_nd21_p645_org.jpg',0,4.5,1,'20171022 12:34:09',1),
								('P016','Type06',null,N'Sony 49 inch KDL-49W750D',N'The 49-inch widescreen size, can be flexible in the living room, office ... and all. The very thin bezel adds to the slim and classy look of the TV.',N'The 49-inch widescreen size, can be flexible in the living room, office ... and all. The very thin bezel adds to the slim and classy look of the TV.',300,'Unit',14.0,69.0,22.9,109.3,15,'http://www.hangdienmaygiare.com/upload/images/products/smart-tivi-led-sony-kdl-49w750c-49-inch-full-hd.jpg',5,3.0,1,'20170727 12:34:09',1),
								('P017','Type06',null,N'Samsung 48 inch UA48J5100',N'Specially designed, equipped with Samsung Triple Protector triple protection with lightning protection, humidity protection and shock protection (this feature is limited to 100% non-blocking action).',N'Specially designed, equipped with Samsung Triple Protector triple protection with lightning protection, humidity protection and shock protection (this feature is limited to 100% non-blocking action).',200,'Unit',13.0,68.4,31.0,107.6,2,'https://cdn.pico.vn/Product/27734/big_120669_tivi-led-samsung-ua48j5100-48_inch.jpg',0,2.0,1,'20171125 12:34:09',1),
								('P018','Type06',null,N'LG 49 inch 49UH850T',N'The slim TV (5.1 cm thick) offers a modern and sophisticated look. The 49-inch screen is just right, suitable for a wide variety of spaces from the living room to the bedroom.',N'The slim TV (5.1 cm thick) offers a modern and sophisticated look. The 49-inch screen is just right, suitable for a wide variety of spaces from the living room to the bedroom.',400,'Unit',12.0,68.0,23.9,110.4,15,'https://media3.scdn.vn/img2/2017/1_3/PmIvnn_simg_2a0eb8_964-964-0-0_cropf_simg_ab1f47_350x350_maxb.png',8,4.5,1,'20171218 12:34:09',1),
								('P019','Type07',null,N'Sony HT-CT80',N'The loudspeaker is only 5.1 cm high, very compact, easy to navigate and suitable for many types of space in your home. The two speakers have been carefully designed for balanced sonic sensitivity on all frequencies, while subwoofer wires add strength to the bass.',N'The loudspeaker is only 5.1 cm high, very compact, easy to navigate and suitable for many types of space in your home. The two speakers have been carefully designed for balanced sonic sensitivity on all frequencies, while subwoofer wires add strength to the bass.',700,'Unit',3.0,5.1,8.1,90.0,3,'https://sonycenter.sony.com.vn/Data/Sites/1/Product/431/ct80-1.jpeg',10,1.0,1,'20170602 12:34:09',1),
								('P020','Type07',null,N'LG SH3B',N'Compact, lightweight simple design. Thanks to this, it can easily arrange the bar to match the entertainment needs of your home. In addition, the LG bar speakers are connected by wireless Bluetooth connectivity, so your room will always be clean and tidy because there is no need to hang around in the room.',N'Compact, lightweight simple design. Thanks to this, it can easily arrange the bar to match the entertainment needs of your home. In addition, the LG bar speakers are connected by wireless Bluetooth connectivity, so your room will always be clean and tidy because there is no need to hang around in the room.',400,'Unit',3.0,39.0,26.0,95.4,15,'https://media3.scdn.vn/img2/2017/1_3/PmIvnn_simg_2a0eb8_964-964-0-0_cropf_simg_ab1f47_350x350_maxb.png',8,4.5,1,'20170912 12:34:09',1),
								('P021','Type07',null,N'Aqua 9 kg AQW-D901AT N',N'The very simple and familiar but practical design from the famous maker Aqua. The coating is covered with shiny static coating and transparent cover for easy cleaning and cleaning. The machine has a washing capacity of up to 9 kg which can comfortably meet the needs of washing in crowded families (over 6 people).',N'The very simple and familiar but practical design from the famous maker Aqua. The coating is covered with shiny static coating and transparent cover for easy cleaning and cleaning. The machine has a washing capacity of up to 9 kg which can comfortably meet the needs of washing in crowded families (over 6 people).',400,'Unit',40.0,99.0,67.0,59.0,15,'http://dienmayhaihoabn.com/wp-content/uploads/2017/03/may-giat-aqua-aqw-d901at-n-1-1-org.jpg',8,4.5,1,'20171206 12:34:09',1),
								('P022','Type08',null,N'LG 11 kg WF-D1117DD',N'The combination of white and green looks graceful, cool eyes. The arched, plastic-coated handle adds comfort to the user. Large capacity, fast heating, radiate heat all over the soleplate to quickly smooth out all creases.',N'The combination of white and green looks graceful, cool eyes. The arched, plastic-coated handle adds comfort to the user. Large capacity, fast heating, radiate heat all over the soleplate to quickly smooth out all creases.',400,'Unit',42.5,99.2,60.6,59.0,15,'https://cdn3.tgdd.vn/Products/Images/1944/75458/may-giat-lg-wf-d1117dd-300x300.jpg',8,4.5,1,'20171111 12:34:09',1),
								('P023','Type08',null,N'Electrolux EDI1014',N'Luxurious design, detailed lines make it easy to harmonize with the interior space in your home. With a washing capacity of up to 11 kg, this LG washing machine will help your family clean clothes for over 6 people.',N'Luxurious design, detailed lines make it easy to harmonize with the interior space in your home. With a washing capacity of up to 11 kg, this LG washing machine will help your family clean clothes for over 6 people.',400,'Unit',42.5,99.2,60.6,59.0,15,'https://tikicdn.com/media/catalog/product/e/l/electrolux_dry_iron_300watt_edi1014__1.jpg',8,4.5,1,'20171010 12:34:09',1),
								('P024','Type08',null,N'Panasonic PABU-NI-317TXRA',N'LExuding heat efficiently helps to smooth out wrinkles on clothing, giving you a clean, clean outfit.',N'LExuding heat efficiently helps to smooth out wrinkles on clothing, giving you a clean, clean outfit.',400,'Unit',0.9,12.2,10.6,23.0,15,'http://www.anhngan.com/data/bt6/ban-ui-hoi-nuoc-panasonic-pabu-ni-w650cslra-1475658330.jpg',8,4.5,1,'20171215 12:34:09',1)
go

create table Brands
(
	brandId varchar(10) primary key,
	productId varchar(10) foreign key references Products(productId),
	brandName nvarchar(100),
	brandIcon varchar(100),
	brandStatus bit 
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
	productName nvarchar(50),
	productPrice float,
	productDiscount int,
	editTime datetime not null default getdate(),
	primary key([version], productId)
)
go

create table ProductsComment
(
	commentID varchar(10) primary key,
	userId varchar(10) foreign key references Users(userId),
	productId varchar(10) foreign key references Products(productId),
	commentContent nvarchar(3000),
	commentStatus bit
)
go

create table OrderMaster
(
	orderMId	varchar(10) primary key,
	userId varchar(10) foreign key references Users(userId),
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
	userId varchar(10) foreign key references Users(userId),
	orderAddressLat float,
	orderAddressLng	float,
	orderAddressDetail	nvarchar(1000),
	orderPhone varchar(20)
)
go




