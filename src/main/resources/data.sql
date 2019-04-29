INSERT INTO biciclette VALUES (1,2016,'Scott xy1'),(2,2015,'Scott xy2'),(3,2017,'Scott xy3'),(4,2018,'Scott xy4'),(5,2019,'atala xxxx');
INSERT INTO utenti(id_utente,cap,cellulare,cognome,data_di_nascita,indirizzo,mail,nome,num_carta_di_credito,ENABLEd,password,role) 
	VALUES (1,'30199','2432222232','Bianchi','1999-10-25','via Roma - Trento','b@b','Aldo','1234-1111-4444',true,'$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','USER'),
		   (2,'30198','25452','Rossi','1979-11-15','via Milano - Verona','r@r','Carlo','4434-66611-4444',true,   '$2a$10$5e3dB36HeRcozRgp8xQfw.tfD3Qsut8xu/NT9g/DSpVKg9Kzuitrq','ADMIN');
           
INSERT INTO stazioni VALUES (1,'38100','piazza Dante'),(2,'38100','Via Berlino 7'),(3,'38100','piazza Milano 10');
INSERT INTO slots
(id_Slot,stato,slot_Bicicletta,slot_stazione)
 VALUES (1,false,1,1),(2,true,NULL,1),(3,true,NULL,1),(4,true,2,2),(5,false,3,2),(7,false,5,1);
INSERT INTO noleggi VALUES (1,'2019-03-16 12:00','2019-03-16 13:15',1,1,2,1),(2,'2019-03-20','2019-03-20',4,NULL,2,1);