package labs;

import java.awt.BorderLayout;
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
	private double totalAmount = 0;
	private double groceryAmount = 0;
	private double gasAmount = 0;
	private double shoppingAmount = 0;
	private double rentAmount = 0;
	private double workIncomeAmount = 0;
	private double otherIncomeAmount = 0;
	private double othersAmount = 0;
	
	private JLabel grocery = new JLabel();
	private JLabel gas = new JLabel();
	private JLabel shopping = new JLabel();
	private JLabel rent = new JLabel();
	private JLabel workincome = new JLabel();
	private JLabel otherincome = new JLabel();
	private JLabel others = new JLabel();
	private JLabel totalamount = new JLabel();
	
	private JButton incomeButton = new JButton("income");
	private JButton expendButton = new JButton("expend");
	private JComboBox category = new JComboBox( new String[] { "Grocery", "Gas", "Shopping", "Rent", "Others", "Work income", "Other income"});
	
	private JPanel getExpendAmountInfo()
	{
		JPanel eInfo = new JPanel();
		eInfo.setLayout(new GridLayout(5,1));
		eInfo.add(grocery);
		eInfo.add(gas);
		eInfo.add(shopping);
		eInfo.add(rent);
		eInfo.add(others);
		
		return eInfo;
				
	}
	
	private JPanel getIncomeAmountInfo()
	{
		JPanel iInfo = new JPanel();
		iInfo.setLayout(new GridLayout(2,1));
		iInfo.add(workincome);
		iInfo.add(otherincome);
		
		return iInfo;
	}
	
	private JPanel getBottonPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		panel.add(category);
		panel.add(incomeButton);
		panel.add(expendButton);
		
		
		incomeButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(category.getItemAt(category.getSelectedIndex()) == "Work income")
						{
							totalAmount++;
							workIncomeAmount++;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Other income")
						{
							totalAmount++;
							otherIncomeAmount++;
							updateLabel();
						}
						
					}
				});
		
		expendButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						if(category.getItemAt(category.getSelectedIndex()) == "Grocery")
						{
							totalAmount--;
							groceryAmount++;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Gas")
						{
							totalAmount--;
							gasAmount++;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Shopping")
						{
							totalAmount--;
							shoppingAmount++;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Rent")
						{
							totalAmount--;
							rentAmount++;
							updateLabel();
						}
						
						else if(category.getItemAt(category.getSelectedIndex()) == "Others")
						{
							totalAmount--;
							othersAmount++;
							updateLabel();
						}
					}
				});
				
		return panel;
		
	}
	
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
		setSize(500,200);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottonPanel(), BorderLayout.SOUTH);
		getContentPane().add(getExpendAmountInfo(), BorderLayout.WEST);
		getContentPane().add(getIncomeAmountInfo(), BorderLayout.CENTER);
		getContentPane().add(totalamount, BorderLayout.EAST);
		//getContentPane().add(category, BorderLayout.EAST);
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
	
	


