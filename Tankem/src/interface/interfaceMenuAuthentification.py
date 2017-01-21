# -*- coding: utf-8 -*-
from direct.showbase.ShowBase import ShowBase
from direct.gui.OnscreenText import OnscreenText 
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.interval.LerpInterval import *
from direct.interval.IntervalGlobal import *
from direct.showbase.Transitions import Transitions
from direct.showbase.ShowBase import ShowBase
from direct.actor.Actor import Actor #modele1 animé
import threading
from threading import Timer
import sys
import bcrypt
import random


sys.path.insert(0, '../../../common')
from internal.DAO.DAOOracleMap import * 


class InterfaceMenuAuthentification(ShowBase):
	def __init__(self,unDTOUsers):

		self.accept("AllerAuthentification",self.ouvrirMenu)

		self.unDTOUsers=unDTOUsers
		self.baseSort = base.cam.node().getDisplayRegion(0).getSort()
		base.cam.node().getDisplayRegion(0).setSort(20)
		#Strings contenant des textes
		self.message1 = "Attente de la connexion du joueur 1."
		self.message2 = "Attente de la connexion du joueur 2."
		self.username1=""
		self.username2=""
		self.modele1=None
		self.modele2=None
		self.arreterDecoration=False
		
		#Liste contenant les direct widgets pour facilité le hide
		self.listeCheckBox1=[]
		self.listeCheckBoxSec1=[]
		self.listeNomArmes=[]
		self.listeNbArmes=[]
		self.listeTotalNbArmes1=[0,0,0,0,0,0]
		self.choixPrim1=[1]
		self.choixSec1=[0]
		self.isReader1 = False
		self.listeCheckBox2=[]
		self.listeCheckBoxSec2=[]
		self.listeNomArmes2=[]
		self.listeNbArmes2=[]
		self.listeTotalNbArmes2=[0,0,0,0,0,0]
		self.choixPrim2=[1]
		self.choixSec2=[0]
		self.isReader2 = False
		self.labelJoueur1=[]
		self.labelJoueur2=[]

		self.unDTOMap=self.setupMap()

	def ouvrirMenu(self):

		btnScale = (0.18,0.18)
		text_scale = 0.12
		borderW = (0.04, 0.04)
		couleurBack = (1,1,1,1)
		separation = 0.5
		hauteur = -0.6

		#Label pour les login
		self.textJoueur1 = OnscreenText(text = "Joueur1", pos = (-1.3,.7), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)	
		self.textJoueur2 = OnscreenText(text = "Joueur2", pos = (0.8,0.7), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)	
		self.textMdp1 = OnscreenText(text = "Password1", pos = (-1.3,.5), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)	
		self.textMdp2 = OnscreenText(text = "Password2", pos = (0.8,0.5), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)

		#Ajouter les champs
		self.champNom1 = DirectEntry(text = "" ,scale=.05, numLines = 2,focus=1, pos=(-1,0,0.75), initialText="Joueur1")
		self.champMdp1 = DirectEntry(text = "" ,scale=.05, numLines = 2,focus=1, pos=(-1,0,0.55), initialText="AAAaaa111", obscured=1)
		self.champNom2 = DirectEntry(text = "" ,scale=.05, numLines = 2,focus=1, pos=(1,0,0.75), initialText="Joueur2")
		self.champMdp2 = DirectEntry(text = "" ,scale=.05, numLines = 2,focus=1, pos=(1,0,0.55), initialText="AAAaaa111", obscured=1)
		self.messageConnexion = DirectEntry(text = "" ,scale=.05, numLines =2,width=50,focus=1, pos=(-1.3,0,0.89), initialText=self.message1+"\n "+self.message2)

		#Ajouter les boutons
		self.boutonLogin1 = DirectButton(text = ("Login J1!", "!", "!", "disabled"),
						text_scale=(0.1,0.1),
						borderWidth = borderW,
						text_bg=couleurBack,
						frameColor=couleurBack,
						relief=2,
						command=lambda:self.connexion(1),
						pos = (-0.75,0,0.3))
		self.boutonLogin2 = DirectButton(text = ("Login J2!", "!", "!", "disabled"),
						text_scale=(0.1,0.1),
						borderWidth = borderW,
						text_bg=couleurBack,
						frameColor=couleurBack,
						relief=2,
						command=lambda:self.connexion(2),
						pos = (1.25,0,0.3))

		#Afficher les tanks
		self.afficherTank1(1)
		self.afficherTank2(2)

		#Un cadre pour rendre la page plus presentable
		self.deco = OnscreenText(text = "    ", pos = (0,-0.3,-5), 
		scale = 5,mayChange=1, bg=(0.9,0.9,1,1),frame=(0,0,0,1))

		#Bouton combattre. Sera cacher au debut
		self.boutonStart = DirectButton(text = ("Combattre", "!", "!", "disabled"),
						text_scale=btnScale,
						borderWidth = borderW,
						text_bg=couleurBack,
						frameColor=couleurBack,
						command=self.appuyerCombattre,
						pos = (0,0,-0.8))
		self.boutonStart.hide()

#Methode pour aller a la prochaine page
	def chargeMenuNiveaux(self):
			self.arreterDecoration=True
			self.hideWidgets2()
			#On démarre!
			listeInfoUsers=[self.unDTOUsers,self.unDTOMap,self.username1, self.username2,self.chercherLesArmes()]
			Sequence(Func(self.cacher),
					 Func(lambda : messenger.send("AllerChoixNiveau",listeInfoUsers))
					 ).start()

	def connexion(self, noJoueur):

		#Vérification quel bouton a été appuyé
		if noJoueur==1:
			#Vérifier que le user n'est pas déja connecté
			if self.username2 != self.champNom1.get():
				if (self.unDTOUsers.siUsernameExiste(self.champNom1.get())):
					print self.champNom1.get()
					#Hashe le mot de passe
					salt=self.unDTOUsers.getMdpUser(self.champNom1.get())
					reponseHashe = bcrypt.hashpw(self.champMdp1.get(), salt) #Passer le salt du premier mdp
					#Si le bon mot de passe... continuer
					if (reponseHashe==salt):
						self.message1="Le joueur 1 ("+self.champNom1.get()+") est connecté!"
						self.username1=self.champNom1.get()
						self.changerCouleurTank1()
						self.bougerTank1()
						self.listeArmes1 = self.unDTOUsers.getToutsArmesJoueur(self.unDTOUsers.getIdUser(self.username1))
						for i in range(len(self.listeArmes1)-1):
							self.listeTotalNbArmes1[i] = self.listeArmes1[i][1]

						self.afficherChoixArmes(1,0)

					#Mauvais mot de passe
					else:
						self.message1 ="Erreur! Mauvais mot de passe pour le joueur 1..."
				#Username existe pas
				else:
					self.message1 = "Le username du joueur 1 n'existe pas!"
			#Déjà connecté
			else:
				self.message1 ="Erreur! Le joueur 2 est déjà connecté avec ce nom!"
		#Vérification quel bouton a été appuyé
		else:
			if self.username1 != self.champNom2.get():
				if (self.unDTOUsers.siUsernameExiste(self.champNom2.get())):
					print self.champNom2.get()

					salt=self.unDTOUsers.getMdpUser(self.champNom2.get())
					reponseHashe = bcrypt.hashpw(self.champMdp2.get(), salt) #Passer le salt du premier mdp

					if (reponseHashe==salt):
						self.message2="Le joueur 2 ("+self.champNom2.get()+") est connecté!"
						self.username2=self.champNom2.get()
						self.changerCouleurTank2()
						self.bougerTank2()

						self.listeArmes2 = self.unDTOUsers.getToutsArmesJoueur(self.unDTOUsers.getIdUser(self.username2))
						
						for i in range(len(self.listeArmes2)-1):
							self.listeTotalNbArmes2[i] = self.listeArmes2[i][1]

						self.afficherChoixArmes2(1,0)

					else:
						self.message2="Erreur! Mauvais mot de passe pour le joueur 2..."

				else:
					self.message2="Le username du joueur 2 n'existe pas!"
			else:
				self.message2 ="Erreur! Le joueur 1 est déjà connecté avec ce nom!"

		self.messageConnexion.set(self.message1+"\n "+self.message2)
		if self.username1!="" and self.username2!="":
			self.pretACombattre()


	def cacher(self):
			#Est esssentiellement un code de "loading"
		#On remet la caméra comme avant
		base.cam.node().getDisplayRegion(0).setSort(self.baseSort)
		self.boutonStart.hide()

	def setupMap(self):
		leDTOMap=DTOMap()
		leDAOMap=DAOOracleMap()
		leDTOMap=leDAOMap.read()
		return leDTOMap

	def afficherTank1(self, couleur):
		# On charge le modèles
		self.modele1 = loader.loadModel("../asset/Tank/tank")
		#On réduit sa taille un peu...
		self.modele1.setScale(0.450,0.450,0.450)
		self.modele1.setPos(-7, 10, 0)

		#Pour l'animation
		self.modele1.reparentTo(render)
		dureeRot = 5

		intervalRotation = self.modele1.hprInterval(dureeRot, (360,0,0))
		self.sequ1= Sequence(intervalRotation)
		self.sequ1.loop()

	def afficherTank2(self, couleur):
		# On charge le modèles
		self.modele2 = loader.loadModel("../asset/Tank/tank")
		#On réduit sa taille un peu...
		self.modele2.setScale(0.450,0.450,0.450)
		self.modele2.setPos(6, 10, 0)

		#On multiple la couleur de la texture du tank par ce facteur. Ça permet de modifier la couleur de la texture du tank

		self.modele2.setHpr(0,0,0)
		self.modele2.reparentTo(render)

		dureeRot = 5

		intervalRotation = self.modele2.hprInterval(dureeRot, (-360,0,0))
		self.sequ2 = Sequence(intervalRotation)
		self.sequ2.loop()

#Faire bouger le tank quand le joueur se connecte
	def bougerTank1(self):
		posInit =(-7, 10, 0)
		self.modele1.setPos(posInit)

		duree = 1
		intervalAvancer= self.modele1.posInterval(duree, (-1, 10, 0))
		avancerTank = Sequence(intervalAvancer)
		avancerTank.start()


	def bougerTank2(self):
		posInit =(7, 10, 0)
		self.modele2.setPos(posInit)

		duree = 1
		intervalAvancer= self.modele2.posInterval(duree, (1, 10, 0))
		avancerTank = Sequence(intervalAvancer)
		avancerTank.start()

	def pretACombattre(self):
		self.boutonStart.show()
		testInterval = self.boutonStart.scaleInterval(2,(1.5,1.5,1.5),startScale=(0.5,0.5,0.5))
		testInterval2= self.boutonStart.scaleInterval(2,(0.5,0.5,0.5),startScale=(1.5,1.5,1.5))
		self.seqBut = Sequence(testInterval,testInterval2)
		self.seqBut.loop()

	def appuyerCombattre(self):
		#Cacher les widgets et terminer l'animation qui tourne
		self.hideWidgets()
		self.sequ1.finish()
		self.sequ2.finish()
		self.seqBut.finish()

		dureeRot = 2
		self.modele1.setHpr(0,0,0)
		self.modele2.setHpr(-270,0,0)

		intervalRotation = self.modele1.hprInterval(dureeRot, (270,0,0))
		derniereRotation = Sequence(intervalRotation)
		derniereRotation.start()

		intervalRotation2 = self.modele2.hprInterval(dureeRot, (90,0,0))
		derniereRotation2 = Sequence(intervalRotation2)
		derniereRotation2.start()

		#Faire afficher les noms
		self.labelTank1 = OnscreenText(text = self.username1, pos = (-0.7,-0.6), 
		scale = 0.2,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1, 
		bg=(0.7,0.8,0,1),frame=(0,0,0,1))

		self.labelTank2 = OnscreenText(text = self.username2, pos = (0.7,-0.6), 
		scale = 0.2,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1, 
		bg=(0.7,0.8,0,1),frame=(0,0,0,1))

		
		#Faire des animations
		testInterval = self.labelTank1.scaleInterval(2,(1.5,1.5,1.5),startScale=(0.5,0.5,0.5))
		testInterval2= self.labelTank1.scaleInterval(2,(0.5,0.5,0.5),startScale=(1.5,1.5,1.5))
		self.seqTank1 = Sequence(testInterval,testInterval2)
		self.seqTank1.loop()

		testInterval = self.labelTank2.scaleInterval(2,(1.5,1.5,1.5),startScale=(0.5,0.5,0.5))
		testInterval2= self.labelTank2.scaleInterval(2,(0.5,0.5,0.5),startScale=(1.5,1.5,1.5))
		self.seqTank2 = Sequence(testInterval,testInterval2)
		self.seqTank2.loop()



		self.labelVS1 = OnscreenText(text = "V.S", pos = (0,-0.5), 
		scale = 0.2,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1, 
		bg=(1,0.2,0.2,1),frame=(0,0,0,1))
		self.labelVS2 = OnscreenText(text = "V.S", pos = (0,-0.5), 
		scale = 0.2,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1, 
		bg=(0.2,0.2,1,1),frame=(0,0,0,1))
		labelVSInterval1 = self.labelVS1.hprInterval(2,(0,0,0),startHpr=(0,0,360))
		labelVSInterval1.loop()
		labelVSInterval2 = self.labelVS2.hprInterval(2,(0,0,0),startHpr=(0,0,-360))
		labelVSInterval2.loop()

		messenger.send("updateArmurie",self.chercherInfoAUpdate())
		print"apres messenger"
		print self.chercherLesArmes()

#JAI CHANGER LE TEMPS DE LANIMATION
		t = Timer(1.0, lambda:self.chargeMenuNiveaux())
		t.start() # after 30 seconds, "hello, world" will be printed
		self.decoration()


#Faire une animation de "neige"
	def decoration(self):

		if not self.arreterDecoration:
			threading.Timer(0.1,lambda:self.decoration()).start()

			labelTest = OnscreenText(text = "TANK'EM!!", pos = (random.randrange(5),random.randrange(5),random.randrange(5)), 
			scale = 0.2,fg=(random.randrange(10),random.randrange(10),random.randrange(10),1),align=TextNode.ACenter,mayChange=1, 
			bg=(0.1,1,0.6,1),frame=(0,0,0,1))
			labelTest.setHpr(random.randrange(70),random.randrange(70),random.randrange(70))


			testInterval = labelTest.posInterval(3, Point3(-8, -2, -2), startPos=Point3(random.randrange(5),random.randrange(5), random.randrange(5)))

			mySequence = Sequence(testInterval)
			mySequence.start()

	def hideWidgets(self):
		self.boutonStart.hide()
		self.textJoueur1.hide()
		self.textJoueur2.hide()
		self.textMdp1.hide()
		self.textMdp2.hide()
		self.boutonLogin1.hide()
		self.boutonLogin2.hide()
		self.messageConnexion.set("Préparez vous pour le combat de votre vie....!!!!!!\n ÊTES-VOUS PRÊTS???")
		self.champNom1.hide()
		self.champMdp1.hide()
		self.champNom2.hide()
		self.champMdp2.hide()
		self.labelNom1.hide()
		self.labelNom2.hide()
		for item in self.listeNomArmes:
			item.hide()
		for item in self.listeNbArmes:
			item.hide()
		for item in self.listeNomArmes2:
			item.hide()
		for item in self.listeNbArmes2:
			item.hide()
		for item in self.listeCheckBox1:
			item.hide()
		for item in self.listeCheckBoxSec1:
			item.hide()
		for item in self.listeCheckBox2:
			item.hide()
		for item in self.listeCheckBoxSec2:
			item.hide()
		for item in self.labelJoueur1:
			item.hide()
		for item in self.labelJoueur2:
			item.hide()

		self.labelNom1.hide()
		self.labelNom2.hide()
		self.labelPrim1.hide()
		self.labelPrim2.hide()
		self.labelSec1.hide()
		self.labelSec2.hide()




	def hideWidgets2(self):
		self.modele1.hide()
		self.modele2.hide()
		self.labelVS1.hide()
		self.labelVS2.hide()
		self.labelTank2.hide()
		self.labelTank1.hide()
		self.messageConnexion.hide()
		self.deco.hide()

	def changerCouleurTank1(self):

		#On multiple la couleur de la texture du tank par ce facteur. Ça permet de modifier la couleur de la texture du tank
		RGBJ1 = [self.unDTOUsers.getRougeUser(self.username1),self.unDTOUsers.getVertUser(self.username1),self.unDTOUsers.getBleuUser(self.username1),1]
		self.modele1.setColorScale(RGBJ1[0],RGBJ1[1],RGBJ1[2],RGBJ1[3])



	def changerCouleurTank2(self):

		RGBJ2 = [self.unDTOUsers.getRougeUser(self.username2),self.unDTOUsers.getVertUser(self.username2),self.unDTOUsers.getBleuUser(self.username2),1]
		self.modele2.setColorScale(RGBJ2[0],RGBJ2[1],RGBJ2[2],RGBJ2[3])

	def afficherChoixArmes(self,choix1,choix2):
		self.choixPrim1=[choix1]
		self.choixSec1=[choix2]
		
		for item in self.listeNomArmes:
			item.hide()

		for item in self.listeCheckBox1:
			item.hide()
		for item in self.listeCheckBoxSec1:
			item.hide()
		for item in self.listeNbArmes:
			item.hide()

		for item in self.labelJoueur1:
			item.hide()

		self.listeCheckBox1=[]
		self.listeCheckBoxSec1=[]
		self.listeNomArmes=[]
		self.labelJoueur1=[]
		#Afficher les labels et les radiobuttons
		self.labelNom1 = OnscreenText(text = "Nom:", pos = (-1.6,0.1,0), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
		self.labelJoueur1.append(self.labelNom1)
		self.texteNom = OnscreenText(text = "AucuneArme", pos = (-1.5,0,0), 
		scale = 0.06,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
		self.listeNomArmes.append(self.texteNom)

		self.sec1 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(-0.8,0,0),variable=self.choixSec1,value=[0], command=self.radioChoixJoueur1)
		self.listeCheckBoxSec1.append(self.sec1)



		self.texteNom = OnscreenText(text = "Canon", pos = (-1.5,-0.1,0), 
		scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
		self.prim1 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(-1.2,0,-0.1),variable=self.choixPrim1,value=[1], command=self.radioChoixJoueur1)
		self.listeCheckBox1.append(self.prim1)		

		self.listeNomArmes.append(self.texteNom)
		self.sec1 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(-0.8,0,-0.1),variable=self.choixSec1,value=[1], command=self.radioChoixJoueur1)
		self.listeCheckBoxSec1.append(self.sec1)


		self.labelPrim1 = OnscreenText(text = "Primaire:", pos = (-1.2,0.1,0), 
		scale = 0.05,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
		self.labelSec1 = OnscreenText(text = "Secondaire:", pos = (-0.8,0.1,0), 
		scale = 0.05,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
		self.labelJoueur1.append(self.labelPrim1)
		self.labelJoueur1.append(self.labelSec1)


#Pour chaque arme de la DTO, faire ajouter l'arme dans la liste
		nbDeChoix = len(self.listeArmes1)-1
		for i in range(nbDeChoix):
			texteNomArme = OnscreenText(text = self.listeArmes1[i][0], 
			pos = (-1.5,-0.2-0.1*i,0), scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.listeNomArmes.append(texteNomArme)

			texteNb = OnscreenText(text =str(self.listeTotalNbArmes1[i]) , 
			pos = (-0.6,-0.2-0.1*i,0), scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.listeNbArmes.append(texteNb)
			if self.listeArmes1[i][1]!=0:
				b = DirectRadioButton(text = "Ok" ,scale=.05, pos=(-1.2,0,-0.2-0.1*i),variable=self.choixPrim1,value=[i+2], command=self.radioChoixJoueur1)
				self.listeCheckBox1.append(b)		
				c = DirectRadioButton(text = "Ok" ,scale=.05, pos=(-0.8,0,-0.2-0.1*i),variable=self.choixSec1,value=[i+2], command=self.radioChoixJoueur1)
				self.listeCheckBoxSec1.append(c)
			
		#Code pour seulement pouvoir selection un radiobutton de la liste
		for button in self.listeCheckBox1:
			button.setOthers(self.listeCheckBox1)

		for button in self.listeCheckBoxSec1:
			button.setOthers(self.listeCheckBoxSec1)

		self.isReader1 = True
	# Callback function to set  text 
	def radioChoixJoueur1(self,status=None):
		if self.isReader1 ==True:


			print"La valeur selectionnée PRIM:"+ str(self.choixPrim1)
			print"La valeur selectionnée Secondaire:"+ str(self.choixSec1)
			#Si on choisi une arme bonus, soustraire l'inventaire de 1.

			#Refresh les valeurs du nombre d'armes
			for i in range(len(self.listeArmes1)-1):
				self.listeTotalNbArmes1[i] = self.listeArmes1[i][1]

			#Si on choisi une arme bonus, soustraire l'inventaire de 1.
			if self.choixPrim1>1:
				if self.listeTotalNbArmes1[self.choixPrim1[0]-2]-1>=0:
					self.listeTotalNbArmes1[self.choixPrim1[0]-2]-=1
			#Si on choisi une arme bonus, soustraire l'inventaire de 1.
			if self.choixSec1[0]>1:

				if self.listeTotalNbArmes1[self.choixSec1[0]-2]-1>=0:
					self.listeTotalNbArmes1[self.choixSec1[0]-2]-=1
				else:
					self.choixSec1[0]=0


			self.isReader1 =False
			self.afficherChoixArmes(self.choixPrim1[0],self.choixSec1[0])


	def afficherChoixArmes2(self,choix1,choix2):
			self.choixPrim2=[choix1]
			self.choixSec2=[choix2]

		#Cacher les anciens
			for item in self.listeNomArmes2:
				item.setText("")

			for item in self.listeCheckBox2:
				item.hide()
			for item in self.listeCheckBoxSec2:
				item.hide()
			for item in self.listeNbArmes2:
				item.hide()

			for item in self.labelJoueur2:
				item.hide()

			self.listeCheckBox2=[]
			self.listeCheckBoxSec2=[]
			self.listeNomArmes2=[]
			self.labelJoueur2=[]

		#Afficher les labels et les radiobuttons

			self.labelNom2 = OnscreenText(text = "Nom:", pos = (0.7,0.1,0), 
			scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.texteNom2 = OnscreenText(text = "AucuneArme", pos = (0.7,0,0), 
			scale = 0.06,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.listeNomArmes2.append(self.texteNom2)

			self.sec2 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(1.4,0,0),variable=self.choixSec2,value=[0], command=self.radioChoixJoueur2)
			self.listeCheckBoxSec2.append(self.sec2)



			self.texteNom2 = OnscreenText(text = "Canon", pos = (0.7,-0.1,0), 
			scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.prim2 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(1,0,-0.1),variable=self.choixPrim2,value=[1], command=self.radioChoixJoueur2)
			self.listeCheckBox2.append(self.prim2)		

			self.listeNomArmes2.append(self.texteNom2)
			self.sec2 = DirectRadioButton(text = "Ok" ,scale=.05, pos=(1.4,0,-0.1),variable=self.choixSec2,value=[1], command=self.radioChoixJoueur2)
			self.listeCheckBoxSec2.append(self.sec2)


			self.labelPrim2 = OnscreenText(text = "Primaire:", pos = (1,0.1,0), 
			scale = 0.05,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
			self.labelSec2 = OnscreenText(text = "Secondaire:", pos = (1.4,0.1,0), 
			scale = 0.05,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)

			self.labelJoueur2.append(self.labelNom2)
			self.labelJoueur2.append(self.labelPrim2)
			self.labelJoueur2.append(self.labelSec2)

	#Pour chaque arme de la DTO, faire ajouter l'arme dans la liste
			nbDeChoix2 = len(self.listeArmes2)-1
			for i in range(nbDeChoix2):
				texteNomArme2 = OnscreenText(text = self.listeArmes2[i][0], 
				pos = (0.7,-0.2-0.1*i,0), scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
				self.listeNomArmes2.append(texteNomArme2)

				texteNb2 = OnscreenText(text =str(self.listeTotalNbArmes2[i]) , 
				pos = (1.6,-0.2-0.1*i,0), scale = 0.07,fg=(0,0,0,1),align=TextNode.ACenter,mayChange=1)
				self.listeNbArmes2.append(texteNb2)
				if self.listeArmes2[i][1]!=0:
					b = DirectRadioButton(text = "Ok" ,scale=.05, pos=(1,0,-0.2-0.1*i),variable=self.choixPrim2,value=[i+2], command=self.radioChoixJoueur2)
					self.listeCheckBox2.append(b)		
					c = DirectRadioButton(text = "Ok" ,scale=.05, pos=(1.4,0,-0.2-0.1*i),variable=self.choixSec2,value=[i+2], command=self.radioChoixJoueur2)
					self.listeCheckBoxSec2.append(c)
				
			#Code pour seulement pouvoir selection un radiobutton de la liste
			for button in self.listeCheckBox2:
				button.setOthers(self.listeCheckBox2)

			for button in self.listeCheckBoxSec2:
				button.setOthers(self.listeCheckBoxSec2)

			self.isReader2 = True
		# Callback function to set  text 
	def radioChoixJoueur2(self,status=None):
		if self.isReader2 ==True:

			print"La valeur selectionnée PRIM:"+ str(self.choixPrim2)
			print"La valeur selectionnée Secondaire:"+ str(self.choixSec2)
			#Si on choisi une arme bonus, soustraire l'inventaire de 1.
			#Refresh les valeurs du nombre d'armes
			for i in range(len(self.listeArmes2)-1):
				self.listeTotalNbArmes2[i] = self.listeArmes2[i][1]
				#Si on choisi une arme bonus, soustraire l'inventaire de 1.
			if self.choixPrim2>1:
				if self.listeTotalNbArmes2[self.choixPrim2[0]-2]-1>=0:
					self.listeTotalNbArmes2[self.choixPrim2[0]-2]-=1
			#Si on choisi une arme bonus, soustraire l'inventaire de 1.
			if self.choixSec2[0]>1:

				if self.listeTotalNbArmes2[self.choixSec2[0]-2]-1>=0:
					self.listeTotalNbArmes2[self.choixSec2[0]-2]-=1
				else:
					self.choixSec2[0]=0


			self.isReader2 =False
			self.afficherChoixArmes2(self.choixPrim2[0],self.choixSec2[0])

	def chercherInfoAUpdate(self):
		idUser1 = self.unDTOUsers.getIdUser(self.username1)
		idUser2 = self.unDTOUsers.getIdUser(self.username2)

		listeModifUser=[]

		if self.choixPrim1[0]>1:
			choix=[]
			choix.append(idUser1)
			choix.append(self.unDTOUsers.getIdArme(self.listeNomArmes[self.choixPrim1[0]].getText()))
			listeModifUser.append(choix)

		if self.choixSec1[0]>1:
			choix=[]
			choix.append(idUser1)
			choix.append(self.unDTOUsers.getIdArme(self.listeNomArmes[self.choixSec1[0]].getText()))
			listeModifUser.append(choix)

		if self.choixPrim2[0]>1:
			choix=[]
			choix.append(idUser2)
			choix.append(self.unDTOUsers.getIdArme(self.listeNomArmes2[self.choixPrim2[0]].getText()))
			listeModifUser.append(choix)
		if self.choixSec2[0]>1:
			choix=[]
			choix.append(idUser2)
			choix.append(self.unDTOUsers.getIdArme(self.listeNomArmes2[self.choixSec2[0]].getText()))
			listeModifUser.append(choix)

		listeInfo=[]
		listeInfo.append(listeModifUser)
		return listeInfo

	def chercherLesArmes(self):
		listeArme1=[]
		listeArme2=[]
		listeArme1.append(self.listeNomArmes[self.choixPrim1[0]].getText())
		listeArme1.append(self.listeNomArmes[self.choixSec1[0]].getText())
		listeArme2.append(self.listeNomArmes2[self.choixPrim2[0]].getText())
		listeArme2.append(self.listeNomArmes2[self.choixSec2[0]].getText())
		listeInfo=[]
		listeInfo.append(listeArme1)
		listeInfo.append(listeArme2)
		return listeInfo
