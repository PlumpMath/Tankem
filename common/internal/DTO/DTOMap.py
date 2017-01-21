#-*- coding: utf-8 -*
class DTOMap():
	def __init__(self):
		self.dictoInfosNiveau={}
		self.dictoCases={}
		self.dictoTanks={}
		self.dictoNivFavorisJoueurs={}
		self.dictoNbFoisJoueNiveau={}

	def getValueNiveau(self,idniveau):
		return self.dictoInfosNiveau.get(idniveau)
		#un liste avec infos niveau

	def setValueNiveau(self,niveau,listeinfos):
		if niveau not in self.dictoInfosNiveau:
			self.dictoInfosNiveau[niveau]=[]
			for info in listeinfos:
				self.dictoInfosNiveau[niveau].append(info)

	def getDictoNiveau(self):
		return self.dictoInfosNiveau


	def getCasesNiveau(self,idniveau):
		return self.dictoCases.get(idniveau)
		#liste de listes

	def setCaseNiveau(self,niveau,case):
		if niveau not in self.dictoCases:
			self.dictoCases[niveau]=[]
		self.dictoCases[niveau].append(case)

	def getDictoCases(self):
		return self.dictoCases


	def getTanksNiveau(self,idniveau):
		return self.dictoTanks.get(idniveau)

	def setTankNiveau(self,idNiveau,tank):
		if idNiveau not in self.dictoTanks:
			self.dictoTanks[idNiveau]=[]
		self.dictoTanks[idNiveau].append(tank)

	def getDictoTanks(self):
		return self.dictoTanks


# pour les niv favori de chaque joueur
	def insertUnFavoriNiveau(self,idNiveau, idJoueur):
		if idJoueur not in self.dictoNivFavorisJoueurs:
			self.dictoNivFavorisJoueurs[idJoueur]=[]
		self.dictoNivFavorisJoueurs[idJoueur].append(idNiveau)

	def getTousLesNivFavoris(self):
		return self.dictoNivFavorisJoueurs

	def getNivFavoriUnJoueur(self,idJoueur):
		return self.dictoNivFavorisJoueurs[idJoueur]

####nb de fois que niveau favoris par tous les joueurs
	def nbFoisNiveauFavori(self,idNiveau):
		nbFoisFavori=0
		for joueur in self.dictoNivFavorisJoueurs:
			if idNiveau in self.dictoNivFavorisJoueurs[joueur]:
				nbFoisFavori=nbFoisFavori+1
		return nbFoisFavori



#######Fonctions pour   obtenir des infos sur nb fois niv joué
	def insertNbFoisNivJouer(self, idNiveau, nombre):
		self.dictoNbFoisJoueNiveau[idNiveau]=nombre


	def obtenirNbFoisNiveauJoueTotal(self, idNiveau):
		return self.dictoNbFoisJoueNiveau.get(idNiveau)


	def getNiveauxCreerParUnJoueur(self, nomJoueur):
		listeNomNivDuJoueur=[]
		print self.dictoInfosNiveau
		for niveau in self.dictoInfosNiveau:
			if self.dictoInfosNiveau.get(niveau)[8]== nomJoueur:
				listeNomNivDuJoueur.append(self.dictoInfosNiveau.get(niveau)[1])

		return listeNomNivDuJoueur
