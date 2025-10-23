import java.util.Iterator;
import java.util.List;
import java.util.ArrayList; // import the ArrayList class


public class LinkedListDeque61B<T> implements Deque61B<T>{

    private class Node{
        public Node first;
        public T item;
        public Node next;
        public Node(Node f, T i, Node n){
            first = f;
            item = i;
            next = n;
        }
    }

    private Node sentinel;
    private int size;

    public LinkedListDeque61B() {
        size = 0;
        sentinel = new Node(null, null, null);
        sentinel.first = sentinel;
        sentinel.next = sentinel;
    }


    /** returns an iterator (a.k.a. seer) into ME */
    public Iterator<T> iterator() {
        return new Deque61BIterator();
    }
    private class Deque61BIterator implements Iterator<T> {
        private Node current;

        public Deque61BIterator() {
            current = sentinel.next; // start at first element
        }

        public boolean hasNext() {
            return current != sentinel; // stop when back at sentinel
        }

        public T next() {
            T item = current.item;
            current = current.next; // move forward
            return item;
        }
    }

    //Note: The instanceof operator behaves a little strangely with generic types,
    // for reasons beyond the scope of this course. For example, if you want to check if
    // lst is an instance of a List<Integer>, you should use lst instanceof List<?> rather
    // than lst instanceof List<Integer>. Unfortunately, this is not able to check the types of
    // the elements, but it’s the best we can do.
    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof LinkedListDeque61B<?> other)) return false;
        if(this.size != other.size) return false;

        for (int i = 0; i < size(); i++) {
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
        size += 1;
        Node tmp = new Node(sentinel, x, sentinel.next);
        sentinel.next.first = tmp;
        sentinel.next = tmp;
    }

    @Override
    public void addLast(T x) {
        size += 1;
        Node tmp = new Node(sentinel.first, x, sentinel);
        sentinel.first.next = tmp;
        sentinel.first = tmp;
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();

        Node p = new Node(this.sentinel,null,null);
        while(p.first.next != this.sentinel){
            p.first = p.first.next;
            returnList.add(p.first.item);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        if(this.sentinel.next == this.sentinel && this.sentinel.first == this.sentinel){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public T removeFirst() {
        if(size == 0){
            return null;
        }


        T result = this.sentinel.next.item;
        this.sentinel.next = this.sentinel.next.next;
        this.sentinel.next.first.next = null;
        this.sentinel.next.first.first = null;
        this.sentinel.next.first = this.sentinel;
        size -= 1;
        return result;

    }

    @Override
    public T removeLast() {
        if(size == 0){
            return null;
        }
        T result = this.sentinel.first.item;
        this.sentinel.first.next = null;
        this.sentinel.first = this.sentinel.first.first;
        this.sentinel.first.next.first = null;
        this.sentinel.first.next = this.sentinel;
        size -= 1;
        return result;


    }

    @Override
    public T get(int index) {
        if(index > this.size - 1 || index < 0){
            return null;
        }
        Node p = new Node(this.sentinel,null,null);
        while(p.first.next != this.sentinel){
            p.first = p.first.next;
            if(index == 0){
                return p.first.item;
            }
            index -= 1;
        }
        return null;
    }

    @Override
    public T getRecursive(int index) {
        Node p = new Node(this.sentinel,null,null);
        return getRecursiveHelper(index, p);

    }
    public T getRecursiveHelper(int index,Node p){
        if(size == 0){
            return null;
        }else if(index > this.size - 1 || index < 0){
            return null;
        }else if(index == 0){
            return p.first.next.item;
        }else{
            p.first = p.first.next;
            return getRecursiveHelper(index - 1, p);
        }
    }


}
