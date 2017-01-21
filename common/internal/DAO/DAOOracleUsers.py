# -*- coding: utf-8 -*-
import cx_Oracle
from ..DTO.DTOUser import *
from Connexion import *

class DAOOracleUsers():


	def read(self):	
		monCurseur = Connexion().getCurseur()

		#Creation du DTOMap vide
		leDTO=DTOUser()

		###############################Pour infos tous les Users###############################

		commandeInfosUsers="SELECT * FROM tankemUtilisateur"

#id, username,nom, prenom, ,dp, idq1, rep1, idq2, rep2, idvouleur

		#fetch valeur et met dans une autre liste
		self.executer(monCurseur, commandeInfosUsers)
		tupleInfosTousUsers=monCurseur.fetchall()
		
		unUser=[]
		for tupleUnUser in tupleInfosTousUsers:
			for unInfo in tupleUnUser:
				unUser.append(unInfo)
			leDTO.insertUserDictionnaire(unUser[1],unUser)
			#print unNiveau
			unUser=[]
				
########################################Pour Avoir les questions###############
		commandeQuestions="SELECT * FROM tankemQuestion"

		self.executer(monCurseur, commandeQuestions)
		tupleTousQuestions=monCurseur.fetchall()

		listeInfosUneQuestion=[]
		for tupleUneQuestion in tupleTousQuestions:
			for unInfoQ in tupleUneQuestion:
				listeInfosUneQuestion.append(unInfoQ)
			leDTO.insertUneQuestion(listeInfosUneQuestion[0],listeInfosUneQuestion[1])
			listeInfosUneQuestion=[]


########################################Pour Avoir les armes###############
		commandeArmurie="SELECT * FROM tankemArmurie"

		self.executer(monCurseur, commandeArmurie)
		tupleTousArmes=monCurseur.fetchall()

		listeInfosUnArme=[]
		for tupleUnUnArme in tupleTousArmes:
			for unInfo in tupleUnUnArme:
				listeInfosUnArme.append(unInfo)
			leDTO.insertUnArme(listeInfosUnArme[0],listeInfosUnArme[1])
			listeInfosUnArme=[]

########################################Pour Avoir les armurie de chaque Joueur###############
		commandeArmurieJoueurs="SELECT * FROM tankemArmurieUtilisateur"

		self.executer(monCurseur, commandeArmurieJoueurs)
		tupleTousBonus=monCurseur.fetchall()

		listeInfosUnBonus=[]
		for tupleUnBonus in tupleTousBonus:
			for unInfo in tupleUnBonus:
				listeInfosUnBonus.append(unInfo)
			leDTO.insertUnBonus(listeInfosUnBonus[0],listeInfosUnBonus)
			listeInfosUnBonus=[]
		

		return leDTO


	def updateBonusFinPartie(self,idGagnant,listeDesBonus):
		monCurseur = Connexion().getCurseur()
		
		for idArme in listeDesBonus:
			selectBonus="SELECT nb_stock FROM tankemArmurieUtilisateur WHERE id_utilisateur=:1 AND id_arme=:2"
			self.executerAvecParams(monCurseur, selectBonus,[idGagnant,idArme])
			tupleArmeBonus=monCurseur.fetchall()
			
			nbArme=tupleArmeBonus[0][0]
			nbArme+=1
			updateString ="UPDATE tankemArmurieUtilisateur set nb_stock = :1 where id_utilisateur = :2 AND id_arme=:3"
			self.executerAvecParams(monCurseur,updateString, [nbArme,idGagnant,idArme])

			monCurseur.execute('COMMIT')

	def updateNbFoisNivJouer(self,idUser,idNiveau):
		monCurseur = Connexion().getCurseur()
		selectNbFoisJouer="SELECT nb_fois_joue FROM tankemNiveauNbFois WHERE id_utilisateur=:1 AND id_niveau=:2"
		self.executerAvecParams(monCurseur,selectNbFoisJouer,[idUser,idNiveau])
		tupleNivJouer=monCurseur.fetchall()

		if tupleNivJouer:
			nbFoisJouer=tupleNivJouer[0][0]
			nbFoisJouer+=1
			updateString="UPDATE tankemNiveauNbFois set nb_fois_joue = :1 where id_utilisateur = :2 AND id_niveau=:3"
			self.executerAvecParams(monCurseur,updateString,[nbFoisJouer,idUser,idNiveau])

		else:
			insertString="INSERT INTO tankemNiveauNbFois VALUES(:1,:2,:3)"
			self.executerAvecParams(monCurseur,insertString,[idNiveau,idUser, 1])
		monCurseur.execute('COMMIT')


	def ajoutAuFavori(self,idNiveau,idUser):
		monCurseur = Connexion().getCurseur()
		selectNbFoisJouer="SELECT * FROM tankemNiveauFavori WHERE id_niveau=:1 AND id_utilisateur=:2"
		self.executerAvecParams(monCurseur,selectNbFoisJouer,[idNiveau,idUser])
		tupleNivFavori=monCurseur.fetchall()

		if tupleNivFavori:
			pass
		else:
			insertString="INSERT INTO tankemNiveauFavori VALUES(:1,:2)"
			self.executerAvecParams(monCurseur,insertString,[idNiveau,idUser])
			monCurseur.execute('COMMIT')


	def executer(self,cursor, strCommande):
		try:
			cursor.execute(strCommande)
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


	def updateNbArmes(self,idUser,idArme):
		monCurseur = Connexion().getCurseur()
		commande="UPDATE TANKEMARMURIEUTILISATEUR SET NB_STOCK= NB_STOCK-1 WHERE id_utilisateur=:1 AND id_arme=:2"
		self.executerAvecParams(monCurseur,commande,[idUser,idArme])
		monCurseur.execute('COMMIT')