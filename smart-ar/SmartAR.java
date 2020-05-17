import java.util.*;

/**
 * SmartAR class for license plates.
 * 
 * @author Giuseppe Campanelli
 * @author Michael McMahon
 *
 */
public class SmartAR<T extends Comparable<? super T>> {

    private int _keyLength;
    private TreeMap<String, LinkedList<T>> _licenseHashMap;
    private LinkedList<Pair<String, LinkedList<T>>> _licenseSequence;
    private int threshold;


    /**
     * Creates a new SmartAR
     */
    public SmartAR()
    {
        _licenseHashMap =  new TreeMap<>();
        _licenseSequence =  new LinkedList<>();
    }

    /**
     * Sets the threshold to determine which data structure to use.
     * 
     * @param threshold - the data threshold to chose a linkedList or tree map.
     * 
     * @throws ThresholdOutOfBoundsException
     */
    public void setThreshold(int threshold) throws ThresholdOutOfBoundsException
    {
        this.threshold = threshold;
        
        if (threshold < 100 || threshold > 500_000)
        {
            throw new ThresholdOutOfBoundsException("The threshold must be between 100 and 500000");
        }

        handleThresholdViolation();

    }

    private void handleThresholdViolation()
    {
        if (_licenseSequence != null) {
            if (_licenseSequence.size() > threshold) {
                _licenseHashMap = copySequenceToTreeMap(_licenseSequence);
                _licenseSequence.clear();
                _licenseSequence = null;
            }
        }

        if (_licenseHashMap != null) {
            if (_licenseHashMap.size() <= threshold) {
                _licenseSequence = copyTreeMapToSequence(_licenseHashMap);
                _licenseHashMap.clear();
                _licenseHashMap = null;
            }
        }

    }

    private TreeMap<String, LinkedList<T>> copySequenceToTreeMap(LinkedList<Pair<String, LinkedList<T>>> data)
    {
        TreeMap<String, LinkedList<T>> copy = new TreeMap<String, LinkedList<T>>();
        Iterator<Pair<String, LinkedList<T>>> it = data.iterator();
        
        if (it != null) {
            _licenseSequence.sort(new Comparator<Pair<String, LinkedList<T>>>() {
                @Override
                public int compare(Pair<String, LinkedList<T>> o1, Pair<String, LinkedList<T>> o2) {
                    return o1.getFirst().compareToIgnoreCase(o2.getFirst());
                }
            });

            while (it.hasNext()) {
                Pair<String, LinkedList<T>> current = it.next();
                copy.put(current.getFirst(), current.getSecond());
            }
        }
        
        return copy;
    }

    private LinkedList<Pair<String, LinkedList<T>>> copyTreeMapToSequence(TreeMap<String, LinkedList<T>> data)
    {
        LinkedList<Pair<String, LinkedList<T>>> copy = new LinkedList<Pair<String, LinkedList<T>>>();
        Set<Map.Entry<String, LinkedList<T>>> set =  data.entrySet();
        Iterator<Map.Entry<String, LinkedList<T>>> it = set.iterator();
        
        if (it != null) {
            while (it.hasNext()) {
                Map.Entry<String, LinkedList<T>> current = it.next();
                copy.add(new Pair<>(current.getKey(), current.getValue()));
            }
        }
        
        return copy;
    }


    /**
     * Set the license plate length. clears the memory of the smart AR since it is set to a new length
     * 
     * @param length -  the length of the license plate.
     * 
     * @throws LicensePlateLengthViolationException
     */
    public void setKeyLength(int length) throws LicensePlateLengthViolationException
    {
        if (this._keyLength == length)
            return;
        if (length < 6 || length > 12)
            throw new LicensePlateLengthViolationException("The license plate identifier must be between 6 and 12 characters long.");
        if (_licenseSequence != null)
            _licenseSequence.clear();
        else if (_licenseHashMap != null)
            _licenseHashMap.clear();
        
        _keyLength = length;
    }

    /**
     * Generate N new license plates
     * 
     * @param numOfPlates -  the number of license plates
     * 
     * @return a Linked list of license plates.
     */
    public LinkedList<String> generate(int numOfPlates)
    {
        LinkedList<String> sequence = new LinkedList<>();
        String alphanumeric;

        for (int i = 0; i < numOfPlates; i++) {
            alphanumeric = generateRandomAlphanumeric(_keyLength);

            if (sequence.contains(alphanumeric)) {
                i--;
                continue;
            }

            if (_licenseHashMap != null && _licenseHashMap.containsValue(alphanumeric)) {
                i--;
                continue;
            }

            if (_licenseSequence != null && _licenseSequence.contains(alphanumeric)) {
                i--;
                continue;
            }

            sequence.add(alphanumeric);

        }

        return sequence;
    }

    private String generateRandomAlphanumeric(int length)
    {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder string = new StringBuilder();
        int pos;

        while (string.length() < length) {
            pos = (int) (Math.random() * 36);
            string.append(characters.charAt(pos));
        }

        return string.toString();
    }

    /**
     * Get all the license plates
     * 
     * @return a sequence of all the plates.
     */
    public LinkedList<String> allKeys()
    {
    	LinkedList<String> keys = new LinkedList<>();
    	
        if(_licenseSequence == null) {
            Set<String> set = _licenseHashMap.keySet();
            Iterator<String> it = set.iterator();
            int i = 0;
            
            if (it != null) {
                while (it.hasNext()) {
                	keys.add(it.next());
                }
            }
            return keys;
        }
        else {
            Iterator<Pair<String, LinkedList<T>>> it = _licenseSequence.iterator();
            
            if (it != null) {
                while (it.hasNext()) {
                    keys.add(it.next().getFirst());
                }
            }
            Collections.sort(keys);
            return keys;
        }
    }

    /**
     * Add the key and the value.
     * 
     * @param plate - the license plate
     * @param value - the value associated with the plate
     * 
     * @return true if was added otherwise false.
     */
    public boolean add(String plate, T value)
    {
        handleThresholdViolation();
        if (_licenseSequence !=  null) {
            Iterator<Pair<String, LinkedList<T>>> it =  _licenseSequence.iterator();
            if (it != null) {
                while (it.hasNext()) {
                    Pair<String, LinkedList<T>> current = it.next();
                    if (current.getFirst().equals(plate)) {
                        _licenseSequence.remove(current);
                        LinkedList<T> data = current.getSecond();
                        data.add(value);
                        current.setSecond(data);
                        _licenseSequence.add(current);
                        break;
                    }
                }
                
                LinkedList<T> data = new LinkedList<>();
                data.add(value);
                _licenseSequence.add(new Pair<>(plate, data));

                return true;
            }
        }
        else {
            LinkedList<T> data = _licenseHashMap.get(plate);
            if (data != null) {
                data.add(value);
                _licenseHashMap.replace(plate, data);
            }
            else {
                data =  new LinkedList<>();
                data.add(value);
                _licenseHashMap.put(plate, data);
            }
            
            handleThresholdViolation();
            return true;
        }

        return false;
    }

    /**
     * Remove the key and its associated data
     * 
     * @param plate - the license plate
     * 
     * @return true if it removed it.
     */
    public boolean remove(String plate)
    {
        if (_licenseHashMap !=  null) {
            _licenseHashMap.remove(plate);
            return true;
        }
        else {
            Iterator<Pair<String, LinkedList<T>>> it =  _licenseSequence.iterator();
            if (it != null) {
                while (it.hasNext()) {
                    Pair<String, LinkedList<T>> current = it.next();
                    if (current.getFirst().equals(plate)) {
                        _licenseSequence.remove(current);
                        return true;
                    }
                }
            }
        }

        handleThresholdViolation();
        return false;
    }

    /**
     * Get the all the values of the plate
     * 
     * @param plate
     * 
     * @return a LinkList of all the values.
     */
    public LinkedList<T> getValues(String plate)
    {
        LinkedList<T> data = null;
        
        if (_licenseSequence !=  null) {
            Iterator<Pair<String, LinkedList<T>>> it =  _licenseSequence.iterator();
            if (it != null) {
                while (it.hasNext()) {
                    Pair<String, LinkedList<T>> current = it.next();
                    if (current.getFirst().equals(plate)) {
                        data = current.getSecond();
                        break;
                    }
                }
            }
        }
        else {
           data = _licenseHashMap.get(plate);
        }

        return data;
    }

    /**
     * Get the next key from the given key
     * 
     * @param plate - the license plate
     * 
     * @return - the next key.
     */
    public String nextKey(String plate)
    {
        if (_licenseSequence !=  null) {
            _licenseSequence.sort(new Comparator<Pair<String, LinkedList<T>>>() {
                @Override
                public int compare(Pair<String, LinkedList<T>> o1, Pair<String, LinkedList<T>> o2) {
                    return o1.getFirst().compareToIgnoreCase(o2.getFirst());
                }
            });

            Iterator<Pair<String, LinkedList<T>>> it =  _licenseSequence.iterator();
            return getNextKeyFromIterator(it, plate);
        }
        else {
            return _licenseHashMap.higherKey(plate);
        }
    }

    /**
     * Get the previous key from the given key
     * 
     * @param plate - the license plate
     * 
     * @return - the previous key.
     */
    public String prevKey(String plate)
    {
        if (_licenseSequence !=  null)  {
            _licenseSequence.sort(new Comparator<Pair<String, LinkedList<T>>>() {
                @Override
                public int compare(Pair<String, LinkedList<T>> o1, Pair<String, LinkedList<T>> o2) {
                    return o1.getFirst().compareToIgnoreCase(o2.getFirst());
                }
            });

            Iterator<Pair<String, LinkedList<T>>> it =  _licenseSequence.descendingIterator();
            return getNextKeyFromIterator(it, plate);
        }
        else {
           return _licenseHashMap.lowerKey(plate);
        }
    }


    private String getNextKeyFromIterator(Iterator<Pair<String, LinkedList<T>>> it, String plate)
    {
        if (it != null) {
            while (it.hasNext()) {
                Pair<String, LinkedList<T>> current = it.next();
                if (current.getFirst().equals(plate)) {
                    if(it.hasNext()) {
                        return it.next().getFirst();
                    }
                }
            }
        }
        return null;
    }

    /***
     * Get previous cars from the plates history
     * 
     * @param plate - the license plate
     * 
     * @return a LinkList of type T, which is the history of the plate
     */
    public LinkedList<T> previousCars(String plate)
    {
        LinkedList<T> data = getValues(plate);
        data.removeFirst();
        return data;
    }

}