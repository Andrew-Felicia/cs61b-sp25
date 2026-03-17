package core;

import tileengine.TETile;
import tileengine.Tileset;

/**
 * this class helps to build rooms or hallways.
 */
public class Room {

    private Index bL;
    private Index bR;
    private Index tL;
    private Index tR;
    private int width;
    private int height;

    /**
     * Constructor.
     * @param bL represents bottom-left coordinate
     * @param bR represents bottom-right coordinate
     * @param tL represents top-left coordinate
     * @param tR represents top-right coordinate
     * @param w  represents the width of the room or hallway
     * @param h  represents the height of the room or hallway
     */
    public Room(Index bL, Index bR, Index tL, Index tR, int w, int h) {
        if(bL != null) {
            this.bL = bL;
            this.bR = new Index(this.bL.GetX() + w - 1, this.bL.GetY());
            this.tL = new Index(this.bL.GetX() , this.bL.GetY() + h - 1);
            this.tR = new Index(this.bL.GetX() + w - 1, this.bL.GetY() + h - 1);
            width = w;
            height = h;
        }else if(bR != null) {
            this.bR = bR;
            this.bL = new Index(this.bR.GetX() - w + 1, this.bR.GetY());
            this.tL = new Index(this.bR.GetX() - w + 1, this.bR.GetY() + h - 1);
            this.tR = new Index(this.bR.GetX(), this.bR.GetY() + h - 1);
            width = w;
            height = h;
        }else if(tL != null) {
            this.tL = tL;
            this.bR = new Index(this.tL.GetX() + w - 1, this.tL.GetY() - h + 1);
            this.bL = new Index(this.tL.GetX(), this.tL.GetY() - h + 1);
            this.tR = new Index(this.tL.GetX() + w - 1, this.tL.GetY());
            width = w;
            height = h;
        }else if(tR != null) {
            this.tR = tR;
            this.bL = new Index(this.tR.GetX() - w + 1, this.tR.GetY() - h + 1);
            this.bR = new Index(this.tR.GetX(), this.tR.GetY() - h + 1);
            this.tL = new Index(this.tR.GetX() - w + 1, this.tR.GetY());
            width = w;
            height = h;
        }else {
            this.bL = bL;
            this.bR = bR;
            this.tL = tL;
            this.tR = tR;
            width = w;
            height = h;
        }
    }

    /**
     * draw on the world based on bottom-left coordinate
     * @param world represent the world you wanna draw.
     */

    public void MakeRoom(TETile[][] world) {
        for(int i = this.bL.GetX(); i < this.bL.GetX() + this.width; i++) {
            for(int j = this.bL.GetY(); j < this.bL.GetY() + this.height; j++) {
                if(i == this.bL.GetX() || i == this.bL.GetX() + this.width - 1 || j == this.bL.GetY()
                        || j == this.bL.GetY() + this.height - 1) {
                    world[i][j] = Tileset.WALL;
                }else {
                    world[i][j] = Tileset.FLOOR;
                }
            }
        }
    }

    /**
     *
     * @return bottom-left coordinate of this room or hallway.
     */
    public Index GetbL() {
        return this.bL;
    }

    /**
     *
     * @return bottom-right coordinate of this room or hallway.
     */
    public Index GetbR() {
        return this.bR;
    }

    /**
     *
     * @return top-left coordinate of this room or hallway.
     */
    public Index GettL() {
        return this.tL;
    }

    /**
     *
     * @return top-right coordinate of this room or hallway.
     */
    public Index GettR() {
        return this.tR;
    }

    /**
     *
     * @return the width of this room or hallway.
     */
    public int GetW() {
        return this.width;
    }

    /**
     *
     * @return the height coordinate of this room or hallway.
     */
    public int GetH() {
        return this.height;
    }


}
