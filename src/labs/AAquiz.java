package labs;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;
import javax.swing.*;

public class AAquiz extends JFrame
{ 
	
	private int correct = 0;
	private int wrong = 0;
	private String q = null;
	/**
	 * On the top of the frame displaying the number of correct and incorrect answers.
	 * @return 2*1 panel.
	 */
	private JLabel numCorrectAnswer = new JLabel();
	private JLabel numWrongAnswer = new JLabel();
	private JPanel getRecord()
	{
		JPanel displayRecord = new JPanel();
		displayRecord.setLayout(new GridLayout(0,2));
		displayRecord.add(numCorrectAnswer);
		displayRecord.add(numWrongAnswer);
		return displayRecord;
	}
	

	
	
	private JLabel lastQuestion = new JLabel();
	private JLabel question = new JLabel();
	private JTextField answerBox = new JTextField();
	private JButton confirmButton = new JButton("Confirm");
	String promptQuestion;
	String correct_answer;
	private static Boolean quizStarted = false;
	/**
	 * In the middle of the frame displaying last question and it's answer, 
	 * box for current question input, and button for confirming current
	 * input.
	 * @return 1*3 panel. 
	 */
	private JPanel getAnswer()
	{
		JPanel displayQ = new JPanel();
		displayQ.setLayout(new GridLayout(4,1));
				
		displayQ.add(lastQuestion);
		lastQuestion.setHorizontalAlignment(JLabel.CENTER);
		displayQ.add(question);
		question.setHorizontalAlignment(JLabel.CENTER);
		question.setFont(new Font("SansSerif", Font.BOLD, 16));
		displayQ.add(answerBox);
		displayQ.add(confirmButton);
		
		confirmButton.addActionListener(
				new ActionListener()
				{ 
					public void actionPerformed(ActionEvent e)
					{
						if(quizStarted == true)
						{
							String userAnswer = answerBox.getText().toUpperCase();
							if(userAnswer.equals(correct_answer))
							{
								stopwatch.stop();
								correct++;
								updateLabel();
								index = random1.nextInt(20);
								promptQuestion = FULL_NAMES[index];
								correct_answer = SHORT_NAMES[index];									
								question.setText(promptQuestion);
								startTimer(30);
							}
							else if(! userAnswer.equals("") && !userAnswer.equals(correct_answer))
							{
								stopwatch.stop();
								wrong++;
								updateLabel();
								index = random1.nextInt(20);
								promptQuestion = FULL_NAMES[index];
								correct_answer = SHORT_NAMES[index];									
								question.setText(promptQuestion);
								startTimer(30);
							}
							
						}						
					}
				});
		
		answerBox.addKeyListener(new KeyAdapter() {
	         public void keyPressed(KeyEvent e) {
	             if (e.getKeyCode()==KeyEvent.VK_ENTER) {
	                confirmButton.doClick();
	             }
	          }
	       });   
				
		return displayQ;
	}
	
 
	
	
	Timer stopwatch;
    int count = 0;
    int delay = 1000;
    public static JLabel timeRemain = new JLabel();
    /**
     * A timer. Input count down time "countPassed", terminate program when count down hits 0.
     * @param countPassed
     */
	public void startTimer(int countPassed)
	{
        ActionListener action = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(count == 0)
                {
                	stopwatch.stop();
                	cancelButton.doClick();               	
                }
                else
                {
                	timeRemain.setText(count + " seconds left");
                    count --;
                }

            }
        };
        stopwatch = new Timer(delay, action);
        stopwatch.setInitialDelay(0);
        stopwatch.start();
        count = countPassed;
	}
	

	
	
	private JButton startButton = new JButton("Start quiz");
	private JButton cancelButton = new JButton("End quiz");
	int index;
	Random random1 = new Random();
	/**
	 * On the bottom of the frame displaying a button to start the quiz, 
	 * a button to end the quiz, and seconds left for answering current
	 * question
	 * @return 3*1 panel
	 */
	private JPanel getStartCancel()
	{
		JPanel startCancel = new JPanel();
		startCancel.setLayout(new GridLayout(1,4));
		startCancel.add(startButton);
		startCancel.add(cancelButton);
		startCancel.add(timeRemain);
		
		startButton.addActionListener(
				new ActionListener()
				{ 
					public void actionPerformed(ActionEvent e)
					{
						if(quizStarted == false)
						{
							index = random1.nextInt(20);
							promptQuestion = FULL_NAMES[index];
							correct_answer = SHORT_NAMES[index];									
							question.setText(promptQuestion);
							startTimer(30);	
							quizStarted = true;
						}
						
												
					}
				});
		
							
		cancelButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						String endMessage = "Quiz will end with " + correct + " correct answers and " + wrong + " incorrect answers." + "\nReset?";
						int input = JOptionPane.showOptionDialog(null,  endMessage, "AAquiz", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

						if(input == JOptionPane.OK_OPTION)
						{
							stopwatch.stop();
							promptQuestion = null;
							correct_answer = null;
							question.setText(null);
						    correct = 0;
						    wrong = 0;
						    startButton.setText("Start quiz");
						    quizStarted = false;
						    timeRemain.setText(null);
						    updateLabel();
						}
					}
				});
		
		return startCancel; 
	}
	

	
	
	/**
	 * updating JLabels.
	 */
	private void updateLabel()
	{
		
		question.setText(q);
		lastQuestion.setText("Last question: " + promptQuestion + "(" + correct_answer + ")");
		numCorrectAnswer.setText("Correct: " + correct);
		numCorrectAnswer.setHorizontalAlignment(JLabel.CENTER);

		numWrongAnswer.setText("Incorrect: " + wrong);
		numWrongAnswer.setHorizontalAlignment(JLabel.CENTER);

		answerBox.setText(null);
		validate();
	}
	
	
	
	public AAquiz()
	{
		super("AA quiz");
		setLocationRelativeTo(null);
		setSize(350,200);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(getRecord(), BorderLayout.NORTH);
		getContentPane().add(getAnswer(), BorderLayout.CENTER);
		getContentPane().add(getStartCancel(), BorderLayout.SOUTH);
		
		updateLabel();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
			
	public static void main(String[] args)
	{
		new AAquiz();
	}
	
	public static String[] SHORT_NAMES = 
		{ 
		"A", "R", "N", "D", "C", "Q", "E", 
		"G", "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" 
		};
	
	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"
		};
	
}
