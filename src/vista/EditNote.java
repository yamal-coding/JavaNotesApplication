package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import logica.Nota;
import control.Controlador;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTextArea;

import java.awt.Color;

public class EditNote extends JDialog implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField noteNameTextField;
	private JLabel noteContentLabel;
	private JTextArea noteContentTextArea;
	
	private Controlador c;
	private Nota n;
	private int index;
	
	
	
	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			EditNote dialog = new EditNote();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Create the dialog.
	 */
	public EditNote(Controlador c, Nota n, int index) {
		this.c = c;
		this.n = n;
		this.index = index;
		
		initGUI();
	}

	private void initGUI() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		SpringLayout sl_contentPanel = new SpringLayout();
		contentPanel.setLayout(sl_contentPanel);
		
		JLabel noteNameLabel = new JLabel("Name:");
		sl_contentPanel.putConstraint(SpringLayout.NORTH, noteNameLabel, 21, SpringLayout.NORTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, noteNameLabel, -365, SpringLayout.EAST, contentPanel);
		contentPanel.add(noteNameLabel);
		
		noteNameTextField = new JTextField();
		sl_contentPanel.putConstraint(SpringLayout.NORTH, noteNameTextField, -3, SpringLayout.NORTH, noteNameLabel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, noteNameTextField, 6, SpringLayout.EAST, noteNameLabel);
		noteNameTextField.setText(this.n.getNombre());
		contentPanel.add(noteNameTextField);
		noteNameTextField.setColumns(10);
		{
			noteContentLabel = new JLabel("Content:");
			sl_contentPanel.putConstraint(SpringLayout.NORTH, noteContentLabel, 22, SpringLayout.SOUTH, noteNameLabel);
			sl_contentPanel.putConstraint(SpringLayout.EAST, noteContentLabel, 0, SpringLayout.EAST, noteNameLabel);
			contentPanel.add(noteContentLabel);
		}
		
		noteContentTextArea = new JTextArea();
		noteContentTextArea.setBackground(Color.GREEN);
		noteContentTextArea.setText(this.n.getContenido());
		sl_contentPanel.putConstraint(SpringLayout.NORTH, noteContentTextArea, -5, SpringLayout.NORTH, noteContentLabel);
		sl_contentPanel.putConstraint(SpringLayout.WEST, noteContentTextArea, 6, SpringLayout.EAST, noteContentLabel);
		sl_contentPanel.putConstraint(SpringLayout.SOUTH, noteContentTextArea, -10, SpringLayout.SOUTH, contentPanel);
		sl_contentPanel.putConstraint(SpringLayout.EAST, noteContentTextArea, -20, SpringLayout.EAST, contentPanel);
		contentPanel.add(noteContentTextArea);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				okButton.setName("OK");
				okButton.addActionListener(this);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.setName("Cancel");
				cancelButton.addActionListener(this);
				buttonPane.add(cancelButton);
			}
		}
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		String opcion = b.getName();
		
		if (opcion.equals("OK")){
			String nombre = noteNameTextField.getText();
			if (nombre.equals(""))
				JOptionPane.showMessageDialog(this, "La nota debe tener un nombre");
			else {
				Nota newNote = new Nota(nombre, noteContentTextArea.getText(), this.n.getId());
				c.editNote(newNote, index, true);
				dispose();
			}
		}
		else if (opcion.equals("Cancel")){
			c.editNote(null, this.index, false);
			dispose();
		}
		
	}
}
