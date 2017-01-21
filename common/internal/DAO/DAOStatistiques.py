 # -*- coding: utf-8 -*-
import cx_Oracle
from Connexion import *
from ..DTO.DTOStatistiques import *

class DAOStatistiques():
	def __init__(self,DTOStatisques):
		self.DTOStats=DTOStatisques
		self.idPartie=self.obtenirIdPartie()


	def ecrireInfosDebutPartie(self):
		self.ecrireDefinitionPartie()
		self.joueursImplique()
		


	def ecrireStatsFinPartie(self):
		self.insertionCaseThermique()
		self.insertionUtilisationArmes()
		self.definirGagnant()



	def ecrireDefinitionPartie(self):
		monCurseur = Connexion().getCurseur()
		idNiveau=self.DTOStats.getIdNiveau()
		commandeInfoPartie="INSERT INTO TANKEMPARTIE VALUES(:1,:2,SYSDATE,NULL)"
		#fetch valeur et met dans une autre liste
		self.executerAvecParams(monCurseur, commandeInfoPartie,[self.idPartie,idNiveau])
		monCurseur.execute('COMMIT')
		

	def joueursImplique(self):
		monCurseur = Connexion().getCurseur()
		user1=self.DTOStats.idUser1
		user2=self.DTOStats.idUser2
		commandeJoueur="INSERT INTO TANKEMJOUEURIMPLIQUE VALUES(:1,:2)"
		array=[(self.idPartie,user1),(self.idPartie,user2)]

		self.executerPlusieurs(monCurseur, commandeJoueur,array)

		monCurseur.execute('COMMIT')


	def insertionCaseThermique(self):
		monCurseur = Connexion().getCurseur()
		grosseurX=self.DTOStats.grosseurX
		grosseurY=self.DTOStats.grosseurY
		commandeCaseThermique="INSERT INTO TANKEMCASETHERMIQUE VALUES(:1,:2,:3,:4)"

		array=[]

		for i in range(grosseurX):
			for j in range (grosseurY):
				idCase=self.obtenirIdCaseThermique()
				array.append((idCase,self.idPartie,i,j))
				
		self.executerPlusieurs(monCurseur,commandeCaseThermique,array)
		self.insertionDonneesJoueursPartie(array)
		monCurseur.execute('COMMIT')

	def insertionDonneesJoueursPartie(self,infosCase):
		monCurseur = Connexion().getCurseur()
		grosseurX=self.DTOStats.grosseurX
		grosseurY=self.DTOStats.grosseurY
		
		user1=self.DTOStats.idUser1
		user2=self.DTOStats.idUser2

		carte=self.DTOStats.carte
		
		lesinfosCase=infosCase

		commandeInfoJoueurCase="INSERT INTO TANKEMJOUEURCASE VALUES(:1,:2,:3,:4,:5,:6)"
		array=[]
		k=0
		for i in range(grosseurX):
			for j in range (grosseurY):
				idCase=lesinfosCase[k][0]
				dommagedonneeJ1=carte[i][j].get(0)[1]
				dommagedonneeJ2=carte[i][j].get(1)[1]

				dommagerecueJ1=carte[i][j].get(0)[2]
				dommagerecueJ2=carte[i][j].get(1)[2]
				
				tempsJ1=carte[i][j].get(0)[0]
				tempsJ2=carte[i][j].get(1)[0]
				array.append((user1,idCase,self.idPartie,dommagedonneeJ1,dommagerecueJ1,tempsJ1))
				array.append((user2,idCase,self.idPartie,dommagedonneeJ2, dommagerecueJ2,tempsJ2))
				k+=1

		self.executerPlusieurs(monCurseur,commandeInfoJoueurCase,array)
		monCurseur.execute('COMMIT')

	def insertionUtilisationArmes(self):
		monCurseur = Connexion().getCurseur()
		commandeArmeUtilisation="UPDATE TANKEMARMURIEUTILISATEUR SET NBTIR=:1, QTEDMG=:2 WHERE ID_UTILISATEUR=:3 AND ID_ARME=:4"
		commandeGetIdArme="SELECT ID FROM TANKEMARMURIE WHERE NOM_ARME=:1"
		
		commandeNbTir="SELECT NBTIR, QTEDMG FROM TANKEMARMURIEUTILISATEUR WHERE ID_UTILISATEUR=:1 AND ID_ARME=:2"
		dictArmeUseJ1=self.DTOStats.dictArmesUtiliseJ1
		dictArmeDmgJ1=self.DTOStats.dictArmesDommageJ1
		dictArmeUseJ2=self.DTOStats.dictArmesUtiliseJ2
		dictArmeDmgJ2=self.DTOStats.dictArmesDommageJ2

		user1=self.DTOStats.idUser1
		user2=self.DTOStats.idUser2

		array=[]
		for key in self.DTOStats.dictArmesUtiliseJ1:
			self.executerAvecParams(monCurseur,commandeGetIdArme,[key])
			tupleidArme=monCurseur.fetchall()
			idArme=tupleidArme[0][0]
			self.executerAvecParams(monCurseur,commandeNbTir,[user1,idArme])
			tupleAncienJ1=monCurseur.fetchall()
			


			print tupleAncienJ1[0][0]
			nbTirJ1=tupleAncienJ1[0][0]+dictArmeUseJ1.get(key)
			dmgTotJ1=tupleAncienJ1[0][1]+dictArmeDmgJ1.get(key)

			self.executerAvecParams(monCurseur,commandeNbTir,[user2,idArme])
			tupleAncienJ2=monCurseur.fetchall()
			
			nbTirJ2=tupleAncienJ2[0][0]+dictArmeUseJ2.get(key)
			dmgTotJ2=tupleAncienJ2[0][1]+dictArmeDmgJ2.get(key)
			
			array.append((nbTirJ1,dmgTotJ1,user1,idArme))
			array.append((nbTirJ2,dmgTotJ2,user2,idArme))
			
		self.executerPlusieurs(monCurseur,commandeArmeUtilisation,array)
		monCurseur.execute('COMMIT')

	def definirGagnant(self):
		monCurseur = Connexion().getCurseur()
		idGagnant=self.DTOStats.getIdGagnant()
		commandeGagnant="UPDATE TANKEMPARTIE SET ID_GAGNANT=:1 WHERE ID=:2"
		self.executerAvecParams(monCurseur,commandeGagnant,[idGagnant,self.idPartie])
		monCurseur.execute('COMMIT')



	def obtenirIdPartie(self):
		monCurseur = Connexion().getCurseur()
		commande="SELECT SEQ_PARTIE.nextval FROM DUAL"
		self.executer(monCurseur, commande)
		tupleIdPartie=monCurseur.fetchall()
		return tupleIdPartie[0][0]

	def obtenirIdCaseThermique(self):
		monCurseur = Connexion().getCurseur()
		commande="SELECT SEQ_CASETHERMIQUE.nextval FROM DUAL"
		self.executer(monCurseur, commande)
		tupleIdCase=monCurseur.fetchall()
		return tupleIdCase[0][0]

	def executer(self,cursor, strCommande):
		try:
			cursor.execute(strCommande)
		except cx_Oracle.DatabaseError as e:
			error, =e.args
			print u"Échec de lecture dans la base de données. "
			print error.code #code de l'erreur si veux chercher sur google
			print error.message #description
			#print error.context ##quel ligne/caracter


	def executerPlusieurs(self,cursor, strCommande,array):
		try:
			cursor.executemany(strCommande,array)
		except cx_Oracle.DatabaseError as e:
			error, =e.args
			print u"Échec de lecture dans la base de données. "
			print error.code #code de l'erreur si veux chercher sur google
			print error.message #description
			#print error.context ##quel ligne/caracter

	def executerAvecParams(self,cursor, strCommande, infos):
		try:
			cursor.execute(strCommande,(infos))
		except cx_Oracle.DatabaseError as e:
			error, =e.args
			print u"Échec!!!!!!!!!!!! "
			print error.code #code de l'erreur si veux chercher sur google
			print error.message #description
			#print error.context ##quel ligne/caracter