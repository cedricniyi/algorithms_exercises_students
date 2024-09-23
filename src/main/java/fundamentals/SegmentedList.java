package fundamentals;

import java.util.*;

/**
 * Author Pierre Schaus
 *
 * The SegmentedList is a linear structure that consists of several segments (or groups) of elements,
 * where each segment can have its own size.
 * It is like a list of lists, but the user interacts with it as a single linear sequence.
 * The iterator will need to traverse each segment in order, element by element,
 * seamlessly presenting the entire structure as a single flat sequence.
 *
 * Be careful with the iterator, it should implement the fail-fast behavior.
 * A fail-fast iterator detects illegal concurrent modification during iteration (see the tests).
 *
 * Example:
 *
 *         SegmentedList<Integer> segmentedList = create();
 *
 *         // Add segments
 *         List<Integer> segment1 = new ArrayList<>();
 *         segment1.add(1); segment1.add(2); segment1.add(3);
 *         segmentedList.addSegment(segment1); // add [1,2,3]
 *
 *         List<Integer> segment2 = new ArrayList<>();
 *         segment2.add(4); segment2.add(5);
 *         segmentedList.addSegment(segment2); // add [4,5]
 *
 *         List<Integer> segment3 = new ArrayList<>();
 *         segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
 *         segmentedList.addSegment(segment3); // add [6,7,8,9]
 *
 *         segmentedList.removeSegment(1); // remove [4,5], the segment in second position
 *
 *         // Iterate through the SegmentedList that is elements 1,2,3,6,7,8,9
 *         for (Integer value : segmentedList) {
 *             System.out.println(value);
 *         }
 *
 *         // Example of using get()
 *         System.out.println("Element at global index 4: " + segmentedList.get(4)); // Should print 5
 *
 * @param <T>
 */
public interface SegmentedList<T> extends Iterable<T> {

    // Add a new segment (list) to the SegmentedList.
    void addSegment(List<T> segment);

    // Remove a segment by its index.
    void removeSegment(int index);

    // Get the total size of the segmented list (across all segments).
    int size();

    // Retrieve an element at a global index (spanning all segments).
    T get(int globalIndex);

    // Static method inside the interface
    static <T> SegmentedList<T> create() {
        return new SegmentedListImpl<>();
    }

}

class SegmentedListImpl<T> implements SegmentedList<T> {

    // TODO: Implement the SegmentedList interface here

    List<List<T>> baseList = new LinkedList<>();


    // Add a new segment (list) to the SegmentedList.
    public void addSegment(List<T> segment) {
        baseList.add(segment);
    }

    // Remove a segment by its index.
    public void removeSegment(int index) {
        baseList.remove(index);
    }

    // Get the total size of the segmented list (across all segments).
    public int size() {
        int counter = 0;
        for (List<T> seg: baseList){
            counter += seg.size();
        }
         return counter;
    }

    // Retrieve an element at a global index (spanning all segments).
    public T get(int globalIndex) {
        Iterator<T> iterator = this.iterator();
        int currentIndex = 0;

        while (iterator.hasNext()) {
            T element = iterator.next();
            if (currentIndex == globalIndex) {
                return element;
            }
            currentIndex++;
        }
        throw new IndexOutOfBoundsException();
    }


    // Return an iterator for the segmented list.
    @Override
    public Iterator<T> iterator() {
         return new CustomIterator();
    }

    private class CustomIterator implements Iterator<T> {

        int count = size();
        int totalCounter = 0;
        int idx = 0;
        int idxSegment = 0;
        List<T> currentSegment;

        @Override
        public boolean hasNext() {
            if(count!=size()) throw new ConcurrentModificationException();
            if(count==0) return false;
            if(totalCounter<count){
                return true;
            }
            return false;
        }

        @Override
        public T next() {
            if(count!=size()) throw new ConcurrentModificationException();
            currentSegment = baseList.get(idxSegment);
            if (idx < currentSegment.size()){
                idx +=1;
                totalCounter +=1;
                return currentSegment.get(idx-1);
            }else{
                if(idxSegment == baseList.size()-1) throw new NoSuchElementException();

                idxSegment += 1;
                idx = 1;
                totalCounter +=1;
                currentSegment = baseList.get(idxSegment);
                return currentSegment.get(0);
            }

        }
    }

    public static void main(String[] args) {
        System.out.println("Hello World");
        SegmentedList<Integer> segmentedList = SegmentedList.create();

        // Add segments
        List<Integer> segment1 = new ArrayList<>();
        segment1.add(1); segment1.add(2); segment1.add(3);
        segmentedList.addSegment(segment1); // add [1,2,3]

        List<Integer> segment2 = new ArrayList<>();
        segment2.add(4); segment2.add(5);
        segmentedList.addSegment(segment2); // add [4,5]

        List<Integer> segment3 = new ArrayList<>();
        segment3.add(6); segment3.add(7); segment3.add(8); segment3.add(9);
        segmentedList.addSegment(segment3); // add [6,7,8,9]

        for (Integer value : segmentedList) {
            System.out.println(value);
        }

        segmentedList.removeSegment(1);
        System.out.println("------");
        for (Integer value : segmentedList) {
            System.out.println(value);
        }

//        System.out.println("----");
//        System.out.println(segmentedList.get(0));
//        System.out.println(segmentedList.get(1));
//        System.out.println(segmentedList.get(2));
//        System.out.println(segmentedList.get(3));
//        System.out.println(segmentedList.get(4));
//        System.out.println(segmentedList.get(5));
//        System.out.println(segmentedList.get(6));
//        System.out.println(segmentedList.get(7));
//        System.out.println(segmentedList.get(8));
//        System.out.println(segmentedList.get(9));
//        System.out.println("----");

    }

}
