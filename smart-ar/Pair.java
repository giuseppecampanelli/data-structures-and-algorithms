/**
 * Pair data structure.
 * 
 * @author Giuseppe Campanelli
 * @author Michael McMahon
 *
 */
public class Pair<T, V>
{
    private T first;
    private V second;
    
    /**
     * Default constructor for the Pair class.
     */
    public Pair() {}
    
    /**
     * Two parameter constructor for the Pair class.
     * 
     * @param first first item in pair
     * @param second second item in pair
     */
    public Pair(T first, V second)
    {
        this.first = first;
        this.second = second;
    }
    
    /**
     * Gets the first item in the pair
     * 
     * @return first item
     */
    public T getFirst()
    {
        return first;
    }

    /**
     * Gets the second item in the pair
     * 
     * @return second item
     */
    public V getSecond()
    {
        return second;
    }

    /**
     * Sets the first item in the pair
     * 
     * @param first item
     */
    public void setFirst(T first)
    {
        this.first = first;
    }

    /**
     * Sets the second item in the pair
     * 
     * @param second item
     */
    public void setSecond(V second)
    {
        this.second = second;
    }
}