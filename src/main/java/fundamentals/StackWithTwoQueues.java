package fundamentals;

import java.util.ArrayDeque;
import java.util.EmptyStackException;
import java.util.Queue;

/**
 * Author: Pierre Schaus and Auguste Burlats
 * Implement the abstract data type stack using two queues
 * You are not allowed to modify or add the instance variables,
 * only the body of the methods
 */
public class StackWithTwoQueues<E> {

    Queue<E> queue1;
    Queue<E> queue2;

    public StackWithTwoQueues() {
        queue1 = new ArrayDeque();
        queue2 = new ArrayDeque();
    }

    /**
     * Looks at the object at the top of this stack
     * without removing it from the stack
     * Complexity : O(1)
     */
    public boolean empty() {
         return queue1.isEmpty() && queue2.isEmpty();
    }

    /**
     * Returns the first element of the stack, without removing it from the stack
     *
     * @throws EmptyStackException if the stack is empty
     * Complexity : O(n), with n = size of the stack
     */
    public E peek() throws EmptyStackException {
        if(empty()) throw new EmptyStackException();
        E headElement = pop();
        push(headElement);
        return headElement;
    }

    /**
     * Remove the first element of the stack and returns it
     *
     * @throws EmptyStackException if the stack is empty
     * Complexity : O(n), with n = size of the stack
     */
    public E pop() throws EmptyStackException {
        if(empty()) throw new EmptyStackException();
        Queue<E> queueToEmpty;
        Queue<E> otherQueue;

        if (queue1.isEmpty()){
            queueToEmpty = queue2;
            otherQueue = queue1;
        }else {
            queueToEmpty = queue1;
            otherQueue = queue2;
        }

        while (!queueToEmpty.isEmpty()){
            E tmpItem = queueToEmpty.remove();
            if (queueToEmpty.isEmpty()){
                return tmpItem;
            }
            otherQueue.add(tmpItem);
        }
         return null;
    }

    /**
     * Adds an element to the stack
     *
     * @param item the item to add
     */
    public void push(E item) {
        if (queue1.isEmpty()){
            queue2.add(item);
        }else{
            queue1.add(item);
        }
    }

    @Override
    public String toString() {
        if (queue1.isEmpty()){
            System.out.println(queue2);
        }else{
            System.out.println(queue1);
        }
        return "";
    }
    
}
