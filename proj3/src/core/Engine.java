package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import static java.awt.event.KeyEvent.*;

/**
 * build a Main Menu for the player.
 */
public class Engine {
    private static final int WIDTH = 31;
    private static final int HEIGHT = 31;
    private static final Path Saving = Path.of("Archive");//the folder name.

    Interactive interactive = null;//using seed to draw random world.
    Font MenuOpsfont = new Font("sans serif", Font.BOLD, 20);
    Font Menufont = new Font("sans serif", Font.BOLD, 30);
    Font Flower = new Font("sans serif", Font.BOLD, 20);
    TERenderer ter = new TERenderer();


    /**
     * build the main menu.
     */
    public void Main_Menu() {

        ter.initialize(WIDTH, HEIGHT);//decide the rendering canvas size.

        // Fill in the tile grid with FLOOR tiles.
        // If you don't fill in the grid, you'll get a NullPointerException!
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        //render the world to screen.
        ter.renderFrame(world);

        //the Main Menu.
        StdDraw.setPenColor(Color.white);
        StdDraw.setFont(Menufont);
        StdDraw.text(15,25,"Build your own world.");
        StdDraw.setFont(MenuOpsfont);
        StdDraw.text(15,20,"(N) New Game");
        StdDraw.text(15,15,"(L) Load Game");
        StdDraw.text(15,10,"(Q) Quit Game");
        StdDraw.setFont(Flower);
        this.DrawFlower();
        StdDraw.show();

        char c; // Variable for saving the most recent character typed by the user.

        while(true) {
            while(StdDraw.hasNextKeyTyped()) {
                // nextKeyTyped returns the next key to process.
                // Always check hasNextKeyTyped before calling nextKeyTyped.
                // If you call nextKeyTyped and there's no key to process, code will crash!
                c = StdDraw.nextKeyTyped();

                c = Character.toLowerCase(c);
                switch (c) {
                    case 'n':
                        //handle with seed input.
                        boolean message = false;//if user input illegal seed, message will decide to act different.
                        StringBuilder inputString = this.handleInputSeed(message);//store input seed.
                        long seed;

                        //generate interactive based on seed which user inputs.
                        if(inputString.isEmpty()) {
                            seed = 0;
                            interactive = new Interactive(seed); //when you create the object, the random world already been created.
                        } else {
                            if(!isValid(inputString)) {
                                message = true;
                            }
                            while(!isValid(inputString)) {
                                inputString = this.handleInputSeed(message);
                            }
                            seed = Long.parseLong(inputString.toString());
                            interactive = new Interactive(seed); //when you create the object, the random world already been created.
                        }

                        break;
                    case 'l':
                        interactive = this.LoadTheWorld();
                        break;
                    case 'q':
                        System.exit(0);
                        break;
                    default:
                        break;
                }
                //see more details below about runGame.
                this.runGame();
            }
        }
    }


    /**
     * this function do as follows:
     * 1.move the avatar in the world based on user input.
     * 2.HUD:show the name of the tile in the world based on where is the mouse cursor.
     * 3.save and load the game.
     */
    private void runGame() {
        while(true) {
            while(StdDraw.hasNextKeyTyped()) {
                char userChar = Character.toLowerCase(StdDraw.nextKeyTyped());
                if(userChar == 'w' || userChar == 's' || userChar == 'a' || userChar == 'd') {
                    interactive.MoveAvatar(userChar);
                } else if(StdDraw.isKeyPressed(VK_SHIFT) && StdDraw.isKeyPressed(VK_SEMICOLON)) {
                    TETile[][] question = new TETile[30][30];
                    for(int i = 0; i < 30; i++) {
                        for(int j = 0; j < 30; j++) {
                            question[i][j] = Tileset.NOTHING;
                        }
                    }
                    ter.initialize(30,30);
                    ter.renderFrame(question);

                    StdDraw.clear(Color.BLACK);
                    StdDraw.setPenColor(Color.white);
                    StdDraw.setFont(MenuOpsfont);
                    StdDraw.text(15, 20, "press q/Q to quit and save the game.");
                    StdDraw.text(15, 15, "press other key to continue");
                    StdDraw.show();
                    while(true) {
                        if(StdDraw.hasNextKeyTyped()) {
                            char userChar1 = Character.toLowerCase(StdDraw.nextKeyTyped());
                            if(userChar1 == 'q') {
                                this.SaveTheWorld(interactive);
                                System.exit(0);
                            } else {
                                //these two lines below is very important.
                                //it will change back to the random world.
                                ter.initialize(World.WIDTH,World.HEIGHT + 3);
                                ter.renderFrame(interactive.world);
                                break;
                            }
                        }
                    }
                } else if(userChar == 't') {
                    interactive.SwitchTheLight();
                }

            }

            interactive.HUD();
        }
    }




    /**
     * this function helps to decorate the Main Menu, drawing some flower.
     *
     */
    private void DrawFlower() {
        for (int i = 0; i < WIDTH; i++) {
            StdDraw.text(i + 0.5, 0.5, "❀");
            StdDraw.text(i + 0.5, HEIGHT - 0.5, "❀");
        }
        for (int i = 0; i < HEIGHT - 1; i++) {
            StdDraw.text(0.5, i + 1, "❀");
            StdDraw.text(WIDTH - 0.5, i + 1, "❀");
        }
    }

    /**
     * when you enter the main menu and press n/N to start new game, you will be asked to enter
     * a sequences of numbers, and this function helps to check if the user input contains only
     * numbers,if only numbers, then it's valid, otherwise will be invalid.
     * @param string : user input
     * @return true:if valid, false otherwise.
     */
    private boolean isValid(StringBuilder string) {
        if(string.isEmpty()) {
            return true;
        } else if(string.length() >= 20) {
            return false;
        } else {
            for(int i = 0; i < string.length(); i++) {
                if(!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * helper method of Main_Menu.
     * this function handle with user input seed after hit n/N to start new game,
     * user can input number/character/del/backspace, but only numbers are legal,
     * ends with s/S.
     * @param message if user input illegal character, message will decide to put different things on screen.
     * @return user input, what ever is, legal or illegal,all return.
     */
    private StringBuilder handleInputSeed(boolean message) {
        StringBuilder inputString = new StringBuilder();//the character you type on the screen will be store here.
        boolean finished = false;
        while(!finished) {
            if(StdDraw.hasNextKeyTyped()) {
                char s = Character.toLowerCase(StdDraw.nextKeyTyped());
                if(s == 's') {
                    finished = true;
                } else if((int) s == 8 || (int) s == 127) {  //if user input backspace or del then delete the last
                                                            //character of inputString.
                    if(!inputString.isEmpty()) {
                        inputString.deleteCharAt(inputString.length() - 1);
                    }
                } else {
                    inputString.append(s);
                }
            }
            StdDraw.clear(Color.black);
            StdDraw.setFont(Menufont);
            StdDraw.text(15, 20, "Enter seed followed by S");
            StdDraw.setFont(MenuOpsfont);
            if(message) {
                StdDraw.text(15, 15,"Invalid input, please re-enter.");
                StdDraw.text(15, 10, inputString.toString());
            } else {
                StdDraw.text(15, 15, inputString.toString());
            }
            StdDraw.setFont(Flower);
            this.DrawFlower();
            StdDraw.show();
        }
        return inputString;
    }



    /**
     * this function helps to save the game.
     * printStackTrace()	logs error, continues
     * throw new RuntimeException	crashes program
     * custom message	user-friendly
     *
     * store the info about how to retore the world in this format:
     * 456789,59,3,13:10
     */
    private void SaveTheWorld(Interactive iw) {
        StoreWorldData contents = iw.SaveContents();
        try {
            Files.createDirectories(Saving);
            Path saveFile = Saving.resolve("save.txt");
            Files.writeString(saveFile, contents.toString());
        } catch (IOException e) {
            System.out.println("Failed to save game!");
            throw new RuntimeException(e);
            //e.printStackTrace();
        }
    }

    /**
     * this function helps to load the game.
     * store the info about how to retore the world in this format:
     *    456789,59,3,13:10,...,...,
     */
    private Interactive LoadTheWorld() {
        String[] saved;
        Map<Integer, Integer> LightPos = new HashMap<>();
        try {
            Path saveFile = Saving.resolve("save.txt");
            saved = Files.readString(saveFile).split(",");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            throw new RuntimeException(e);
        }
        //retrieve info.
        long seed = Long.parseLong(saved[0]);
        int x = Integer.parseInt(saved[1]);
        int y = Integer.parseInt(saved[2]);
        for(int i = 3; i < saved.length; i++) {
            String[] parts = saved[i].split(":");
            int lx = Integer.parseInt(parts[0]);
            int ly = Integer.parseInt(parts[1]);
            LightPos.put(lx, ly);
        }
        return new Interactive(seed, new Index(x, y), LightPos);
    }


}
