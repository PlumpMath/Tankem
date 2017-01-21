#-*- coding: utf-8 -*-
#ouvrir avec python27: C:\Python27\python.exe selectionnerFichier.py
from Tkinter import Tk
from tkFileDialog import askopenfilename


def choisirFichierCSV():
	#Empêche une fenêtre Tkinter d'ouvrir
	Tk().withdraw()

	nomFichier = askopenfilename(filetypes=[("CSV Files","*.csv"),("All files","*")], defaultextension="*")
	return nomFichier

