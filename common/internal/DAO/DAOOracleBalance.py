
# -*- coding: utf-8 -*-
import cx_Oracle
from ..DTO.DTOBalance import *
from Connexion import *
import re
#from DTOBalance import DTOBalance

class DAOOracleBalance():


	def read(self):		
		monCurseur = Connexion().getCurseur()
		commandeParam="SELECT * FROM tankemBalance"
			
		#Creation du DTOBalance avec dict vide
		leDTO=DTOBalance()

			#fetch nom des colonnes et met dans une liste
		lesNomsColonnes=leDTO.getColonnes()

			#fetch valeur et met dans une autre liste
		self.executer(monCurseur, commandeParam)
		lesValeursParam=monCurseur.fetchall()
			
		listeValeursParams=[]
		for tupleDonnee in lesValeursParam:
			for valeur in tupleDonnee:
				listeValeursParams.append(valeur)

			#met liste des nomColonne dans key du DTO et et valeur dans value du DTO
		for i in range(len(lesNomsColonnes)):
			leDTO.setValue(lesNomsColonnes[i],listeValeursParams[i])
						
			
		return leDTO



	def update(self,unDTO):

		strTotal=""
		monCurseur =Connexion().getCurseur()
		for key in unDTO.getDict():
			if re.match("^\d+?\.\d+?$", unDTO.getValue(key)) or (unDTO.getValue(key)).isdigit(): #si cest un chiffre
				strTotal=strTotal+key+"="+unDTO.getValue(key)+' ,'
			else:
				strTotal=strTotal+key+"= '"+unDTO.getValue(key)+"' ,"#si cest une phrase 

		strTotal=strTotal[:-1]

		laCommande = "UPDATE tankemBalance SET "+strTotal

		self.executerUpdate(monCurseur, laCommande)
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

	def executerUpdate(self,cursor, strCommande):
		try:
			cursor.execute(strCommande)
			print u"Données insérées dans DB avec succès"

		except cx_Oracle.DatabaseError as e:
			error, =e.args
			print error.code #code de l'erreur si veux chercher sur google
			print error.message #description
			print u"Échec de l'insertion des données dans la DB"




		