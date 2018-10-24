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
	
	private JTextField grocery = new JTextField();
	private JTextField gas = new JTextField();
	private JTextField shopping = new JTextField();
	private JTextField rent = new JTextField();
	private JTextField workincome = new JTextField();
	private JTextField otherincome = new JTextField();
	private JTextField others = new JTextField();
	private JTextField totalamount = new JTextField();
	
	private JButton incomeButton = new JButton("income");
	private JButton expendButton = new JButton("expend");
	private JComboBox category = new JComboBox( new String[] { "Grocery", "Gas", "Shopping", "Rent", "Work income", "Other income", "Others"});
	
	private JPanel getAmountInfo()
	{
		JPanel info = new JPanel();
		info.setLayout(new GridLayout(8,1));
		info.add(grocery);
		info.add(gas);
		info.add(shopping);
		info.add(rent);
		info.add(workincome);
		info.add(otherincome);
		info.add(others);
		info.add(totalamount);
		
		return info;
				
	}
	
	private JPanel getBottonPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(incomeButton);
		panel.add(expendButton);
		
		incomeButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						totalAmount++;
						updateLabel();
					}
				});
		
		expendButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						totalAmount--;
						updateLabel();
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
		//currentAmount.setText("Current total amount: "+ totalAmount);
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
				String oldAmount = reader.readLine();
				double temp = Double.parseDouble(oldAmount);
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
		setSize(500,300);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getBottonPanel(), BorderLayout.SOUTH);
		getContentPane().add(getAmountInfo(), BorderLayout.CENTER);
		getContentPane().add(category, BorderLayout.EAST);
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
	
	


