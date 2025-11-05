import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V extends Comparable<V>> implements Map61B<K, V>{

    private class BSTNode {
        K key;
        V value;
        BSTNode left;
        BSTNode right;

        public BSTNode(K key, V value, BSTNode left, BSTNode right){
            this.key = key;
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }


    private BSTNode root; //dummy node,similar to sentinel
    private int size = 0;



    /* Returns whether this BSTSet's root's key is greater than, equal to, or less
     * than the other BSTSet's root's key, following the usual `compareTo`
     * convention.
     */
//    public int compareRoots(BSTMap<K, V> other) {
//        return this.root.key.compareTo(other.root.key);
//    }


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
        this.root = putRec(key, value, this.root);
    }

    public BSTNode putRec(K key, V value, BSTNode bstNode){
        if(bstNode == null){
            bstNode = new BSTNode(key, value, null, null);
            this.size += 1;
            return bstNode;
        }

        int tmp = key.compareTo(bstNode.key);
        if(tmp < 0){
            bstNode.left = putRec(key, value, bstNode.left);
        }
        else if(tmp > 0){
            bstNode.right = putRec(key, value, bstNode.right);
        }
        else{
            bstNode.key = key;
            bstNode.value = value;
        }
        return bstNode;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     *
     * @param key
     */
    @Override
    public V get(K key) {
        return getRec(key, this.root);
    }

    public V getRec(K key, BSTNode bstNode) {
        if(bstNode == null) {return null;}

        int tmp = key.compareTo(bstNode.key);
        if(tmp < 0){
            return getRec(key, bstNode.left);
        }
        else if(tmp > 0){
            return getRec(key, bstNode.right);
        }
        else {
            return bstNode.value;
        }
    }

    /**
     * Returns whether this map contains a mapping for the specified key.
     *
     * @param key
     */
    @Override
    public boolean containsKey(K key) {
        return containsKeyRec(key, this.root);
    }

    public boolean containsKeyRec(K key, BSTNode bstNode) {
        if(bstNode == null){
            return false;
        }

        int tmp = key.compareTo(bstNode.key);
        if(tmp < 0) {
            return containsKeyRec(key, bstNode.left);
        }
        else if(tmp > 0) {
            return containsKeyRec(key, bstNode.right);
        }
        else {
            //return bstNode.key == key; // you hit the  Law of the Broken Futon.
            return true;
            //return bstNode.key.equals(key); //this line also works.but don't need to compare again.
        }

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
        this.root = null;
        this.size = 0;
    }

    /**
     * Returns a Set view of the keys contained in this map. Not required for Lab 7.
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
     * Not required for Lab 7. If you don't implement this, throw an
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
