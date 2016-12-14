package control;

import observer.Observer;
import logica.Core;
import logica.Nota;

public class Controlador {
	private Core core;
	
	public Controlador(Core db){
		this.core = db;
	}
	
	public void addObserver(Observer o){
		core.addObserver(o);
	}
	
	public void loadNotes(){
		core.loadNotes();
	}
	
	public void addNote(Nota n, boolean ok){
		core.addNote(n, ok);
	}
	
	public void deleteNote(String id, int index){
		core.deleteNote(id, index);
	}
	
	public void editNote(Nota n, int index, boolean ok){
		core.editNote(n, index, ok);
	}
}
