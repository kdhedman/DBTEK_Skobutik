  drop database skobutik;
 create database skobutik;
use skobutik;
create table Storlek(
	id int not null auto_increment primary key,
	Skostorlek int not null
);

insert into Storlek
	(Skostorlek)
	values
	(30),(31),(32),(33),(34),
	(35),(36),(37),(38),(39),
	(40),(41),(42),(43),(44),
	(45),(46),(47),(48),(49);

create table Färg (
	id int not null auto_increment primary key,
	Färg varchar(50) not null
);

insert into Färg
	(Färg) values
	('Vit'),('Svart'),('Blå'),('Grön'),
	('Gul'),('Röd'),('Lila');

create table Kategori (
	id int not null auto_increment primary key,
	Kategori varchar(50) not null
);

insert into Kategori
	(Kategori) values
	('Street'),('Outdoor'),('Work'),
	('Workout'),('Posh'),('Dandy'),
	('Sunbathing');

create table Ort(
	id int not null auto_increment primary key,
	namn varchar(50)	default ('Default namn')
);

insert into Ort
	(Namn) values
	('Disneyland'),('Hembyn'),
	('Studiestad'),('Långtbortistan'),
	('inhyrarborg'),('King of the hill');

create table kund(
    id int not null auto_increment primary key,
    namn varchar(50) not null default 'ortnamn',
    adress varchar(50) not null,
    OrtID int default (7) references ort(id) on delete set null,
    -- För att visa att vi KAN ta bort saker trots Foreign keys.
    passerord varchar(50) not null default 'passerord'
);

insert into kund
    (namn, adress, ortID, passerord) VALUES
    ('Askungen', 'Pumpagränd 2', 1, 'Askungen1'),
    ('Elin', 'Elinborhär 2', 2, 'Elin1'),
    ('Sigrun', 'Nackademin 3', 3, 'Sigrun1'),
    ('David', 'Elinborhär 2', 2, 'David1'),
    ('Jesper', 'Hemma', 4, 'Jesper1'),
    ('Mahmoud', 'Nackademin 3', 3, 'Mahmoud1'),
    ('Lotta', 'Nackademin 2', 3, 'Lotta1'),
    ('Josef', 'Konsultadress 1', 5, 'Josef1'),
    ('Carl-Johan', 'Konsultadress 2', 5, 'Carl-Johan1'),
    ('Kungen', 'Slottet', 6, 'Kungen1');

create table leverans (
	id int not null auto_increment primary key,
	Kundid int , Datum timestamp,
	foreign key (Kundid) references kund(id)
);

insert into leverans (kundid,datum) value
(1,('2001-01-21')),(2,('2001-01-22')),(3,('2001-03-23')),
(10,('2001-03-24')),(5,('2001-04-25')),(4,('2001-04-26')),(6,('2001-04-26'));

create table märke(
	id int not null auto_increment,
	märke varchar(50) not null,
	primary key(id)
);

insert into märke
	(märke) values
	('Dr.Martens'),
	('Nike'),
	('Adidas'),
	('Sko-Janne'),
	('PHAT'),
	('HoeSoul'),
	('Tjannel');

create table skomodell(
	id int not null auto_increment,
	skomodell varchar(50) not null,
	pris int not null,
	märkeID int not null,
	Lagerstatus int default 1000,
	primary key(id),
	foreign key(märkeID) references märke(id)
);

insert into skomodell
	(skomodell, pris, märkeID)
	VALUES
	('Skaterboi', 1200, 5),
	('SkaterGRL', 1199, 5),
	('TANK', 1699, 1),
	('Vaniljsko', 299, 2),
	('Chokladsko', 399, 7),
	('Träsko', 1, 4),
	('Glas-sko', 20000, 6),
	('Råttfällan', 1234, 3),
	('Flingor', 455, 1),
	('Ecco', 799, 6);

create table beställning(
	id int not null auto_increment,
	skomodellID int,
	kvantitet int not null,
	storlekID int not null,
	färgid int not null,
	leveransID int not null,
	primary key(id),
	foreign key(skomodellID) references skomodell(id) on delete set null,
    -- Om en tar bort en skomodell vill vi inte att gamla beställningar försvinner så vi sätter skomodellID till null istället.
	foreign key(storlekID) references storlek(id),
	foreign key(färgID) references färg(id),
	foreign key(leveransID) references leverans(id)
);

insert into beställning
	(skomodellID, kvantitet, storlekID, färgID, leveransID)
	VALUES
	(7, 10, 6, 3, 1),
	(2, 1, 7, 1, 1),
	(6, 2, 6, 2 , 2),
	(8, 1, 7, 4, 3),
	(9, 100, 13, 5, 4),
	(1, 2, 7, 1, 5),
	(10, 1, 9, 2, 6),
    (10, 2, 9, 2, 7);

create table FärgMappning(
	id int not null auto_increment,
	skomodellID int not null,
	färgID int not null,
	primary key(id),
	foreign key(skomodellID) references skomodell(id) on delete cascade,
    -- Här har vi cascade för om en tar bort en skomodell vill vi ta bort alla relaterade färgmappningar
	foreign key(färgID) references färg(id)
);

insert into FärgMappning
	(skomodellID, färgID)
	values
	(1,1), (1,2), (1,3),
	(2,4), (2,5), (2,6),
	(3,7), (3,1), (3,2),
	(4,3), (4,4), (4,5),
	(5,6), (5,7), (5,1),
	(6,2), (6,3), (6,4),
	(7,5), (7,6), (7,7),
	(8,1), (8,2), (8,3),
	(9,4), (9,5), (9,6),
	(10,7), (10,1), (10,2);

create table StorleksMappning(
	id int not null auto_increment,
	skomodellID int not null,
	storlekID int not null,
	primary key(id),
	foreign key(skomodellID) references skomodell(id) on delete cascade,
    -- Här har vi cascade för om en tar bort en skomodell vill vi ta bort alla relaterade storleksmappningar
	foreign key(storlekID) references storlek(id)
);

insert into StorleksMappning
	(skomodellID, storlekID)
	VALUES
	(1,5), (1,6), (1,7),
	(2,18), (2,19),
	(3,14), (3,15), (3,16),
	(4,6), (4,7), (4,8), (4,9),
	(5,14), (5,15), (5,16),
	(6,2), (6,6),
	(7,5), (7,6),
	(8,9), (8,11), (8,13),
	(9,12), (9,13), (9,14),
	(10,7), (10,11), (10,14), (10,16);

create table KategoriMappning(
	id int not null auto_increment,
	skomodellID int not null,
	kategoriID int not null,
	primary key(id),
	foreign key(skomodellID) references skomodell(id) on delete cascade,
    -- Här har vi cascade för om en tar bort en skomodell vill vi ta bort alla relaterade kategorimappningar
	foreign key(kategoriID) references kategori(id)
);

insert into KategoriMappning
	(skomodellID, kategoriID)
	VALUES
	(1,1), (1,2),
	(2,1), (2,2),
	(3,4),
	(4,5), (4,6),
	(5,5), (5,6),
	(6,7), (6,2), (6,1),
	(7,5), (7,6),
	(8,4), (8,2),
	(9,3), (9,6),
	(10,6), (10,5), (10,1);

create table betygskala(
	id int not null auto_increment primary key,
	betyg varchar(50)
);

insert into betygskala
	(betyg) values
	('Missnöjd'), ('Ganska nöjd'),
	('Nöjd'), ('Mycket nöjd');

create table betyg(
	id int not null auto_increment primary key,
	betygskalaID int not null,
	kommentar varchar(50),
	kundID int,
	skomodellID int ,
	foreign key(betygskalaID) references betygskala(id),
	foreign key(kundID) references kund(id) ,
	foreign key(skomodellID) references skomodell(id) on delete cascade
    -- Om vi tar bort en skomodell vill vi också ta bort alla betyg för den modellen.
);

insert into betyg
	(betygskalaID, kommentar, kundID, skomodellID)
	VALUES
	(1, 'Det här är ju en tjejsko!!!', 8, 7),
	(2, 'Aldelles för små, men snygga.', 10, 7),
	(4, 'Bästa skon ever!', 5, 1),
	(4, 'Vilken bra skobutik! Helt klart VG!!!', 3, 8);

    create index storleksmappning_skomodellid_index on storleksmappning (skomodellid);
    create index färgmappning_skomodellid_index on färgmappning (skomodellid);
   -- Vi har val dessa två eftersom de kommer växa sig väldigt stora iom att det blir fler skomodeller.

create table slutilager(
    id int not null auto_increment,
    skomodellID int not null,
    datum timestamp default current_timestamp,
    primary key(id),
    foreign key(skomodellID) references skomodell(id)
);

delimiter //
create trigger after_skomodell_update
    after update on skomodell
    for each row
begin
    if new.lagerstatus < 1 then
        insert into slutilager(skomodellID) VALUES (new.id);
    end if;
end//
delimiter ;

  CREATE DEFINER=`skobutik`@`%` PROCEDURE `Stored_Procedure_Add_to_Cart`(Skomodell int, Kund int )
  BEGIN
declare variable_temp date default null;
  select datum into variable_temp from leverans where kundid=kund  order by datum asc limit 1;
  if variable_temp is not null then
insert into leverans  (kundiD)values (kund);
  insert into beställning (Skomodellid, kvantitet, storlekID, färgID, leveransID) values(skomodell, 1,6, 3, last_insert_id());
  update skomodell set lagerstatus = (lagerstatus - 1) where skomodell.id=skomodell;
  end if;
if variable_temp is null then
if kund != all(select kundid from leverans) then
insert into leverans  (kundiD)values (kund);
  end if;
if skomodell = any(select skomodellid from beställning join leverans on leverans.id = beställning.leveransid where kundid=kund)  then
  update beställning set kvantitet = (kvantitet+1) where leveransid = (select id from leverans where kundid=kund  order by id asc limit 1) and skomodellid = skomodell;
  update skomodell set lagerstatus = (lagerstatus - 1) where skomodell.id=skomodell;
  else if skomodell != all (select skomodellid from beställning join leverans on leverans.id=beställning.leveransID where kundid=kund) then
insert into beställning (Skomodellid, kvantitet, storlekID, färgID, leveransID) values(skomodell, 1,6, 3,(select id from leverans where kundid=kund  order by datum asc limit 1));
  update skomodell set lagerstatus = (lagerstatus - 1) where skomodell.id=skomodell;
  end if;
  end if;
  end if;

  END