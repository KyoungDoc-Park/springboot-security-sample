
--create tables

drop table users;
create table users(
	user_seq varchar(10),
	user_id varchar(100),
	user_nm varchar(50),
	password varchar(100),
	country_cd varchar(20),
	telco_cd varchar(10),	
	api_key varchar(100),
	use_yn char(1),
	insert_dt timestamp,
	mod_dt timestamp,
	insert_id varchar(20),
	modify_id varchar(20),
	primary key (user_id)
);
                         
drop table auth;                         
create table auth(
	auth_cd varchar(5),
	auth_nm varchar(30),
    use_yn varchar(1),
    insert_dt timestamp,
    mod_dt timestamp,
    insert_id varchar(20),
    modify_id varchar(20),
    primary key (auth_cd)
);

drop table user_auth;
create table user_auth(
	auth_cd varchar(10),
	user_seq varchar(10),
    use_yn varchar(1),
    insert_dt timestamp,
    mod_dt timestamp,
    insert_id varchar(20),
    modify_id varchar(20),
    primary key (auth_cd, user_seq)
    );

drop table menu;
create table menu(
    menu_id varchar(5),
	menu_nm varchar(30),
	menu_lv int,
	up_menu_id varchar(5),
	menu_url varchar(50),
    use_yn varchar(1),
    insert_dt timestamp,
    mod_dt timestamp,
    insert_id varchar(20),
    modify_id varchar(20),
    primary key (menu_id)
);

drop table menu_auth;    
create table menu_auth(
	menu_id varchar(5),
	auth_cd varchar(10),
    use_yn varchar(1),
    insert_dt timestamp,
    mod_dt timestamp,
    insert_id varchar(20),
    modify_id varchar(20)    
);





