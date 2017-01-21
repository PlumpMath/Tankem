#-*- coding: utf-8 -*-

import dependencies

from internal.DAO.DAOCsvBalance import *
from internal.DAO.DAOOracleBalance import *

Connexion().seConnecter()
leDTOBalance= DTOBalance()
leDAOOracleBal=DAOOracleBalance()
unDAOCSVBal=DAOCSVBalance()

leDTOBalance=leDAOOracleBal.read()

unDAOCSVBal.create(leDTOBalance)
Connexion().seDeconnecter()