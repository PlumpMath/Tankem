#-*- coding: utf-8 -*-
import cx_Oracle

class Connexion():
	#creation var static
	__dictPartage__ ={}
	def __init__(self):
		#partage de dictionnaire
		self.__dict__=self.__dictPartage__
		if not hasattr(self,"monCurseur"):
			self.monCurseur=None
			self.maConnexion=None
			#self.patate="Toi"
		

	def seConnecter(self):
		self.maConnexion = cx_Oracle.connect("e1232188","E","10.57.4.60/DECINFO.edu")
		#self.maConnexion = cx_Oracle.connect("e0728849","VVVvvv111","10.57.4.60/DECINFO.edu")
		#self.maConnexion = cx_Oracle.connect("e1041268","AAAaaa111","10.57.4.60/DECINFO.edu")

		print u"Connexion effectué"
		self.monCurseur= self.maConnexion.cursor()

			
	
	def seDeconnecter(self):
		print "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA!!!!!!!!!!!!!!!!!!!"
		self.monCurseur.close()
		self.maConnexion.close()
		


	def getCurseur(self):
	#	self.patate=self.patate+"ggggggg"
	#	print self.patate
		return self.monCurseur
		

