package net.wintastic.wincremental;

import net.wintastic.lwjgl.Input;
import net.wintastic.lwjgl.math.GameMathHelper;
import net.wintastic.wincremental.tiles.Board;
import net.wintastic.wincremental.tiles.Position;
import net.wintastic.wincremental.tiles.Tile;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Camera {
    enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    //TODO: Possibly add a z-position to manage tile scale instead of directly modifying tileSize in GameManager when zooming
    private Vector2f position;
    private Vector2f velocity;
    private float maxSpeed;
    private float acceleration;
    private Vector2f prevMousePosition;

    public Camera(Vector2f position) {
        this.position = position;
        this.velocity = GameMathHelper.VECTOR2F_ZERO;
        this.maxSpeed = 5;
        this.acceleration = 1f;
    }

    public void update() {
        handleKeyboardInput();
        handleMouseInput();
        updatePosition();
    }

    private void handleKeyboardInput() {
        if (Input.isKeyDown(Keyboard.KEY_UP))
            move(Direction.UP);
        if (Input.isKeyDown(Keyboard.KEY_DOWN))
            move(Direction.DOWN);
        if (Input.isKeyDown(Keyboard.KEY_LEFT))
            move(Direction.LEFT);
        if (Input.isKeyDown(Keyboard.KEY_RIGHT))
            move(Direction.RIGHT);
    }

    private void handleMouseInput() {
        if (Input.isButtonDown(2) && GameManager.board.mouseInBoard()) {
            velocity = new Vector2f(0, 0);
            if (prevMousePosition != null) {
                Vector2f p = Input.mousePosition();
                GameMathHelper.addVector(position, new Vector2f(prevMousePosition.x - p.x, prevMousePosition.y - p.y));
            }
            prevMousePosition = Input.mousePosition();
        } else {
            prevMousePosition = null;
        }
        int scrollWheel = Input.scrollWheel();
        if (scrollWheel != 0) {
            Position p = Board.getMouseTilePosition();
            if (GameManager.tileSize + scrollWheel >= 4 && GameManager.tileSize + scrollWheel <= 128) {
                GameManager.tileSize += scrollWheel;
                Tile.scaleX = (float) GameManager.tileSize / AssetLibrary.grassTileSprite.texture.getImageWidth();
                Tile.scaleY = (float) GameManager.tileSize / AssetLibrary.grassTileSprite.texture.getImageHeight();
                GameManager.viewportWidth = (GameManager.resX - GameManager.menuBarWidth) / GameManager.tileSize;
                GameManager.viewportHeight = (GameManager.resY - GameManager.toolbarHeight) / GameManager.tileSize;
                GameManager.camera.position.x += scrollWheel * p.x;
                GameManager.camera.position.y += scrollWheel * p.y;
            }
        }
    }

    private void updatePosition() {
        GameMathHelper.addVector(position, velocity);
        GameMathHelper.lerp(velocity, new Vector2f(0, 0), acceleration / 2);
        GameMathHelper.clamp(velocity, -maxSpeed, maxSpeed, -maxSpeed, maxSpeed);
        GameMathHelper.clamp(position, 0, GameManager.mapWidth * GameManager.tileSize - GameManager.viewportWidth * GameManager.tileSize,
                0, GameManager.mapHeight * GameManager.tileSize - GameManager.viewportHeight * GameManager.tileSize);
    }

    public void move(Direction direction) {
        switch (direction) {
            case UP:
                velocity.y -= acceleration;
                break;
            case DOWN:
                velocity.y += acceleration;
                break;
            case LEFT:
                velocity.x -= acceleration;
                break;
            case RIGHT:
                velocity.x += acceleration;
                break;
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

}
