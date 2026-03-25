package core;



import java.util.HashMap;
import java.util.Map;

/**
 * this class helps to store the necessary data we need to restore the world when loading the game.
 */
public class StoreWorldData {
    long seed;  //the seed used to build the randomWorld.
    Index AvatarPos; //store avatar x,y-coordinate.
    Map<Integer, Integer> LightPos; //store the locations of lights;

    /**
     * constructor.
     * @param seed .
     * @param AvatarPos .
     * @param LightPos .
     */
    public StoreWorldData(long seed, Index AvatarPos,
                          Map<Integer, Integer> LightPos) {
        this.seed = seed;
        this.AvatarPos = AvatarPos;
        this.LightPos = LightPos;

    }

    /**
     *
     * @return seed.
     */
    public long GetSeed() {
        return this.seed;
    }

    /**
     *
     * @return avatar position.
     */
    public Index GetAvatarPos() {
        return this.AvatarPos;
    }

    /**
     *
     * @return lights positions.
     */
    public Map<Integer, Integer> GetLightPos() {
        return this.LightPos;
    }

    /**
     * this function helps to convert everything into string.
     * @return the string after converting.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(seed).append(",");
        sb.append(AvatarPos.GetX()).append(",");
        sb.append(AvatarPos.GetY());

        if(LightPos != null) {
            for (Map.Entry<Integer, Integer> entry : LightPos.entrySet()) {
                sb.append(",");
                sb.append(entry.getKey()).append(":").append(entry.getValue());
            }
        }


        return sb.toString();
    }



}
