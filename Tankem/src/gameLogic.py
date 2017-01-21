# -*- coding: utf-8 -*-
from util import *

#Importer la DTO
#import sys
#sys.path.insert(0, '../../common/internal/DTO')
#from DTOBalance import *
import sys
sys.path.insert(0, '../../common')
from internal.DAO.DAOOracleBalance import *
from internal.DAO.DAOOracleUsers import *
from internal.DAO.DAOStatistiques import *


from direct.showbase.ShowBase import ShowBase
from panda3d.core import *
from panda3d.bullet import BulletWorld
from panda3d.bullet import BulletPlaneShape
from panda3d.bullet import BulletRigidBodyNode
from panda3d.bullet import BulletDebugNode

#Modules de notre jeu
from map import Map
from inputManager import InputManager
from interface import * 
import time
from attributionBonus import AttributionBonus 


#Classe qui gère les phases du jeu (Menu, début, pause, fin de partie)
class GameLogic(ShowBase):
    def __init__(self,pandaBase):
        #######################AJOUTER #########################################################
        self.pandaBase = pandaBase
        self.accept("DemarrerPartie",self.startGame)
        self.unDTO=DTOBalance()
        self.setupBalance()

        self.attribueurBonus=AttributionBonus()
        self.accept("MortJoueur",self.finDePartie)
        self.accept("AjoutFavori",self.ajoutFavori)
        self.accept("updateArmurie",self.updateArmurie)
        self.accept("escape", exit)



    def finDePartie(self,idPerdant):
        self.tempsFin=time.time()
        tempsPartie=self.tempsFin-self.tempsDebut
        for tank in self.map.listTank:
            if tank.identifiant!=idPerdant:
                vieGagnant=tank.pointDeVie
                vieMax=tank.pointDeVieMax
                vieJoueur100=100 * vieGagnant / vieMax
                idGagnant=tank.identifiant
        #Ajout des bonus
        if idGagnant==0:
            idJoueurGagnant=self.leDTOUsers.getIdUser(self.username1)
            idJoueurPerdant=self.leDTOUsers.getIdUser(self.username2)
            messenger.send("AttribuerBonus",[idJoueurGagnant,self.username1,vieJoueur100,tempsPartie])
        else:
            idJoueurGagnant=self.leDTOUsers.getIdUser(self.username2)
            idJoueurPerdant=self.leDTOUsers.getIdUser(self.username1)
            messenger.send("AttribuerBonus",[idJoueurGagnant,self.username2,vieJoueur100,tempsPartie])
        self.map.DTOStat.definitionGagnant(idJoueurGagnant)
        ####Ajout nb fois jouer
        if self.idNiveau!=000:
            self.unDAOUser=DAOOracleUsers()
            self.unDAOUser.updateNbFoisNivJouer(idJoueurGagnant,self.idNiveau)
            self.unDAOUser.updateNbFoisNivJouer(idJoueurPerdant,self.idNiveau)
        
        self.DAOStats.ecrireStatsFinPartie()

    def updateArmurie(self,listeInfo):
        print listeInfo
        self.unDAOUser=DAOOracleUsers()
        for info in listeInfo:
            self.unDAOUser.updateNbArmes(info[0],info[1])
            print"un update!"




    def setup(self):
        self.setupBulletPhysics()

        self.setupCamera()
        self.setupMap()
        self.setupLightAndShadow()

        #Création d'une carte de base
        if self.idNiveau==000:
            self.map.construireMapHasard()
        else:
            self.map.creerCarteParDefaut()

        #A besoin des éléments de la map
        self.setupControle()
        self.setupInterface()

        #Fonction d'optimisation
        #DOIVENT ÊTRE APPELÉE APRÈS LA CRÉATION DE LA CARTE
        #Ça va prendre les modèles qui ne bougent pas et en faire un seul gros
        #en faisant un seul gros noeud avec
        self.map.figeObjetImmobile()

        #DEBUG: Décommenter pour affiche la hiérarchie
        #self.pandaBase.startDirect()

        messenger.send("ChargementTermine")

    def startGame(self,idNiveau,leDTOMap, leDTOUsers, username1, username2,listeArmes):
        self.listeArmes= listeArmes
        self.username1= username1
        self.username2= username2
        self.idNiveau=idNiveau
        self.leDTOMap=leDTOMap
        self.leDTOUsers=leDTOUsers

        self.tempsDebut=time.time()

        self.setup()
        self.DAOStats=DAOStatistiques(self.map.DTOStat)
        self.DAOStats.ecrireInfosDebutPartie()
        #On démarrer l'effet du compte à rebour.
        #La fonction callBackDebutPartie sera appelée à la fin
        self.interfaceMessage.effectCountDownStart(self.unDTO.getValue("messageCompteRebour"),self.callBackDebutPartie)
        self.interfaceMessage.effectMessageGeneral(self.unDTO.getValue("messageAccueilContenu"),self.unDTO.getValue("messageAccueilDuree"))

    def setupBulletPhysics(self):
        debugNode = BulletDebugNode('Debug')
        debugNode.showWireframe(True)
        debugNode.showConstraints(True)
        debugNode.showBoundingBoxes(False)
        debugNode.showNormals(False)
        self.debugNP = render.attachNewNode(debugNode)

        self.mondePhysique = BulletWorld()
        self.mondePhysique.setGravity(Vec3(0, 0, -9.81))
        self.mondePhysique.setDebugNode(self.debugNP.node())
        taskMgr.add(self.updatePhysics, "updatePhysics")

        taskMgr.add(self.updateCarte, "updateCarte")

    def setupCamera(self):
        #On doit désactiver le contrôle par défaut de la caméra autrement on ne peut pas la positionner et l'orienter
        self.pandaBase.disableMouse()

        #Le flag pour savoir si la souris est activée ou non n'est pas accessible
        #Petit fail de Panda3D
        taskMgr.add(self.updateCamera, "updateCamera")
        self.setupTransformCamera()


    def setupTransformCamera(self):
        #Défini la position et l'orientation de la caméra
        self.positionBaseCamera = Vec3(0,-18,32)
        camera.setPos(self.positionBaseCamera)
        #On dit à la caméra de regarder l'origine (point 0,0,0)
        camera.lookAt(render)

    def setupMap(self):
        self.map = Map(self,self.mondePhysique,self.idNiveau,self.leDTOMap, self.leDTOUsers, self.username1, self.username2,self.listeArmes)
        #On construire la carte comme une coquille, de l'extérieur à l'intérieur
        #Décor et ciel
        self.map.construireDecor(camera)
        #Plancher de la carte
        self.map.construirePlancher()
        #Murs et éléments de la map

    def setupLightAndShadow(self):
        #Lumière du skybox
        plight = PointLight('Lumiere ponctuelle')
        plight.setColor(VBase4(1,1,1,1))
        plnp = render.attachNewNode(plight)
        plnp.setPos(0,0,0)
        camera.setLight(plnp)

        #Simule le soleil avec un angle
        dlight = DirectionalLight('Lumiere Directionnelle')
        dlight.setColor(VBase4(0.8, 0.8, 0.6, 1))
        dlight.get_lens().set_fov(75);
        dlight.get_lens().set_near_far(0.1, 60);
        dlight.get_lens().set_film_size(30,30);
        dlnp = render.attachNewNode(dlight)
        dlnp.setPos(Vec3(-2,-2,7))
        dlnp.lookAt(render)
        render.setLight(dlnp)

        #Lumière ambiante
        alight = AmbientLight('Lumiere ambiante')
        alight.setColor(VBase4(0.25, 0.25, 0.25, 1))
        alnp  = render.attachNewNode(alight)
        render.setLight(alnp)

        #Ne pas modifier la valeur 1024 sous peine d'avoir un jeu laid ou qui lag
        dlight.setShadowCaster(True, 1024,1024)
        #On doit activer l'ombre sur les modèles
        render.setShaderAuto()

    def setupControle(self,):
        #Créer le contrôle
        #A besoin de la liste de tank pour relayer correctement le contrôle
        self.inputManager = InputManager(self.map.listTank,self.debugNP,self.pandaBase)
        self.accept("initCam",self.setupTransformCamera)

    def setupInterface(self):
        self.interfaceTank = []
        self.interfaceTank.append(InterfaceTank(0,self.map.listTank[0].couleur))
        self.interfaceTank.append(InterfaceTank(1,self.map.listTank[1].couleur))

        self.interfaceMessage = InterfaceMessage(self)

    def callBackDebutPartie(self):
        #Quand le message d'introduction est terminé, on permet aux tanks de bouger
        self.inputManager.debuterControle()

    #Mise à jour du moteur de physique
    def updateCamera(self,task):
        #On ne touche pas à la caméra si on est en mode debug
        if(self.inputManager.mouseEnabled):
            return task.cont

        vecTotal = Vec3(0,0,0)
        distanceRatio = 1.0
        if (len(self.map.listTank) != 0):
            for tank in self.map.listTank:
                vecTotal += tank.noeudPhysique.getPos()
            vecTotal = vecTotal/len(self.map.listTank)

        vecTotal.setZ(0)
        camera.setPos(vecTotal + self.positionBaseCamera)
        return task.cont

    #Mise à jour du moteur de physique
    def updatePhysics(self,task):
        dt = globalClock.getDt()
        messenger.send("appliquerForce")
        self.mondePhysique.doPhysics(dt)
        #print(len(self.mondePhysique.getManifolds()))

        #Analyse de toutes les collisions
        for entrelacement in self.mondePhysique.getManifolds():
            node0 = entrelacement.getNode0()
            node1 = entrelacement.getNode1()
            self.map.traiterCollision(node0, node1)
        return task.cont

    def updateCarte(self,task):

        self.map.update(task.time)
        return task.cont

    def setupBalance(self):
        unDAO=DAOOracleBalance()
        if Connexion().getCurseur()!=None:
            self.unDTO=unDAO.read()

    def ajoutFavori(self,identifiant):
        self.unDAOUser=DAOOracleUsers()
        if identifiant==0:
            idJoueur=self.leDTOUsers.getIdUser(self.username1)
        else:
            idJoueur=self.leDTOUsers.getIdUser(self.username2)
        self.unDAOUser.ajoutAuFavori(self.idNiveau,idJoueur)


    def exit(self):
        Connexion.seDeconnecter()
        exit()
        #exitFunc() panda3d