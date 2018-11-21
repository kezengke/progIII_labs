package labs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class PrimeNumGen extends JFrame
{
	
	private final JTextArea aTextField = new JTextArea();
	private final JButton primeButton = new JButton("Start");
	private final JButton cancelButton = new JButton("Cancel");
	private volatile boolean cancel = false;
	private final PrimeNumGen thisFrame;
	private static final int numThreads = Runtime.getRuntime().availableProcessors();
	
	public static void main(String[] args)
	{
		PrimeNumGen png = new PrimeNumGen("Primer Number Generator");
		
		// don't add the action listener from the constructor
		png.addActionListeners();
		png.setVisible(true);
		
	}
	
	//layout and frame structure
	private PrimeNumGen(String title)
	{
		super(title);
		this.thisFrame = this;
		cancelButton.setEnabled(false);
		aTextField.setEditable(false);
		setSize(400, 200);
		setLocationRelativeTo(null);
		//kill java VM on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(primeButton,  BorderLayout.SOUTH);
		getContentPane().add(cancelButton,  BorderLayout.EAST);
		getContentPane().add( new JScrollPane(aTextField),  BorderLayout.CENTER);
	}
	
	//actionlistener for cancel button
	private class CancelOption implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			cancel = true;
		}
	}
	
	//
	private void addActionListeners()
	{
		cancelButton.addActionListener(new CancelOption());
	
		primeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					
					String num = JOptionPane.showInputDialog("Enter a large integer");
					Integer max =null;
					
					try
					{
						max = Integer.parseInt(num);
					}
					catch(Exception ex)
					{
						JOptionPane.showMessageDialog(
								thisFrame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						ex.printStackTrace();
					}
					
					if( max != null)
					{
						aTextField.setText("");
						primeButton.setEnabled(false);
						cancelButton.setEnabled(true);
						cancel = false;
						new Thread(new UserInput(max)).start();

					}
				}});
		}
	
	//identify if the number is prime
	private static boolean isPrime( int i)
	{
		for( int x=2; x < i -1; x++)
			if( i % x == 0  )
				return false;
		
		return true;
	}
	
	
	private class UserInput implements Runnable
	{
		private final int max;
		private final long startTime;
		List<Integer> list = Collections.synchronizedList(new ArrayList<Integer>());
		
		private UserInput(int num)
		{
			this.max = num;
			this.startTime = System.currentTimeMillis();
		}
		
		public void run()
		{
			long lastUpdate = System.currentTimeMillis();
			Semaphore semaphore = new Semaphore(numThreads);
			
			for (int i = 1; i < max && ! cancel; i++) 
			{
				try
				{
					semaphore.acquire();
					Worker w = new Worker(i, semaphore, lastUpdate);
					new Thread(w).start();
					if( System.currentTimeMillis() - lastUpdate > 500)
					{
						float time = (System.currentTimeMillis() -startTime )/1000f;
						final String outString= "Found " + list.size() + " in " + i + " of " + max + " " 
									+ time + " seconds ";
						
						SwingUtilities.invokeLater( new Runnable()
						{
							@Override
							public void run()
							{
								aTextField.setText(outString);
							}
						});
						
						lastUpdate = System.currentTimeMillis();	
					}
				} 
				
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}

				
			}
			
			int numAquired = 0;
			while (numAquired < numThreads)
			{
				try
				{
					semaphore.acquire();
					numAquired++;
				} 
				catch (InterruptedException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			final StringBuffer buff = new StringBuffer();
			
			for( Integer i2 : list)
				buff.append(i2 + "\n");
			
			if( cancel)
				buff.append("cancelled\n");
			
			float time = (System.currentTimeMillis() - startTime )/1000f;
			buff.append("Time = " + time + " seconds " );
			
			SwingUtilities.invokeLater( new Runnable()
			{
				@Override
				public void run()
				{
					cancel = false;
					primeButton.setEnabled(true);
					cancelButton.setEnabled(false);
					aTextField.setText( (cancel ? "cancelled " : "") +  buff.toString());
					
				}
			});
			
			
			
		}// end run
		
		class Worker implements Runnable
		{
			private final int currentNumber;
			private Semaphore semaphore;
			private long lastUpdate;
			
			public Worker(int currentNumber, Semaphore semaphore, long lastUpdate)
			{
				this.currentNumber = currentNumber;
				this.semaphore = semaphore;
				this.lastUpdate = lastUpdate;
			}
			
			@Override
			public void run()
			{
				try
				{
					if( isPrime(currentNumber))
					{
						list.add(currentNumber);
							
					}
					semaphore.release();
				}
				
				catch(Exception ex)
				{
					ex.printStackTrace();
					System.exit(1);
				}
			}
		}
		
	}  // end UserInput
}
	
	