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
alter sequence logins_id_seq restart with 1;

create table genres (
	id serial primary key,
	name varchar(20)
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
	username varchar(20),
	password varchar(20)
);

create table authors (
	id serial primary key,
	first_name varchar(20),
	last_name varchar(20),
	bio varchar,
	points int,
	username varchar(20),
	password varchar(20)
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
	reason varchar,
	submission_date date
);

create table genre_editor_join (
	id serial primary key,
	genre int references genres(id),
	editor int references editors(id),
	senior bool,
	assistant bool
);

alter table editors add column username varchar(20);
alter table editors add column password varchar(20);
alter table authors add column username varchar(20);
alter table authors add column password varchar(20);

alter table stories add column submission_date date;

update stories set submission_date = '2021-06-18';
update stories set submission_date = '2021-06-04' where id = 3;

insert into story_types values 
(default, 'novel', 50),
(default, 'novella', 25),
(default, 'short story', 20),
(default, 'article', 10);

create or replace procedure "Project_1".resetTables()
language sql
as $$
	delete from stories;
	delete from authors;
	delete from genre_editor_join;
	delete from genres;
	delete from editors;

	alter sequence editors_id_seq restart with 1;
	alter sequence authors_id_seq restart with 1;
	alter sequence stories_id_seq restart with 1;
	alter sequence genre_editor_join_id_seq restart with 1;
	alter sequence genres_id_seq restart with 1;
$$;

call "Project_1".resetTables();



select gej.id, g.id as g_id, g.name, e.id as e_id, e.first_name, e.last_name, gej.senior, gej.assistant
from genre_editor_join gej 
full join genres g
on gej.genre = g.id
full join editors e
on gej.editor = e.id
order by gej.senior desc, gej.assistant desc;



select gej.id, g.id as g_id, g.name, e.id as e_id, e.first_name, e.last_name, gej.senior, gej.assistant
from genre_editor_join gej 
full join genres g
on gej.genre = g.id
full join editors e
on gej.editor = e.id
where gej.id = 5;



select * from stories where genre = 2;




