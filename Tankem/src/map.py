## -*- coding: utf-8 -*-
from util import *
from entity import *

from direct.showbase import DirectObject
from panda3d.core import *
from panda3d.bullet import BulletPlaneShape
from panda3d.bullet import BulletRigidBodyNode
from panda3d.bullet import BulletBoxShape
from panda3d.bullet import YUp
from direct.interval.IntervalGlobal import *

from internal.DAO.DAOStatistiques import *
import random

#Module qui sert à la création des maps
class Map(DirectObject.DirectObject):
	def __init__(self,parent, mondePhysique, idNiveau,unDTOMap, unDTOUsers,username1,username2,listeArmes):
		###########################AJOUTER ######################################
		self.listeArmes= listeArmes
		self.gameLogic = parent #Parent c'est le gamelogic
		self.blocsTempsMouvement=self.gameLogic.unDTO.getValue("blocsTempsMouvement")
		###########################AJOUTER POUR LA PHASE 4#######################
		self.dictArmes = {'Canon': 0, 'Grenade': 0, 'Mitraillette': 0,'Piege': 0, 'Shotgun': 0, 'Guide': 0,'Spring':0};


		#On garde le monde physique en référence
		self.mondePhysique = mondePhysique
		self.temps=0.0

		self.leDTOUsers=unDTOUsers
		self.user1=username1
		self.user2=username2

		
		#recoit le DTOMap et id du niveau du gamelogic qu'on va utiliser pour construire la carte
		if unDTOMap is not None:
			self.lesInfosDuNiveau=unDTOMap.getValueNiveau(idNiveau)
			self.lesCasesDuNivAFaire=unDTOMap.getCasesNiveau(idNiveau)
			self.lesTanksDuNiveau=unDTOMap.getTanksNiveau(idNiveau)

		#initialisation des constantes utiles
		if idNiveau==000:
			self.map_nb_tuile_x = 10
			self.map_nb_tuile_y = 10
		else:
			self.map_nb_tuile_x = self.lesInfosDuNiveau[6]
			self.map_nb_tuile_y = self.lesInfosDuNiveau[7]

		self.map_grosseur_carre = 2.0 #dimension d'un carré

		
		#On veut que le monde soit centré. On calcul donc le décalage nécessaire des tuiles
		self.position_depart_x = - self.map_grosseur_carre * self.map_nb_tuile_x / 2.0
		self.position_depart_y = - self.map_grosseur_carre * self.map_nb_tuile_y / 2.0

		self.DTOStat=DTOStatistiques(self.map_nb_tuile_x,self.map_nb_tuile_y,self.position_depart_x,self.position_depart_y,self.map_grosseur_carre)
		self.DTOStat.modifierIdNiveau(idNiveau)
		self.DTOStat.modifierIdJoueur(self.leDTOUsers.getIdUser(self.user1),self.leDTOUsers.getIdUser(self.user2))
		#On déclare des listes pour les tanks, les items et les balles
		self.listTank = []
		self.listeItem = []
		self.listeBalle = []

		#Dictionnaire qui contient des noeuds animés.
		#On pourra attacher les objets de notre choix à animer
		self.dictNoeudAnimation = {}
		self.creerNoeudAnimationImmobile() #Pour être consistant, on créé une animation... qui ne bouge pas
		self.creerNoeudAnimationVerticale() #Animation des blocs qui bougent verticalement
		self.creerNoeudAnimationVerticaleInverse() #Idem, mais décalé

		#Initialise le contenu vide la carte
		#On y mettra les id selon ce qu'on met
		self.endroitDisponible = [[True for x in range(self.map_nb_tuile_y)] for x in range(self.map_nb_tuile_x)]

		#Message qui permettent la création d'objets pendant la partie
		self.accept("tirerCanon",self.tirerCanon)
		self.accept("tirerMitraillette",self.tirerMitraillette)
		self.accept("lancerGrenade",self.lancerGrenade)
		self.accept("lancerGuide",self.lancerGuide)
		self.accept("deposerPiege",self.deposerPiege)
		self.accept("tirerShotgun",self.tirerShotgun)

	def bloquerEndroitGrille(self,i,j,doitBloquer):
		self.endroitDisponible[i][j] = doitBloquer

	def figeObjetImmobile(self):
		self.noeudOptimisation.flattenStrong()

	def construireMapHasard(self):
		#Utilisation du module de création au hasard
		maze = mazeUtil.MazeBuilder(self.map_nb_tuile_x, self.map_nb_tuile_y)
		maze.build()
		mazeArray = maze.refine(.75)

		#Interprétation du résultat de l'algo
		for row in mazeArray:
			for cell in row:
				if(cell.type == 1):
					typeMur = random.randint(0, 5)
					#On créé des murs!
					#60% du temps un mur immobile
					#20% du temps un mur mobile
					#20% du temps un mur mobile inverse
					if(typeMur <= 1):
						self.creerMur(cell.row, cell.col, "AnimationMurVerticale" if typeMur == 1 else "AnimationMurVerticaleInverse")
					else:
						self.creerMur(cell.row, cell.col,"AnimationMurImmobile")

		rgbJ1=[self.leDTOUsers.getRougeUser(self.user1),self.leDTOUsers.getVertUser(self.user1),self.leDTOUsers.getBleuUser(self.user1)]
		rgbJ2=[self.leDTOUsers.getRougeUser(self.user2),self.leDTOUsers.getVertUser(self.user2),self.leDTOUsers.getBleuUser(self.user2)]
		self.creerChar(6,6,0,Vec3(rgbJ1[0],rgbJ1[1],rgbJ1[2])) #Char noir
		self.creerChar(3,3,1,Vec3(rgbJ2[0],rgbJ2[1],rgbJ2[2])) #Char gris-jaune

		#Dans la carte par défaut, des items vont appraître constamment entre 10 et 20 secondes d'interval
		self.genererItemParInterval(10,20)

	#Une map par défaut quelconque
	def creerCarteParDefaut(self):
		if self.lesCasesDuNivAFaire is not None:
			for case in self.lesCasesDuNivAFaire: #0:id 1:x 2:y 3:type
				if case[3]>1: 
					self.creerMur(case[1],case[2],"AnimationMurVerticale" if case[3]==2 else "AnimationMurVerticaleInverse") 
				else:
					self.creerMur(case[1],case[2],"AnimationMurImmobile")

		#Va avoir un item dès le départ
		self.creerItemPositionHasard()

		rgbJ1=[self.leDTOUsers.getRougeUser(self.user1),self.leDTOUsers.getVertUser(self.user1),self.leDTOUsers.getBleuUser(self.user1)]
		rgbJ2=[self.leDTOUsers.getRougeUser(self.user2),self.leDTOUsers.getVertUser(self.user2),self.leDTOUsers.getBleuUser(self.user2)]
		
		#for tank in self.lesTanksDuNiveau:  ############# tank[0][0] idNiv,  [0][1]: X ,[0][2]: Y

		self.creerChar(self.lesTanksDuNiveau[0][2],self.lesTanksDuNiveau[0][1],0,Vec3(rgbJ1[0],rgbJ1[1],rgbJ1[2]))
		self.creerChar(self.lesTanksDuNiveau[1][2],self.lesTanksDuNiveau[1][1],1,Vec3(rgbJ2[0],rgbJ2[1],rgbJ2[2]))


		print "oekrpoewjrepojfewoinfesubfseonfeoinfeosinfesion###################"
		print"joueur1:"
		print self.listeArmes[0][0]
		print self.listeArmes[0][1]
		print"joueur2:"
		print self.listeArmes[1][0]
		print self.listeArmes[1][1]

		self.listTank[0].armePrimaire=self.listeArmes[0][0]
		self.listTank[0].armeSecondaire=self.listeArmes[0][1]
		self.listTank[1].armePrimaire=self.listeArmes[1][0]
		self.listTank[1].armeSecondaire=self.listeArmes[1][1]


		#Dans la carte par défaut, des items vont appraître constamment
		self.genererItemParInterval(self.lesInfosDuNiveau[2],self.lesInfosDuNiveau[3])

	def construireDecor(self, camera):
		modele = loader.loadModel("../asset/Skybox/skybox")
		modele.set_bin("background", 0);
		modele.set_two_sided(True);
		modele.set_depth_write(False);
		modele.set_compass();
		verticalRandomAngle = random.randint(0,45)
		modele.setHpr(0,verticalRandomAngle,-90)
		randomGrayScale = random.uniform(0.6,1.2)
		semiRandomColor = Vec4(randomGrayScale,randomGrayScale,randomGrayScale,1)
		modele.setColorScale(semiRandomColor)
		modele.setPos(0,0,0.5)
		#Et oui! Le ciel est parenté à la caméra!
		modele.reparentTo(camera)

	def construirePlancher(self):
		#Optimisation... on attache les objets statiques au même noeud et on utiliser
		#la méthode flattenStrong() pour améliorer les performances.
		self.noeudOptimisation = NodePath('NoeudOptimisation')
		self.noeudOptimisation.reparentTo(render)

		#Construction du plancher
		# On charge les tuiles qui vont former le plancher
		for i in range(0,self.map_nb_tuile_x):
			for j in range(0,self.map_nb_tuile_y):
				modele = loader.loadModel("../asset/Floor/Floor")
				# Reparentage du modèle à la racine de la scène
				modele.reparentTo(self.noeudOptimisation)
				self.placerSurGrille(modele,i, j)

		#Construction du plancher si on tombe
		#Un plan devrait marche mais j'ai un bug de collision en continu...
		shape = BulletBoxShape(Vec3(50,50,5))
		node = BulletRigidBodyNode('Frontfiere sol')
		node.addShape(shape)
		np = render.attachNewNode(node)
		np.setTag("EntiteTankem","LimiteJeu")
		np.setPos(Vec3(0,0,-9.0))
		self.mondePhysique.attachRigidBody(node)

		#Construction de l'aire de jeu sur laquelle on joue
		shape = BulletBoxShape(Vec3(-self.position_depart_x, -self.position_depart_y, 2))
		node = BulletRigidBodyNode('Plancher')
		node.addShape(shape)
		np = render.attachNewNode(node)
		np.setTag("EntiteTankem","Plancher")
		HACK_VALUE = 0.02 #Optimisation de collision, les masques ne marchent pas
		np.setZ(-2.00 - HACK_VALUE)
		self.mondePhysique.attachRigidBody(node)

#Ici pour trouver la case y = ax+b 
#float = dep+ (int + 0.5)*grosseur du carré
#int (0.5+(float-DEP)/grosseur) = int
	def placerSurGrille(self,noeud,positionX, positionY):
		# On place l'objet en calculant sa position sur la grille
		noeud.setX(self.position_depart_x + (positionX+0.5) * self.map_grosseur_carre)
		noeud.setY(self.position_depart_y + (positionY+0.5) * self.map_grosseur_carre)

	def tirerCanon(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic, identifiantLanceur,self.mondePhysique)
		self.listeBalle.append(someBalle)
		someBalle.projetter(position,direction)

	def tirerMitraillette(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic, identifiantLanceur,self.mondePhysique)
		self.listeBalle.append(someBalle)
		someBalle.projetterRapide(position,direction)

	def lancerGrenade(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic, identifiantLanceur, self.mondePhysique)
		self.listeBalle.append(someBalle)
		someBalle.lancer(position,direction)

	def lancerGuide(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic, identifiantLanceur, self.mondePhysique)
		self.listeBalle.append(someBalle)

		#On définit la position d'arrivé de missile guidé
		noeudDestination = self.listTank[0].noeudPhysique
		if(identifiantLanceur == 0):
			noeudDestination = self.listTank[1].noeudPhysique

		someBalle.lancerGuide(position,noeudDestination)

	def deposerPiege(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic,identifiantLanceur, self.mondePhysique)
		self.listeBalle.append(someBalle)
		someBalle.deposer(position,direction)

	def tirerShotgun(self, identifiantLanceur, position, direction):
		#Création d'une balle de physique
		someBalle = balle.Balle(self.gameLogic,identifiantLanceur,self.mondePhysique)
		self.listeBalle.append(someBalle)
		someBalle.projetterVariable(position,direction)

	#####################################################
	#Création des différentes entités sur la carte
	#####################################################

	def creerItem(self, positionX, positionY, armeId):
		#L'index dans le tableau d'item coincide avec son
		#itemId. Ça va éviter une recherche inutile pendant l'éxécution
		itemCourrant = item.Item(armeId,self.mondePhysique)
		self.listeItem.append(itemCourrant)
		#On place le tank sur la grille
		self.placerSurGrille(itemCourrant.noeudPhysique,positionX,positionY)

	def creerItemHasard(self, positionX, positionY):
		listeItem = ["Mitraillette", "Shotgun", "Piege", "Grenade", "Guide","Spring"]
		itemHasard = random.choice(listeItem)
		self.creerItem(positionX, positionY,itemHasard)

	def creerItemPositionHasard(self):
		#Pas de do while en Python! Beurk...
		randomX = random.randrange(0,self.map_nb_tuile_x-1)
		randomY = random.randrange(0,self.map_nb_tuile_y-1)

		#Tant qu'on trouve pas d'endroit disponibles...
		while(not self.endroitDisponible[randomX][randomY]):
			randomX = random.randrange(0,self.map_nb_tuile_x-1)
			randomY = random.randrange(0,self.map_nb_tuile_y-1)

		#Quand c'est fait on met un item au hasard
		self.creerItemHasard(randomX,randomY)

	def genererItemParInterval(self, delaiMinimum, delaiMaximum):
		#Délai au hasard entre les bornes
		delai = random.uniform(delaiMinimum, delaiMaximum)
		intervalDelai = Wait(delai)
		intervalCreerItem = Func(self.creerItemPositionHasard)
		intervalRecommence = Func(self.genererItemParInterval,delaiMinimum,delaiMaximum)

		sequenceCreation = Sequence(intervalDelai,
									intervalCreerItem,
									intervalRecommence,
									name="Creation item automatique")
		#On le joue une fois et il se rappelera lui-même :-)
		sequenceCreation.start()

	def creerMur(self,positionX, positionY, strAnimation = None):
		mur = Wall(self.mondePhysique)
		#On place le bloc sur la grille
		self.placerSurGrille(mur.noeudPhysique,positionX,positionY)
		self.bloquerEndroitGrille(positionX,positionY,True)

		if(strAnimation):
			mur.animate(self.dictNoeudAnimation[strAnimation])

	def creerNoeudAnimationImmobile(self):
		noeudAnimationCourrant = NodePath("AnimationMurImmobile")
		self.dictNoeudAnimation["AnimationMurImmobile"] = noeudAnimationCourrant
		noeudAnimationCourrant.reparentTo(render)

	def creerNoeudAnimationVerticale(self):
		#Création d'un noeud vide
		noeudAnimationCourrant = NodePath("AnimationMurVerticale")
		tempsMouvement = self.blocsTempsMouvement
		blocPosInterval1 = LerpPosInterval( noeudAnimationCourrant,
											tempsMouvement,
											Vec3(0,0,-1.95),
											startPos=Vec3(0,0,0))
		blocPosInterval2 = LerpPosInterval( noeudAnimationCourrant,
											tempsMouvement,
											Vec3(0,0,0),
											startPos=Vec3(0,0,-1.95))
		delai = Wait(1.2)
		# On créé une séquence pour bouger le bloc
		mouvementBloc = Sequence()
		mouvementBloc = Sequence(   blocPosInterval1,
									delai,
									blocPosInterval2,
									delai,
									name="mouvement-bloc")

		mouvementBloc.loop()

		noeudAnimationCourrant.reparentTo(render)
		#Ajout dans le dicitonnaire de l'animation
		self.dictNoeudAnimation["AnimationMurVerticale"] = noeudAnimationCourrant

	def creerNoeudAnimationVerticaleInverse(self):
		#Création d'un noeud vide
		noeudAnimationCourrant = NodePath("AnimationMurVerticaleInverse")
		tempsMouvement = self.blocsTempsMouvement
		blocPosInterval1 = LerpPosInterval( noeudAnimationCourrant,
											tempsMouvement,
											Vec3(0,0,-1.95),
											startPos=Vec3(0,0,0))
		blocPosInterval2 = LerpPosInterval( noeudAnimationCourrant,
											tempsMouvement,
											Vec3(0,0,0),
											startPos=Vec3(0,0,-1.95))
		delai = Wait(1.2)
		# On créé une séquence pour bouger le bloc
		mouvementBloc = Sequence()
		mouvementBloc = Sequence(   blocPosInterval2,
									delai,
									blocPosInterval1,
									delai,
									name="mouvement-bloc-inverse")
		mouvementBloc.loop()

		noeudAnimationCourrant.reparentTo(render)
		#Ajout dans le dicitonnaire de l'animation
		self.dictNoeudAnimation["AnimationMurVerticaleInverse"] = noeudAnimationCourrant


	def creerChar(self,positionX, positionY, identifiant, couleur):
		someTank = tank.Tank(self.gameLogic,identifiant,couleur,self.mondePhysique,self.DTOStat)
		#On place le tank sur la grille
		self.placerSurGrille(someTank.noeudPhysique,positionX,positionY)

		#Ajouter le char dans la liste
		self.listTank.append(someTank)

#ICI POUR RECUEILLIR INFORMATION SUR L'ARME ET LE DOMMAGE
#indiceTank est celui qui se fait mal, donc l'autre et celui qui attaque
	def traiterCollision(self,node0, node1):
		#Pas très propre mais enfin...
		indiceTank = int(self.traiterCollisionTankAvecObjet(node0, node1,"Balle"))
		if(indiceTank != -1):
			tireurBalleId = int(self.trouverTag(node0, node1, "lanceurId"))
			balleId = int(self.trouverTag(node0, node1, "balleId"))
			#Prend 1 de dommage par défaut si la balle n'a pas été tirée par le tank
			self.listeBalle[balleId].exploser()
			if(tireurBalleId != indiceTank):
				self.listTank[indiceTank].prendDommage(1,self.mondePhysique)
			return
		
		indiceTank = int(self.traiterCollisionTankAvecObjet(node0, node1,"Item"))
		if(indiceTank != -1):
			itemID = int(self.trouverTag(node0, node1, "itemId"))
			if(itemID != -1):
				#Avertit l'item et le tank de la récupération
				itemCourrant = self.listeItem[itemID]
				itemCourrant.recupere()
				self.listTank[indiceTank].recupereItem(itemCourrant.armeId)
				return

		indiceTank = int(self.traiterCollisionTankAvecObjet(node0, node1,"LimiteJeu"))
		if(indiceTank != -1):
			#Un tank est tombé. mouhahahadddddddddd
			self.listTank[indiceTank].tombe(self.mondePhysique)
			return


	#Méthode qui va retourner -1 si aucune collision avec un tank
	#Ou encore l'index du tank touché si applicable
	def traiterCollisionTankAvecObjet(self,node0,node1,testEntite):
		tag0 = node0.getTag("EntiteTankem")
		tag1 = node1.getTag("EntiteTankem")
		retour = -1
		if(tag0 == "Tank" and tag1 == testEntite):
		   retour = node0.getTag("IdTank")

		if(tag0 == testEntite and tag1 == "Tank"):
			retour = node1.getTag("IdTank")
		return retour

	#Trouve si un des 2 noeuds a le tag indiqué
	def trouverTag(self,node0, node1, tag):
		retour = ""
		#On trouve l'ID de l'item qui a collisionné
		if(node0.getTag(tag) != ""):
			retour = node0.getTag(tag)

		if(node1.getTag(tag) != ""):
			retour = node1.getTag(tag)

		return retour

	#On met à jour ce qui est nécessaire de mettre à jour
	def update(self,tempsTot):
			for tank in self.listTank:
				difftemps=tempsTot-self.temps
				
				posXCase=tank.noeudPhysique.getX()
				posYCase=tank.noeudPhysique.getY()

				self.DTOStat.ajouterTemps(tank.identifiant,posXCase,posYCase,difftemps)
				tank.traiteMouvement()
			self.temps=tempsTot

#METHODES AJOUTER POUR GENERER LE MAP CHOISI PAR LE USER:
	def genererMapChoisi(self, id, DTOMap):
		nbTuileX= DTOMap.getValueNiveau(id)[6]
		nbTuileY= DTOMap.getValueNiveau(id)[7]
		nomMap = DTOMap.getValueNiveau(id)[1]
		tempsMin = DTOMap.getValueNiveau(id)[2]
		tempsMax = DTOMap.getValueNiveau(id)[3]
		tempsStatus = DTOMap.getValueNiveau(id)[5]

		


		#Faut avoir un borg avec un try-connect

