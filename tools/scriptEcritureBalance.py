#-*- coding: utf-8 -*-
import dependencies
import time
from internal.DAO.DAOCsvBalance import *
from internal.DAO.DAOOracleBalance import *

Connexion().seConnecter()
leDTOBalance= DTOBalance()
leDAOOracleBal=DAOOracleBalance()
unDAOCSVBal=DAOCSVBalance()

leDTOBalance=unDAOCSVBal.read()
leDAOOracleBal.update(leDTOBalance)
Connexion().seDeconnecter()
time.sleep(10)