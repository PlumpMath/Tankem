#-*- coding: utf-8 -*-
import csv
from Tkinter import Tk
from tkFileDialog import *
from ..DTO.DTOBalance import *
import os
import subprocess
#from DTOBalance import DTOBalance

class DAOCSVBalance():
	def __init__(self):
		print"creation du CSV***************"

	def create(self,unDTO):
		#nomFichier=choisirFichierCSV()
		leDictDTO = unDTO.getDict()
		listeCle = []
		listeValeur =[]      
		for item in leDictDTO:
			listeCle.append(item)
			listeValeur.append(leDictDTO[item])

		
		fichier=self.creerFichierCSV()
		#Bonne version d'ecriture dans un fichier csv à partir d'un dictionnaire
		confirmer = False
		try:
			with open(fichier,'wb') as f:
				writer = csv.writer(f,  delimiter=";",quotechar='"')
				writer.writerow(["Table de la balance (modifiable)"]) #Mettre elements dans [] si on veut que ca reste dans la meme colonne
				writer.writerow(listeCle) 
				writer.writerow(listeValeur)
				print u"Données transférées dans le fichier avec succès"
				confirmer = True
		except:
			print u"Échec du transfert des données de la DB dans fichier"

		#Ouverture du fichier
		if confirmer == True:
			subprocess.call(fichier, shell=True)


	def read(self):
		fichier=self.choisirFichierCSV()
		with open(fichier,"r") as fichier:
			print("Ouverture du fichier")
			unDTO=DTOBalance()
			reader=csv.reader(fichier,delimiter=";",quotechar='"')
			listeToute=[]
			for row in reader:
				listeToute.append(row)
				print row

			#print len(listeToute[1])
			for i in range (len(listeToute[1])):
				unDTO.setValue(listeToute[1][i],listeToute[2][i]) #Car listeToute[0] est le titre de la table
			return unDTO


	def choisirFichierCSV(self):
	#Empêche une fenêtre Tkinter d'ouvrir
		Tk().withdraw()

		nomFichier = askopenfilename(filetypes=[("CSV Files","*.csv"),("All files","*")], defaultextension="*")
		return nomFichier

	def creerFichierCSV(self):
		#Empêche une fenêtre Tkinter d'ouvrir
			Tk().withdraw()
			desktop = os.path.expanduser("~/Desktop")
			nomFichier = asksaveasfilename(initialdir=desktop,initialfile="BalanceTankem.csv",filetypes=[("CSV Files","*.csv"),("All files","*")], defaultextension="*")
			return nomFichier





