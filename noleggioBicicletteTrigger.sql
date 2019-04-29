drop database if exists noleggio;
create database noleggio;
use noleggio;
DROP TABLE IF EXISTS biciclette;
CREATE TABLE biciclette (
  id_bicicletta bigint(20) NOT NULL AUTO_INCREMENT,
  anno_di_produzione int(11) NOT NULL,
  modello varchar(255) DEFAULT NULL,
  PRIMARY KEY (id_bicicletta)
) ;
-- INSERT INTO biciclette VALUES (1,2016,'Scott xy1'),(2,2015,'Scott xy2'),(3,2017,'Scott xy3'),(4,2018,'Scott xy4');



DROP TABLE IF EXISTS stazioni;
CREATE TABLE stazioni (
  id_stazione bigint(20) NOT NULL AUTO_INCREMENT,
  cap varchar(255) DEFAULT NULL,
  indirizzo varchar(255) DEFAULT NULL,
  PRIMARY KEY (id_stazione)
);
-- INSERT INTO stazioni VALUES (1,'38100','piazza Dante'),(2,'38100','Via Berlino 7'),(3,'38100','piazza Milano 10');
DROP TABLE IF EXISTS slots;
CREATE TABLE slots (
  id_slot bigint(20) NOT NULL AUTO_INCREMENT,
  stato bit(1) DEFAULT NULL,
  slot_bicicletta bigint(20) DEFAULT NULL,
  slot_stazione bigint(20) NOT NULL,
  PRIMARY KEY (id_slot),
  index  FK_index_slots_slot_bicicletta (slot_bicicletta),
  index FK_index_slots_slot_stazione (slot_stazione),
  CONSTRAINT FK_slots_slot_stazione_id_stazione FOREIGN KEY (slot_stazione) REFERENCES stazioni (id_stazione),
  CONSTRAINT FKFK_slots_slot_stazione_id_bicicletta FOREIGN KEY (slot_bicicletta) REFERENCES biciclette (id_bicicletta)
);
-- INSERT INTO slots VALUES (1,'\0',1,1),(2,'',NULL,1),(3,'',NULL,1),(4,'\0',2,2),(5,'\0',3,2);



DROP TABLE IF EXISTS utenti;
CREATE TABLE utenti (
  id_utente bigint(20) NOT NULL AUTO_INCREMENT,
  cap varchar(255) DEFAULT NULL,
  cellulare varchar(255) DEFAULT NULL,
  cognome varchar(255) DEFAULT NULL,
  data_di_nascita date DEFAULT NULL,
  indirizzo varchar(255) DEFAULT NULL,
  mail varchar(255) not null,
  nome varchar(255) DEFAULT NULL,
  num_carta_di_credito varchar(255) DEFAULT NULL,
  enabled boolean default true,
  password varchar(255) DEFAULT NULL,
  role varchar(15) default 'USER',
  PRIMARY KEY (id_utente),
  index idx_utenti_mail (mail)
);

-- INSERT INTO utenti VALUES (1,'30199','2432222232','Bianchi','1999-10-25','via Roma - Trento','ab@gmail.com','Aldo','1234-1111-4444'),(2,'30198','25452','Rossi','1979-11-15','via Milano - Verona','crb@gmail.com','Carlo','4434-66611-4444');

DROP TABLE IF EXISTS noleggi;
CREATE TABLE noleggi (
  id_noleggio bigint(20) NOT NULL AUTO_INCREMENT,
  data_fine datetime DEFAULT NULL,
  data_inizio datetime DEFAULT NULL,
  id_bicicletta bigint(20) NOT NULL,
  slot_noleggio_arrivo bigint(20) DEFAULT NULL,
  slot_noleggio_partenza bigint(20) NOT NULL,
  id_utente bigint(20) NOT NULL,
  PRIMARY KEY (id_noleggio),
  index FK_index_noleggi_id_bicicletta (id_bicicletta),
  index FK_index_noleggi_slot_noleggio_arrivo (slot_noleggio_arrivo),
  index FK_index_noleggi_slot_noleggio_partenza (slot_noleggio_partenza),
  index FK_index_noleggi_id_utente (id_utente),
  CONSTRAINT FK_noleggi_slot_noleggio_arrivo_id_slot FOREIGN KEY (slot_noleggio_arrivo) REFERENCES slots (id_slot),
  CONSTRAINT FK_noleggi_slot_noleggio_partenza_id_slot FOREIGN KEY (slot_noleggio_partenza) REFERENCES slots (id_slot),
  CONSTRAINT FK_noleggi_id_bicicletta_id_bicicletta FOREIGN KEY (id_bicicletta) REFERENCES biciclette (id_bicicletta),
  CONSTRAINT FK_noleggi_id_utente_id_utente FOREIGN KEY (id_utente) REFERENCES utenti (id_utente)
) ;
-- INSERT INTO noleggi VALUES (1,'2019-03-16','2019-03-15',1,1,2,1),(2,'2019-03-20','2019-03-20',4,NULL,2,1);

delimiter //
create trigger trNoleggio
	after insert on Noleggi FOR EACH ROW
BEGIN
	update slots set stato=1, slot_bicicletta=null where id_slot=new.slot_noleggio_partenza;
end; //
delimiter ;

delimiter //
create trigger trRestituzione
	after update on Noleggi FOR EACH ROW
BEGIN
	update slots set stato=0, slot_bicicletta=new.id_bicicletta  where id_slot=new.slot_noleggio_arrivo;
end; //
delimiter ;

create view view_biciclette_in_uso as
select biciclette.id_bicicletta as id_bicicletta, utenti.nome as nome, utenti.cognome as cognome ,stazioni.id_stazione as  id_stazione
from biciclette, utenti, noleggi,stazioni,slots
where 	noleggi.id_utente = utenti.id_utente
	and noleggi.id_bicicletta = biciclette.id_bicicletta
    and noleggi.slot_noleggio_partenza=slots.id_slot
    and slots.slot_stazione=stazioni.id_stazione
	and slot_noleggio_arrivo is null; 
    
CREATE TABLE ruoli (
  id_ruolo int(11) NOT NULL AUTO_INCREMENT,
  ruolo varchar(255) DEFAULT NULL,
  PRIMARY KEY (id_ruolo)
);

CREATE TABLE utenti_ruoli (
  id_utente bigint(20) NOT NULL,
  id_ruolo int(11) NOT NULL,
  PRIMARY KEY (id_utente,id_ruolo),
  CONSTRAINT FK_utenti_roles_id_utenti FOREIGN KEY (id_ruolo) REFERENCES ruoli (id_ruolo),
  CONSTRAINT FK_utenti_roles_id_role  FOREIGN KEY (id_utente) REFERENCES utenti (id_utente)
) ;

 
INSERT INTO `biciclette` VALUES (1,2016,'Scott xy1'),(2,2015,'Scott xy2'),(3,2017,'Scott xy3'),(4,2018,'Scott xy4'),(5,2019,'atala xxxx');
INSERT INTO `utenti`(id_utente,cap,cellulare,cognome,data_di_nascita,indirizzo,mail,nome,num_carta_di_credito,ENABLEd,password,role) 
	VALUES (1,'30199','2432222232','Bianchi','1999-10-25','via Roma - Trento','b@b','Aldo','1234-1111-4444',true,'$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','USER'),
		   (2,'30198','25452','Rossi','1979-11-15','via Milano - Verona','r@r','Carlo','4434-66611-4444',true,   '$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','ADMIN');
INSERT INTO `stazioni` VALUES (1,'38100','piazza Dante'),(2,'38100','Via Berlino 7'),(3,'38100','piazza Milano 10');
INSERT INTO `slots` VALUES (1,0,1,1),(2,1,NULL,1),(3,1,NULL,1),(4,1,2,2),(5,0,3,2),(7,0,5,1);
INSERT INTO `noleggi` VALUES (1,'2019-03-16 12:00','2019-03-16 13:15',1,1,2,1),(2,'2019-03-20','2019-03-20',4,NULL,2,1);

insert into ruoli(id_ruolo,ruolo) values(1,'user'),(2,'admin');
insert into utenti_ruoli(id_utente,id_ruolo) values(1,1),(2,1);