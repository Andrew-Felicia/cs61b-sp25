package Lab9;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.awt.*;
import java.util.Random;

/**
 * Draws a world initially full of trees.
 */
public class Task3 {
    /**
     * Fills the entire 2D world with the Tileset.TREE tile.
     */
    private static final int WORLD_HEIGHT = 15;
    private static final int WORLD_WIDTH = 30;

    private static final long SEED = 2873124;
    private static final long SEED1 = 345611111;
    private static final Random RANDOM = new Random(SEED);
    private static final Random RANDOM1 = new Random(SEED1);

    private static void fillWithTrees(TETile[][] world) {
        // fills in a block 15 tiles wide by 5 tiles tall
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                world[x][y] = Tileset.TREE;
            }
        }
    }

    private static void drawSquare(TETile[][] world, int startX, int startY, int size, TETile tile) {
        for (int x = startX; x < startX + size; x++) {
            if(x > WORLD_WIDTH - 1) {
                break;
            }
            for (int y = startY; y > startY - size; y--) {
                if(y < 0){
                    break;
                }
                world[x][y] = tile;
            }
        }
    }

    private static void addRandomSquare(TETile[][] world, Random rand) {
        int startX = RandomUtils.uniform(rand, 0, 30);
        int startY = RandomUtils.uniform(rand, 0, 15);
        int size = RandomUtils.uniform(rand, 3, 8);
        drawSquare(world, startX, startY, size, randomTile());

    }

    /** Picks a RANDOM tile with a 33% change of being
     *  a wall, 33% chance of being a flower, and 33%
     *  chance of being empty space.
     */
    private static TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = RANDOM1.nextInt(3);
        return switch (tileNum) {
            case 0 -> Tileset.WALL;
            case 1 -> Tileset.FLOWER;
            default -> Tileset.WATER;
        };
    }


    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(30, 20);
        int tmp = 0;

        // initialize tiles
        TETile[][] world = new TETile[WORLD_WIDTH][WORLD_HEIGHT];
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        fillWithTrees(world);
        //drawSquare(world, 10, 7, 5, Tileset.FLOWER);

        char c; // Variable for saving the most recent character typed by the user.

        // This outer infinite-loop allows the game to continue indefinitely, until the user quits.
        while (true) {

            // hasNextKeyTyped checks if the user has typed a key that we haven't processed.
            // This loop runs until all unprocessed keys are processed.
            // If there are no unprocessed keys, we go back to the outer infinite loop to wait for the next key.
            while (StdDraw.hasNextKeyTyped()) {

                // nextKeyTyped returns the next key to process.
                // Always check hasNextKeyTyped before calling nextKeyTyped.
                // If you call nextKeyTyped and there's no key to process, code will crash!
                c = StdDraw.nextKeyTyped();

                c = Character.toLowerCase(c);

                // Switch statements can be useful to replace long if-else statements!
                switch (c) {
                    case '1':
                        addRandomSquare(world, RANDOM);
                        tmp += 1;
                        break;
                    case '2':
                        addRandomSquare(world, RANDOM);
                        tmp += 1;
                        break;
                    case '3':
                        addRandomSquare(world, RANDOM);
                        tmp += 1;
                        break;
                    case '4':
                        addRandomSquare(world, RANDOM);
                        tmp += 1;
                        break;
                    case '5':
                        addRandomSquare(world, RANDOM);
                        tmp += 1;
                        break;
                    case 'q':
                        System.exit(0); // Closes the game window and quits the game.
                        break;
                    default:
                        break;
                }
                StdDraw.setPenColor(Color.white);
                StdDraw.textLeft(1, 17, "Number of squares: " + tmp);
                StdDraw.pause(2);
                StdDraw.show();


            }


            // draws the world to the screen
            ter.renderFrame(world);
            StdDraw.pause(2);
        }
    }
}