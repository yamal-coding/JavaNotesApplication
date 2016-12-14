from Controller import Controller
from DataBase import DataBase
from Modelo import Modelo

notesFile = "notes.xml"

dataBase = DataBase(notesFile)
modelo = Modelo(dataBase)
controller = Controller(modelo)

controller.loadNotes()
controller.createNote('Note 1', 'This is the note one')