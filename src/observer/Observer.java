package observer;

import logica.Nota;

public interface Observer {
	public void onAddNote(Nota n);
	public void onDeleteNote(int index);
	public void onEditNote(Nota n, int index);
	public void onOperationError();
	public void onOperationCancelled();
}
