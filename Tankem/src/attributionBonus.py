## -*- coding: utf-8 -*-
from util import *

from direct.showbase.ShowBase import ShowBase
from direct.gui.OnscreenText import OnscreenText 
from direct.gui.DirectGui import *
from panda3d.core import *
from direct.interval.LerpInterval import *
from direct.interval.IntervalGlobal import *
import time
from random import randint
from internal.DAO.DAOOracleUsers import *
 
class AttributionBonus(ShowBase):
    def __init__(self):
        self.accept("AttribuerBonus",self.attributionBonus)
        self.nbBonus=1
        self.listeBonus=[]
        self.leDAOUsers=DAOOracleUsers()

    def bonusRandom(self,vie,temps):
        tempsPartie=temps
        pointVie=vie
        if tempsPartie<60:
            self.nbBonus+=1
        if pointVie==100:
            self.nbBonus+=3
        elif pointVie>50:
            self.nbBonus+=1

    def leRandom(self):
        for i in range(self.nbBonus):
            self.listeBonus.append(randint(1,6))

    def attributionBonus(self,idGagnant,nomGagnant,vie,temps):
        self.bonusRandom(vie,temps)
        self.leRandom()
        print self.listeBonus
        self.leDAOUsers.updateBonusFinPartie(idGagnant,self.listeBonus)
        messenger.send("MessageBonus",[self.listeBonus,nomGagnant])