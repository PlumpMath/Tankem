## -*- coding: utf-8 -*-
from util import *

from direct.showbase.ShowBase import ShowBase
from direct.gui.OnscreenText import OnscreenText 
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.interval.LerpInterval import *
from direct.interval.IntervalGlobal import *
from direct.gui.DirectGui import DirectFrame
import time
 
class InterfaceMessage(ShowBase):
    def __init__(self, parent):
        ###########################AJOUTER #########################
        self.gameLogic = parent #Parent c'est le gamelogic

        self.messageSignalDebutpartie=self.gameLogic.unDTO.getValue("messageSignalDebutpartie")
        self.messageFinPartie =self.gameLogic.unDTO.getValue("messageFinPartie")

        self.accept("MessageBonus",self.afficherLesBonus)
        self.accept("tankElimine",self.displayGameOver)
        self.callBackFunction = None

        self.accept("showHelp",self.displayHelp)

        self.createHelpText()
        self.displayHelp(False)
        #self.tempsPartie

    def effectCountDownStart(self,nombre,callbackFunction):
        self.callBackFunction = callbackFunction
        self.displayCountDown(nombre)
        
    def displayCountDown(self, nombre):
        message = str(nombre)
        startScale = 0.4

        text = TextNode('Compte à rebour')
        text.setText(message)
        textNodePath = aspect2d.attachNewNode(text)
        textNodePath.setScale(startScale)
        text.setShadow(0.05, 0.05)
        text.setShadowColor(0, 0, 0, 1)
        text.setTextColor(0.5, 0.5, 1, 1)
        text.setAlign(TextNode.ACenter)

        effetScale = LerpScaleInterval(textNodePath, 1.0, 0.05, startScale)
        effetFadeOut = LerpColorScaleInterval(textNodePath, 1.0, LVecBase4(1,1,1,0), LVecBase4(1,1,1,1))
        effetFadeOut.start()

        recursion = Func(self.displayCountDown,nombre-1)

        #Le prochain tour, on affiche la message de début de partie
        if(nombre == 1):
            recursion = Func(self.displayStartGame)
        sequence = Sequence(effetScale,recursion)
        sequence.start()

    def displayStartGame(self):
        message = self.messageSignalDebutpartie
        startScale = 0.4

        text = TextNode('Début de la partie')
        text.setText(message)
        textNodePath = aspect2d.attachNewNode(text)
        textNodePath.setScale(startScale)
        text.setShadow(0.05, 0.05)
        text.setShadowColor(0, 0, 0, 1)
        text.setTextColor(0.5, 0.5, 1, 1)
        text.setAlign(TextNode.ACenter)

        delai = Wait(0.3)
        effetFadeOut = LerpColorScaleInterval(textNodePath, 0.15, LVecBase4(1,1,1,0), LVecBase4(1,1,1,1), blendType = 'easeIn')

        sequence = Sequence(delai,effetFadeOut,Func(self.callBackFunction))
        sequence.start()
        self.start=time.time()

    def displayGameOver(self, idPerdant):
        joueurGagnant = 1 if idPerdant == 1 else 2
        message = self.messageFinPartie+ "\n"+self.nomGagnant + " a gagné!"
        startScale = 0.3

        text = TextNode('Annonce game over')
        text.setText(message)
        textNodePath = aspect2d.attachNewNode(text)
        textNodePath.setScale(startScale)
        textNodePath.setColorScale(LVecBase4(1,1,1,0))
        text.setShadow(0.05, 0.05)
        text.setShadowColor(0, 0, 0, 1)
        text.setTextColor(0.01, 0.2, 0.7, 1)
        text.setAlign(TextNode.ACenter)

        delai = Wait(0.5)
        effetFadeIn = LerpColorScaleInterval(textNodePath, 1, LVecBase4(1,1,1,1), LVecBase4(1,1,1,0), blendType = 'easeIn')

        sequence = Sequence(delai,effetFadeIn)
        sequence.start()
        self.end=time.time()
        self.tempsPartie=self.end-self.start

    def effectMessageGeneral(self, message, duration):
        text = TextNode('Message general')

        startScale = 0.12
        text.setText(message)
        textNodePath = aspect2d.attachNewNode(text)
        textNodePath.setScale(startScale)
        textNodePath.setColorScale(LVecBase4(1,1,1,0))
        text.setShadow(0.05, 0.05)
        text.setShadowColor(0, 0, 0, 1)
        text.setTextColor(0.01, 0.2, 0.7, 1)
        text.setAlign(TextNode.ACenter)
        textNodePath.setPos(Vec3(0,0,0.65))

        delai = Wait(duration)
        effetFadeIn = LerpColorScaleInterval(textNodePath, 0.3, LVecBase4(1,1,1,1), LVecBase4(1,1,1,0), blendType = 'easeIn')
        effetFadeOut = LerpColorScaleInterval(textNodePath, 0.3, LVecBase4(1,1,1,0), LVecBase4(1,1,1,1), blendType = 'easeIn')

        sequence = Sequence(effetFadeIn,delai,effetFadeOut)
        sequence.start()

    def displayHelp(self,mustShow):
        self.textNodePath.show() if mustShow else self.textNodePath.hide()

    def createHelpText(self):
        #TODO: touches sont dupliquées dans le inputManager et ici. On doit centraliser
        message = """Contrôle\n
        Contrôle avec la souris: F2\n
        ----Joueur 1----\n
        Bouger: wasd\n
        Tirer arme principale: v\n
        Tirer arme secondaire: b\n
        Détonation des balles: b\n
        \n
        ----Joueur 2----\n
        Bouger: flèches\n
        Tirer arme principale: NumPad-1\n
        Tirer arme secondaire: NumPad-2\n
        Détonation des balles: NumPad-3\n\n
        """

        text = TextNode('Aide')
        text.setText(message)
        self.textNodePath = aspect2d.attachNewNode(text)
        self.textNodePath.setScale(0.055)
        self.textNodePath.setColorScale(LVecBase4(1,1,1,1))
        text.setShadow(0.05, 0.05)
        text.setShadowColor(0, 0, 0, 1)
        text.setTextColor(0.01, 0.2, 0.1, 1)
        text.setAlign(TextNode.ALeft)
        self.textNodePath.setPos(Vec3(-1.65,0,0.65))


    def afficherLesBonus(self,listeDesBonus, nomGagnant):
        self.nomGagnant=nomGagnant
        nbShotgun=0
        nbGrenade=0
        nbPiege=0
        nbGuide=0
        nbSpring=0
        nbMitraillette=0
        listeBonus=listeDesBonus
        for bonus in listeBonus:
            if bonus==1:
                nbShotgun+=1
            elif bonus==2:
                nbGrenade+=1
            elif bonus==3:
                nbPiege+=1
            elif bonus==4:
                nbGuide+=1
            elif bonus==5:
                nbSpring+=1
            elif bonus==6:
                nbMitraillette+=1
        leTexte="Les bonus de "+self.nomGagnant+"\n\n"+"Shotgun: "+ str(nbShotgun)+"\n"+"Grenade: "+ str(nbGrenade)+"\n"+"Piege: "+ str(nbPiege)+"\n"+"Missile guide: "+ str(nbGuide)+"\n"+"Spring: "+ str(nbSpring)+"\n"+"Mitraillette: "+str(nbMitraillette)
        print leTexte
        self.labeBonus = OnscreenText(text = leTexte, pos = (0,0.9), 
        scale = 0.1,fg=(0,0,0,1),align=TextNode.ACenter, 
        bg=(1,0.2,0.2,0.7),frame=(0,0,0,1))

        borderW = (0.04, 0.04)
        self.boutonFavoriJ1 = DirectButton(text = ("Ajouter au favori"),
                        text_scale=(0.1,0.1),
                        borderWidth = borderW,
                        text_bg=(0.33,0.82,0.86,0.85),
                        frameColor=(0.33,0.82,0.86,0.85),
                        relief=2,
                        command=lambda:messenger.send("AjoutFavori",[0]),
                        pos = (-1,0,0.4))
        
        self.boutonFavoriJ2 = DirectButton(text = ("Ajouter au favori"),
                        text_scale=(0.1,0.1),
                        borderWidth = borderW,
                        text_bg=(0.33,0.82,0.86,0.85),
                        frameColor=(0.33,0.82,0.86,0.85),
                        relief=2,
                        command=lambda:messenger.send("AjoutFavori",[1]),
                        pos = (1,0,0.4))

