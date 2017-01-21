 # -*- coding: utf-8 -*-
class DTOStatistiques(object):
	"""docstring for DTOStatistiques"""
	def __init__(self, grosseurX, grosseurY,posDepartX,posDepartY, grosseurCarree):
		self.grosseurY = grosseurY
		self.grosseurX = grosseurX
		self.carte=[[0 for x in range(grosseurY)] for y in range(grosseurX)]
		for i in range(0,grosseurX):
			for j in range(0,grosseurY):
				self.carte[i][j]={0:[0,0,0], 1:[0,0,0]}
		self.posDepartX=posDepartX
		self.posDepartY=posDepartY
		self.grosseurCarree=grosseurCarree

		self.idNiveau = 0
		self.dictArmesUtiliseJ1 = {'Canon': 0, 'Grenade': 0, 'Mitraillette': 0,'Piege': 0, 'Shotgun': 0, 'Guide': 0,'Spring':0};
		self.dictArmesDommageJ1 = {'Canon': 0, 'Grenade': 0, 'Mitraillette': 0,'Piege': 0, 'Shotgun': 0, 'Guide': 0,'Spring':0};
		self.dictArmesUtiliseJ2 = {'Canon': 0, 'Grenade': 0, 'Mitraillette': 0,'Piege': 0, 'Shotgun': 0, 'Guide': 0,'Spring':0};
		self.dictArmesDommageJ2 = {'Canon': 0, 'Grenade': 0, 'Mitraillette': 0,'Piege': 0, 'Shotgun': 0, 'Guide': 0,'Spring':0};

		self.dommagePrisJ1 = 0
		self.dommageFaitJ1 = 0
		self.dommagePrisJ2 = 0
		self.dommageFaitJ2 = 0

		self.idUser1=None
		self.idUser2=None

		self.idGagnant=None 

	def ajouterTemps(self,idJoueur,posX,posY, temps):
		positionX=self.calculPositionX(posX)
		positionY=self.calculPositionY(posY)
		if positionX< self.grosseurX and positionY<self.grosseurY and positionX>=0 and positionY>=0:
			self.carte[positionX][positionY].get(idJoueur)[0]+=temps

	def ajtDommageDonnee(self,idJoueur,posX,posY, dommage):
		positionX=self.calculPositionX(posX)
		positionY=self.calculPositionY(posY)
		self.carte[positionX][positionY].get(idJoueur)[1]+=dommage
		print self.dictArmesUtiliseJ2

	def ajtDommageRecu(self,idJoueur,posX,posY, dommage):
		positionX=self.calculPositionX(posX)
		positionY=self.calculPositionY(posY)
		self.carte[positionX][positionY].get(idJoueur)[2]+=dommage

	
	def calculPositionX(self,posX):
		return (int)(((posX-self.posDepartX)/self.grosseurCarree)-0.5)

	def calculPositionY(self, posY):
		return (int)(((posY-self.posDepartY)/self.grosseurCarree)-0.5)

	def modifierDictArmeUtilise(self,idJoueur,arme):
		if arme!="AucuneArme":
			if idJoueur==0 :
				self.dictArmesUtiliseJ1[arme]+=1
			else:
				self.dictArmesUtiliseJ2[arme]+=1

	def modifierDictArmeDommage(self,idJoueur,arme,dommage):
		if arme!="AucuneArme":
			if idJoueur==0 :
				self.dictArmesDommageJ1[arme]+=dommage
			else:
				self.dictArmesDommageJ2[arme]+=dommage

	def modifierDommageRecu(self,idJoueur,dommage):
		if idJoueur==0:
			self.dommagePrisJ1+= dommage
		else:
			self.dommagePrisJ2+= dommage

	def modifierDommageFait(self,idJoueur,dommage):
		if idJoueur==0:
			self.dommageFaitJ1+= dommage
		else:
			self.dommageFaitJ2+= dommage

	def modifierIdNiveau(self,idNiveau):
		self.idNiveau = idNiveau

	def modifierIdJoueur(self,idJoueur1,idJoueur2):
		self.idUser1=idJoueur1
		self.idUser2=idJoueur2		

	def definitionGagnant(self,idGagnant):
		self.idGagnant=idGagnant

	def getIdNiveau(self):
		return self.idNiveau

	def getIdGagnant(self):
		return self.idGagnant