package labs;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
//import javax.swing.Timer;

import javax.swing.*;

public class AAquiz extends JFrame
{
	private int correct = 0;
	private int wrong = 0;
	private String q = null;
	
	private JLabel numCorrectAnswer = new JLabel();
	private JLabel numWrongAnswer = new JLabel();
	
	private JLabel question = new JLabel();
	private JTextField answerBox = new JTextField("Enter answer here");
	
	int index;
	Random random1 = new Random();
	private JButton startButton = new JButton("Start quiz");
	private JButton cancelButton = new JButton("End quiz");
	String promptQuestion;
	String correct_answer;
	private static Boolean quizStarted = false;
	
	public static JLabel timeRemain = new JLabel();
	
    Timer stopwatch;

    int count = 0;
    int delay = 1000;
	
	private JPanel getRecord()
	{
		JPanel displayRecord = new JPanel();
		displayRecord.setLayout(new GridLayout(0,2));
		displayRecord.add(numCorrectAnswer);
		displayRecord.add(numWrongAnswer);
		return displayRecord;
	}
	
	private JPanel getAnswer()
	{
		JPanel displayQ = new JPanel();
		displayQ.setLayout(new GridLayout(3,1));
		displayQ.add(question);
		displayQ.add(answerBox);
				
		return displayQ;
	}
	
	public void startTimer(int countPassed)
	{
        ActionListener action = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                if(count == 0)
                {
                	stopwatch.stop();
                }
                else
                {
                	
                }
            	while(count>0)
                {
            		timeRemain.setText(count + " seconds left");
                    count --;
                    
                    if(quizStarted == false)
					{
						index = random1.nextInt(20);
						promptQuestion = FULL_NAMES[index];
						correct_answer = SHORT_NAMES[index];									
						question.setText(promptQuestion);
						startButton.setText("Confirm");
						quizStarted = true;

						
					}
					
					else
					{
						String userAnswer = answerBox.getText().toUpperCase();
						if(userAnswer.equals(correct_answer))
						{
							correct++;
							updateLabel();
						}
						else if(! userAnswer.equals("") && !userAnswer.equals(correct_answer))
						{
							wrong++;
							updateLabel();
						}
						
						index = random1.nextInt(20);
						promptQuestion = FULL_NAMES[index];
						correct_answer = SHORT_NAMES[index];									
						question.setText(promptQuestion);						
					}
                }
            }
        };
        stopwatch = new Timer(delay, action);
        stopwatch.setInitialDelay(0);
        stopwatch.start();
        count = countPassed;
	}
	
	
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
						startTimer(30);							
					}
				});
		
							
		cancelButton.addActionListener(
				new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						
						String endMessage = "Quiz ended with " + correct + " correct answers and " + wrong + " incorrect answers." + "\nReset?";
						int input = JOptionPane.showOptionDialog(null,  endMessage, "AAquiz", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

						if(input == JOptionPane.OK_OPTION)
						{
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
	
	public void setCountDownLabelText(String text) 
	{
		timeRemain.setText(text);
	}
	
	private void updateLabel()
	{
		question.setText(q);
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
		{ "A","R", "N", "D", "C", "Q", "E", 
		"G",  "H", "I", "L", "K", "M", "F", 
		"P", "S", "T", "W", "Y", "V" };
	
	public static String[] FULL_NAMES = 
		{
		"alanine","arginine", "asparagine", 
		"aspartic acid", "cysteine",
		"glutamine",  "glutamic acid",
		"glycine" ,"histidine","isoleucine",
		"leucine",  "lysine", "methionine", 
		"phenylalanine", "proline", 
		"serine","threonine","tryptophan", 
		"tyrosine", "valine"};
	
}
