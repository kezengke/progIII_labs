package labs;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class FastaSequence
{
	private String header;
	private StringBuffer sequence = new StringBuffer();
	
	
	public static List<FastaSequence> readFastaFile(String filepath) throws Exception
	{
		List<FastaSequence> fastaList = new ArrayList<FastaSequence>();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(filepath)));
		
		FastaSequence fs = new FastaSequence();
		Boolean firstSequence = true;
		
		for (String nextLine = reader.readLine(); nextLine != null; nextLine = reader.readLine())
		{
			if(nextLine.startsWith(">")&& firstSequence == true)
			{
				fs.header = nextLine.replaceAll(">", "");
				fastaList.add(fs);
				firstSequence = false;
			}
			else if(nextLine.startsWith(">")&& firstSequence == false)
			{
				fs = new FastaSequence();
				fs.header = nextLine.replaceAll(">", "");
				fastaList.add(fs);
			}
			
			else
			{
				fs.sequence.append(nextLine);
			}
		}
		
		
		return fastaList;
	}
	
	public static void writeUnique(String inFile, String outFile ) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(new File(inFile)));
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		
		
	}
	
	// returns the header of this sequence without the “>”
	public String getHeader()
	{
		return header;
	}

	// returns the Dna sequence of this FastaSequence
	public String getSequence()
	{
		return sequence.toString();
	}
		
		
	// returns the number of G’s and C’s divided by the length of this sequence
	public float getGCRatio()
	{
		float gcCounter = 0;
		String currentSequence = this.getSequence();
		float sequenceLength = currentSequence.length();
		for(int i=0; i<sequenceLength; i++)
		{
			char c = currentSequence.charAt(i);
			if (c == 'G'|| c == 'C')
			{
				++gcCounter;
			}
		}
		float gcRatio = gcCounter/sequenceLength;
		return gcRatio;
	}
}

public class Lab4
{
	
	public static void main(String[] args) throws Exception
	{	
		List<FastaSequence> fastaList = FastaSequence.readFastaFile("/Users/2kisa/programmingIII/FastaSeqCL.txt");
		
		for( FastaSequence fs : fastaList)
		{
			System.out.println(fs.getHeader());
			System.out.println(fs.getSequence());
			System.out.println(fs.getGCRatio());
		}
	}

}
