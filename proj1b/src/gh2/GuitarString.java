package gh2;

import deque.ArrayDeque61B;
import deque.Deque61B;
import edu.princeton.cs.algs4.StdAudio;

// TODO: maybe more imports

//Note: This file will not compile until you complete the Deque61B implementations
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final
     * means the values cannot be changed at runtime. We'll discuss this and
     * other topics in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor
    private static int capacity;
    /* Buffer for storing sound data. */
    // TODO: uncomment the following line once you're ready to start this portion
    private Deque61B<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        // TODO: Initialize the buffer with capacity = SR / frequency. You'll need to
        //       cast the result of this division operation into an int. For
        //       better accuracy, use the Math.round() function before casting.
        //       Your should initially fill your buffer with zeros.
        capacity = (int) Math.round(SR / frequency);

        buffer = new ArrayDeque61B<>();
        buffer.removeLast(); //make nextFirst and nextLast pointing at the same address
//        for(int i = 0; i < buffer.toList().size(); i++){
//            buffer.addFirst(0.0);
//        }
//        while(buffer.toList().size() < capacity){
//            buffer.addFirst(0.0);
//        }
//        while(buffer.toList().size() > capacity){
//            buffer.removeFirst();
//        }
        // fill the buffer with zeros
        for (int i = 0; i < capacity; i++) {
            buffer.addFirst(0.0);
        }

    }


    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {
        // TODO: Dequeue everything in buffer, and replace with random numbers
        //       between -0.5 and 0.5. You can get such a number by using:
        //       double r = Math.random() - 0.5;
        //
        //       Make sure that your random numbers are different from each
        //       other. This does not mean that you need to check that the numbers
        //       are different from each other. It means you should repeatedly call
        //       Math.random() - 0.5 to generate new random numbers for each array index.
        for(int i = 0; i < capacity; i++){  //remove what we have added inside buffer
            buffer.removeFirst();
        }

        for(int i = 0; i < capacity; i++){
            buffer.addFirst(Math.random() - 0.5);     //initialize buff
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm.
     */
    public void tic() {
        // TODO: Dequeue the front sample and enqueue a new sample that is
        //       the average of the two multiplied by the DECAY factor.
        //       **Do not call StdAudio.play().**
        double front = sample();
        buffer.removeLast();
        double end;
        //seems like it's not possible to use buffer.get() here.
        double second = sample();
        end = DECAY * 0.5 * (front + second);
        buffer.addFirst(end);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        // TODO: Return the correct thing.
        double returnValue = buffer.removeLast();
        buffer.addLast(returnValue);
        return returnValue;
    }
}
    // TODO: Remove all comments that say TODO when you're done.
