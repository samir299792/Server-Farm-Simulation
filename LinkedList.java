/*
 * Samir Bikram Dhami
 * Lab 4: Server Farm Simulation
 * 11th March, 2024
 * CS 231 A
 * File: LinkedList.java --> Defines the class LinkedList
 */

//Importing the required library
import java.util.Comparator;
import java.util.Iterator;

public class LinkedList<T> implements Iterable<T>,Queue<T>{

    private Node<T> first;
    private Node<T> tail;
    private int size;
    
    /*
     * Container class for a generic type Node
     */
    private static class Node<T>{
        private Node<T> next; //private field for reference to the next node
        private T item; //private field to store any kind of item

        /*
        * Constructs a node with reference for next and a field to store the getData()
        */
        public Node(T item){
            this.next = null;
            this.item = item;
        }

        /*
         * returns the getData() of the container field
         */
        public T getData(){
            return item;
        }

        /**
         * method to set the next field 
         * 
         * @param n next node
         */
        public void setNext(Node<T> n){
            this.next = n;
        }

        /**
         * method to set the next field
         * 
         * @return the next field
         */
        public Node<T> getNext(){
            return next;
        }
    }
    
    //Iterator subclass that handles traversing the list
    private class LLIterator implements Iterator<T>{
        Node<T> walker; //walker node

        //Constructor with a given first
        public LLIterator(Node<T> first){
            walker = first;
        }

        //returns true if there are still getData()s to traverse
        public boolean hasNext(){
            return walker != null;
        }

        //returns the next item in the list, which is the item contained in the current node. 
        public T next() {
            T out = walker.getData();
            walker = walker.next;
            return out;
        }
    }

    /*
     * Constructor that intializes the fields for an empty list
     */
    public LinkedList(){
        this.first = null;
        this.tail = null;
        this.size = 0 ;
    }

    /**
     * method to return the size of the list
     * 
     * @return the size of the list
     */
    public int size(){
        return size;
    }

    /**
     * method to empty the list
     */
    public void clear(){
        this.first = null;
        this.tail = null;
        this.size = 0 ;
    }
    
    /**
     * Checks if the list is empty or not
     * 
     * @return a boolean getData()
     */
    public boolean isEmpty(){
        if(size==0){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * To-String method
     */
    public String toString(){
        String str = ("[");
        Node<T> current = first;
        while (current != null) {
            str += (current.getData());
            if (current.getNext() != null) {
                str += (", ");
            }
            current = current.getNext();
        }
        str+=("]");
        return str.toString();
    }

    /**
     * inserts the item at the beginning of the list.
     * 
     * @param item which needs to be inserted
     */
    public void add(T item){
        if(size==0){
            first = new Node<T>(item);
            tail = first;
        }
        else{  
            Node<T> newNode = new Node<T>(item);
            newNode.setNext(first);
            first = newNode;
        }
        size++;
    }

    /**
     * Method to add an item to the end of the list
     * 
     * @param item which needs to be added
     */
    public void addLast(T item){
        Node<T> newNode = new Node<T>(item);
        if(size==0){
            add(item);
            return;
        }
        else {  
            tail.setNext(newNode);
            tail = newNode;
        }
        size++;
    }

    /**
     * Method to check if the given object is in the linkedlist or not
     * 
     * @param o which needs to be checked for its presence
     * @return a boolean getData() after checking
     */
    public boolean contains(Object o){
        Node<T> temp = first;
        for(int i=0; i<size; i++){
            if (o == temp.getData()){
                return true;
            }
            temp = temp.next;
        }
        return false;
    }
    
    /**
     * Gets the item at a given index
     * 
     * @param index 
     * @return the item at the index
     */
    public T get(int index){
        Node<T> temp = first;
        if (index == size-1){
            return getLast();
        } else{
            for(int i=0; i<index; i++){
                temp = temp.next;
            }
            return temp.getData();
        }
    }

    public T getLast(){
        return tail.getData();
    }

    /**
     * Remove and return the first item of the list
     * 
     * @return the first item
     */
    public T remove(){
        Node<T> temp = first;
        first = first.next;
        size--;
        if(size==0){
            tail = null;
        }
        return temp.getData();

        
    }   

    public T removeLast(){
        Node<T> walker = first;
        T temp = tail.getData();
        for(int i=0;i<size-2;i++){
            walker = walker.next;
        }
        walker.next = null;
        tail = walker;
    return temp;
    }

    /**
     * method to insert the item at a given index
     * 
     * @param index to insert the item at
     * @param item which needs to be inserted
     */
    public void add(int index, T item) {
        Node<T> newItem = new Node<T>(item);
        Node<T> walker = first;
        int i = 0;
        while (i < index - 1) {
            walker = walker.getNext();
            i++;
        }
        if (index == 0) {
            add(item);
            return;
        }
        newItem.setNext(walker.getNext());
        walker.setNext(newItem);
        size++;
    }

    /**
     * method to remove the iteam at the given index and return it
     * 
     * @param index at which the item needs to be removed
     * @return the removed item
     */
    public T remove(int index){
        T temp;
        if (index == 0) {
            temp = first.getData();
            first = first.next;
        } else {
            Node<T> current = first;
            for (int i = 0; i < index - 1; i++) {
                current = current.getNext();
            }
            temp = current.next.getData();
            current.next = current.next.next;

        }
        size--; 
        return temp;
    }

    /**
     * Method to check if two linkedlist contains the same items in same order
     */
    @SuppressWarnings("unchecked")
    public boolean equals(Object o) {
        if (!(o instanceof LinkedList)) {
            return false;
        }
        // If I have reached this line, o must be a LinkedList
        LinkedList<T> oAsALinkedList = (LinkedList<T>) o;
        // Now I have a reference to something Java knows is a LinkedList!
        int smallestLength = oAsALinkedList.size() < this.size() ? oAsALinkedList.size() : this.size();
        for (int i = 0; i < smallestLength; i++) {
            if (oAsALinkedList.get(i) != this.get(i))
                return false;
        }
        return true;
    }

    //// Return a new LLIterator pointing to the first of the list
    public Iterator<T> iterator(){
        return new LLIterator( this.first );
    }

    @Override
    public void offer(T item) {
        addLast(item);
    }

    @Override
    public T peek() {
        if (size==0){
            return null;
        }
        return first.getData();
    }

    @Override
    public T poll() {
        return remove();
    } 

    public T findMin(Comparator<T>comparator) {
        // Finds and returns the minimum element in the LinkedList according to the specified comparator.
        if (this.size() == 0) {
            return null;
        }
        T currentMin = first.getData();
        for (T item : this) {
            if (comparator.compare(currentMin, item) == -1) {
                currentMin = item;
            } 
        }
        return currentMin;
    }

    public T removeMin(Comparator<T>comparator) {
        // Removes and returns the minimum element in the LinkedList according to the specified comparator.
        if (this.size() == 0) {
            return null;
        }
        if (this.size == 1) {
            T currentMin = this.first.getData();
            first = null;
            tail = null;
            size--;
            return currentMin;
        }
        Node<T> temp = first;
        T currentMin = first.getData();
        int minIndex = 0;

        for (int i = 0; i < this.size - 2; i++) {
            Node<T> next = temp.next;
            if (comparator.compare(next.getData(), currentMin) == 1) {
                currentMin = next.getData();
                minIndex = i + 1;
            }
            temp = next;
        }

        temp = this.first;

        for (int i = 0; i < minIndex - 1; i++) {
            temp = temp.next;
        }

        if (minIndex == this.size - 1) {
            tail = temp.next;
            temp.next = temp.next.next;
        } else if (minIndex == 0) {
            this.first = temp.next;
        } else {
            temp.next = temp.next.next;
        }

        size--;
        return currentMin;
    }

}


