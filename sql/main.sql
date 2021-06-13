drop table genres;
drop table story_types;
drop table editors;
drop table authors;
drop table stories;
drop table genre_editor_join;

alter sequence genres_id_seq restart with 1;
alter sequence story_types_id_seq restart with 1;
alter sequence editors_id_seq restart with 1;
alter sequence authors_id_seq restart with 1;
alter sequence stories_id_seq restart with 1;
alter sequence genre_editor_join_id_seq restart with 1;

create table genres (
	id serial primary key,
	name varchar(20),
	senior_editor int references editors(id)
);

create table story_types (
	id serial primary key,
	name varchar(15),
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
	bio varchar,
	points int
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

insert into story_types values 
(default, 'novel', 50),
(default, 'novella', 25),
(default, 'short story', 20),
(default, 'article', 10);

