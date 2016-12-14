import xml.etree.ElementTree as ET
import xml.dom.minidom as MD
from Note import Note

class DataBase:
	def __init__(self, file):
		self.file = file

	def loadNotes(self):
		#this function loads the list of notes from de xml file called self.file
		#it saves the notes in a dictionary called notes and returns it
		notes = []
		tree = ET.parse(self.file)
		root = tree.getroot()

		for child in root:
			id = child.get('id')
			name = child.find('name').text
			content = child.find('content').text
			
			newNote = Note(name, content, id)

			notes.append(newNote)

		return notes

	def createNote(self, note):
		#this function creates a new note on the xml file called self.file
		tree = ET.parse(self.file)
		root = tree.getroot()

		newNote = ET.SubElement(root, "note", id=note.getID())
		ET.SubElement(newNote, "name").text = note.getName()
		ET.SubElement(newNote, "content").text = note.getContent()

		tree.write(self.file) 


	def deleteNote(self, id):
		#this function deletes a note with the id parameter on the xml file called self.file
		tree = ET.parse(self.file)
		root = tree.getroot()

		for child in root:
			if child.get('id') == id:
				root.remove(child)
				break

		tree.write(self.file)


	def editNote(self, id, name, content):
		#this function edits a note with the id parameter on the xml file called self.file changing the old name and content
		#by the new ones
		tree = ET.parse(self.file)
		root = tree.getroot()

		for note in root:
			if note.get('id') == id:
				for child in note:
					if child.tag == 'name':
						child.text = name
					if child.tag == 'content':
						child.text = content
				break

		tree.write(self.file)
