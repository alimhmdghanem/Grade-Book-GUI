import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 * @author Ali Ghanem
 *
 */
public class MainFrame extends JFrame implements ActionListener {

	private StudentsList stdList;
	private Chart chart;
	private JTabbedPane tab;
	private JPanel list, menuPanel;
	private JScrollPane sc;
	private JTable table;
	private String[] columnNames = { "NAME", "LAST NAME", "ID", "QUIZ1", "QUIZ2", "PROJECT", "MIDTERM", "FINAL",
			"AVERAGE", "GRADE" };
	private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	private JMenuBar menus;
	private JMenu menuStudent, menuSort, menuFilter, menuExit;
	private JMenuItem mAdd, mAddRandom, mReadStudentFile, mRemove, mRemoveAll, mSortLastName, mSortId, mSortAverage,
			mFilter, mRemoveFilter, mHelp, mSave, mExit;

	public MainFrame() {

		stdList = new StudentsList();
		chart = new Chart(stdList);
		readSavedData();

		setLayout(new BorderLayout());

		// =================Menus============================================ //

		menuPanel = new JPanel();
		menuPanel.setLayout(new GridLayout(0, 1));

		menus = new JMenuBar();

		menuStudent = new JMenu("Student");
		menuSort = new JMenu("Sort");
		menuFilter = new JMenu("Filter");
		menuExit = new JMenu("Help and exit");

		mAdd = new JMenuItem("Add student");
		mAdd.addActionListener(this);
		mAddRandom = new JMenuItem("Add random students");
		mAddRandom.addActionListener(this);
		mReadStudentFile = new JMenuItem("Read 'Student' file");
		mReadStudentFile.addActionListener(this);
		mRemove = new JMenuItem("Remove student");
		mRemove.addActionListener(this);
		mRemoveAll = new JMenuItem("Remove all students");
		mRemoveAll.addActionListener(this);
		mSortLastName = new JMenuItem("Sort by last name");
		mSortLastName.addActionListener(this);
		mSortId = new JMenuItem("Sort by ID");
		mSortId.addActionListener(this);
		mSortAverage = new JMenuItem("Sort by average");
		mSortAverage.addActionListener(this);
		mFilter = new JMenuItem("Filter by letter grade");
		mFilter.addActionListener(this);
		mRemoveFilter = new JMenuItem("Remove filter");
		mRemoveFilter.addActionListener(this);
		mHelp = new JMenuItem("Help");
		mHelp.addActionListener(this);
		mSave = new JMenuItem("Save and exit");
		mSave.addActionListener(this);
		mExit = new JMenuItem("Exit without saving");
		mExit.addActionListener(this);

		menuStudent.add(mAdd);
		menuStudent.add(mAddRandom);
		menuStudent.add(mReadStudentFile);
		menuStudent.add(mRemove);
		menuStudent.add(mRemoveAll);
		menuSort.add(mSortLastName);
		menuSort.add(mSortId);
		menuSort.add(mSortAverage);
		menuFilter.add(mFilter);
		menuFilter.add(mRemoveFilter);
		menuExit.add(mHelp);
		menuExit.add(mSave);
		menuExit.add(mExit);

		menus.add(menuStudent);
		menus.add(menuSort);
		menus.add(menuFilter);
		menus.add(menuExit);

		menuPanel.add(menus);
		add(menuPanel, BorderLayout.NORTH);

		// =================Tabs============================================= //

		table = new JTable(tableModel);
		sc = new JScrollPane(table);
		// table row sorter will sort a specific column
		TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(table.getModel());
		table.setRowSorter(sorter);
		list = new JPanel();
		list.setLayout(new BorderLayout());
		list.add(sc);
		tab = new JTabbedPane();
		tab.addTab("List", list);
		tab.addTab("Chart", chart);

		// ================================================================== //

		add(tab);
		setTitle("Grade Book");

		setResizable(false);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(1024, 800);
		setVisible(true);
	}

	public void onExit() {
		int exit = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit without saving your data?",
				"Exit", JOptionPane.YES_NO_OPTION);
		if (exit == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	public void readSavedData() {
		try {
			Scanner s = new Scanner(new File("savedData.txt"));
			while (s.hasNext()) {
				String line = s.nextLine();
				String[] lineElements = line.split(",");
				Student st = new Student();
				st.setFirstName(lineElements[0]);
				st.setLastName(lineElements[1]);
				st.setID(lineElements[2]);
				st.setQuiz1(Double.parseDouble(lineElements[3]));
				st.setQuiz2(Double.parseDouble(lineElements[4]));
				st.setProject(Double.parseDouble(lineElements[5]));
				st.setMidterm(Double.parseDouble(lineElements[6]));
				st.setFinall(Double.parseDouble(lineElements[7]));
				st.setAverage(Double.parseDouble(lineElements[8]));
				st.setLetterGrade(lineElements[9]);
				stdList.getList().add(st);

			}
			s.close();
			getTableData();
			chart.getInfo(stdList);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void saveData() {
		try {
			PrintWriter writer = new PrintWriter(new FileOutputStream("savedData.txt"));
			for (int i = 0; i < stdList.getList().size(); i++) {
				String firstName = stdList.getList().get(i).getFirstName();
				String lastName = stdList.getList().get(i).getLastName();
				String id = stdList.getList().get(i).getID();
				double quiz1 = stdList.getList().get(i).getQuiz1();
				double quiz2 = stdList.getList().get(i).getQuiz2();
				double project = stdList.getList().get(i).getProject();
				double midterm = stdList.getList().get(i).getMidterm();
				double finalGrade = stdList.getList().get(i).getFinall();
				double average = stdList.getList().get(i).getAverage();
				String letterGrade = stdList.getList().get(i).getLetterGrade();
				writer.print(firstName + "," + lastName + "," + id + "," + quiz1 + "," + quiz2 + "," + project + ","
						+ midterm + "," + finalGrade + "," + average + "," + letterGrade + "\n");
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	public void getTableData() {
		for (int i = 0; i < stdList.getList().size(); i++) {
			String firstName = stdList.getList().get(i).getFirstName();
			String lastName = stdList.getList().get(i).getLastName();
			String id = stdList.getList().get(i).getID();
			double quiz1 = stdList.getList().get(i).getQuiz1();
			double quiz2 = stdList.getList().get(i).getQuiz2();
			double project = stdList.getList().get(i).getProject();
			double midterm = stdList.getList().get(i).getMidterm();
			double finall = stdList.getList().get(i).getFinall();
			double avreage = stdList.getList().get(i).getAverage();
			String letterGrades = stdList.getList().get(i).getLetterGrade();
			Object[] data = { firstName, lastName, id, quiz1, quiz2, project, midterm, finall, avreage, letterGrades };

			tableModel.addRow(data);
		}

	}

	public void filterGrade() {
		String grade = JOptionPane.showInputDialog(this, "Which grade do you want to filter?");
		if (grade != null) {
			tableModel.setRowCount(0);
			for (int i = 0; i < stdList.getList().size(); i++) {
				if (stdList.getList().get(i).getLetterGrade().equalsIgnoreCase(grade)) {
					String firstName = stdList.getList().get(i).getFirstName();
					String lastName = stdList.getList().get(i).getLastName();
					String id = stdList.getList().get(i).getID();
					double quiz1 = stdList.getList().get(i).getQuiz1();
					double quiz2 = stdList.getList().get(i).getQuiz2();
					double project = stdList.getList().get(i).getProject();
					double midterm = stdList.getList().get(i).getMidterm();
					double finall = stdList.getList().get(i).getFinall();
					double avreage = stdList.getList().get(i).getAverage();
					String letterGrade = stdList.getList().get(i).getLetterGrade();
					Object[] data = { firstName, lastName, id, quiz1, quiz2, project, midterm, finall, avreage,
							letterGrade };

					tableModel.addRow(data);
				}
			}
		}
	}

	public void deleteStudent() {
		String id = JOptionPane.showInputDialog(this, "Enter the ID of the student you want to remove from the list");
		if (id != null) {
			String message = "No such student was found.";
			for (int i = 0; i < stdList.getList().size(); i++) {
				if (stdList.getList().get(i).getID().equals(id)) {
					stdList.getList().remove(i);
					tableModel.removeRow(i);
					message = "Student has been deleted.";
					chart.getInfo(stdList);
				}
			}
			JOptionPane.showMessageDialog(this, message);
		}
	}

	public void clearAll() {
		stdList.getList().clear();

		tableModel.setRowCount(0);

		chart.getInfo(stdList);

		JOptionPane.showMessageDialog(this, "Student list has been cleared.");
	}

	public void addStudent() {
		JTextField txtFirstName = new JTextField();
		JTextField txtLastName = new JTextField();
		JTextField txtID = new JTextField();
		JTextField txtQuiz1 = new JTextField();
		JTextField txtQuiz2 = new JTextField();
		JTextField txtProject = new JTextField();
		JTextField txtMidterm = new JTextField();
		JTextField txtFinal = new JTextField();

		Object[] message = { "First Name:", txtFirstName, "Last Name:", txtLastName, "ID:", txtID, "Quiz 1 Grade:",
				txtQuiz1, "Quiz 2 Grade:", txtQuiz2, "Project Grade:", txtProject, "Midterm Grade:", txtMidterm,
				"Final Grade:", txtFinal, };

		int option = JOptionPane.showConfirmDialog(this, message, "Enter all student's properties",
				JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			String firstName = txtFirstName.getText();
			String lastName = txtLastName.getText();
			String id = txtID.getText();
			String quiz1Grade = txtQuiz1.getText();
			String quiz2Grade = txtQuiz2.getText();
			String projectGrade = txtProject.getText();
			String midtermGrade = txtMidterm.getText();
			String finalGrade = txtFinal.getText();
			if (firstName != null && lastName != null && id != null && quiz1Grade != null && quiz2Grade != null
					&& projectGrade != null && midtermGrade != null && finalGrade != null) {
				try {
					double qu1 = Double.parseDouble(quiz1Grade);
					double qu2 = Double.parseDouble(quiz2Grade);
					double pro = Double.parseDouble(projectGrade);
					double mid = Double.parseDouble(midtermGrade);
					double fin = Double.parseDouble(finalGrade);

					Student std = new Student(firstName, lastName, id, qu1, qu2, pro, mid, fin);

					// Add student to the list
					stdList.getList().add(std);

					// get table data
					tableModel.setRowCount(0);
					getTableData();

					// update chart info
					chart.getInfo(stdList);

					JOptionPane.showMessageDialog(this,
							"Student has been added successfully. Total number of students is "
									+ stdList.getList().size());
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(this, "An error ocurred. please try again.");
				}
			}
		}
	}

	public void addRandomStudent() {
		String stringLimit = JOptionPane.showInputDialog(this, "Hom many students do you to add?");

		if (stringLimit != null) {
			// convert the input string to integer
			int limit = Integer.parseInt(stringLimit);

			/*
			 * delete all the rows in the table model in order to avoid taking the same
			 * students more than one time
			 */
			tableModel.setRowCount(0);

			// generate the same number of students to the array list
			stdList.generateRandomStudents(limit);

			// add the students information to the table
			getTableData();

			// update the chart:
			chart.getInfo(stdList);

			JOptionPane.showMessageDialog(this,
					limit + " students were added. Total number of students is " + stdList.getList().size());
		}
	}

	public void readStudentFile() {
		stdList.readFile();
		getTableData();
		chart.getInfo(stdList);
	}

	public void showHelpMessage() {
		String msg = "You can add students by clicking on Student -> Add Student then enter the properties of the student and click Ok.\nBefore you exit you need to save your data by clicking on Help and exit -> Save and exit.";
		JOptionPane.showMessageDialog(this, msg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(mAdd)) {
			addStudent();
		}

		else if (e.getSource().equals(mAddRandom)) {
			addRandomStudent();
		}

		else if (e.getSource().equals(mReadStudentFile)) {
			readStudentFile();
		}

		else if (e.getSource().equals(mRemove)) {
			deleteStudent();
		}

		else if (e.getSource().equals(mRemoveAll)) {
			clearAll();
		}

		else if (e.getSource().equals(mSortLastName)) {
			table.getRowSorter().toggleSortOrder(1);
		}

		else if (e.getSource().equals(mSortId)) {
			table.getRowSorter().toggleSortOrder(2);
		}

		else if (e.getSource().equals(mSortAverage)) {
			table.getRowSorter().toggleSortOrder(8);
		}

		else if (e.getSource().equals(mFilter)) {
			filterGrade();
		}

		else if (e.getSource().equals(mRemoveFilter)) {
			// delete rows from the table
			tableModel.setRowCount(0);
			// add all students which are in the students list to the table
			getTableData();

		}

		else if (e.getSource().equals(mSave)) {
			saveData();
			System.exit(0);
		}

		else if (e.getSource().equals(mHelp)) {
			showHelpMessage();
		}

		else {
			onExit();
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}

}
