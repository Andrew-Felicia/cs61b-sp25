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
