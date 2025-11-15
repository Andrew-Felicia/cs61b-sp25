package hashmap;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

/**
 *  A hash table-backed Map implementation.
 *
 *  Assumes null keys will never be inserted, and does not resize down upon remove().
 *  @author YOUR NAME HERE
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    /**
     * Protected helper class to store key/value pairs
     * The protected qualifier allows subclass access
     */
    protected class Node {
        K key;
        V value;

        Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    /* Instance Variables */
    private Collection<Node>[] buckets;
    int size;
    int initialCapacity;
    double loadFactor;

    /** Constructors */
    public MyHashMap() {
        this.initialCapacity = 16;
        this.loadFactor = 0.75;
        this.size = 0;
        this.buckets = (Collection<Node>[]) new Collection[this.initialCapacity];
        for(int i = 0; i < this.initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    public MyHashMap(int initialCapacity) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = 0.75;
        this.size = 0;
        this.buckets = (Collection<Node>[]) new Collection[this.initialCapacity];
        for(int i = 0; i < this.initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * MyHashMap constructor that creates a backing array of initialCapacity.
     * The load factor (# items / # buckets) should always be <= loadFactor
     *
     * @param initialCapacity initial size of backing array
     * @param loadFactor maximum load factor
     */
    public MyHashMap(int initialCapacity, double loadFactor) {
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
        this.size = 0;
        this.buckets = (Collection<Node>[]) new Collection[this.initialCapacity];
        for(int i = 0; i < this.initialCapacity; i++) {
            buckets[i] = createBucket();
        }
    }

    /**
     * Returns a data structure to be a hash table bucket
     *
     * The only requirements of a hash table bucket are that we can:
     *  1. Insert items (`add` method)
     *  2. Remove items (`remove` method)
     *  3. Iterate through items (`iterator` method)
     *  Note that this is referring to the hash table bucket itself,
     *  not the hash map itself.
     *
     * Each of these methods is supported by java.util.Collection,
     * Most data structures in Java inherit from Collection, so we
     * can use almost any data structure as our buckets.
     *
     * Override this method to use different data structures as
     * the underlying bucket type
     *
     * BE SURE TO CALL THIS FACTORY METHOD INSTEAD OF CREATING YOUR
     * OWN BUCKET DATA STRUCTURES WITH THE NEW OPERATOR!
     */
    protected Collection<Node> createBucket() {
        return new LinkedList<Node>();
    }

    // TODO: Implement the methods of the Map61B Interface below
    // Your code won't compile until you do so!


    public void resize(int newCapacity) {
        Collection<Node>[] tmp = (Collection<Node>[]) new Collection[newCapacity];
        for(int i = 0; i < newCapacity; i++) {
            tmp[i] = createBucket();
        }
        for(int i = 0; i < this.initialCapacity; i++) {
            if(buckets[i] == null)  {continue;}
            for(Node n : buckets[i]) {
                int index = Math.floorMod(n.key.hashCode(), newCapacity);
                tmp[index].add(n);
            }

        }
        this.buckets = tmp;
        tmp = null;
        this.initialCapacity = newCapacity;

    }




    /**
     * Associates the specified value with the specified key in this map.
     * If the map already contains the specified key, replaces the key's mapping
     * with the value specified.
     *
     * @param key
     * @param value
     */
    @Override
    public void put(K key, V value) {
        if(key == null) {
            throw new IllegalArgumentException("null key");
        }

        if((double) size / this.initialCapacity > this.loadFactor) {
            resize((int) (this.initialCapacity * 2));
        }

        int index = Math.floorMod(key.hashCode(), this.initialCapacity);

        //check if the map already exists.
        for(Node n : buckets[index]) {
            if(n.key.equals(key)) {
                n.value = value;
                return;
            }
        }
        buckets[index].add(new Node(key, value));
        this.size += 1;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        for(int i = 0; i < this.initialCapacity; i++) {
            if(this.buckets[i] == null) {
                return null;
            }
            for(Node n : this.buckets[i]) {
                if(n.key.equals(key)) {
                    return n.value;
                }
            }
        }

        return null;
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        for(int i = 0; i < this.initialCapacity; i++) {
            if(this.buckets[i] == null) {
                return false;
            }
            for(Node n : this.buckets[i]) {
                if(n.key.equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns the number of key-value mappings in this map.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Removes every mapping from this map.
     */
    @Override
    public void clear() {
        this.size = 0;
        for(int i = 0; i < this.initialCapacity; i++) {
            if(this.buckets[i] == null) {continue;}
            this.buckets[i] = null;
        }
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for this lab.
     * If you don't implement this, throw an UnsupportedOperationException.
     */
    @Override
    public Set<K> keySet() {
        //return Set.of();
        throw new UnsupportedOperationException();
    }

    /**
     * Removes the mapping for the specified key from this map if present,
     * or null if there is no such mapping.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException.
     *
     * @param key
     */
    @Override
    public V remove(K key) {
        //return null;
        throw new UnsupportedOperationException();
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<K> iterator() {
        //return null;
        throw new UnsupportedOperationException();
    }



}
