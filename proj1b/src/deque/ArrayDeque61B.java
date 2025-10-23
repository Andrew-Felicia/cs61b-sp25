package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T>{
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;

    public ArrayDeque61B() {
        size = 0;
        items = (T[]) new Object[8];
        nextFirst = 3;
        nextLast = 4;
    }

    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new ArrayDeque61BIterator();
    }

    private class ArrayDeque61BIterator implements Iterator<T> {
        private int wizPos;

        public ArrayDeque61BIterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public T next() {
            T returnItem = items[wizPos];
            wizPos += 1;
            return returnItem;
        }
    }

    //Note: The instanceof operator behaves a little strangely with generic types,
    // for reasons beyond the scope of this course. For example, if you want to check if
    // lst is an instance of a List<Integer>, you should use lst instanceof List<?> rather
    // than lst instanceof List<Integer>. Unfortunately, this is not able to check the types of
    // the elements, but it’s the best we can do.
    //question: when we use for i : something, where does the iterator starts to iterate?

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ArrayDeque61B<?> other)) return false;
        if(!(this.size == other.size)) return false;//seems like if i write this line above second, it doesn't work

        for(int i = 0; i < items.length; i++){
            T a = this.get(i);
            Object b = other.get(i);
            if(a != b) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.toList().toString();
    }


    @Override
    public void addFirst(T x) {
        if(size == items.length){
            resize(size * 2);
        }
        items[nextFirst] = x;
        size += 1;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);
    }

    @Override
    public void addLast(T x) {
        if(size == items.length){
            resize(size * 2);
        }
        items[nextLast] = x;
        size += 1;
        nextLast = Math.floorMod(nextLast + 1, items.length);
    }

    //resizing up or down the items
    public void resize(int capacity) {
        T[] newitems = (T[]) new Object[capacity];            //create new items

        for(int i = 0; i < this.size; i++){                   //copy the elements from old items to new items
            nextFirst = Math.floorMod(nextFirst + 1, items.length);
            newitems[i] = items[nextFirst];
        }

        nextLast = this.size;                                //alert the address of nextFirst and nextLast
        nextFirst = newitems.length - 1;

        items = newitems;                                    //change the reference from items to new items
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>(); //This is one location where you are allowed to use a Java data structure.
//        for(int i = 0; i < items.length; i++){
//            if(items[i] != null){
//                returnList.add(items[i]);
//            }
//        }
        for(int i = 0; i < this.items.length; i++){
                returnList.add(items[i]);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(size == 0) return true;
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if(this.size <= items.length * 0.25){
            resize(items.length / 2);
        }
        T result;
        if(this.size == 0) return null;
        nextFirst = Math.floorMod(nextFirst + 1, items.length);
        result = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        return result;
    }

    @Override
    public T removeLast() {
        if(this.size <= items.length * 0.25){
            resize(items.length / 2);
        }
        T result;
        if(this.size == 0) return null;
        nextLast = Math.floorMod(nextLast - 1, items.length);
        result = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        return result;
    }

    @Override
    public T get(int index) {
        if(index < 0 || index >= items.length){
            return null;
        }
        return this.items[index];

    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}

