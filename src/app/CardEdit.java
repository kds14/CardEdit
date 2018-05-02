package app;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;


import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class CardEdit {

	private JFrame frame;
	private static final int width = 640;
	private static final int height = 300;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CardEdit window = new CardEdit();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public CardEdit() throws ParserConfigurationException {
		initialize(new XMLParser());
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws ParserConfigurationException
	 */
	@SuppressWarnings("serial")
	private void initialize(final XMLParser xmlParser)
			throws ParserConfigurationException {
		// initialize the main frame
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, width, height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// split the frame into two
		 SplitPane splitPane = new SplitPane(width / 3);
		//JSplitPane splitPane = new JSplitPane();
		splitPane.setDividerLocation(width / 3);
		frame.getContentPane().add(splitPane);

		// set up the menu bar
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		final JFileChooser fc = new JFileChooser();
		File cf = new File(".");
		fc.setCurrentDirectory(cf);

		// set up the last in the left panel
		final JList<String> list = new JList<>();
		splitPane.setLeftComponent(list);
		DefaultListCellRenderer renderer = (DefaultListCellRenderer) list
				.getCellRenderer();
		renderer.setHorizontalAlignment(SwingConstants.CENTER);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// set up table
		table = new JTable();
		table.setFont(new Font("Tahoma", Font.PLAIN, 11));
		table.setCellSelectionEnabled(true);
		table.setFillsViewportHeight(true);

		final DefaultTableModel tableModel = new DefaultTableModel() {
			Class[] columnTypes = new Class[] { String.class, String.class };

			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}

			boolean[] columnEditables = new boolean[] { false, true };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		table.setModel(tableModel);
		splitPane.setRightComponent(table);

		// on list item selection, display details
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				JList l = (JList) e.getSource();
				Card c = cards.get(l.getSelectedIndex());
				tableModel.setDataVector(c.getDataVector(), new Object[] { "",
						"Card Data" });
				table.setModel(tableModel);
				table.getColumnModel().getColumn(0).setMaxWidth(64);
				table.getColumnModel().getColumn(0).setResizable(false);
				table.getColumnModel().getColumn(1).setResizable(false);
			}
		});
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		// on file load, parse XML file
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fc.showOpenDialog(frame);
				File fi = fc.getSelectedFile();
				try {
					cards = xmlParser.parseDocument(fi);
				} catch (SAXException | IOException e) {
					JOptionPane.showMessageDialog(null, "Could not parse file!");
					e.printStackTrace();
				}

				DefaultListModel<String> m = new DefaultListModel<>();
				for (Card c : cards) {
					m.addElement(c.getId());
				}
				list.setModel(m);
			}
		});
		mnFile.add(mntmLoad);

		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		// TODO add save button functionality
		
		frame.getContentPane().setLayout(
				new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
	}
}