#-*- coding: utf-8 -*-
class DTOBalance():
	def __init__(self):
		self.dictonnaire={}
		self.listeColonnes=[
		"charsVitese",
		"charsVitesseRotation",
		"charsPointsDeVie",
		"blocsTempsMouvement",
		"canonVitesseBalle",
		"canonTempsRecharge",
		"mitrailletteVitesseBalle",
		"mitrailletteTempsRecharge",
		"grenadeVitesseInitialeBalle",
		"grenadeTempsRecharge",
		"shotgunVitesseBalle",
		"shotgunTempsRecharge",
		"shotgunOuvertureFusil",
		"piegeVitesseBalle",
		"piegeTempsRecharge",
		"missileGuideVitesseBalle",
		"missileGuideTempsRecharge",
		"springVitesseInitialeSaut",
		"springTempsRecharge",
		"explosionBallesGrosseur",
		"messageAccueilDuree",
		"messageCompteRebour",
		"messageAccueilContenu",
		"messageSignalDebutpartie",
		"messageFinPartie"
		]
		self.listeValeursDefauts=[
		7,
		1500,
		200,
		0.8,
		14,
		1.2,
		18,
		0.4,
		16,
		0.8,
		13,
		1.8,
		0.4,
		1,
		0.8,
		30,
		3,
		10,
		0.5,
		0.8,
		3,
		3,
		"Bienvenue a TANKEM!",
		"Debut de partie...",
		"Fin de partie!"
		]
		self.remplirDictValeurDefaut()

	def getColonnes(self):
		return self.listeColonnes

	def getValue(self,key):
		return self.dictonnaire.get(key)

	def setValue(self,key,value):
		self.dictonnaire[key] = value

	def getDict(self):
		return self.dictonnaire

	def remplirDictValeurDefaut(self):
		for i in range(len(self.listeColonnes)):
			self.dictonnaire[self.listeColonnes[i]] = self.listeValeursDefauts[i]


