CREATE TABLE tankemTypeCase(
	typedecase NUMBER PRIMARY KEY,
	stringTypeCase VARCHAR2(20) NOT NULL 
);
CREATE TABLE tankemStatusNiveau(
	statusNiveau NUMBER PRIMARY KEY,
	stringStatus VARCHAR2(20) NOT NULL 
);
CREATE TABLE tankemNiveau(
	id NUMBER PRIMARY KEY,
	nom VARCHAR2(60) NOT NULL,
	temps_min NUMBER,
	temps_max NUMBER,
	date_creation DATE,
	status NUMBER NOT NULL,
	grosseur_x NUMBER NOT NULL,
	grosseur_y NUMBER NOT NULL,
	FOREIGN KEY (status) REFERENCES tankemStatusNiveau(statusNiveau)
);
CREATE SEQUENCE seq_niv
START WITH 1
INCREMENT BY 1;
CREATE TABLE tankemCase(
	id_niveau NUMBER NOT NULL,
	valeurx NUMBER NOT NULL,
	valeury NUMBER NOT NULL,
	typecase NUMBER NOT NULL,
	FOREIGN KEY (id_niveau) REFERENCES tankemNiveau(id),
	FOREIGN KEY (typecase) REFERENCES tankemTypeCase(typedecase)
);
CREATE TABLE tankemTank(
	id_niveau NUMBER NOT NULL,
	valeurx NUMBER NOT NULL,
	valeury NUMBER NOT NULL,
	FOREIGN KEY (id_niveau) REFERENCES tankemNiveau(id)
);
INSERT INTO tankemTypeCase VALUES(1,'Mur');
INSERT INTO tankemTypeCase VALUES(2,'MurVert');
INSERT INTO tankemTypeCase VALUES(3,'MurVertInvers');
INSERT INTO tankemStatusNiveau VALUES(1,'Public');
INSERT INTO tankemStatusNiveau VALUES(2,'Equipe');
INSERT INTO tankemStatusNiveau VALUES(3,'Prive');
INSERT INTO tankemStatusNiveau VALUES(4,'Inactif');
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'Volcano',4,8,TO_DATE('2016/03/18', 'yyyy/mm/dd'),1,5,5);
INSERT INTO tankemCase VALUES(1,2,3,1);
INSERT INTO tankemCase VALUES(1,4,3,1);
INSERT INTO tankemCase VALUES(1,3,4,2);
INSERT INTO tankemCase VALUES(1,1,4,3);
INSERT INTO tankemCase VALUES(1,4,1,1);
INSERT INTO tankemCase VALUES(1,0,3,1);
INSERT INTO tankemTank VALUES(1,2,2);
INSERT INTO tankemTank VALUES(1,3,4);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'Phoenix Lavoie',1,3,TO_DATE('2016/03/18', 'yyyy/mm/dd'),1,10,10);
INSERT INTO tankemCase VALUES(2,2,3,1);
INSERT INTO tankemCase VALUES(2,4,3,1);
INSERT INTO tankemCase VALUES(2,3,4,2);
INSERT INTO tankemCase VALUES(2,1,4,3);
INSERT INTO tankemCase VALUES(2,6,8,2);
INSERT INTO tankemCase VALUES(2,9,9,3);
INSERT INTO tankemCase VALUES(2,2,8,1);
INSERT INTO tankemCase VALUES(2,7,9,1);
INSERT INTO tankemCase VALUES(2,1,6,3);
INSERT INTO tankemCase VALUES(2,5,8,2);
INSERT INTO tankemCase VALUES(2,5,0,3);
INSERT INTO tankemCase VALUES(2,0,4,2);
INSERT INTO tankemCase VALUES(2,7,4,3);
INSERT INTO tankemCase VALUES(2,2,8,3);
INSERT INTO tankemCase VALUES(2,0,7,2);
INSERT INTO tankemCase VALUES(2,3,6,2);
INSERT INTO tankemCase VALUES(2,1,3,2);
INSERT INTO tankemCase VALUES(2,1,0,3);
INSERT INTO tankemCase VALUES(2,2,1,2);
INSERT INTO tankemCase VALUES(2,3,1,3);
INSERT INTO tankemCase VALUES(2,8,8,3);
INSERT INTO tankemCase VALUES(2,9,5,2);
INSERT INTO tankemCase VALUES(2,7,6,2);
INSERT INTO tankemTank VALUES(2,2,6);
INSERT INTO tankemTank VALUES(2,8,2);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'SrumBattle',5,10,TO_DATE('2016/03/18', 'yyyy/mm/dd'),2,5,9);
INSERT INTO tankemCase VALUES(3,2,3,1);
INSERT INTO tankemCase VALUES(3,4,3,1);
INSERT INTO tankemCase VALUES(3,3,4,2);
INSERT INTO tankemCase VALUES(3,1,4,3);
INSERT INTO tankemCase VALUES(3,4,7,3);
INSERT INTO tankemCase VALUES(3,0,6,1);
INSERT INTO tankemCase VALUES(3,2,7,2);
INSERT INTO tankemCase VALUES(3,1,0,3);
INSERT INTO tankemTank VALUES(3,0,0);
INSERT INTO tankemTank VALUES(3,3,4);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'Private Room',3,5,TO_DATE('2016/03/18', 'yyyy/mm/dd'),2,6,12);
INSERT INTO tankemCase VALUES(4,2,3,2);
INSERT INTO tankemCase VALUES(4,5,8,1);
INSERT INTO tankemCase VALUES(4,4,11,2);
INSERT INTO tankemCase VALUES(4,3,10,3);
INSERT INTO tankemCase VALUES(4,0,4,2);
INSERT INTO tankemCase VALUES(4,1,9,1);
INSERT INTO tankemCase VALUES(4,5,3,2);
INSERT INTO tankemCase VALUES(4,1,7,3);
INSERT INTO tankemCase VALUES(4,2,6,2);
INSERT INTO tankemCase VALUES(4,0,11,3);
INSERT INTO tankemCase VALUES(4,2,9,2);
INSERT INTO tankemCase VALUES(4,3,5,3);
INSERT INTO tankemTank VALUES(4,0,5);
INSERT INTO tankemTank VALUES(4,9,5);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'Tag3000',1,2,TO_DATE('2016/03/18', 'yyyy/mm/dd'),3,10,10);
INSERT INTO tankemCase VALUES(5,2,3,1);
INSERT INTO tankemCase VALUES(5,6,3,2);
INSERT INTO tankemCase VALUES(5,8,7,2);
INSERT INTO tankemCase VALUES(5,1,4,3);
INSERT INTO tankemCase VALUES(5,0,6,1);
INSERT INTO tankemCase VALUES(5,0,2,1);
INSERT INTO tankemCase VALUES(5,3,8,2);
INSERT INTO tankemCase VALUES(5,3,1,3);
INSERT INTO tankemCase VALUES(5,4,8,1);
INSERT INTO tankemCase VALUES(5,7,3,3);
INSERT INTO tankemCase VALUES(5,4,4,2);
INSERT INTO tankemCase VALUES(5,8,2,3);
INSERT INTO tankemCase VALUES(5,7,7,1);
INSERT INTO tankemCase VALUES(5,1,8,2);
INSERT INTO tankemCase VALUES(5,5,0,2);
INSERT INTO tankemCase VALUES(5,5,4,3);
INSERT INTO tankemTank VALUES(5,5,5);
INSERT INTO tankemTank VALUES(5,4,6);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'ERP Vs Blender',1,3,TO_DATE('2016/03/18', 'yyyy/mm/dd'),3,12,12);
INSERT INTO tankemCase VALUES(6,11,2,1);
INSERT INTO tankemCase VALUES(6,5,7,3);
INSERT INTO tankemCase VALUES(6,7,2,2);
INSERT INTO tankemCase VALUES(6,4,4,3);
INSERT INTO tankemCase VALUES(6,0,3,1);
INSERT INTO tankemCase VALUES(6,10,5,1);
INSERT INTO tankemCase VALUES(6,2,3,2);
INSERT INTO tankemCase VALUES(6,2,6,3);
INSERT INTO tankemCase VALUES(6,1,5,1);
INSERT INTO tankemCase VALUES(6,0,8,1);
INSERT INTO tankemCase VALUES(6,9,7,2);
INSERT INTO tankemCase VALUES(6,8,7,3);
INSERT INTO tankemCase VALUES(6,8,4,1);
INSERT INTO tankemCase VALUES(6,5,5,3);
INSERT INTO tankemCase VALUES(6,3,1,2);
INSERT INTO tankemCase VALUES(6,1,9,3);
INSERT INTO tankemCase VALUES(6,3,8,1);
INSERT INTO tankemCase VALUES(6,11,1,2);
INSERT INTO tankemCase VALUES(6,7,3,2);
INSERT INTO tankemCase VALUES(6,9,4,3);
INSERT INTO tankemCase VALUES(6,3,10,1);
INSERT INTO tankemCase VALUES(6,11,10,2);
INSERT INTO tankemCase VALUES(6,7,9,2);
INSERT INTO tankemCase VALUES(6,9,10,3);
INSERT INTO tankemTank VALUES(6,7,9);
INSERT INTO tankemTank VALUES(6,0,2);
INSERT INTO tankemNiveau VALUES(seq_niv.nextVal,'Cimetiere',5,10,TO_DATE('2016/03/18', 'yyyy/mm/dd'),4,3,7);
INSERT INTO tankemCase VALUES(7,2,3,1);
INSERT INTO tankemCase VALUES(7,1,6,1);
INSERT INTO tankemCase VALUES(7,0,0,2);
INSERT INTO tankemCase VALUES(7,1,5,3);
INSERT INTO tankemTank VALUES(7,4,0);
INSERT INTO tankemTank VALUES(7,3,2);
COMMIT;