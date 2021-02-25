drop database skobutik;
create database skobutik;
use skobutik;
create table Storlek
(
    id         int not null auto_increment primary key,
    Skostorlek int not null
);

insert into Storlek
    (Skostorlek)
values (30),
       (31),
       (32),
       (33),
       (34),
       (35),
       (36),
       (37),
       (38),
       (39),
       (40),
       (41),
       (42),
       (43),
       (44),
       (45),
       (46),
       (47),
       (48),
       (49);

create table Färg
(
    id   int         not null auto_increment primary key,
    Färg varchar(50) not null
);

insert into Färg
    (Färg)
values ('Vit'),
       ('Svart'),
       ('Blå'),
       ('Grön'),
       ('Gul'),
       ('Röd'),
       ('Lila');

create table Kategori
(
    id       int         not null auto_increment primary key,
    Kategori varchar(50) not null
);

insert into Kategori
    (Kategori)
values ('Street'),
       ('Outdoor'),
       ('Work'),
       ('Workout'),
       ('Posh'),
       ('Dandy'),
       ('Sunbathing');

create table Ort
(
    id   int not null auto_increment primary key,
    namn varchar(50) default ('Default namn')
);

insert into Ort
    (Namn)
values ('Disneyland'),
       ('Hembyn'),
       ('Studiestad'),
       ('Långtbortistan'),
       ('inhyrarborg'),
       ('King of the hill');

create table kund
(
    id        int         not null auto_increment primary key,
    namn      varchar(50) not null default 'ortnamn',
    adress    varchar(50) not null,
    OrtID     int                  default (7) references ort (id) on delete set null,
    -- För att visa att vi KAN ta bort saker trots Foreign keys.
    passerord varchar(50) not null default 'passerord'
);

insert into kund
    (namn, adress, ortID, passerord)
VALUES ('Askungen', 'Pumpagränd 2', 1, 'Askungen1'),
       ('Elin', 'Elinborhär 2', 2, 'Elin1'),
       ('Sigrun', 'Nackademin 3', 3, 'Sigrun1'),
       ('David', 'Elinborhär 2', 2, 'David1'),
       ('Jesper', 'Hemma', 4, 'Jesper1'),
       ('Mahmoud', 'Nackademin 3', 3, 'Mahmoud1'),
       ('Lotta', 'Nackademin 2', 3, 'Lotta1'),
       ('Josef', 'Konsultadress 1', 5, 'Josef1'),
       ('Carl-Johan', 'Konsultadress 2', 5, 'Carl-Johan1'),
       ('Kungen', 'Slottet', 6, 'Kungen1');

create table leverans
(
    id     int not null auto_increment primary key,
    Kundid int,
    Datum  timestamp,
    foreign key (Kundid) references kund (id)
);

insert into leverans (kundid, datum) value
    (1, ('2001-01-21')), (2, ('2001-01-22')), (3, ('2001-03-23')),
    (10, ('2001-03-24')), (5, ('2001-04-25')), (4, ('2001-04-26')), (6, ('2001-04-26'));

create table märke
(
    id    int         not null auto_increment,
    märke varchar(50) not null,
    primary key (id)
);

insert into märke
    (märke)
values ('Dr.Martens'),
       ('Nike'),
       ('Adidas'),
       ('Sko-Janne'),
       ('PHAT'),
       ('HoeSoul'),
       ('Tjannel');

create table skomodell
(
    id          int         not null auto_increment,
    skomodell   varchar(50) not null,
    pris        int         not null,
    märkeID     int         not null,
    Lagerstatus int default 1000,
    primary key (id),
    foreign key (märkeID) references märke (id)
);

insert into skomodell
    (skomodell, pris, märkeID)
VALUES ('Skaterboi', 1200, 5),
       ('SkaterGRL', 1199, 5),
       ('TANK', 1699, 1),
       ('Vaniljsko', 299, 2),
       ('Chokladsko', 399, 7),
       ('Träsko', 1, 4),
       ('Glas-sko', 20000, 6),
       ('Råttfällan', 1234, 3),
       ('Flingor', 455, 1),
       ('Ecco', 799, 6);

create table lagermappning
(
    id          int not null auto_increment primary key,
    SkomodellId int not null,
    StorlekId   int not null,
    FärgId      int not null,
    Lagerstatus int,
    foreign key (skomodellid) references skomodell (id),
    foreign key (storlekid) references storlek (id),
    foreign key (färgid) references färg (id)

);

insert into lagermappning
    (Skomodellid, storlekid, färgid, lagerstatus)
values ((1), (1), (1), (10)),
       ((1), (1), (2), (10)),
       ((1), (1), (3), (10)),
       ((1), (2), (4), (10)),
       ((1), (2), (5), (10)),
       ((1), (2), (6), (10)),
       ((1), (3), (7), (10)),
       ((1), (3), (1), (10)),
       ((1), (3), (2), (10)),
       ((2), (4), (3), (10)),
       ((2), (4), (4), (10)),
       ((2), (4), (5), (10)),
       ((2), (5), (6), (10)),
       ((2), (5), (7), (10)),
       ((2), (5), (1), (10)),
       ((2), (6), (2), (10)),
       ((2), (6), (3), (10)),
       ((2), (6), (4), (10)),
       ((3), (7), (5), (10)),
       ((3), (7), (6), (10)),
       ((3), (8), (7), (10)),
       ((3), (8), (1), (10)),
       ((3), (8), (2), (10)),
       ((3), (9), (3), (10)),
       ((3), (9), (4), (10)),
       ((3), (9), (5), (10)),
       ((3), (10), (6), (10)),
       ((3), (10), (7), (10)),
       ((4), (10), (1), (10)),
       ((4), (11), (2), (10)),
       ((4), (11), (3), (10)),
       ((4), (11), (4), (10)),
       ((4), (12), (5), (10)),
       ((4), (12), (6), (10)),
       ((4), (12), (7), (10)),
       ((4), (13), (1), (10)),
       ((4), (13), (2), (10)),
       ((5), (13), (3), (10)),
       ((5), (14), (4), (10)),
       ((5), (14), (5), (10)),
       ((5), (14), (6), (10)),
       ((5), (15), (7), (10)),
       ((5), (15), (1), (10)),
       ((5), (15), (2), (10)),
       ((5), (16), (3), (10)),
       ((5), (16), (4), (10)),
       ((6), (16), (5), (10)),
       ((6), (17), (6), (10)),
       ((6), (17), (7), (10)),
       ((6), (17), (1), (10)),
       ((6), (18), (2), (10)),
       ((6), (18), (3), (10)),
       ((6), (18), (4), (10)),
       ((6), (19), (5), (10)),
       ((6), (19), (6), (10)),
       ((7), (19), (7), (10)),
       ((7), (20), (1), (10)),
       ((7), (20), (2), (10)),
       ((7), (20), (3), (10)),
       ((7), (1), (4), (10)),
       ((7), (1), (5), (10)),
       ((7), (1), (6), (10)),
       ((7), (2), (7), (10)),
       ((7), (2), (1), (10)),
       ((8), (2), (2), (10)),
       ((8), (3), (3), (10)),
       ((8), (3), (4), (10)),
       ((8), (3), (5), (10)),
       ((8), (4), (6), (10)),
       ((8), (4), (7), (10)),
       ((8), (4), (1), (10)),
       ((8), (5), (2), (10)),
       ((9), (5), (3), (10)),
       ((9), (5), (4), (10)),
       ((9), (6), (5), (10)),
       ((9), (6), (6), (10)),
       ((9), (6), (7), (10)),
       ((9), (7), (1), (10)),
       ((9), (7), (2), (10)),
       ((9), (7), (3), (10)),
       ((9), (8), (4), (10)),
       ((10), (8), (5), (10)),
       ((10), (8), (6), (10)),
       ((10), (8), (7), (10)),
       ((10), (9), (1), (10)),
       ((10), (9), (2), (10)),
       ((10), (9), (3), (10)),
       ((10), (10), (4), (10)),
       ((10), (10), (5), (10)),
       ((10), (10), (7), (10));

create table beställning
(
    id               int not null auto_increment,
    kvantitet        int not null,
    LagermappningsId int not null,
    leveransID       int not null,
    primary key (id),
    foreign key (LagermappningsId) references lagermappning (id),
    foreign key (leveransID) references leverans (id)
);

insert into beställning
    (kvantitet, lagermappningsid, leveransID)
VALUES (10, 6, 1),
       (1, 7, 1),
       (2, 6, 2),
       (1, 7, 3),
       (100, 13, 4),
       (2, 7, 5),
       (11, 9, 6),
       (2, 9, 7);

create table KategoriMappning
(
    id          int not null auto_increment,
    skomodellID int not null,
    kategoriID  int not null,
    primary key (id),
    foreign key (skomodellID) references skomodell (id) on delete cascade,
    -- Här har vi cascade för om en tar bort en skomodell vill vi ta bort alla relaterade kategorimappningar
    foreign key (kategoriID) references kategori (id)
);

insert into KategoriMappning
    (skomodellID, kategoriID)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (3, 4),
       (4, 5),
       (4, 6),
       (5, 5),
       (5, 6),
       (6, 7),
       (6, 2),
       (6, 1),
       (7, 5),
       (7, 6),
       (8, 4),
       (8, 2),
       (9, 3),
       (9, 6),
       (10, 6),
       (10, 5),
       (10, 1);

create table betygskala
(
    id    int not null auto_increment primary key,
    betyg varchar(50)
);

insert into betygskala
    (betyg)
values ('Missnöjd'),
       ('Ganska nöjd'),
       ('Nöjd'),
       ('Mycket nöjd');

create table betyg
(
    id           int not null auto_increment primary key,
    betygskalaID int not null,
    kommentar    varchar(50),
    kundID       int,
    skomodellID  int,
    foreign key (betygskalaID) references betygskala (id),
    foreign key (kundID) references kund (id),
    foreign key (skomodellID) references skomodell (id) on delete cascade
    -- Om vi tar bort en skomodell vill vi också ta bort alla betyg för den modellen.
);

insert into betyg
    (betygskalaID, kommentar, kundID, skomodellID)
VALUES (1, 'Det här är ju en tjejsko!!!', 8, 7),
       (2, 'Aldelles för små, men snygga.', 10, 7),
       (4, 'Bästa skon ever!', 5, 1),
       (4, 'Vilken bra skobutik! Helt klart VG!!!', 3, 8);

-- create index storleksmappning_skomodellid_index on storleksmappning (skomodellid);
-- create index färgmappning_skomodellid_index on färgmappning (skomodellid);
-- Vi har val dessa två eftersom de kommer växa sig väldigt stora iom att det blir fler skomodeller.

create table slutilager
(
    id              int not null auto_increment,
    lagermappningID int not null default 1,
    datum           timestamp    default current_timestamp,
    primary key (id),
    foreign key (lagermappningID) references lagermappning (id)
);

-- Trigger

delimiter //
create trigger after_lagermappning_update
    after update
    on lagermappning
    for each row
begin
    if new.Lagerstatus < 1 then
        insert into slutilager(lagermappningID) VALUES (new.id);
    end if;
end//
delimiter ;

-- Stored Procedure

delimiter //
CREATE
    DEFINER = `skobutik`@`%` PROCEDURE `Stored_Procedure_Add_to_Cart`(Skomodell int, Kund int, StorlekIN int, FärgIN varchar(20))
BEGIN
    declare variable_temp date default null;
    declare variable_lagermappningsID int;
    select id
    into variable_lagermappningsID
    from Lagermappning
    where storlekID = (select id from storlek where skostorlek = StorlekIN)
      and färgid = (select färg.id from färg where färg = FärgIN)
      and skomodellid = skomodell;
    select datum into variable_temp from leverans where kundid = kund order by datum asc limit 1;
    if variable_temp is not null then
        insert into leverans (kundiD) values (kund);
        insert into beställning (kvantitet, lagermappningsid, leveransID)
        values (1, (select id
                    from Lagermappning
                    where storlekID = (select id from storlek where skostorlek = StorlekIN)
                      and färgid = (select färg.id from färg where färg = FärgIN)
                      and skomodellid = skomodell), last_insert_id());
        update lagermappning
        set lagerstatus = (lagerstatus - 1)
        where storlekID = (select id from storlek where skostorlek = StorlekIN)
          and färgid = (select färg.id from färg where färg = FärgIN)
          and skomodellid = Skomodell;
    end if;
    if variable_temp is null then
        if kund != all (select kundid from leverans) then
            insert into leverans (kundiD) values (kund);
        end if;

        if variable_lagermappningsID = any (select lagermappningsid
                                            from beställning
                                                     join leverans on leverans.id = beställning.leveransid
                                                     join lagermappning on beställning.lagermappningsId = lagermappning.id
                                            where kundid = kund) then
            update beställning
            set kvantitet = (kvantitet + 1)
            where leveransid = (select id from leverans where kundid = kund order by id desc limit 1)
              and lagermappningsid = variable_lagermappningsID;
            update lagermappning
            set lagerstatus = (lagerstatus - 1)
            where storlekID = (select id from storlek where skostorlek = StorlekIN)
              and färgid = (select färg.id from färg where färg = FärgIN)
              and skomodellid = Skomodell;
        else
            if variable_lagermappningsID != all (select lagermappningsid
                                                 from beställning
                                                          join leverans on leverans.id = beställning.leveransid
                                                          join lagermappning on beställning.lagermappningsId = lagermappning.id
                                                 where kundid = kund) then
                insert into beställning (kvantitet, lagermappningsid, leveransID)
                values (1, (select id
                            from Lagermappning
                            where storlekID = (select id from storlek where skostorlek = StorlekIN)
                              and färgid = (select färg.id from färg where färg = FärgIN)
                              and skomodellid = skomodell),
                        (select id from leverans where kundid = kund order by datum asc limit 1));

                update lagermappning
                set lagerstatus = (lagerstatus - 1)
                where storlekID = (select id from storlek where skostorlek = StorlekIN)
                  and färgid = (select färg.id from färg where färg = FärgIN)
                  and skomodellid = Skomodell;
            end if;
        end if;
    end if;
END//
delimiter ;

delimiter //
CREATE
    DEFINER = `skobutik`@`%` PROCEDURE `Stored_Procedure_Rate`(Grade int, comment1 varchar(150), customerIDIN int,
                                                               skomodellIDIN int)
BEGIN
    declare variable_existingRating int;
    select id into variable_existingRating from betyg where skomodellid = skomodellIDIN and kundid = customerIDIN;
    if variable_existingRating > 1 then
        delete from betyg where skomodellid = skomodellIDIN and kundid = customerIDIN;
    end if;
    insert into betyg (betygskalaid, kommentar, kundid, skomodellid)
    values ((grade), (comment1), (customerIDIN), (skomodellIDIN));

END//
delimiter ;

-- Function.

delimiter //
CREATE
    DEFINER = `skobutik`@`%` FUNCTION `get_medelbetyg`(skomodellIN int) RETURNS float
    READS SQL DATA
BEGIN

    return (select avg(betygskalaid) as 'Medelbetyg' from betyg where skomodellID = skomodellIN);
END//
delimiter ;

-- View

delimiter //
CREATE ALGORITHM = UNDEFINED DEFINER = `skobutik`@`%` SQL SECURITY DEFINER VIEW `grade_view` AS
SELECT `skomodell`.`skomodell` AS `skomodell`,
       `betygskala`.`betyg`    AS `Genomsnittligt omdömme`,
       AVG(betyg.betygskalaID) AS `Genomsnittligt betyg`
FROM (skomodell
         LEFT JOIN (betyg
    JOIN betygskala ON ((betyg.betygskalaID = betygskala.id))) ON ((betyg.skomodellID = skomodell.id)))
GROUP BY skomodell.skomodell//

delimiter ;