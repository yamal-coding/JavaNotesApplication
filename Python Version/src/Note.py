class Note:
	def __init__(self, name, content, id=""):
		if id != "":
			self.__id = id
		else:
			self.__id = name #concatenado con la hora
		self.__name = name
		self.__content = content

	def getID(self):
		return self.__id

	def getName(self):
		return self.__name

	def getContent(self):
		return self.__content

	def __str__(self):
		return self.__name