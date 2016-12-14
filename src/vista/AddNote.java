package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import control.Controlador;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import logica.Nota;

public class AddNote extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField nombreTextField;
	
	private JButton okButton;
	private JLabel noteNameLabel;
	private JButton cancelButton;
	private JTextPane textoTextPane;
	
	private Controlador c;
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			AddNote dialog = new AddNote();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public AddNote(Controlador c) {
		initGUI();
		
		this.c = c;
	}

	private void initGUI() {
		setBounds(100, 100, 409, 266);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		
		noteNameLabel = new JLabel("Nombre:");
		contentPanel.add(noteNameLabel);
		
		
		nombreTextField = new JTextField();
		contentPanel.add(nombreTextField);
		nombreTextField.setColumns(10);
		
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	
		okButton = new JButton("OK");
		okButton.setActionCommand("OK");
		okButton.setName("OK");
		okButton.addActionListener(this);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);
			
		
		cancelButton = new JButton("Cancel");
		cancelButton.setActionCommand("Cancel");
		cancelButton.setName("Cancel");
		cancelButton.addActionListener(this);
		buttonPane.add(cancelButton);
			
		
		textoTextPane = new JTextPane();
		textoTextPane.setBackground(new Color(102, 255, 153));
		getContentPane().add(textoTextPane, BorderLayout.CENTER);
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b = (JButton)arg0.getSource();
		String opcion = b.getName();
		
		if (opcion.equals("OK")){
			String nombre = nombreTextField.getText();
			if (nombre.equals("")){
				JOptionPane.showMessageDialog(this, "La nota debe tener un nombre");
			}
			else {
				String contenido = textoTextPane.getText();
				Nota note = new Nota(nombre, contenido);
				c.addNote(note, true);
				dispose();
			}
		}
		else if (opcion.equals("Cancel")){
			c.addNote(null, false);
			dispose();
		}
		
	}

	
}
