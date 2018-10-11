package labs;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * a class to parse fasta file.
 * @author 2kisa
 *
 */
class FastaSequence
{
	private String header;
	private StringBuffer sequence = new StringBuffer();
	
	/**
	 * return an ArrayList of FastaSeuquence object. each object contain a header and the corresponding sequence.
	 * @param filepath file location
	 * @return an ArrayList of FastaSequence obj.
	 * @throws Exception
	 */
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
	
	/**
	 * writes each unique sequence to an output file with the 
	 * number of times each sequence was seen in the input file as the header 
	 * (sorted with the sequence seen the fewest times the first)
	 * @param inFile input file path
	 * @param outFile out put file path
	 * @throws Exception
	 */
	public static void writeUnique(String inFile, String outFile) throws Exception
	{
		List<FastaSequence> fastaList = FastaSequence.readFastaFile(inFile);
		BufferedWriter writer = new BufferedWriter(new FileWriter(new File(outFile)));
		
		HashMap<String, Integer> uniqueCountMap = new HashMap<String, Integer>();
		
		for( FastaSequence fs : fastaList)
		{
			Integer count = uniqueCountMap.get(fs.getSequence());
			
			if(count == null)
			{
				count = 0;
			}
			count ++;
			
			uniqueCountMap.put(fs.getSequence(),count);
		}
		
		LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<>();
		 
		uniqueCountMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
		
		Set<String> keys = sortedMap.keySet();
		for(String key:keys)
		{
			writer.write(">" + sortedMap.get(key) + "\n" + key +"\n");
		}
		
		writer.flush();
		writer.close();
		
		
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
		
		FastaSequence wuTest = new FastaSequence();
		
		wuTest.writeUnique("/Users/2kisa/programmingIII/wu_test.txt", "/Users/2kisa/programmingIII/sortedMap.txt");
	}
	
	

}
