package core;

import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.RandomUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

public class World {

    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    public Map<Room, Boolean> roomsRecorder;// store all the rooms you have created for later use.
                                    //first:the room object; second: true for room and false for hallway.

    boolean hallway; //this boolean value is dynamic and will change very often, mainly used in
                     //method MakeRoomOrHallway, and most importantly used as the second parameter for class
                     //variable roomsRecorder.
    TETile[][] world;
    //Your seeds are:
    //        6828422841946483286
    //        6342731849134449137
    //        9164226534851273628
    //        4711289055031179240
    //        8018081004497512468
    long seed;//add an “L” after the seed so that Java recognizes it as a long number,
    Random r;

    /**
     * Main method:this method will create the pseudorandom world based on seed.
     * process of make rooms:
     * using BFS algorithm, and LinkedList to calculate the coordinate of a new room,
     * and then render it to the world.
     * @param seed
     */
    public void MakeNewWorld(long seed) {
        this.seed = seed;
        this.r = new Random(this.seed);
        // Initialization
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        world = new TETile[WIDTH][HEIGHT];
        //fill the world with Nothing.
        FillWithNothing();

        Room firstRoom = this.MakeFirstRoom();
        firstRoom.MakeRoom(world);
        roomsRecorder = new HashMap<>();
        roomsRecorder.put(firstRoom, Boolean.TRUE);

        LinkedList<Room> rooms = new LinkedList<>();
        rooms.add(firstRoom);

        while(!rooms.isEmpty()) {
            Room cur = rooms.removeFirst();
            sideloop:
            for(int i = 0; i < 4; i++) {
                Index bL = null;
                Index bR = null;
                Index tL = null;
                Index tR = null;
                Room newRoom = null;
                if(i == 0){
                    boolean right = RandomUtils.bernoulli(r, 0.5);
                    int y = cur.GettL().GetY();
                    if(right){
                        int x = cur.GettL().GetX() + RandomUtils.uniform(r, 0, cur.GetW() - 2);
                        bL = new Index(x, y);
                    } else{
                        int x = cur.GettR().GetX() - RandomUtils.uniform(r, 0, cur.GetW() - 2);
                        bR = new Index(x, y);
                    }
                } else if(i == 1) {
                    boolean above = RandomUtils.bernoulli(r, 0.5);
                    int x = cur.GettR().GetX();
                    if(above){
                        int y = cur.GetbR().GetY() + RandomUtils.uniform(r, 0, cur.GetH() - 2);
                        bL = new Index(x, y);
                    } else{
                        int y = cur.GettR().GetY() - RandomUtils.uniform(r, 0, cur.GetH() - 2);
                        tL = new Index(x, y);
                    }

                } else if(i == 2) {
                    boolean right = RandomUtils.bernoulli(r, 0.5);
                    int y = cur.GetbL().GetY();
                    if(right){
                        int x = cur.GetbL().GetX() + RandomUtils.uniform(r, 0, cur.GetW() - 2);
                        tL = new Index(x, y);
                    } else{
                        int x = cur.GetbR().GetX() - RandomUtils.uniform(r, 0, cur.GetW() - 2);
                        tR = new Index(x, y);
                    }

                } else{
                    boolean above = RandomUtils.bernoulli(r, 0.5);
                    int x = cur.GettL().GetX();
                    if(above){
                        int y = cur.GetbL().GetY() + RandomUtils.uniform(r, 0, cur.GetH() - 2);
                        bR = new Index(x, y);
                    } else{
                        int y = cur.GettL().GetY() - RandomUtils.uniform(r, 0, cur.GetH() - 2);
                        tR = new Index(x, y);
                    }
                }

                newRoom = this.MakeRoomOrHallway(bL, bR, tL, tR);

                int xStart = newRoom.GetbL().GetX();
                int xEnd = newRoom.GetbR().GetX();
                int yStart = newRoom.GetbL().GetY();
                int yEnd = newRoom.GettL().GetY();

                if(i == 0){
                    yStart += 1;
                } else if(i == 1){
                    xStart += 1;
                } else if(i == 2){
                    yEnd -= 1;
                } else{
                    xEnd -= 1;
                }

                //make sure the room or hallway is valid:1.doesn't go out of the world
                //2.doesn't build on other room or hallway.
                for(int x = xStart; x <= xEnd; x++) {
                    for(int y = yStart; y <= yEnd; y++) {
                        if(x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) {
                            continue sideloop;
                        } else if(world[x][y] != Tileset.NOTHING) {
                            continue sideloop;
                        }
                    }
                }

                newRoom.MakeRoom(world);
                this.MakeEntrance(cur, newRoom, i);
                rooms.add(newRoom);
                if(!this.hallway) {
                    roomsRecorder.put(newRoom, Boolean.TRUE);
                } else {
                    roomsRecorder.put(newRoom, Boolean.FALSE);
                }

            }//end of for loop.
        }//end of while loop.


        //draw to world to screen
        ter.renderFrame(world);

    }


    // Fill grid with NOTHING tiles.
    //Notice that we had to explicitly assign unused squares to Tileset.NOTHING,
    // the pure black tile. If you don’t initialize unused squares, you’ll get
    // a NullPointerException when you try to draw the world.
    private void FillWithNothing() {
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

    }

    /**
     * make the first random room in the world
     * @return Room.
     */
    private Room MakeFirstRoom() {
        //generate the room in this range: 4 ~ 10 * 4 ~ 10
        int width = RandomUtils.uniform(r, 4, 11);
        int height = RandomUtils.uniform(r, 4, 11);
        Index bL = new Index(RandomUtils.uniform(r, 0, 70), RandomUtils.uniform(r, 0, 30));
        Room firstRoom = new Room(bL, null, null, null,  width, height);
        return firstRoom;

    }

    /**
     * this function decides whether to build room or hallway based on width and height.
     * @param bL
     * @param bR
     * @param tL
     * @param tR
     * @return
     */
    private Room MakeRoomOrHallway(Index bL, Index bR, Index tL, Index tR) {
        int width;
        int height;
        this.hallway = RandomUtils.bernoulli(r, 0.5);
        if(hallway) {
            boolean vertical = RandomUtils.bernoulli(r, 0.5);
            if(vertical) {
                width = 3;
                height = RandomUtils.uniform(r, 4, 13);
            }else {
                width = RandomUtils.uniform(r, 4, 13);
                height = 3;
            }
        }else {
            width = RandomUtils.uniform(r, 4, 11);
            height = RandomUtils.uniform(r, 4, 11);
        }
        Room newRoom = new Room(bL, bR, tL, tR, width, height);
        return newRoom;
    }

    /**
     * make entrance between current room/hallway and the new-making room/hallway.
     */
    private void MakeEntrance(Room cur, Room newRoom, int i) {
        int x;
        int y;
        if(i == 0 || i == 2) {
            int xLeft;
            int xRight;
            if(cur.GetbL().GetX() <= newRoom.GetbL().GetX()) {
                xLeft = newRoom.GetbL().GetX();
            }else {
                xLeft = cur.GetbL().GetX();
            }
            if(cur.GetbR().GetX() <= newRoom.GetbR().GetX()) {
                xRight = cur.GetbR().GetX();
            }else {
                xRight = newRoom.GetbR().GetX();
            }
            if(xRight - xLeft == 2) {
                x = xLeft + 1;
            }else {
                x = RandomUtils.uniform(r, xLeft + 1, xRight);
                //x = r.nextInt(xRight - xLeft - 1) + xLeft + 1;
            }
            if(i == 0) {
                y = cur.GettL().GetY();
            }else {
                y = cur.GetbL().GetY();
            }
        }else {
            int yDown;
            int yUp;
            if(cur.GettR().GetY() <= newRoom.GettR().GetY()) {
                yUp = cur.GettR().GetY();
            }else {
                yUp = newRoom.GettR().GetY();
            }
            if(cur.GetbR().GetY() >= newRoom.GetbR().GetY()) {
                yDown = cur.GetbR().GetY();
            }else {
                yDown = newRoom.GetbR().GetY();
            }
            if(yUp - yDown == 2) {
                y = yDown + 1;
            }else {
                y = RandomUtils.uniform(r, yDown + 1, yUp);
                //y = r.nextInt(yUp - yDown - 1) + yDown + 1;
            }
            if(i == 1) {
                x = cur.GettR().GetX();
            }else {
                x = cur.GetbL().GetX();
            }

        }
        world[x][y] = Tileset.FLOOR;

    }

    /**
     * return the world for use by other class.
     * @return TETile[][].
     */
    public TETile[][] GetWorld() {
        return this.world;
    }

    /**
     * in order to have the same pseudorandomness, have to use the same Random object.
     * @return return the Random instance r to be used by other class.
     */
    public Random GetRandom() {
        return this.r;
    }


    public Map<Room, Boolean> GetRoomsRecorder() {
        return this.roomsRecorder;
    }
}
