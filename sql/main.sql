drop table genres;
drop table story_types;
drop table editors;
drop table authors;
drop table stories;
drop table genre_editor_join;

create table genres (
	id serial primary key,
	name varchar(20),
	senior_editor int references editors(id)
);

create table story_types (
	id serial primary key,
	name varchar(10),
	points int
);

create table editors (
	id serial primary key,
	first_name varchar(20),
	last_name varchar(20),
	job_title varchar(20)
);

create table authors (
	id serial primary key,
	first_name varchar(20),
	last_name varchar(20),
	bio varchar
);

create table stories (
	id serial primary key,
	title varchar(50),
	genre int references genres(id),
	story_type int references story_types(id),
	author int references authors(id),
	description varchar,
	tag_line varchar,
	completion_date date,
	approval_status varchar,
	reason varchar
);

create table genre_editor_join (
	id serial primary key,
	genre int references genres(id),
	editor int references editors(id)
);