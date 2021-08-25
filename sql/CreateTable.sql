-- 학생 테이블
CREATE TABLE Student(	
	Student_No varchar(20) PRIMARY KEY,	--학번
	Student_Name varchar(20) NOT NULL ,	--학생 이름
	Department varchar(20) NOT NULL ,	--학생 학과
	Address varchar(100) NOT NULL ,		--학생 주소
);

-- 책 테이블
CREATE TABLE Book(
    bNo varchar(20) primary key,		--책번호
    bTitle varchar2(4000) NOT null,		--책 제목
    bWriter varchar2(4000),				--책 저자
    bPublisher varchar2(4000),			--책 출판사
    bPu_Date varchar2(20)				--책 발행년도
);

-- 대여 테이블
CREATE TABLE Borrow(
	Br_no number(10) NOT NULL,			--대여 번호
	Student_No varchar(20) NOT NULL,	--대여 학생 번호
	bNo varchar(20) NOT NULL,			--대여 책 번호
	Br_date DATE DEFAULT sysdate,		--대여 날
	Ex_date DATE DEFAULT sysdate+3,		--반납예정 날
	state char(1) DEFAULT 'F',			--연체 상태
	CONSTRAINT Boorw_pk PRIMARY KEY (Br_no),
	CONSTRAINT	Borrow_fk_st FOREIGN KEY (Student_No) REFERENCES Student(Student_No),
	CONSTRAINT	Borrow_fk_bo FOREIGN KEY (bNo) REFERENCES book(bNo)
);