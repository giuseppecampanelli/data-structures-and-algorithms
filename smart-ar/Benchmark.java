import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

/**
 * Benchmark for the SmartAR ADT.
 * 
 * @author Giuseppe Campanelli
 * @author Michael McMahon
 *
 */
public class Benchmark {

    public static void main(String args[]) {

        ArrayList<String> platersFile1;
        ArrayList<String> platersFile2;
        ArrayList<String> platersFile3;

        platersFile1 = getDataFromFile("ar_test_file1.txt", 6);
        platersFile2 = getDataFromFile("ar_test_file2.txt", 12);
        platersFile3 = getDataFromFile("ar_test_file3.txt", 9);

        String plate1 = platersFile1.get(20);
        String plate2 = platersFile2.get(800);
        String plate3 = platersFile3.get(150);

        System.out.println("Test 1 with ar_test_fil1.txt small threshold");
        calculateOrderTime(platersFile1, 6, 100, plate1);
        System.out.println("Test 1 with ar_test_fil1.txt big threshold");
        calculateOrderTime(platersFile1, 6, 30000, plate1);
        System.out.println("Test 2 with ar_test_fil1.txt small threshold");
        calculateOrderTime(platersFile2, 12, 1000, plate2);
        System.out.println("Test 2 with ar_test_fil1.txt big threshold");
        calculateOrderTime(platersFile2, 12, 30000, plate2);
        System.out.println("Test 3 with ar_test_fil1.txt small threshold");
        calculateOrderTime(platersFile3, 9, 10000, plate3);
        System.out.println("Test 3 with ar_test_fil1.txt big threshold");;
        calculateOrderTime(platersFile3, 9, 50000, plate3);

    }

    private static void calculateOrderTime(ArrayList<String> plates, int length, int threshold, String plate) {
        long startTime, endTime;

        DateFormat formatter = new SimpleDateFormat("mm:ss:SSS");

        SmartAR<Integer> db =  new SmartAR<>();
        try
        {
            db.setKeyLength(length);
            db.setThreshold(threshold);
            startTime =  System.currentTimeMillis();
            for(int i = 0; i < plates.size(); i++)
            {
                db.add(plates.get(i), i);
            }
            endTime =  System.currentTimeMillis();
            Date date = new Date(endTime-startTime);
            String dateFormatted = formatter.format(date);
            System.out.println("Added " + plates.size() + " in " + dateFormatted + " to SmartAr");

            startTime =  System.currentTimeMillis();
            db.generate(1000);
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Generated 1000 plates in " + dateFormatted + "");

            startTime =  System.currentTimeMillis();
            LinkedList<String> allKeys = db.allKeys();
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Retrieved all plates in " + dateFormatted + "");


            startTime =  System.currentTimeMillis();
            LinkedList<Integer> history = db.getValues(plate);
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Retrieved all values for " + plate + " in " + dateFormatted + "");


            startTime =  System.currentTimeMillis();
            LinkedList<Integer> prevCars = db.previousCars(plate);
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Retrieved all values for " + plate + " in " + dateFormatted + "");

            startTime =  System.currentTimeMillis();
            String next = db.nextKey(plate);
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Retrieved next plate " + plate + " in " + dateFormatted + "");

            startTime =  System.currentTimeMillis();
            String prv = db.prevKey(plate);
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Retrieved previous plate " + plate + " in " + dateFormatted + "");

            startTime =  System.currentTimeMillis();
            boolean rem = db.remove(allKeys.get(20));
            endTime =  System.currentTimeMillis();
            date = new Date(endTime-startTime);
            dateFormatted = formatter.format(date);
            System.out.println("Removed " + plate + " in " + dateFormatted + "");

        }
        catch (LicensePlateLengthViolationException | ThresholdOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
    }

    private static ArrayList<String> getDataFromFile(String fileName, int length)
    {
        BufferedReader br = null;
        ArrayList<String> plates =  new ArrayList<>();
        try {
            br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null)
            {
                if(line.length() == length)
                    plates.add(line);
                line = br.readLine();
            }
            br.close();

        } catch(IOException ioe)
        {
            System.out.println(ioe.getMessage());
        }
        return plates;
    }

}
