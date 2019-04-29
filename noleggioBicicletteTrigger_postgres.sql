	SET statement_timeout = 0;
	SET lock_timeout = 0;
	SET idle_in_transaction_session_timeout = 0;
	SET client_encoding = 'UTF8';
	SET standard_conforming_strings = on;
	SELECT pg_catalog.set_config('search_path', '', false);
	SET check_function_bodies = false;
	SET client_min_messages = warning;
	SET row_security = off;

	--
	-- Name: public; Type: SCHEMA; Schema: -; Owner: nembinbvysfxwe
	--

	
 	drop SCHEMA public cascade;
	
	CREATE SCHEMA public;

	ALTER SCHEMA public OWNER TO nembinbvysfxwe;

	--
	-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: nembinbvysfxwe
	--

	COMMENT ON SCHEMA public IS 'standard public schema';


	SET default_tablespace = '';

	SET default_with_oids = false;
	set schema 'public';

	DROP TABLE IF EXISTS biciclette;
	CREATE TABLE biciclette (
	  id_bicicletta bigserial not null,
	  anno_di_produzione integer NOT NULL,
	  modello varchar(255) DEFAULT NULL,
	  PRIMARY KEY (id_bicicletta)
	) ;


	DROP TABLE IF EXISTS stazioni;
	CREATE TABLE stazioni (
	  id_stazione bigserial not null,
	  cap varchar(255) DEFAULT NULL,
	  indirizzo varchar(255) DEFAULT NULL,
	  PRIMARY KEY (id_stazione)
	);
	
	DROP TABLE IF EXISTS slots;
	CREATE TABLE slots (
	  id_slot bigserial not null,
	  stato boolean DEFAULT true,
	  slot_bicicletta bigint DEFAULT NULL,
	  slot_stazione bigint NOT NULL,
	  PRIMARY KEY (id_slot),

	  CONSTRAINT FK_slots_slot_stazione_id_stazione FOREIGN KEY (slot_stazione) REFERENCES stazioni (id_stazione),
	  CONSTRAINT FKFK_slots_slot_stazione_id_bicicletta FOREIGN KEY (slot_bicicletta) REFERENCES biciclette (id_bicicletta)
	);
	

	create index  FK_index_slots_slot_bicicletta on slots(slot_bicicletta);
	create index  FK_index_slots_slot_stazione on slots(slot_stazione);

	DROP TABLE IF EXISTS utenti;
	CREATE TABLE utenti (
	  id_utente bigserial not null,
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
	  unique(mail)
	);

	-- INSERT INTO utenti VALUES (1,'30199','2432222232','Bianchi','1999-10-25','via Roma - Trento','ab@gmail.com','Aldo','1234-1111-4444'),(2,'30198','25452','Rossi','1979-11-15','via Milano - Verona','crb@gmail.com','Carlo','4434-66611-4444');

	DROP TABLE IF EXISTS noleggi;
	CREATE TABLE noleggi (
	  id_noleggio bigserial not null,
	  data_fine timestamp DEFAULT NULL,
	  data_inizio timestamp DEFAULT NULL,
	  id_bicicletta bigint NOT NULL,
	  slot_noleggio_arrivo bigint DEFAULT NULL,
	  slot_noleggio_partenza bigint NOT NULL,
	  id_utente bigint NOT NULL,
	  PRIMARY KEY (id_noleggio),
	  CONSTRAINT FK_noleggi_slot_noleggio_arrivo_id_slot FOREIGN KEY (slot_noleggio_arrivo) REFERENCES slots (id_slot),
	  CONSTRAINT FK_noleggi_slot_noleggio_partenza_id_slot FOREIGN KEY (slot_noleggio_partenza) REFERENCES slots (id_slot),
	  CONSTRAINT FK_noleggi_id_bicicletta_id_bicicletta FOREIGN KEY (id_bicicletta) REFERENCES biciclette (id_bicicletta),
	  CONSTRAINT FK_noleggi_id_utente_id_utente FOREIGN KEY (id_utente) REFERENCES utenti (id_utente)
	) ;
	create  index FK_index_noleggi_id_bicicletta on noleggi (id_bicicletta);
	create  index FK_index_noleggi_slot_noleggio_arrivo on noleggi(slot_noleggio_arrivo);
	create  index FK_index_noleggi_slot_noleggio_partenza on noleggi(slot_noleggio_partenza);
	create  index FK_index_noleggi_id_utente on noleggi(id_utente);


	-- INSERT INTO noleggi VALUES (1,'2019-03-16','2019-03-15',1,1,2,1),(2,'2019-03-20','2019-03-20',4,NULL,2,1);


	CREATE OR REPLACE FUNCTION ftrNoleggio()
		RETURNS trigger
		LANGUAGE plpgsql	  
		AS
		$$
		BEGIN
			update slots set stato=true, slot_bicicletta=null where id_slot=new.slot_noleggio_partenza;
			RETURN new;
		END; 
		$$;
	
	create trigger trNoleggio
		after insert on Noleggi FOR EACH ROW
		EXECUTE PROCEDURE ftrNoleggio();

	
	CREATE OR REPLACE  FUNCTION ftrRestituzione()
		RETURNS trigger
		LANGUAGE plpgsql	  
		AS
		$$
		BEGIN
			update slots set stato=false, slot_bicicletta=new.id_bicicletta  where id_slot=new.slot_noleggio_arrivo;
			RETURN new;
		END; 
		$$;
	

	
	create trigger trRestituzione
		after update on Noleggi   FOR EACH ROW
		EXECUTE PROCEDURE ftrRestituzione();
	
	create view view_biciclette_in_uso as
	select biciclette.id_bicicletta as id_bicicletta, utenti.nome as nome, utenti.cognome as cognome ,stazioni.id_stazione as  id_stazione
	from biciclette, utenti, noleggi,stazioni,slots
	where 	noleggi.id_utente = utenti.id_utente
		and noleggi.id_bicicletta = biciclette.id_bicicletta
		and noleggi.slot_noleggio_partenza=slots.id_slot
		and slots.slot_stazione=stazioni.id_stazione
		and slot_noleggio_arrivo is null; 
		
	CREATE TABLE ruoli (
	  id_ruolo bigserial not null,
	  ruolo varchar(255) DEFAULT NULL,
	  PRIMARY KEY (id_ruolo)
	);

	CREATE TABLE utenti_ruoli (
	  id_utente bigint NOT NULL,
	  id_ruolo integer NOT NULL,
	  PRIMARY KEY (id_utente,id_ruolo),
	  CONSTRAINT FK_utenti_roles_id_utenti FOREIGN KEY (id_ruolo) REFERENCES ruoli (id_ruolo),
	  CONSTRAINT FK_utenti_roles_id_role  FOREIGN KEY (id_utente) REFERENCES utenti (id_utente)
	) ;

	 
	INSERT INTO biciclette VALUES (1,2016,'Scott xy1'),(2,2015,'Scott xy2'),(3,2017,'Scott xy3'),(4,2018,'Scott xy4'),(5,2019,'atala xxxx');
	INSERT INTO utenti(id_utente,cap,cellulare,cognome,data_di_nascita,indirizzo,mail,nome,num_carta_di_credito,ENABLEd,password,role) 
		VALUES (1,'30199','2432222232','Bianchi','1999-10-25','via Roma - Trento','b@b','Aldo','1234-1111-4444',true,'$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','USER'),
			   (2,'30198','25452','Rossi','1979-11-15','via Milano - Verona','r@r','Carlo','4434-66611-4444',true,   '$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','ADMIN');
	INSERT INTO stazioni VALUES (1,'38100','piazza Dante'),(2,'38100','Via Berlino 7'),(3,'38100','piazza Milano 10');
	INSERT INTO slots(id_Slot,stato,slot_Bicicletta,slot_stazione)
		VALUES (1,false,1,1),(2,true,NULL,1),(3,true,NULL,1),(4,true,2,2),(5,false,3,2),(7,false,5,1);

	INSERT INTO noleggi VALUES (1,'2019-03-16 12:00','2019-03-16 13:15',1,1,2,1),(2,'2019-03-20','2019-03-20',4,NULL,2,1);

	insert into ruoli(id_ruolo,ruolo) values(1,'user'),(2,'admin');
	insert into utenti_ruoli(id_utente,id_ruolo) values(1,1),(2,1);