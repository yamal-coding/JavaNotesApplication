package vista;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JButton;
import javax.swing.JList;

import observer.Observer;
import control.Controlador;

import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.GridLayout;

import logica.Nota;
import javax.swing.JScrollPane;

public class Vista extends JFrame implements Observer, ActionListener {

	private JPanel contentPane;
	private JButton editNoteButton;
	private JButton addNoteButton;
	private JButton deleteNoteButton;
	private JTextPane notaTextPane;
	
	private DefaultListModel<Nota> listModel;
	private JList<Nota> notesList;

	private Controlador c;
	private JScrollPane scrollPane;
	
	/**
	 * Create the frame.
	 */
	public Vista(Controlador c) {
		super("Notes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		this.setLocation(750, 120);
		initGUI();
		
		this.c = c;
		this.c.addObserver(this);
		c.loadNotes();
	}

	private void initGUI() {
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel buttonsPanel = new JPanel();
		contentPane.add(buttonsPanel, BorderLayout.SOUTH);
		
		editNoteButton = new JButton("Edit note");
		editNoteButton.setName("Edit");
		editNoteButton.addActionListener(this);
		editNoteButton.setEnabled(false);
		buttonsPanel.add(editNoteButton);
		
		addNoteButton = new JButton("Add note");
		addNoteButton.setName("Add");
		addNoteButton.addActionListener(this);
		buttonsPanel.add(addNoteButton);
		
		deleteNoteButton = new JButton("Delete Note");
		deleteNoteButton.setName("Delete");
		deleteNoteButton.addActionListener(this);
		deleteNoteButton.setEnabled(false);
		buttonsPanel.add(deleteNoteButton);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		listModel = new DefaultListModel<Nota>();
		
		notesList = new JList<Nota>(listModel);
		notesList.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Nota n = (Nota) notesList.getSelectedValue();
				
				if (n != null){
					notaTextPane.setText(n.getContenido());
					deleteNoteButton.setEnabled(true);
					editNoteButton.setEnabled(true);
				}
			}
		});
		//panel.add(notesList);
		
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(notesList);
		panel.add(scrollPane);
		
		notaTextPane = new JTextPane();
		notaTextPane.setEditable(false);
		panel.add(notaTextPane);
		notaTextPane.setBackground(new Color(0, 255, 102));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		JButton b = (JButton) arg0.getSource();
		String opcion = b.getName();
		
		if (opcion.equals("Add")){
			this.setEnabled(false);
			AddNote dialog = new AddNote(c);
		}
		else if (opcion.equals("Delete")){
			Nota n = (Nota) notesList.getSelectedValue();
			
			if (n != null){
				int index = (Integer) notesList.getSelectedIndex();
				
				c.deleteNote(n.getId(), index);
				
				if (listModel.isEmpty())
					editNoteButton.setEnabled(false);
			}
			
			deleteNoteButton.setEnabled(false);
		}
		else if (opcion.equals("Edit")){
			Nota n = (Nota) notesList.getSelectedValue();
			
			if (n != null){
				int index = (Integer) notesList.getSelectedIndex();
				
				this.setEnabled(false);
				EditNote dialog = new EditNote(c, n, index);
			}
			
			editNoteButton.setEnabled(true);
		}
	}

	@Override
	public void onAddNote(Nota n) {
		this.setEnabled(true);
		
		listModel.addElement(n);
		notesList.setSelectedValue(n, true);
		notaTextPane.setText(n.getContenido());
	}
	
	@Override
	public void onOperationError(){
		if (!this.isEnabled())
			this.setEnabled(true);
	}
	
	@Override
	public void onOperationCancelled(){
		if (!this.isEnabled())
			this.setEnabled(true);
	}
	
	@Override
	public void onDeleteNote(int index){
		listModel.remove(index);
		notaTextPane.setText("");
	}
	
	@Override
	public void onEditNote(Nota n, int index){
		this.setEnabled(true);
		
		listModel.remove(index);
		
		listModel.addElement(n);
		notesList.setSelectedValue(n, true);
		notaTextPane.setText(n.getContenido());				
	}
}
