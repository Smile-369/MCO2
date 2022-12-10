package Entitiy;

/**
 * This abstract class was made for modularity's sake in case any Non-player characters are implemented
 *
 */
public abstract class Entity {
    /**
     * keeps track of the x value of the entity
     */
    public int x;
    /**
     * keeps track of the y value of the entity
     */
    public int y;
    /**
     * speed value of the entity, or the value in which x and y change per event;
     */
    public int speed;
}
