import deque.ArrayDeque61B;

import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    //normal test
     @Test
     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
     void noNonTrivialFields() {
         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                 .toList();

         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
     }

    @Test
    void getTest() {
         ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
         lld1.addFirst(1);
         assertThat(lld1.get(3) == 1).isTrue();
         assertThat(lld1.get(2) == null).isTrue();
         assertThat(lld1.get(1) == null).isTrue();
         lld1.addFirst(2);
         assertThat(lld1.get(2) == 2).isTrue();
     }



    @Test
    void isEmptyTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(1);
        assertThat(lld1.isEmpty()).isFalse();
        lld1.addLast(2);
        assertThat(lld1.isEmpty()).isFalse();
    }

    @Test
    void sizeTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.size() == 0).isTrue();
        lld1.addLast(1);
        assertThat(lld1.size() == 1).isTrue();
        lld1.addFirst(2);
        assertThat(lld1.size() == 2).isTrue();
    }

    @Test
    void toListTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        List<Integer> expected = new ArrayList<>();
        List<Integer> actual = new ArrayList<>();

        lld1.addLast(1);
        expected.add(1);
        assertThat(lld1.toList().size() == 8).isTrue();

        lld1.addFirst(1);
        lld1.addLast(2);
        expected.add(1);
        expected.add(2);
        actual = lld1.toList();
        assertThat(actual.size() == 8).isTrue();

    }

    @Test
    void removeFirstTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        lld1.addFirst(1);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.removeFirst() == 1).isTrue();
        assertThat(lld1.size() == 0).isTrue();

        lld1.addLast(2);
        assertThat(lld1.removeFirst() == 2).isTrue();
        assertThat(lld1.size() == 0).isTrue();
    }

    @Test
    void removeLastTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        lld1.addLast(1);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.removeLast() == 1).isTrue();
        assertThat(lld1.size() == 0).isTrue();
    }

    @Test
    void resizeTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 8; i++){
            lld1.addLast(i);
        }
        assertThat(lld1.size() == 8).isTrue();

        lld1.addFirst(8);
        assertThat(lld1.size() == 9).isTrue();
        assertThat(lld1.toList().contains(8)).isTrue();
    }

    @Test
    void resizeRoughTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 8; i++){
            lld1.addLast(i);
        }
        assertThat(lld1.size() == 8).isTrue();

        for(int i = 8; i < 10000; i++){
            lld1.addFirst(i);
        }
        for(int i = 10000; i < 20000; i++){
            lld1.addLast(i);
        }

        for(int i = 0; i < 20000; i++){
            assertThat(lld1.toList().contains(i)).isTrue();
        }
    }

    @Test
    void resizeDownTest() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 10000; i ++){
            lld1.addLast(i);
            lld1.addFirst(i);
        }

        for(int i = 0; i < 10000; i ++) {
            lld1.removeLast();
        }
        assertThat(lld1.size() <= 10000).isTrue();

        for(int i = 0; i < 10000; i ++) {
            lld1.removeFirst();
        }

        assertThat(lld1.size() <= 10).isTrue();
    }

    //######################################################################################
    //Coverage Tests

    //Flags for add tests
    @Test
    void add_first_from_empty() {
         //Check that addFirst works on an empty deque.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(88);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.toList().contains(88)).isTrue();
    }

    @Test
    void add_last_from_empty() {
         //Check that addLast works on an empty deque.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(88);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.toList().contains(88)).isTrue();
    }

    @Test
    void add_first_nonempty() {
         //Check that addFirst works on a non-empty deque.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(1);
        lld1.addFirst(3);
        assertThat(lld1.size() == 2).isTrue();
        assertThat(lld1.toList().contains(3)).isTrue();
    }

    @Test
    void add_last_nonempty() {
         //Check that addLast works on a non-empty deque.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(1);
        lld1.addLast(3);
        assertThat(lld1.size() == 2).isTrue();
        assertThat(lld1.toList().contains(3)).isTrue();
    }

    @Test
    void add_first_trigger_resize() {
        //Check that addFirst works when called on a full underlying array
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.addLast(i);
        }
        assertThat(lld1.size() == 8).isTrue();
        lld1.addFirst(8);
        assertThat(lld1.toList().contains(8)).isTrue();
        for(int i = 9; i < 10000; i++){
            lld1.addFirst(i);
        }
        for(int i = 9; i < 10000; i++){
            assertThat(lld1.toList().contains(i)).isTrue();
        }
    }

    @Test
    void add_last_trigger_resize() {
        //Check that addLast works when called on a full underlying array
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.addFirst(i);
        }
        assertThat(lld1.size() == 8).isTrue();
        lld1.addLast(8);
        assertThat(lld1.toList().contains(8)).isTrue();
        for(int i = 9; i < 10000; i++){
            lld1.addLast(i);
        }
        for(int i = 9; i < 10000; i++){
            assertThat(lld1.toList().contains(i)).isTrue();
        }
    }

    //Flags for add after remove tests
    @Test
    void add_first_after_remove_to_empty() {
        //Add some elements to a deque and remove them all, then check that addFirst still works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.addFirst(i);
        }
        assertThat(lld1.size() == 8).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.removeFirst();
        }
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addFirst(99);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.toList().contains(99)).isTrue();
    }

    @Test
    void add_last_after_remove_to_empty() {
        //Add some elements to a deque and remove them all, then check that addLast still works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        assertThat(lld1.isEmpty()).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.addFirst(i);
        }
        assertThat(lld1.size() == 8).isTrue();
        for(int i = 0; i < 8; i++){
            lld1.removeLast();
        }
        assertThat(lld1.isEmpty()).isTrue();
        lld1.addLast(99);
        assertThat(lld1.size() == 1).isTrue();
        assertThat(lld1.toList().contains(99)).isTrue();
    }

    //Flags for remove Tests
    @Test
    void remove_first() {
        //Check that removeFirst works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        lld1.addLast(1);
        lld1.addFirst(2);
        assertThat(lld1.removeFirst() == 2).isTrue();
        assertThat(lld1.toList().contains(1)).isTrue();
        assertThat(lld1.size() == 1).isTrue();
    }

    @Test
    void remove_last() {
        //Check that removeLast works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        lld1.addLast(1);
        lld1.addFirst(2);
        assertThat(lld1.removeLast() == 1).isTrue();
        assertThat(lld1.toList().contains(2)).isTrue();
        assertThat(lld1.size() == 1).isTrue();
    }

    @Test
    void remove_first_to_empty() {
        //Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeFirst works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 4; i++){
            lld1.addFirst(i);
        }
        for(int i = 4; i < 8; i++){
            lld1.addLast(i);
        }
        for(int i = 7; i > 3; i--){
            assertThat(lld1.removeLast() == i).isTrue();
        }
        for(int i = 3; i > -1; i--){
            assertThat(lld1.removeFirst() == i).isTrue();
        }
    }

    @Test
    void remove_last_to_empty() {
        //Add some elements to a deque and remove almost all of them.
        // Check that removing the last element with removeLast works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 4; i++){
            lld1.addFirst(i);
        }
        for(int i = 4; i < 8; i++){
            lld1.addLast(i);
        }
        for(int i = 3; i > -1; i--){
            assertThat(lld1.removeFirst() == i).isTrue();
        }
        for(int i = 7; i > 3; i--){
            assertThat(lld1.removeLast() == i).isTrue();
        }
    }

    @Test
    void size_after_remove_to_empty() {
        //Add some elements to a deque and remove them all, then check that size still works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 4; i++){
            lld1.addFirst(i);
        }
        for(int i = 0; i < 4; i++){
            lld1.removeFirst();
            assertThat(lld1.size() == 3 - i).isTrue();
        }
    }

    @Test
    void size_after_remove_from_empty() {
        //Remove from an empty deque, then check that size still works.
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 4; i++){
            lld1.addFirst(i);
        }
        for(int i = 0; i < 4; i++){
            lld1.removeFirst();
            assertThat(lld1.size() == 3 - i).isTrue();
        }
        assertThat(lld1.removeFirst() == null).isTrue();
        assertThat(lld1.size() == 0).isTrue();
    }

    @Test
    void to_list_empty() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);
        assertThat(lld1.toList().size() == 8).isTrue();




//        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
//        assertThat(lld1.toList().size() == 8).isTrue();
//        assertThat(lld1.toList().isEmpty()).isTrue();
    }

    //Enhancements
    //##################################################################################################
    @Test
    void addFirstTestBasicWithoutToList() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        for(int i = 0; i < 4; i++){
            lld1.addFirst(i);
        }
        for(int i : lld1){
            System.out.println(i);
        }
    }

    @Test
    void testEqual() {
        ArrayDeque61B<Integer> lld1 =  new ArrayDeque61B<>();
        ArrayDeque61B<Integer> lld2 =  new ArrayDeque61B<>();

        lld1.addFirst(1);
        lld1.addFirst(2);
        lld1.addFirst(3);

        lld2.addFirst(1);
        lld2.addFirst(2);
        lld2.addFirst(3);

        assertThat(lld1).isEqualTo(lld2);
        //if we don't override the equal method, it will fail.
        //expected: deque.ArrayDeque61B@6025e1b6
        //but was : [null, 3, 2]
        //because it tries to compare the address of the two objects, but we want to make
        //sure that these two objects contains same elements and in the same order

    }

    @Test
    public void toStringTest() {
        ArrayDeque61B<String> lld1 =  new ArrayDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
        //it will not fail.
        //if you don't override the toString method, it will use the default toString,
        //and you will will get this:LinkedListDeque61B@55182842
    }
}
