import java.util.LinkedList;

/**
 * SmartAR data structure tester.
 * 
 * @author Giuseppe Campanelli
 * @author Michael McMahon
 *
 */
public class SmartARTester {

    public static void main(String args[])
    {
        testSmartAR(6, 100, 10);
        testSmartAR(6, 1000, 100);
        testSmartAR(6, 500, 200);
        testSmartAR(6, 100, 300);
        testSmartAR(8, 100, 100);
        testSmartAR(8, 100, 10000);
        testSmartAR(12, 100, 50);
        testSmartAR(12, 1000, 2500);
        testSmartAR(12, 100, 10);
        testSmartAR(12, 2000, 1000);

    }
    
    private static void testSmartAR(int keyLength, int threshold, int elements)
    {
    	SmartAR<Integer> db =  new SmartAR<>();
    	int elementToManipulate = (int) (Math.random() * elements);
    	System.out.println("Key Length: " + keyLength + ", Threshold: " + threshold + ", Elements: " + elements);
        try
        {
            db.setKeyLength(keyLength);
            db.setThreshold(threshold);
            LinkedList<String> keys = db.generate(elements);
            
            for(int i = 0; i < elements; i++)
            {
                db.add(keys.get(i), i);
            }
            
            LinkedList<String> allKeys = db.allKeys();
            System.out.println("Total Keys: " + allKeys.size());
            System.out.println("Element to manipulate: " + allKeys.get(elementToManipulate));
            LinkedList<Integer> values = db.getValues(allKeys.get(elementToManipulate));
            System.out.println("Amount of values for " + allKeys.get(elementToManipulate) + ": " + values.size());
            LinkedList<Integer> prevValues = db.previousCars(allKeys.get(elementToManipulate));
            System.out.println("Amount of values before " + allKeys.get(elementToManipulate) + ": " + prevValues.size());
            String next = db.nextKey(allKeys.get(elementToManipulate));
            System.out.println("Next key: " + next);
            String previous = db.prevKey(allKeys.get(elementToManipulate));
            System.out.println("Previous key: " + previous);
            boolean rem = db.remove(allKeys.get(elementToManipulate));
            System.out.println("Removal of element: " + rem);
            boolean add = db.add(allKeys.get(elementToManipulate), elementToManipulate);
            System.out.println("Addition of element: " + add + "\n");
        }
        catch (LicensePlateLengthViolationException | ThresholdOutOfBoundsException e)
        {
            System.out.println(e.getMessage());
        }
    }
}