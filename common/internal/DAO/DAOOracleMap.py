# -*- coding: utf-8 -*-
import cx_Oracle
from ..DTO.DTOMap import *
import re
from Connexion import *

class DAOOracleMap():


	def read(self):	
		monCurseur = Connexion().getCurseur()

		#Creation du DTOMap vide
		leDTO=DTOMap()

		listeIdNiveaux=[]
		###############################Pour infos niveaux###############################

		commandeInfosNiveau="SELECT * FROM tankemNiveau"

		#fetch valeur et met dans une autre liste
		self.executer(monCurseur, commandeInfosNiveau)
		tupleInfosNiveaux=monCurseur.fetchall()
		
		unNiveau=[]
		for infosUnNiveau in tupleInfosNiveaux:
			print infosUnNiveau
			for unInfo in infosUnNiveau:
				unNiveau.append(unInfo)
			leDTO.setValueNiveau(unNiveau[0],unNiveau)
			listeIdNiveaux.append(unNiveau[0])
			#print unNiveau
			unNiveau=[]
				

		########################################Pour infos cases###############
		commandeCasesNiveau="SELECT * FROM tankemCase"

		self.executer(monCurseur, commandeCasesNiveau)
		tupleTousCases=monCurseur.fetchall()

		index=0
		listeCases=[]
		for infosUneCase in tupleTousCases:
			listeCases.append([])
			for infoCase in infosUneCase:
				listeCases[index].append(infoCase)
			index+=1

		for case in listeCases:
			leDTO.setCaseNiveau(case[0],case)
			print str(case[0])+" :"+str(case)



########################################Pour infos cases###############
		commandeTanksNiveau="SELECT * FROM tankemTank"

		self.executer(monCurseur, commandeTanksNiveau)
		tupleTousTanks=monCurseur.fetchall()

		index=0
		listeTousTanks=[]
		for tousInfosUnTank in tupleTousTanks:
			listeTousTanks.append([])
			for unInfoTank in tousInfosUnTank:
				listeTousTanks[index].append(unInfoTank)
			index+=1


		for tank in listeTousTanks:
			leDTO.setTankNiveau(tank[0],tank)


########################################Pour Avoir les niveau favoris de chaque Joueur###############
		commandeNivFavori="SELECT * FROM tankemNiveauFavori"
		self.executer(monCurseur, commandeNivFavori)
		tupleTousFavori=monCurseur.fetchall()

		listeInfosUnFavori=[]
		for tupleUnFavori in tupleTousFavori:
			for infoDuFavori in tupleUnFavori:
				listeInfosUnFavori.append(infoDuFavori)
			leDTO.insertUnFavoriNiveau(listeInfosUnFavori[0],listeInfosUnFavori[1])
			listeInfosUnFavori=[]

########################################Pour Avoir nb de fois que les niv on été jouer par chaque joueur###############
		for idNiveau in listeIdNiveaux:
			commandeNbFoisNiv="SELECT SUM(nb_fois_joue) from tankemniveaunbfois where id_niveau=:1"
			self.executerAvecParams(monCurseur,commandeNbFoisNiv,[idNiveau])
			tupleUnNivNbFois=monCurseur.fetchone()
			nbFoisJoue=tupleUnNivNbFois[0]
			if not nbFoisJoue:
				nbFoisJoue=0
			leDTO.insertNbFoisNivJouer(idNiveau,nbFoisJoue)

		return leDTO


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