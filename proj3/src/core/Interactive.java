package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import java.awt.*;
import java.util.*;

public class Interactive {
    //basic things needed to build random world
    public TETile[][] world;   //store the world of Tiles.returned by randomWorld
    private Random r;           //store the random created from the seed.
    private World randomWorld;  //the World class, randomWorld is a instance of World class.
    private long seed;          //store user input seed.

    private Index currentAvatarPos;   //store the position of avatar.

    private TERenderer ter = new TERenderer(); //render machine.

    private Map<Room, Boolean> roomsRecorder;// store all the rooms you have created for later use.

    //about light.
    private Map<Integer, Integer> LightPos;//store th positions of lights.
    private boolean LightStatus; //true for light on, false otherwise.
    //first:the room object; second: true for room and false for hallway.
    int radius = 4;     //the light effect radius.
    Color LightColor = new Color(0, 79, 248);//light source color.
    int numLights = 15; //decide how much lights you wanna put here, if it's more than the
    //number of the rooms, then every room will have one light.



///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// constructors.
    /**
     * first constructor.Using for the new game.Building new random world.
     * @param seed the seed using to build pseudorandom world.
     */
    public Interactive(long seed) {
        this.LightStatus = false;
        this.seed = seed;
        randomWorld = new World();
        randomWorld.MakeNewWorld(seed);
        r = randomWorld.GetRandom();
        world = randomWorld.GetWorld();
        roomsRecorder = randomWorld.GetRoomsRecorder();
        this.CreateAvatar(world);
        this.AddLight();
        ter.initialize(World.WIDTH, World.HEIGHT + 3); //decide the random world canvas size.
        ter.renderFrame(world);//render avatar to the world.
    }

    /**
     * second constructor.Using for load the game.
     * @param seed
     * @param AvatarPos
     * @param LightPos
     */
    public Interactive(long seed, Index AvatarPos, Map<Integer, Integer> LightPos) {
        this.LightStatus = false;
        this.seed = seed;
        randomWorld = new World();
        randomWorld.MakeNewWorld(seed);
        r = randomWorld.GetRandom();
        world = randomWorld.GetWorld();
        this.CreateAvatar(world, AvatarPos.GetX(), AvatarPos.GetY());
        this.AddLight(LightPos);
        ter.initialize(World.WIDTH, World.HEIGHT + 3); //decide the random world canvas size.
        ter.renderFrame(world);//render avatar to the world.
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///methods about avatar.
    /**
     * create avatar in the world which we just created.
     * @param world the world we just created.
     */
    private void CreateAvatar(TETile[][] world) {
        boolean placed = false;
        while(!placed) {
            int x = r.nextInt(World.WIDTH);
            int y = r.nextInt(World.HEIGHT);
            if(world[x][y] == Tileset.FLOOR) {
                currentAvatarPos = new Index(x, y);
                world[x][y] = new TETile(world[x][y], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
                placed = true;
            }
        }
    }

    /**
     * create avatar at the give world coordinate.
     * @param world the world you want to put avatar.
     * @param x avatar x-coordinate
     * @param y avatar y-coordinate.
     */
    private void CreateAvatar(TETile[][] world, int x, int y) {
        world[x][y] = new TETile(world[x][y], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
        currentAvatarPos = new Index(x, y);
    }


    /**
     * move avatar around the world based on user input.
     * @param userChar user input.
     */
    public void MoveAvatar(char userChar) {
        int x = currentAvatarPos.GetX();
        int y = currentAvatarPos.GetY();

        if(userChar == 'w') {
            if(world[x][y + 1].equals(Tileset.FLOOR)) {
                currentAvatarPos = new Index(x, y + 1);
                world[x][y] = new TETile(world[x][y], Tileset.FLOOR.character(), Tileset.FLOOR.getTextColor());
                world[x][y + 1] = new TETile(world[x][y + 1], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
                ter.renderFrame(world);
            }
        } else if(userChar == 's') {
            if(world[x][y - 1].equals(Tileset.FLOOR)) {
                currentAvatarPos = new Index(x, y - 1);
                world[x][y] = new TETile(world[x][y], Tileset.FLOOR.character(), Tileset.FLOOR.getTextColor());
                world[x][y - 1] = new TETile(world[x][y - 1], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
                ter.renderFrame(world);
            }
        } else if(userChar == 'a') {
            if(world[x - 1][y].equals(Tileset.FLOOR)) {
                currentAvatarPos = new Index(x - 1, y);
                world[x][y] = new TETile(world[x][y], Tileset.FLOOR.character(), Tileset.FLOOR.getTextColor());
                world[x - 1][y] = new TETile(world[x - 1][y], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
                ter.renderFrame(world);
            }
        } else {
            if(world[x + 1][y].equals(Tileset.FLOOR)) {
                currentAvatarPos = new Index(x + 1, y);
                world[x][y] = new TETile(world[x][y], Tileset.FLOOR.character(), Tileset.FLOOR.getTextColor());
                world[x + 1][y] = new TETile(world[x + 1][y], Tileset.AVATAR.character(), Tileset.AVATAR.getTextColor());
                ter.renderFrame(world);
            }
        }

    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////



///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// HUD.
    /**
     * HUD(Heads Up Display).
     * this function will show what the mouse cursor pointing at to the top-right of the screen.
     */
    public void HUD() {
        int x = (int) StdDraw.mouseX();
        int y = (int) StdDraw.mouseY();
        ter.renderFrame(world);//if you don't render the world, the world will be static, not dynamics,which means it
        //will only show what mouse cursor pointing at once you enter the world. and most important point is that
        //let stdDraw to know where to draw(which canvas to draw).

        StdDraw.setPenColor(Color.WHITE); //if you don't set it, the default color will be black.
        Font HUD = new Font("sans serif", Font.BOLD, 15);
        StdDraw.setFont(HUD);//if don't set font, you will not see anything on the screen.
        StdDraw.text(10, World.HEIGHT + 2, "Press ':' to see options.");
        StdDraw.text(30, World.HEIGHT + 2, "Press t/T to switch on/off the light.");
        StdDraw.show();

        if(x >= 0 && x < World.WIDTH && y >= 0 && y < World.HEIGHT){
            if(world[x][y] == Tileset.FLOOR) {
                StdDraw.text(World.WIDTH -10, World.HEIGHT + 2, "Floor");
                StdDraw.show();
                StdDraw.pause(50);//helps to reduce Flickering.
            } else if(world[x][y] == Tileset.WALL) {
                StdDraw.text(World.WIDTH - 10, World.HEIGHT + 2, "Wall");
                StdDraw.show();
                StdDraw.pause(50);
            } else if(world[x][y] == Tileset.AVATAR) {
                StdDraw.text(World.WIDTH - 10, World.HEIGHT + 2, "You");
                StdDraw.show();
                StdDraw.pause(50);
            } else{
                StdDraw.text(World.WIDTH - 10, World.HEIGHT + 2, "Outside");
                StdDraw.show();
                StdDraw.pause(50);
            }
        } else {
            StdDraw.text(World.WIDTH - 10, World.HEIGHT + 2, "Outside");
            StdDraw.show();
            StdDraw.pause(50);
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
/// methods using to add lights to the world.

    //there's a small bug here, 1.when turn on the light, some of the light will not bright.
    //2.some of the tile which should have light effect doesn't have light effect.


    /**
     * switch on/off the light.
     */
    public void SwitchTheLight() {
        LightStatus = !LightStatus;
        //set the light effect, but the light will only inside the room, will not go through wall or entrance.
        if(LightStatus) {
            for(Map.Entry<Integer, Integer> entry : LightPos.entrySet()) {
                this.LightEffect(entry.getKey(), entry.getValue(), radius, true);
            }
        } else {
            for(Map.Entry<Integer, Integer> entry : LightPos.entrySet()) {
                this.LightEffect(entry.getKey(), entry.getValue(), radius, false);
            }
        }
    }
    /**
     * Light Sources: Add the ability for light sources to affect how the world is rendered,
     * with at least one light source that can be turned on and off with a keypress.
     * The intensity of the light must diminish in a gradient as the distance from the
     * source increases. Light sources should fill a majority of the room it belongs to,
     * and light should not pass through walls.
     * this method is used for new game.
     *
     * there's some bug here: the light effect tile will appear on the entrance,and sometimes the tile which should have
     * light effect doesn't have light effect.
     *
     */
    public void AddLight() {
        //used for create only one single random light inside the world.
//        boolean placed = false;
//        LightPos = new HashMap<>();
//        while(!placed) {
//            int x = r.nextInt(World.WIDTH);
//            int y = r.nextInt(World.HEIGHT);
//            if(world[x][y] == Tileset.FLOOR && world[x][y] != Tileset.AVATAR) {
//                world[x][y] = Tileset.LIGHT;
//                LightPos.put(x, y);
//                placed = true;
//            }
//        }
        this.LightPos = new HashMap<>();

        // collect all the rooms instead of hallways.
        LinkedList<Room> roomList = new LinkedList<>();
        for (Map.Entry<Room, Boolean> entry : this.roomsRecorder.entrySet()) {
            if (entry.getValue()) { // true = room
                roomList.add(entry.getKey());
            }
        }

        // if requested lights > rooms, just put one per room
        int lightsToPlace = Math.min(numLights, roomList.size());

        // shuffle rooms to randomly pick which rooms get lights
        Collections.shuffle(roomList, r);

        for (int i = 0; i < lightsToPlace; i++) {
            Room room = roomList.pollLast();//Retrieves and removes the last element of this list, or returns null if this list is empty.

            // pick a random tile inside the room (avoid walls)
            assert room != null;
            int x = room.GetbL().GetX() + r.nextInt(room.GetW() - 2) + 1;
            int y = room.GetbL().GetY() + r.nextInt(room.GetH() - 2) + 1;

            // make sure we don't overwrite the avatar or non-floor tiles
            if (world[x][y].equals(Tileset.FLOOR) && !world[x][y].equals(Tileset.AVATAR)) {
                world[x][y] = Tileset.LIGHT;  // your light tile
                this.LightPos.put(x, y); //record every light source you have added.
            }
        }

    }

    /**
     * this method is used for load the game.
     * @param LightPos
     */
    public void AddLight(Map<Integer, Integer> LightPos) {
        for(Map.Entry<Integer, Integer> entry : LightPos.entrySet()) {
            world[entry.getKey()][entry.getValue()] = Tileset.LIGHT;
        }
        this.LightPos = LightPos;//this line is very important, without this line, program will
                                 //start to crash from the second load.ChatGpt found out this bug and fixed it.
    }

    /**
     * this function helps to set light effect, with gradient effect.
     * @param lx light source x-coordinate
     * @param ly light source y-coordinate
     * @param radius the light reach range, a circle.
     * @param onOff true to add light effect, false to remove light effect.
     * Inspired by ChatGpt.
     */
    private void LightEffect(int lx, int ly, int radius, boolean onOff) {
        if(onOff){
            world[lx][ly] = world[lx][ly].withBackground(LightColor);
        } else {
            world[lx][ly] =  world[lx][ly].withBackground(Color.black);
        }

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {

                int x = lx + dx;
                int y = ly + dy;

                if (x < 0 || x >= World.WIDTH || y < 0 || y >= World.HEIGHT) continue;

                double dist = Math.sqrt(dx * dx + dy * dy);
                if (dist > radius) continue;

                float factor = 1 - (float)(dist / radius);

                Color blended = new Color(
                        (int)(LightColor.getRed() * factor),
                        (int)(LightColor.getGreen() * factor),
                        (int)(LightColor.getBlue() * factor)
                );

                if (!isBlocked(lx, ly, x, y) && world[x][y].equals(Tileset.FLOOR)) {
                    if(onOff) {
                        world[x][y] = world[x][y].withBackground(blended);
                    } else {
                        world[x][y] = world[x][y].withBackground(Color.black);
                    }
                }
            }
        }
    }

    /**
     * first you set the light radius, and this function check if the light will be blocked by wall from the
     * farest place to light source.
     * @param x0 light source x-coordinate
     * @param y0 light source y-coordinate
     * @param x1 check point x-coordinate
     * @param y1 check point y-coordinate
     * @return true if is blocked, false otherwise.
     * Inspired by ChatGpt.
     */
    private boolean isBlocked(int x0, int y0, int x1, int y1) {
        int dx = x1 - x0;
        int dy = y1 - y0;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        for (int i = 1; i < steps; i++) {
//            int x = x0 + dx * i / steps;
//            int y = y0 + dy * i / steps; this will incur some tile doesn't hava light which should have light.
            int x = x0 + (int)Math.round((double)dx * i / steps);
            int y = y0 + (int)Math.round((double)dy * i / steps);

            // safety check (avoid out-of-bounds)
            if (x < 0 || x >= World.WIDTH || y < 0 || y >= World.HEIGHT) {
                return true;
            }

            if (world[x][y] == Tileset.WALL) {
                return true; // blocked by wall
            }
        }

        return false; // no wall in between
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////




    /**
     * encode the world to a string by using seed, and the avatar coordinate.
     * @return the encode string.
     */
    public StoreWorldData SaveContents() {
        return new StoreWorldData(this.seed,
                currentAvatarPos, LightPos);
    }

}
