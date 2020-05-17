package assignment1;

import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *   Calculates the n-th term of tetranacci using tail recursion.
 *   
 *   @version 27/09/2017
 *   @author Joey Campanelli and Michael McMahon
 */
public class TailRecursiveTetranacci {

    /**
     *   Calculates the n-th term of tetranacci using tail recursion.
     *
     *   @param values Default values of tetranacci.
     *   @param n The n-th term in a tetranacci sequence.
     *
     *   @return The n-th tetranacci term.
     */
    public static BigInteger tailRecursiveTetranacci(BigInteger[] values, int n) {
        if (n <= 3) {
            return values[n];
        }
        BigInteger sum = values[0].add(values[1]).add(values[2]).add(values[3]);
        
        for (int i = 0; i <= 2; i++) {
            values [i] = values[i+1];
        }
        values[3] = sum;

        return tailRecursiveTetranacci(values, n-1);
    }
	
	public static void main(String[] args) {
        int n = 0;
        BigInteger term;
        String output = "";

        System.out.println("-------------------------------");
	    System.out.println("---Tail Recursive Tetranacci---");
        System.out.println("-------------------------------");
        output += "-------------------------------\n";
        output += "---Tail Recursive Tetranacci---\n";
        output += "-------------------------------\n";
	    
	    while (n <= 100) {
            BigInteger[] values = {BigInteger.valueOf(0), BigInteger.valueOf(0), BigInteger.valueOf(0), BigInteger.valueOf(1)};
	        System.out.print(n + "-th term: ");
            output += n + "-th term: ";
            long startTime = System.nanoTime();
            term  = tailRecursiveTetranacci(values, n);
            System.out.println(term.toString());
            output += term + "\n";
            long endTime = System.nanoTime();
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