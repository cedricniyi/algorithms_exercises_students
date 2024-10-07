package fundamentals;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Author Pierre Schaus
 *
 * We are interested in the implementation of a circular simply linked list,
 * i.e. a list for which the last position of the list refers, as the next position,
 * to the first position of the list.
 *
 * The addition of a new element (enqueue method) is done at the end of the list and
 * the removal (remove method) is done at a particular index of the list.
 *
 * A (single) reference to the end of the list (last) is necessary to perform all operations on this queue.
 *
 * You are therefore asked to implement this circular simply linked list by completing the class see (TODO's)
 * Most important methods are:
 *
 * - the enqueue to add an element;
 * - the remove method [The exception IndexOutOfBoundsException is thrown when the index value is not between 0 and size()-1];
 * - the iterator (ListIterator) used to browse the list in FIFO.
 *
 * @param <Item>
 */
public class CircularLinkedList<Item> implements Iterable<Item> {

    private long nOp = 0; // count the number of operations
    private int n;          // size of the stack
    private Node  last;   // trailer of the list

    // helper linked list class
    private class Node {
        private Item item;
        private Node next;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node node = (Node) o;
            return Objects.equals(item, node.item) && Objects.equals(next, node.next);
        }

        @Override
        public int hashCode() {
            return Objects.hash(item, next);
        }
    }

    public CircularLinkedList() {
        this.last = new Node();
        this.last.item = null;
        this.last.next = this.last;
    }

    public boolean isEmpty() {
         return last.equals(last.next);
    }

    public int size() {
         return n;
    }

    private long nOp() {
        return nOp;
    }



    /**
     * Append an item at the end of the list
     * @param item the item to append
     */
    public void enqueue(Item item) {
        Node newNode = new Node();
        newNode.item = item;

        if (isEmpty()){
            last.next = newNode;
            newNode.next = last;
            last = newNode;
        }else{
            Node dummyNode = last.next;
            last.next = newNode;
            newNode.next = dummyNode;
            last = newNode;
        }
        n++;
        nOp++;
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their indices).
     * Returns the element that was removed from the list.
     */
    public Item remove(int index) {
        if (index < 0 || index > size() - 1) throw new IndexOutOfBoundsException();
        if (isEmpty()) return null;
        Node dummyNode = last.next;

        Node baladeur = dummyNode.next;
        Node precedent = dummyNode;

        int nbrIteration = index ;

        while (nbrIteration > 0){
            precedent = baladeur;
            baladeur = baladeur.next;
            nbrIteration--;
        }

        precedent.next = baladeur.next;
        if((index+1)==n){
            last = precedent;
        }
        n--;
        nOp++;
        return baladeur.item;
    }


    /**
     * Returns an iterator that iterates through the items in FIFO order.
     * @return an iterator that iterates through the items in FIFO order.
     */
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    /**
     * Implementation of an iterator that iterates through the items in FIFO order.
     * The iterator should implement a fail-fast strategy, that is ConcurrentModificationException
     * is thrown whenever the list is modified while iterating on it.
     * This can be achieved by counting the number of operations (nOp) in the list and
     * updating it everytime a method modifying the list is called.
     * Whenever it gets the next value (i.e. using next() method), and if it finds that the
     * nOp has been modified after this iterator has been created, it throws ConcurrentModificationException.
     */
    private class ListIterator implements Iterator<Item> {

        // TODO You probably need a constructor here and some instance variables
        private long savedN0p;
        private Node dummyNode;
        private Node baladeur;

        public ListIterator() {
            this.savedN0p = nOp();
            this.dummyNode = last.next;
            this.baladeur = dummyNode.next;
        }

        @Override
        public boolean hasNext() {
            return !baladeur.equals(dummyNode);
        }

        @Override
        public Item next() {
            if (savedN0p!=nOp()) throw new ConcurrentModificationException();
            Node nextNode = baladeur;
            baladeur = baladeur.next;
            return nextNode.item;
        }
    }

    public static void main(String[] args) {
        System.out.println("HelloWorld");

        CircularLinkedList<Integer> list = new CircularLinkedList<>();
        System.out.println("Is list empty? : " + list.isEmpty());

        for(Integer nbr: list){
            System.out.println("-> " + nbr);
        }

        System.out.println("Is it still empty? : " + list.isEmpty());

        list.enqueue(1);

        for(Integer nbr: list){
            System.out.println("-> " + nbr);
        }

        System.out.println("Is it still empty? : " + list.isEmpty());

        list.enqueue(2);

        for(Integer nbr: list){
            System.out.println("-> " + nbr);
        }


        list.remove(0);

        System.out.println("Is it still empty? : " + list.isEmpty());

        for(Integer nbr: list){
            System.out.println("-> " + nbr);
        }

        list.remove(0);

        System.out.println("Is it still empty? : " + list.isEmpty());

        for(Integer nbr: list){
            System.out.println("-> " + nbr);
        }

//        System.out.println("Is list empty? : " + list.isEmpty());
//
//        for(Integer nbr: list){
//            System.out.println("-> " + nbr);
//        }
//
//        System.out.println("------------");
//
//        list.enqueue(2);
//        System.out.println("Is list empty? : " + list.isEmpty());
//
//        for(Integer nbr: list){
//            System.out.println("-> " + nbr);
//        }
    }
}
