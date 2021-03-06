package cool1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class ClassTreasurer
{	
	public static int GetNoOfChars(String str, char chr)
	{
		int count = 0;
		int len = str.length();
		for (int i = 0; i < len; i++)
		{
			if (str.charAt(i) == chr)
			{
				count++;
			}
		}
		return count;
	}
	
	public static int GetMinCost(int N, int K, String V)
	{
		int a = 0;
		int b = 0;
		BigInteger minCost = BigInteger.valueOf(0);
		
		//Iterate thorough all N(N+1)/2 possibilities BACKWARDS - i is start index, j is end index for V
		//METHOD IS TOO SLOW FOR VERY LARGE STRINGS
		for (int i = N - 1; i >= 0; i--)
		{
			System.out.println(i);
			for (int j = N-1; j >= i; j--)
			{	
				//Calculate #A and #B in this substring
				String sub = V.substring(i, j + 1);
				a = GetNoOfChars(sub, 'A');
				b = GetNoOfChars(sub, 'B');
				
				//If Betty wins if we don't do anything
				if (!(a > b + K || Math.abs(a - b) <= K))
				{
					//Obtain minimum number of changes to make either c1: a > b + k OR c2: |a - b| <= k
					int c1 = (int)Math.ceil((double)(b - a + K)/2);
					int c2 = (int)Math.ceil((double)(b - a - K)/2);
					int c = Math.min(c1, c2);		
					
					//Change first c Bs into As
					for (int d = 0; c > 0; d++)
					{
						//Update original string and increase minimum cost, mod 1,000,000,007
						if (V.charAt(i + d) == 'B')
						{
							V = V.substring(0, i + d) + "A" + V.substring(i + d + 1);
							c--;
							
							int prevMinCost = minCost.intValue();
							minCost = BigInteger.valueOf(2).pow(i + d + 1);
							minCost = minCost.add((BigInteger.valueOf(prevMinCost))); 	
							minCost = minCost.mod(BigInteger.valueOf(1000000007));
						}					
					}			
				}	
			}
		}
	
		return minCost.intValue();
	}
	
	public static void main(String[] args)
	{
		final String filepathIn = "ClassTreasurer_Input.txt";
		final String filepathOut = "ClassTreasurer_Output.txt";

		//READ file
		try
		{
			FileReader fr = new FileReader(filepathIn);
			BufferedReader br = new BufferedReader(fr);
			FileWriter fw = new FileWriter(filepathOut, false);
			BufferedWriter bw = new BufferedWriter(fw);

			int T = Integer.parseInt(br.readLine());
			
			for (int i = 1; i <= T; i++)
			{
				//Get data
				String line = br.readLine();
				String[] NK = line.split(" ");
				int N = Integer.parseInt(NK[0]);
				int K = Integer.parseInt(NK[1]);
				String V = br.readLine();
				
				int minCost = GetMinCost(N, K, V);
					
				//Output
				bw.write("Case #" + i + ": " + minCost);
				bw.newLine();
			}
			
			br.close();
			fr.close();
			bw.close();
			fw.close();

			System.out.println("Finished outputting");
		}
		catch (FileNotFoundException e)
		{
			System.out.println("ERROR: File not found!");
		}
		catch (IOException e)
		{
			System.out.println("ERROR: IOException!");
		}
	}
}