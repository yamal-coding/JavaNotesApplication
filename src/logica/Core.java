package logica;

import java.util.ArrayList;

import observer.Observer;

public class Core {
	private Observer o; 
	private DataBase bd;
	
	public Core(){
		bd = new DataBase();
	}
	
	public void addObserver(Observer o){
		this.o = o;
	}
	
	public void addNote(Nota n, boolean ok){
		if (ok){
			if (bd.saveNote(n))
				o.onAddNote(n);
			else
				o.onOperationError();
		}
		else
			o.onOperationCancelled();
	}
	
	public void deleteNote(String id, int index){
		if (bd.deleteNote(id))
			o.onDeleteNote(index);
		else
			o.onOperationError();
	}
	
	public void editNote(Nota n, int index, boolean ok){
		if (ok) {
			if (bd.updateNote(n))
				o.onEditNote(n, index);
			else
				o.onOperationError();
		}
		else
			o.onOperationCancelled();
	}
	
	public void loadNotes(){
		ArrayList<Nota> notas = bd.loadNotes();
		
		if (notas == null)
			o.onOperationCancelled();
		else
			for (Nota n : notas)
				o.onAddNote(n);
	}
}
