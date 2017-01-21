#-*- coding: utf-8 -*
class DTOUser():
	def __init__(self):
		self.dictoInfosUsers={}
		self.dictoQuestions={}
		self.dictoArmurie={}
		self.dictoBonusJoueurs={}


#Pour les infos de tous les users
	def insertUserDictionnaire(self,username, infoUser):
		self.dictoInfosUsers[username]=[]
		for info in infoUser:
			self.dictoInfosUsers[username].append(info)

#####Fonctions pour les Users
	def getTousUser(self):
		return self.dictoInfosUsers

	def getUser(self,username):
		return self.dictoInfosUsers.get(username)

	def getIdUser(self,username):
		return self.dictoInfosUsers.get(username)[0]

	def getUsernameUser(self,username):
		return self.dictoInfosUsers.get(username)[1]

	def getNomUser(self,username):
		return self.dictoInfosUsers.get(username)[2]
	
	def getPrenomUser(self,username):
		return self.dictoInfosUsers.get(username)[3]
	
	def getMdpUser(self,username):
		return self.dictoInfosUsers.get(username)[4]

	def getQAUser(self,username):
		return self.dictoInfosUsers.get(username)[5]
	
	def getRepAUser(self,username):
		return self.dictoInfosUsers.get(username)[6]
	
	def getQBUser(self,username):
		return self.dictoInfosUsers.get(username)[7]
	
	def getRepBUser(self,username):
		return self.dictoInfosUsers.get(username)[8]
	
	def getRougeUser(self,username):
		return self.dictoInfosUsers.get(username)[9]
	
	def getVertUser(self,username):
		return self.dictoInfosUsers.get(username)[10]
	
	def getBleuUser(self,username):
		return self.dictoInfosUsers.get(username)[11]




# Pour les questions
	def insertUneQuestion(self,idQ, laQuestion):
		self.dictoQuestions[idQ]=laQuestion

	def getToutesQuestions(self):
		return self.dictoQuestions

	def getQuestion(self,idQ):
		return self.dictoQuestions.get(idQ)


# Pour Armurie
	def insertUnArme(self,idArme, nomArme):
		self.dictoArmurie[idArme]=nomArme

	def getToutsArmes(self):
		return self.dictoArmurie

	def getUnArme(self,idArme):
		return self.dictoArmurie.get(idArme)

	def getIdArme(self, nomArme):
		#return self.dictoArmurie.get(nomArme)
		listeCle = list(self.dictoArmurie.keys())
		for cle in listeCle:
			if nomArme == self.getUnArme(cle):
				return cle

#Pour les Bonus
	def insertUnBonus(self, idJoueur, listeInfosDuBonus):
		if idJoueur not in self.dictoBonusJoueurs:
			self.dictoBonusJoueurs[idJoueur]=[]
		self.dictoBonusJoueurs[idJoueur].append(listeInfosDuBonus)

	def getTousLesBonus(self):
		return self.dictoBonusJoueurs

	def getToutsArmesJoueur(self,idJoueur):
		listeTousBonusJoueur=self.dictoBonusJoueurs.get(idJoueur)
		listeArmesAvecNom=[]
		for bonus in listeTousBonusJoueur:
			infoUnArme=[]
			infoUnArme.append(self.getUnArme(bonus[1]))
			infoUnArme.append(bonus[2])
			listeArmesAvecNom.append(infoUnArme)

		return listeArmesAvecNom

	def siUsernameExiste(self, username):
		if self.dictoInfosUsers.has_key(username):
			return True
		else:
			return False



