package labs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import javax.swing.*;


public class CheckBook extends JFrame
{
	// initial counters.
	private double totalAmount = 0;
	private double groceryAmount = 0;
	private double gasAmount = 0;
	private double shoppingAmount = 0;
	private double rentAmount = 0;
	private double workIncomeAmount = 0;
	private double otherIncomeAmount = 0;
	private double othersAmount = 0;
	
	Font font1 = new Font("SansSerif", Font.BOLD, 20);
	
	private JLabel expenses = new JLabel("Expenses");
	private JLabel grocery = new JLabel();
	private JLabel gas = new JLabel();
	private JLabel shopping = new JLabel();
	private JLabel rent = new JLabel();
	
	private JLabel incomes = new JLabel("Incomes");
	private JLabel workincome = new JLabel();
	private JLabel otherincome = new JLabel();
	private JLabel others = new JLabel();
	private JLabel totalamount = new JLabel();
	
	private JButton submitButton = new JButton("Update");
	private JComboBox category = new JComboBox( new String[] { "Grocery", "Gas", "Shopping", "Rent", "Others", "Work income", "Other income"});
	private JTextField enterBox = new JTextField("Enter the amount");
	
	//Expenses category display
	private JPanel getExpendAmountInfo()
	{
		JPanel eInfo = new JPanel();
		eInfo.setLayout(new GridLayout(6,1));
		expenses.setFont(new Font("SansSerif", Font.BOLD, 16));
		eInfo.add(expenses);
		eInfo.add(grocery);
		eInfo.add(gas);
		eInfo.add(shopping);
		eInfo.add(rent);
		eInfo.add(others);
		
		return eInfo;
				
	}
	
	// The income category display.
	private JPanel getIncomeAmountInfo()
	{
		JPanel iInfo = new JPanel();
		iInfo.setLayout(new GridLayout(6,1));
		incomes.setFont(new Font("SansSerif", Font.BOLD, 16));
		iInfo.add(incomes);
		iInfo.add(workincome);
		iInfo.add(otherincome);
		
		return iInfo;
	}
	
	// Input box, drag down list, and save button.
	private JPanel getBottonPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.add(enterBox);
		panel.add(category);
		panel.add(submitButton);
		
		//ugly spaghetti code 
		submitButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						String str = enterBox.getText();
						double money = Double.parseDouble(str);
						
						if(category.getItemAt(category.getSelectedIndex()) == "Work income")
						{
							totalAmount += money;
							workIncomeAmount += money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Other income")
						{
							totalAmount+=money;
							otherIncomeAmount+=money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Grocery")
						{
							totalAmount-=money;
							groceryAmount+=money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Gas")
						{
							totalAmount-=money;
							gasAmount+=money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Shopping")
						{
							totalAmount-=money;
							shoppingAmount+=money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Rent")
						{
							totalAmount-=money;
							rentAmount+=money;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Others")
						{
							totalAmount-=money;
							othersAmount+=money;
							updateLabel();
						}
						
					}
				});
				
		return panel;
		
	}
	
	// Updating display.
	private void updateLabel()
	{
		grocery.setText("Grocery: "+ groceryAmount);
		gas.setText("Gas: "+ gasAmount);
		shopping.setText("Shopping: "+ shoppingAmount);
		rent.setText("Rent: " + rentAmount);
		workincome.setText("Work income: " + workIncomeAmount);
		otherincome.setText("Other income: " + otherIncomeAmount);
		others.setText("Others: " + othersAmount);
		totalamount.setText("Total amount: " + totalAmount);
		validate();
	}
	
	// Menu for save and load file.
	private JMenuBar getMyMenuBar()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');
		menuBar.add(fileMenu);
		
		JMenuItem saveItem = new JMenuItem("Save");
		fileMenu.add(saveItem);
		saveItem.setMnemonic('S');
		
		saveItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						saveToFile();
					}
				});
		
		JMenuItem openItem = new JMenuItem("Open");
		fileMenu.add(openItem);
		openItem.setMnemonic('O');
		
		openItem.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						loadFromFile();
					}
				});
		
		return menuBar;
		
	}
	
	// For loading existed file.
	private void loadFromFile()
	{
		JFileChooser openFileChooser = new JFileChooser();
			
		if (openFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(openFileChooser.getSelectedFile()));
				ArrayList<String> amountList = new ArrayList<String>();
				for (String oldAmount = reader.readLine(); oldAmount != null; oldAmount = reader.readLine())
				{
					amountList.add(oldAmount);
				}
				
				// might be able to solve with a loop
				double temp = Double.parseDouble(amountList.get(0));
				groceryAmount = temp;
				
				temp = Double.parseDouble(amountList.get(1));
				gasAmount = temp;
				
				temp = Double.parseDouble(amountList.get(2));
				shoppingAmount = temp;
				
				temp = Double.parseDouble(amountList.get(3));
				rentAmount = temp;
				
				temp = Double.parseDouble(amountList.get(4));
				othersAmount = temp;
				
				temp = Double.parseDouble(amountList.get(5));
				workIncomeAmount = temp;
				
				temp = Double.parseDouble(amountList.get(6));
				otherIncomeAmount = temp;
				
				temp = Double.parseDouble(amountList.get(7));
				totalAmount = temp;
				
				reader.close();
				updateLabel();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not read file", JOptionPane.ERROR_MESSAGE);
			}
			
		}
		
	}
	
	// Saving current records to a .txt file.
	private void saveToFile()
	{
		JFileChooser jfc = new JFileChooser();
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION)
			return;
		
		if (jfc.getSelectedFile() == null)
			return;
		
		File choosenFile = jfc.getSelectedFile();
		
		if (jfc.getSelectedFile().exists())
		{
			String message = "File " + jfc.getSelectedFile().getName() + "exists. Overwrite?";
			
			if(JOptionPane.showConfirmDialog(this, message) != JOptionPane.YES_OPTION)
				return;
		}
		
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(choosenFile));
			writer.write(this.groceryAmount +"\n");
			writer.write(this.gasAmount +"\n");
			writer.write(this.shoppingAmount +"\n");
			writer.write(this.rentAmount +"\n");
			writer.write(this.othersAmount +"\n");
			
			writer.write(this.workIncomeAmount +"\n");
			writer.write(this.otherIncomeAmount +"\n");
			
			writer.write(this.totalAmount +"\n");
			
			writer.flush();
			writer.close();
		}
		
		catch(Exception ex)
		{
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, ex.getMessage(), "Could not write file", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	public CheckBook()
	{
		super("Check Book");
		setLocationRelativeTo(null);
		setSize(450,300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottonPanel(), BorderLayout.NORTH);
		getContentPane().add(getExpendAmountInfo(), BorderLayout.WEST);
		getContentPane().add(getIncomeAmountInfo(), BorderLayout.CENTER);
		totalamount.setFont(new Font("SansSerif", Font.BOLD, 16));
		getContentPane().add(totalamount, BorderLayout.SOUTH);
		setJMenuBar(getMyMenuBar());
		updateLabel();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
		
	public static void main(String[] args)
	{
		new CheckBook();
	}
}
	
	


