import java.util.Stack;
import java.util.ArrayList;

/**
 *   Iterative version of the Right Magnetic Cave game.
 *   
 *   @version 24/10/2017
 *   @author Joey Campanelli and Michael McMahon
 */
public class IterativeRightMagneticCave {

    /**
     *   Determines if the list and marker give a winnable game for Right Magnetic Cave iteratively.
     *
     *   @param list - List of make pieces.
     *   @param marker - Marker where the game starts.
     *
     *   @return Returns whether the game is winnable or not based of the list and marker
     */
    public static boolean iterativeRightMagneticCave(int[] list, int marker) {
        int travelDist = list[marker] % 2 == 0 ? list[marker]/2 : list[marker]/2 + 1;

        if (marker + travelDist == list.length - 1)
            return true;

        int newMarker = list.length - 1;
        Stack<Integer> paths = new Stack<>();
        ArrayList<Integer> visitedNodes = new ArrayList<>();

        // populate stack with initial round.
        for (int i = 0; i  < list.length - 1; i++) {
            if (visitedNodes.contains(i))
                continue;

            travelDist = list[i] % 2 == 0 ? list[i]/2 : list[i]/2 + 1;
            int distFromMarker = Math.abs(newMarker -i);

            if ((travelDist == distFromMarker ) && i != newMarker) {
                visitedNodes.add(i);
                paths.add(i);
            }
        }

        while (!paths.isEmpty()) {
            int currentMarker = paths.pop();

            if (currentMarker == marker)
                return true;

            for (int i = 0; i < list.length-1; i++) {
                if (visitedNodes.contains(i))
                    continue;

                travelDist = list[i] % 2 == 0 ? list[i]/2 : list[i]/2 + 1;
                int distFromMarker = Math.abs(currentMarker - i);

                if ((travelDist == distFromMarker ) && i != currentMarker) {
                    paths.add(i);
                    visitedNodes.add(i);
                }
            }
        }

        return false;
    }

    private static void testBoard(int[] board, int marker, boolean expected) {
        boolean actual = iterativeRightMagneticCave(board, marker);
        StringBuilder builder =  new StringBuilder();

        for (int i = 0; i < board.length-1; i++) {
            builder.append(board[i]);
            builder.append(", ");
        }
        builder.append(board[board.length-1]);

        if (actual == expected)
            System.out.println( "[PASS] Actual: " + actual  + " Expected: " + expected + " Board: [" + builder.toString() + "]" + " Marker: " + marker);
        else
            System.out.println( "[FAIL] Actual: " + actual  + " Expected: " + expected + " Board: [" + builder.toString() + "]" + " Marker: " + marker);
    }
	
	public static void main(String args[]) {
        testBoard(new int[]{8,16,10,4,6,10,2,12,8,0}, 0, true);
        testBoard(new int[]{8,2,12,8,0}, 0, true);
        testBoard(new int[]{1,0}, 0, true);
        testBoard(new int[]{2,1,0}, 0, true);
        testBoard(new int[]{4,1,0}, 0, true);
        testBoard(new int[]{3,0}, 0, false);
        testBoard(new int[]{2,1,2,1,0}, 2, true);
        testBoard(new int[]{2,1,2,0}, 0, true);
        testBoard(new int[]{3,1,2,1,0}, 0, true);
        testBoard(new int[]{3,1,4,1,0}, 1, true);
        testBoard(new int[]{6,9,4,1,7,1,0}, 0, false);
        testBoard(new int[]{10,16,4,6,2,10,0}, 0, false);
        testBoard(new int[]{8,16,10,4,6,10,2,12,8,0}, 3, true);
        testBoard(new int[]{8,16,10,4,6,10,2,12,8,0}, 2, true);
        testBoard(new int[]{18,16,6,5,8,11,2,9,8,0}, 4, true);
        testBoard(new int[]{1,5,4,7,4,2,4,0}, 3, true);
        testBoard(new int[]{1,2,3,4,5,6,7,0}, 2, true);
        testBoard(new int[]{7,6,5,4,3,2,1,0}, 5, true);
        testBoard(new int[]{1,1,1,1,1,1,1,0}, 0, true);
        testBoard(new int[]{4,10,4,10,4,1,0}, 3, false);
        testBoard(new int[]{4,4,4,4,4,2,4,0}, 5, false);
        testBoard(new int[]{5,4,3,2,1,2,3,0}, 1, false);
        testBoard(new int[]{1,3,5,7,9,11,13,0}, 1, true);
        testBoard(new int[]{2,4,6,8,10,12,14,0}, 3, true);
        testBoard(new int[]{1,2,3,4,4,3,2,1,0}, 3, true);
        testBoard(new int[]{1,2,3,4,5,6,5,4,3,2,1,0}, 6, true);
        testBoard(new int[]{5,4,3,2,3,4,5,0}, 5, true);
        testBoard(new int[]{1,2,3,4,3,2,1,0}, 3, true);
        testBoard(new int[]{5,4,3,14,16,12,5,8,9,3,1,2,4,1,0}, 6, true);
        testBoard(new int[]{4,3,2,1,6,4,0}, 2, false);
        testBoard(new int[]{4,3,2,1,6,4,1,5,0}, 2, false);
    }
}