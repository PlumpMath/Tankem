# -*- coding: utf-8 -*-
from util import *

import sys
from panda3d.bullet import *
from panda3d.core import *
from direct.interval.IntervalGlobal import *
from direct.showbase import DirectObject
from internal.DAO.DAOStatistiques import *
import math


class Tank(DirectObject.DirectObject):
    def __init__(self,gamelogic, identifiant,couleur,mondePhysique, DTOStatistiques):
        #################################################AJOUTER ###############################################
        self.gameLogic = gamelogic
        self.charsVitese=self.gameLogic.unDTO.getValue("charsVitese")
        self.charsVitesseRotation=self.gameLogic.unDTO.getValue("charsVitesseRotation")
        self.charsPointsDeVie=self.gameLogic.unDTO.getValue("charsPointsDeVie")
        self.canonTempsRecharge=self.gameLogic.unDTO.getValue("canonTempsRecharge")
        self.mitrailletteTempsRecharge=self.gameLogic.unDTO.getValue("mitrailletteTempsRecharge")
        self.grenadeTempsRecharge=self.gameLogic.unDTO.getValue("grenadeTempsRecharge")
        self.shotgunTempsRecharge=self.gameLogic.unDTO.getValue("shotgunTempsRecharge")
        self.shotgunOuvertureFusil=self.gameLogic.unDTO.getValue("shotgunOuvertureFusil")
        self.piegeTempsRecharge=self.gameLogic.unDTO.getValue("piegeTempsRecharge")
        self.missileGuideTempsRecharge=self.gameLogic.unDTO.getValue("missileGuideTempsRecharge")
        self.springTempsRecharge=self.gameLogic.unDTO.getValue("springTempsRecharge")
        self.springVitesseInitialeSaut=self.gameLogic.unDTO.getValue("springVitesseInitialeSaut")

        #######AJOUTER POUR LA PHASE 4:

        self.armeActif=""
        self.accept("stat dmg",self.updateDommage)

        self.DTOStatistiques = DTOStatistiques
        #Défini les variables pour avancer et tourner
        self.speed = Vec3(0,0,0)
        self.omega = 0.0
        self.pointDeVieMax = self.charsPointsDeVie
        self.pointDeVie = self.pointDeVieMax

        self.etat = "inactif"
        self.hackRecuperation = False
        self.couleur = couleur

        self.debloquerTir()

        #Défini les armes de base
        self.armePrimaire = "Canon"
        self.armeSecondaire = "AucuneArme"

        self.identifiant = identifiant
        # On charge le modèles
        self.modele = loader.loadModel("../asset/Tank/tank")
        #On réduit sa taille un peu...
        self.modele.setScale(0.75,0.75,0.75)
        #On multiple la couleur de la texture du tank par ce facteur. Ça permet de modifier la couleur de la texture du tank
        self.modele.setColorScale(couleur.getX(),couleur.getY(),couleur.getZ(),1)

        #On ajoute une boite
        shape = BulletBoxShape(Vec3(0.6, 0.75, 0.3))
        #shape = BulletCapsuleShape(0.3, 0.75, ZUp)

        #On ajoute un contrôlleur de personnage
        self.playerNode = BulletCharacterControllerNode(shape, 0.4, 'ControlleurTank_' + str(self.identifiant))
        self.noeudPhysique = render.attachNewNode(self.playerNode)

        #Pour ne pas que le char soit entré dans le sol lors de son apparition, on le soulève un peu
        self.noeudPhysique.setZ(1.0)
        mondePhysique.attachCharacter(self.noeudPhysique.node())
        #On reparente le modele au noeud physique afin qu'il le suive
        self.modele.reparentTo(self.noeudPhysique)

        #Ajustement de la hauteur du modèle pour que la physique soit vis-à-vis le modèle
        self.modele.setZ(-0.25)

        self.noeudPhysique.setTag("EntiteTankem","Tank")
        self.noeudPhysique.setTag("IdTank",str( self.identifiant))

    def traiterCommande(self,message):
        directionHaut = Vec3(0,1,0)
        directionBas = Vec3(0,-1,0)
        directionGauche = Vec3(-1,0,0)
        directionDroite = Vec3(1,0,0)

        #Devrait être plus simple, mais un il y a un bug si je laisse une touche enfoncée au départ
        #On contounr le problème

        if(message == "avance"):
            self.speed += directionHaut
        elif(message == "avance-stop"):
            self.speed -= directionHaut
        elif(message == "recule"):
            self.speed += directionBas
        elif(message == "recule-stop"):
            self.speed -= directionBas
        elif(message == "tourne-gauche"):
            self.speed += directionGauche
        elif(message == "tourne-gauche-stop"):
            self.speed -= directionGauche
        elif(message == "tourne-droit"):
            self.speed += directionDroite
        elif(message == "tourne-droit-stop"):
            self.speed -= directionDroite
        #Le mouvement doit être directement bloqué à partir de la fonction
        #traiteMouvement sous peine d'avoir un bug en commencant le jeu
        if(self.etat != "actif"): 
            return

        if(message == "arme-primaire"):
            self.attaquer(self.armePrimaire)
        elif(message == "arme-secondaire"):
            self.attaquer(self.armeSecondaire)
        elif(message == "exploser-balle"):
            messenger.send("detonateur-explosion", [self.identifiant])

    def attaquer(self, nomArme):
        print"nomArme"
        print nomArme

        #Bloque le tir des balles si on est en train de recharger
        if(self.bloquerTir):
            return

        hauteurCanon = Vec3(0,0,0.5)
        distanceCanon = 2.2

        hauteurGrenade = Vec3(0,0,0.7)
        delaiArme = 0

        distanceDerriere = 1.9

        directionQuePointeLeTank = render.getRelativeVector(self.noeudPhysique, Vec3.forward())
        directionDroite = render.getRelativeVector(self.noeudPhysique, Vec3.right())
        directionGauche = render.getRelativeVector(self.noeudPhysique, Vec3.left())

        if(nomArme == "Canon"):
            messenger.send("tirerCanon", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon + directionQuePointeLeTank * distanceCanon, directionQuePointeLeTank])
            delaiArme = self.canonTempsRecharge
        elif(nomArme == "Grenade"):
            messenger.send("lancerGrenade", [self.identifiant,self.noeudPhysique.getPos() + hauteurGrenade, directionQuePointeLeTank])
            delaiArme = self.grenadeTempsRecharge
        elif(nomArme == "Mitraillette"):
            #Tir une balle mais moins de délai pour tirer
            messenger.send("tirerMitraillette", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon + directionQuePointeLeTank * distanceCanon, directionQuePointeLeTank])
            delaiArme = self.mitrailletteTempsRecharge
        elif(nomArme == "Piege"):
            messenger.send("deposerPiege", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon - directionQuePointeLeTank * distanceDerriere, - directionQuePointeLeTank])
            delaiArme = self.piegeTempsRecharge
        elif(nomArme == "Shotgun"):
            messenger.send("tirerShotgun", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon + directionQuePointeLeTank * distanceCanon, directionQuePointeLeTank])
            ouvertureFusil = self.shotgunOuvertureFusil
            directionDroiteDiagonale = directionQuePointeLeTank + directionQuePointeLeTank + directionDroite * ouvertureFusil
            directionDroiteDiagonale.normalize()
            directionGaucheDiagonale = directionQuePointeLeTank + directionQuePointeLeTank + directionGauche * ouvertureFusil
            directionGaucheDiagonale.normalize()
            messenger.send("tirerShotgun", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon + directionDroiteDiagonale * distanceCanon, directionDroiteDiagonale])
            messenger.send("tirerShotgun", [self.identifiant,self.noeudPhysique.getPos() + hauteurCanon + directionGaucheDiagonale * distanceCanon, directionGaucheDiagonale])
            delaiArme = self.shotgunTempsRecharge
        elif(nomArme == "Guide"):
            messenger.send("lancerGuide", [self.identifiant,self.noeudPhysique.getPos() + hauteurGrenade, directionQuePointeLeTank])
            delaiArme = self.missileGuideTempsRecharge
        elif(nomArme == "Spring"):
            self.jump()
            delaiArme = self.springTempsRecharge
        elif(nomArme == "AucuneArme"):
            #Ne fais rien
            pass
        #Pour ajouter le nombre de fois utiliser dans le DTO
        
        self.DTOStatistiques.modifierDictArmeUtilise(self.identifiant,nomArme)


        self.armeActif = nomArme

        #Va bloquer le tir des balles le temps de la recharge
        self.bloquerTir = True
        attendre = Wait(delaiArme)
        fonctionDebloquer = Func(self.debloquerTir)
        sequenceBlloquageTir = Sequence(attendre,fonctionDebloquer)
        sequenceBlloquageTir.start()

        messenger.send("effetRecharge", [self.identifiant,delaiArme])

    def debloquerTir(self):
        self.bloquerTir = False

    def jump(self):
        self.playerNode.setMaxJumpHeight(0.1)
        self.playerNode.setJumpSpeed(self.springVitesseInitialeSaut)
        self.playerNode.setGravity(40)
        self.playerNode.doJump()
        print "Jumping and stuff"

    def tombe(self, mondePhysique):
        self.elimineJoueur(mondePhysique)

        #On le fait tourner un peu
        self.noeudPhysiqueExplosion.node().applyTorqueImpulse(YUp * 1)

        #On le laisse tomber :-D On désactive la collision avec le plancher
        self.noeudPhysiqueExplosion.setCollideMask(BitMask32.allOff())

    def explose(self, mondePhysique):
        self.elimineJoueur(mondePhysique)
        #On lui donne une petite poussé car c'est drôle!
        self.noeudPhysiqueExplosion.node().applyImpulse(ZUp * 5,Point3(-0.5,-0.5,0))


    def elimineJoueur(self, mondePhysique):
        if(self.etat != "actif"):
            return
        self.etat = "inactif"
        self.speed = Vec3(0,0,0)
        self.omega = 0.0

        self.changerPointDeVie(0)

        #On devrait récupérer l'ancienne forme et non s'en créer une
        forme = BulletBoxShape(Vec3(0.6, 0.75, 0.3))

        #On ajoute une forme physique standard
        explosionNoeud = BulletRigidBodyNode("TankExplosion")
        explosionNoeud.addShape(forme)
        self.noeudPhysiqueExplosion = render.attachNewNode(explosionNoeud)
        self.noeudPhysiqueExplosion.node().setMass(3.0)
        self.noeudPhysiqueExplosion.setTransform(self.noeudPhysique.getTransform())
        self.noeudPhysiqueExplosion.setZ(self.noeudPhysiqueExplosion.getZ() + 1.0)
        self.modele.reparentTo(self.noeudPhysiqueExplosion)
        mondePhysique.attachRigidBody(explosionNoeud)

        mondePhysique.removeCharacter(self.playerNode)
        self.playerNode = None

        messenger.send("MortJoueur",[self.identifiant])
        messenger.send("tankElimine", [self.identifiant])

    def prendDommage(self, dommage, mondePhysique):
        #Chaque collision détectée nous fait perdre un point de vie
        self.changerPointDeVie(self.pointDeVie - dommage)
        self.modele.setColorScale(self.couleur.getX(),self.couleur.getY(),self.couleur.getZ(),1)
        #Vérifie si le tank explose
        if(self.pointDeVie <= 0):
            self.explose(mondePhysique)

        messenger.send("stat dmg",[self.identifiant,dommage])



    def changerPointDeVie(self, nouvelleValeur):
        self.pointDeVie = nouvelleValeur

        #On prévient l'interface graphique du changement
        pointDeVieSurCent = 100 * self.pointDeVie / self.pointDeVieMax
        messenger.send("effetPointDeVie", [self.identifiant,pointDeVieSurCent])


    def recupereItem(self, armeId):
        #ATTENTION: pour une raison inconnue, la récupération de l'item est détectée 2 fois...
        #On fait un beau hack...
        if(not self.hackRecuperation):
            self.hackRecuperation = True
            return

        if(self.hackRecuperation):
            self.hackRecuperation = False
            #Assigne une nouvelle arme à l'arme secondaire
            self.armeSecondaire = armeId

#Pour le temps trouver le temps
    def traiteMouvement(self):
        if (self.playerNode != None):

            #Si on a un petit mouvement, on ne bouge pas le tank
            #OU que le tank n'est pas actif
            if(self.speed.lengthSquared() < 0.2 or self.etat != "actif"):
                self.playerNode.setLinearMovement(0.0, False)
                self.playerNode.setAngularMovement(0.0)
            else:
                speedCopy = Vec3(self.speed)
                speedCopy.normalize()

                vitesseAvancer = self.charsVitese
                vitesseMaxTourner = self.charsVitesseRotation
                #On bouge le joueur dans la bonne direction
                #Renormalize le vecteur pour ne pas avoir un bug comme le Quake 3
                #qui nous permettrait de bouger en diagonal rapidement
                self.playerNode.setLinearMovement(speedCopy * vitesseAvancer, False)

                #Cacul un vectoriel pour tourner le tank quand on bouge
                #C'est plutôt complexe. On se base sur la théorie du pilotage
                # ("steering behavior"). Vous trouverez plein d'articles sur le sujet
                directionQuePointeLeTank = render.getRelativeVector(self.noeudPhysique, Vec3.forward())
                signeAngle = 0
                produitVectoriel = directionQuePointeLeTank.cross(speedCopy)
                angleDegre = directionQuePointeLeTank.angleDeg(speedCopy)

                #Décide de la direction de rotation
                if(produitVectoriel.getZ() > 0.01):
                    signeAngle = 1.0

                if(produitVectoriel.getZ() < -0.01):
                    signeAngle = -1.0

                #Réduire progressivement la rotation selon l'angle
                #avec une courbe en racine carrée
                angleMax = 180
                facteur = angleDegre / angleMax
                vitesseTourner = vitesseMaxTourner * math.sqrt(facteur)

                angleCritiqueIntermediaire = 5.0
                if(angleDegre < angleCritiqueIntermediaire):
                    vitesseIntermediaireTourner = vitesseMaxTourner * 0.5
                    vitesseTourner = vitesseIntermediaireTourner * facteur

                #Arrêter la rotation selon l'angle
                angleCritiqueDegre = 1.0
                if(angleDegre < angleCritiqueDegre):
                    vitesseTourner = 0.0
                    signeAngle = 0.0
                self.playerNode.setAngularMovement(signeAngle * vitesseTourner)

    def updateDommage(self,idVictime,domageEnvoye):
        posX = self.noeudPhysique.getX()
        posY = self.noeudPhysique.getY()
        
        #Le tank actuel est la victime.
        if idVictime == self.identifiant: 
            print"Le tank "+str(self.identifiant)+" est la victime"
            #Le total des dommages recu est ajouté
            self.DTOStatistiques.modifierDommageRecu(self.identifiant, domageEnvoye)
            #Ajouter le dommage dans la case
            self.DTOStatistiques.ajtDommageRecu(self.identifiant,posX,posY,domageEnvoye)
        #Le tank actuel est celui fait mal.
        else:
            print"le tank " +str(self.identifiant)+ " est l'harceleur"
            #Le total des dommages fait est ajouté
            self.DTOStatistiques.modifierDommageFait(self.identifiant, domageEnvoye)
            #Ajouter le dommage selon l'Arme.
            self.DTOStatistiques.modifierDictArmeDommage(self.identifiant,self.armeActif,domageEnvoye)
            #Ajouter le dommage dans la case
            self.DTOStatistiques.ajtDommageDonnee(self.identifiant,posX,posY,domageEnvoye)



