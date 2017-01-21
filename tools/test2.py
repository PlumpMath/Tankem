#-*- coding: utf-8 -*-
import dependencies
from internal.DAO.DAOOracleMap import * 

unDto=DTOMap()
unDAO=DAOOracleMap()
unDto=unDAO.read()
print "----------------------------------"
print unDto.getValueNiveau(47)
print unDto.getValueNiveau(47)[1]
print unDto.getCasesNiveau(47)
#print unDto.getValueNiveau(3)
#print unDto.getCasesNiveau(1)