use master

if exists(select * from sys.databases where name='MantechHelpdesk') drop database MantechHelpdesk
create database MantechHelpdesk

go

use MantechHelpdesk

go

create table Roles(
	RoleID	int identity primary key,
	RoleName nvarchar(50) not null,
	Description nvarchar(max),
);

create table Departments(
	DepartmentID int identity primary key,
	DepartmentName nvarchar(200) unique not null,
	IsEnable bit default 1 not null
);

create table Users(
	UserID int identity primary key,
	RoleID int foreign key references Roles(RoleID) not null,
	DepartmentID int foreign key references Departments(DepartmentID) not null,
	Username nvarchar(50) unique not null,
	Password nvarchar(50) not null,
	FirstName nvarchar(50) not null,
	LastName nvarchar(50) not null,
	IsOnline bit default 0 not null,
	LastVisit datetime default getdate() not null,
	CreateTime datetime not null default getdate(),
	CreateIP nvarchar(20) not null,
	EditorID int foreign key references Users(UserID),
	EditTime datetime default getdate(),
	EditIP nvarchar(20),
	IsEnable bit default 1 not null
);

create table Threads(
	ThreadID int identity primary key,
	DepartmentID int foreign key references Departments(DepartmentID) not null,
	ThreadName nvarchar(256) not null,
	CreateTime datetime not null,
	CreateIP nvarchar(20) not null,
	EditTime datetime default getdate(),
	EditIP nvarchar(20),
	IsEnable bit default 1 not null	
);

create table Complaints(
	ComplaintID int identity primary key,
	ThreadID int foreign key references Threads(ThreadID) not null,
	UserID int foreign key references Users(UserID) not null,
	Title nvarchar(256) not null,
	ComplaintContent nvarchar(max) not null,
	CreateTime datetime not null,
	CreateIP nvarchar(20) not null,
	EditorID int foreign key references Users(UserID),
	EditIP nvarchar(20),
	IsEnable bit default 1 not null,
	IsFinished bit default 0 not null,
	FinishedTime datetime,
	HasReplied bit default 0 not null,
	RepliedTime datetime,
	UserRef int foreign key references Users(UserID),
	Priority int not null
);

create table Answers(
	AnswerID int identity primary key,
	ComplaintID int foreign key references Complaints(ComplaintID) not null,
	AnswerContent nvarchar(max) not null,
	CreateTime datetime not null,
	CreateIP nvarchar(20) not null,
	EditorID int foreign key references Users(UserID),
	EditTime datetime default getdate(),
	EditIP nvarchar(20),
	IsEnable bit default 0 not null
);

create table Articles(
	ArticleID int identity primary key,
	UserID int foreign key references Users(UserID) not null,
	Title nvarchar(500) not null,
	[Content] nvarchar(max) not null,
	CreateTime datetime not null,
	CreateIP nvarchar(20) not null,
	EditTime datetime default getdate(),
	EditIP nvarchar(20),
	IsEnable bit default 1 not null,
	DislikeCount	int	not null,
	LikeCount	int not null
);

create table FAQs(
	FAQID int identity primary key,
	Title nvarchar(500) not null,
	[Content] nvarchar(max) not null,
	Feedback nvarchar(max),
	CreateIP nvarchar(20) not null,
	CreateTime datetime not null,
	IsEnable bit default 1 not null
);

create table Roles_Users(
	RoleID int foreign key references Roles(RoleID),
	UserID int foreign key references Users(UserID),
	primary key(RoleID, UserID)
)
--insert data

insert into Roles(RoleName) values('admin')
insert into Roles(RoleName) values('employee')

go

/*create procedure Roles_SelectAll(@pageIndex int, @pageSize int) as
begin
	WITH OrdersRN AS
	(
    SELECT ROW_NUMBER() OVER(ORDER BY RoleName, RoleID) AS RowNum
          ,RoleID
          ,RoleName
          ,Description
      FROM dbo.Roles
	)

	SELECT * 
	FROM OrdersRN
	WHERE RowNum BETWEEN (@PageIndex - 1) * @PageSize + 1 
                  AND @PageIndex * @PageSize
	ORDER BY RoleName,RoleID;
end
go

select * from Roles*/