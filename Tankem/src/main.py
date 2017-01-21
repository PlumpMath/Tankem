## -*- coding: utf-8 -*-
#Ajout des chemins vers les librarires
from util import inclureCheminCegep
import sys
#Importe la configuration de notre jeu
from panda3d.core import loadPrcFile
loadPrcFile("config/ConfigTankem.prc")



#Module de Panda3D
from direct.showbase.ShowBase import ShowBase

#Modules internes
from gameLogic import GameLogic
from interface import *
import sys
sys.path.insert(0, '../../common')
from internal.DAO.Connexion import *
from internal.DAO.DAOOracleUsers import * 
 
class Tankem(ShowBase):
    def __init__(self):
        ShowBase.__init__(self)
        self.DAOUser=DAOOracleUsers()
        self.unDTOUsers=None
        self.textConnexion="Normal"
        try:
            Connexion().seConnecter()
            self.unDTOUsers=self.setupUsers()
        except:
            print "Erreur de connexion"
            self.textConnexion="Aucun"
        self.demarrer()

    def demarrer(self):
        self.gameLogic = GameLogic(self)
        #Commenter/décommenter la ligne de votre choix pour démarrer le jeu
        #Démarre dans le menu

        self.menuChoixNiveaux=InterfaceMenuChoixNiveau()
        self.menuAuthentification=InterfaceMenuAuthentification(self.unDTOUsers)

        self.menuPrincipal = InterfaceMenuPrincipal(self.textConnexion)
        #Démarre directement dans le jeu
        #messenger.send("DemarrerPartie")

    def setupUsers(self):
        leDTOUsers=DTOUser()
        leDTOUsers=self.DAOUser.read()
        return leDTOUsers
#Main de l'application.. assez simple!
app = Tankem()
app.run()