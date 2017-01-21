

ALTER TABLE tankemNiveau ADD proprietaire NUMBER;
ALTER TABLE tankemNiveau ADD FOREIGN KEY (proprietaire) REFERENCES tankemUtilisateur(id);
/*Changer le mode "equipe"*/
UPDATE tankemNiveau SET status=3 WHERE status=2; 
/*Ajouter un nom aux anciens niveaux*/
UPDATE tankemNiveau SET proprietaire=1 WHERE proprietaire IS NULL;
ALTER TABLE tankemNiveau MODIFY proprietaire  NUMBER NOT NULL;




CREATE TABLE tankemNiveauFavori(
	id_niveau NUMBER NOT NULL,
	id_utilisateur NUMBER NOT NULL,
	FOREIGN KEY (id_niveau) REFERENCES tankemNiveau(id),
	FOREIGN KEY (id_utilisateur) REFERENCES tankemUtilisateur(id)
);
CREATE TABLE tankemNiveauNbFois(
	id_niveau NUMBER NOT NULL,
	id_utilisateur NUMBER NOT NULL,
	nb_fois_joue NUMBER NOT NULL,
	FOREIGN KEY (id_niveau) REFERENCES tankemNiveau(id),
	FOREIGN KEY (id_utilisateur) REFERENCES tankemUtilisateur(id)
);

COMMIT;