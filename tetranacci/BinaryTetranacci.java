package assignment1;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *   Calculates the n-th term of tetranacci using binary recursion.
 *   
 *   @version 27/09/2017
 *   @author Joey Campanelli and Michael McMahon
 */
public class BinaryTetranacci {

    /**
     *   Calculates the n-th term of tetranacci using binary recursion.
     *
     *   @param n - The n-th term in a tetranacci sequence.
     *
     *   @return The n-th tetranacci term.
     */
	public static long recursiveBinaryTetranacci(int n) {
		if (n == 0 || n == 1 || n == 2) {
            return 0L;
        } else if (n == 3) {
            return 1L;
        }
            
        return recursiveBinaryTetranacci(n-1) + recursiveBinaryTetranacci(n-2) + recursiveBinaryTetranacci(n-3) + recursiveBinaryTetranacci(n-4);
	}
	
	public static void main(String[] args) {
        int n = 0;
        long term;
        String output = "";
        System.out.println("-------------------------------");
	    System.out.println("-------Binary Tetranacci-------");
        System.out.println("-------------------------------");
        output += "-------------------------------\n";
        output += "-------Binary Tetranacci-------\n";
        output += "-------------------------------\n";
	    
	    while (n <= 40) {
	        System.out.print(n + "-th term: ");
            output += n + "-th term: ";
            long startTime = System.currentTimeMillis();
            term = recursiveBinaryTetranacci(n);
            System.out.println(term);
            output += term + "\n";
            long endTime = System.currentTimeMillis();
            long elapsed = endTime - startTime;
            System.out.println("Time elasped: " + elapsed + " milliseconds\n");
            output += "Time elasped: " + elapsed + " milliseconds\n";
	        n += 5;
	    }

        try(FileWriter fw = new FileWriter("out.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(output + "\n\n");
        } catch (IOException e) {
            System.out.println("Failed to append results to out.txt.");
        }
	}
}