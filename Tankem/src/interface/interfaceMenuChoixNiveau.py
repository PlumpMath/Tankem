# -*- coding: utf-8 -*-
from direct.showbase.ShowBase import ShowBase
from direct.gui.OnscreenText import OnscreenText 
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.interval.LerpInterval import *
from direct.interval.IntervalGlobal import *
from direct.showbase.Transitions import Transitions

import sys
sys.path.insert(0, '../../../common')
from internal.DAO.DAOOracleMap import * 


class InterfaceMenuChoixNiveau(ShowBase):
	def __init__(self):
		self.accept("AllerChoixNiveau",self.ouvrirMenu)
		self.sound = loader.loadSfx("../asset/Menu/demarrage.mp3")
		curtain = loader.loadTexture("../asset/Menu/loading.jpg")

		self.transition = Transitions(loader)
		self.transition.setFadeColor(0, 0, 0)
		self.transition.setFadeModel(curtain)
		self.baseSort = base.cam.node().getDisplayRegion(0).getSort()
		base.cam.node().getDisplayRegion(0).setSort(20)
	
 
	def ouvrirMenu(self, unDTOUsers,unDTOMap,username1,username2,listeArmes):
		self.listeArmes=listeArmes
		self.username1 = username1
		self.username2 = username2
		self.unDTOUsers=unDTOUsers
		self.unDTOMap=unDTOMap
		numItemsVisible = 5
		itemHeight = 0.11
		 
		self.myScrolledList = DirectScrolledList(
			decButton_pos= (0.29, 0, 0.53),
			decButton_text = "Dec",
			decButton_text_scale = 0.04,
			decButton_borderWidth = (0.005, 0.005),
		 
			incButton_pos= (0.29, 0, -0.15),
			incButton_text = "Inc",
			incButton_text_scale = 0.04,
			incButton_borderWidth = (0.005, 0.005),
		 
			frameSize = (-1.4, 1.7, -0.5, 0.75),
			frameColor = (1,0,0,0.5),
			pos = (-0.12, 0, 0),
			numItemsVisible = numItemsVisible,
			forceHeight = itemHeight,
			itemFrame_frameSize = (-1.2, 1.2, -0.5, 0.11),
			itemFrame_pos = (0.27, 0, 0.4),
			)
		 
		btn=DirectButton(text = ("Hasard", "click!", "roll", "disabled"),
						  text_scale=0.075, borderWidth = (0.01, 0.01),
						  relief=2, command=lambda:self.chargeJeu(000))
		self.myScrolledList.addItem(btn)

		if Connexion().getCurseur()!=None:
			self.dictInfoNiveaux=self.unDTOMap.getDictoNiveau()
			listeIdUsersEnOrdre=[]
			######Met les niv en ordre de proprietaire
			tupleUser=self.unDTOUsers.getTousUser()
			for username in tupleUser:
				listeIdUsersEnOrdre.append(tupleUser.get(username)[0])
			listeIdUsersEnOrdre.sort()

			listeDesUsersEnOrdre=[]
			for idJoueur in listeIdUsersEnOrdre:
				print idJoueur
				for username in tupleUser:	
					if tupleUser.get(username)[0]==idJoueur:
						listeDesUsersEnOrdre.append(tupleUser.get(username))

			for user1 in listeDesUsersEnOrdre:
				for key in self.dictInfoNiveaux:
					if self.dictInfoNiveaux.get(key)[8]==user1[0]:
						infosNiveau=[]
						infosNiveau=self.dictInfoNiveaux.get(key)
						
						nomProprio=user1[1]	
						nbFoisFavori=self.unDTOMap.nbFoisNiveauFavori(infosNiveau[0])
						nbFoisJouer=self.unDTOMap.obtenirNbFoisNiveauJoueTotal(infosNiveau[0])

						if infosNiveau[5]!=4:
							textLabel=str(infosNiveau[1])+" --> Propriétaire: "+str(nomProprio)+" -- Favoris: "+str(nbFoisFavori)+ " -- Joué: "+ str(nbFoisJouer)
							btn=DirectButton(text = (textLabel, "Bon choix!", self.stringHover(infosNiveau[5]), "disabled"),
										  text_scale=0.075, borderWidth = (0.01, 0.01),
										  relief=2, command=lambda idNiveau=infosNiveau[0]:self.chargeJeu(idNiveau))
							self.myScrolledList.addItem(btn)
		

	def chargeJeu(self,idNiveau):
		listeInfo=[idNiveau,self.unDTOMap,self.unDTOUsers, self.username1, self.username2,self.listeArmes]
		#On démarre!
		Sequence(Func(lambda : self.transition.irisOut(0.2)),
			SoundInterval(self.sound),
			Func(self.cacher),
			Func(lambda : messenger.send("DemarrerPartie",listeInfo)),
			Wait(0.2), #Bug étrange quand on met pas ça. L'effet de transition doit lagger
			Func(lambda : self.transition.irisIn(0.2))
		).start()

	def cacher(self):
			#Est esssentiellement un code de "loading"
		#On remet la caméra comme avant
		base.cam.node().getDisplayRegion(0).setSort(self.baseSort)
		self.myScrolledList.hide()
		
	def setupMap(self):
		leDTOMap=DTOMap()
		leDAOMap=DAOOracleMap()
		leDTOMap=leDAOMap.read()
		return leDTOMap

	def stringHover(self,typeNiv):
		if typeNiv==1:
			return "Public"
		if typeNiv==2:
			return "Privé"
		if typeNiv==3:
			return "Équipe"
		if typeNiv==4:
			return "Inactif"


