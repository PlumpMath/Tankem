/*CREATION DES TABLES ET DES SEQUENCES NÉCÉSSAIRE POUR LES STATISTIQUE*/
/*Username: 1232188 password:E*/


CREATE SEQUENCE seq_partie
START WITH 1
INCREMENT BY 1;

CREATE TABLE tankemPartie(
	id NUMBER PRIMARY KEY,
	id_niveau NUMBER NOT NULL,
	debut_partie DATE NOT NULL,
	id_gagnant NUMBER,
	FOREIGN KEY (id_niveau) REFERENCES tankemNiveau(id),
	FOREIGN KEY (id_gagnant) REFERENCES tankemUtilisateur(id)
);

CREATE TABLE tankemJoueurImplique(
	id_partie NUMBER NOT NULL,
	id_joueur NUMBER NOT NULL,
	FOREIGN KEY (id_partie) REFERENCES tankemPartie(id),
	FOREIGN KEY (id_joueur) REFERENCES tankemUtilisateur(id)
);

CREATE SEQUENCE seq_caseThermique
START WITH 1
INCREMENT BY 1;

CREATE TABLE tankemCaseThermique(
	id NUMBER PRIMARY KEY,
	id_partie NUMBER NOT NULL,
	valeurx NUMBER NOT NULL,
	valeury NUMBER NOT NULL,
	FOREIGN KEY (id_partie) REFERENCES tankemPartie(id)
);

CREATE TABLE tankemJoueurCase(
	id_joueur NUMBER NOT NULL,
	id_case NUMBER NOT NULL,
	id_partie NUMBER NOT NULL,
	QTE_DMG_DONNE NUMBER,
	QTE_DMG_RECU NUMBER,
	temps_passe NUMBER(4,2),
	FOREIGN KEY (id_partie) REFERENCES tankemPartie(id),
	FOREIGN KEY (id_joueur) REFERENCES tankemUtilisateur(id),
	FOREIGN KEY (id_case) REFERENCES tankemCaseThermique(id)
);


/*MODIFICATION DES TABLES*/

/*MODIFICATION DE LA TABLE tankemArmurieUtilisateur POUR AJOUTER LE CHAMP NBTIR (par weapon)*/
ALTER TABLE tankemArmurieUtilisateur ADD nbtir NUMBER;
UPDATE tankemArmurieUtilisateur SET nbtir=0 WHERE nbtir IS NULL;
ALTER TABLE tankemArmurieUtilisateur MODIFY nbtir  NUMBER NOT NULL;

/*MODIFICATION DE LA TABLE tankemArmurieUtilisateur POUR AJOUTER LE CHAMP qtedmg (par weapon)*/
ALTER TABLE tankemArmurieUtilisateur ADD qtedmg NUMBER;
UPDATE tankemArmurieUtilisateur SET qtedmg=0 WHERE qtedmg IS NULL;
ALTER TABLE tankemArmurieUtilisateur MODIFY qtedmg  NUMBER NOT NULL;

COMMIT;