package assignment1;

import java.math.BigInteger;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *   Calculates the n-th term of tetranacci using linear recursion.
 *   
 *   @version 27/09/2017
 *   @author Joey Campanelli and Michael McMahon
 */
public class LinearTetranacci {

    /**
     *   Calculates the n-th term of tetranacci using linear recursion.
     *
     *   @param n - The n-th term in a tetranacci sequence.
     *
     *   @return The n-th tetranacci term.
     */
    public static BigInteger[] recursiveLinearTetranacci(int n) {
        if (n <= 3) {
            BigInteger[] result = {BigInteger.valueOf(1), BigInteger.valueOf(0), BigInteger.valueOf(0), BigInteger.valueOf(0)};
            return result;
        }
        else {
            BigInteger[] previous = recursiveLinearTetranacci(n-1);
            BigInteger[] result = {previous[0].add(previous[1]).add(previous[2]).add(previous[3]), previous[0], previous[1], previous[2]};
            return result;
        }
    }
	
	public static void main(String[] args) {
        int n = 0;
        BigInteger term;
        String output = "";

        System.out.println("-------------------------------");
	    System.out.println("-------Linear Tetranacci-------");
        System.out.println("-------------------------------");
        output += "-------------------------------\n";
        output += "-------Linear Tetranacci-------\n";
        output += "-------------------------------\n";
	    
	    while (n <= 100) {
	        System.out.print(n + "-th term: ");
            output += n + "-th term: ";
            long startTime = System.currentTimeMillis();
            term  = recursiveLinearTetranacci(n)[0];
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