package notes;

import java.awt.EventQueue;

import control.Controlador;
import logica.Core;
import vista.Vista;

public class Main {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Core db = new Core();
					Controlador c = new Controlador(db);
					Vista frame = new Vista(c);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
