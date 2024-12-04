# COMP2042 Developing Maintainable Software

---

## <ins>1.0 My Github</ins>
https://github.com/jkgithub02/CW2024

---

## <ins>2.0 Compilation Instructions</ins>
1. Ensure you have Java installed (version X.X required).
2. Install any necessary dependencies:
   - Dependency 1
   - Dependency 2
3. Open the terminal and navigate to the project directory.
4. Compile the project with:
    ```sh
    javac -d bin src/**/*.java
    ```
5. Run the project with:
    ```sh
    java -cp bin MainClass
    ```

---

## <ins>3.0 Features</ins>
### <ins>3.1 Implemented and Working Properly</ins>
#### 1. Added left-right movement for the user plane.
#### 2. Added a main menu.
#### 3. Created a pause menu with its handler.
#### 4. Added a kill count label to track kills.
#### 5. Added background images, changed the hearts icon, and added sound effects and background music.
#### 6. Added one additional level.
#### 7. Added arcade mode and leaderboard.
#### 8. Added game over and win screens using FXML and controllers.
#### 9. Allowed users to restart levels or games from various screens.
#### 10. Added a leaderboard that sorts and shows the highest scores.
#### 11. Added sound effects for taking damage and firing projectiles.
#### 12. Added a health bar for the boss plane.
#### 13. Added a settings page to adjust music and sound effects volume.

### <ins>3.2 Implemented but Not Working Properly</ins>
#### 1. TODO

### <ins>3.3 Not Implemented</ins>
#### 1. Mini health bar for enemies.
#### 2. In Game Power-ups.
#### 2. Display user final score in gameover screen during the arcade level.

---

## <ins>4.0 Refactoring Process</ins>
### <ins>4.1 New Java Classes</ins>
#### 1. Refactored `levelparent` into multiple managers following single-responsibility principle:
    - collisionmanager
    - entitymanager
    - gameinitializer
    - inputmanager
    - navigationmanager
    - pausemanager
    - soundmanager

### <ins>4.2 Modified Java Classes</ins>
#### 1. Fixed NullPointerException from wrong shieldImage name.
#### 2. Adjusted projectile firing offset.
#### 3. Replaced observer and observable implementations with listeners.
#### 4. Readjusted and cropped image sizes to fix large hitbox issues.
#### 5. Organized files into relevant packages.
#### 6. Deleted redundant code.
#### 7. Fixed shield not showing issue in the boss level.
#### 8. Fixed kill count issue with collisions.
#### 9. Implemented factory design patterns for actors.
#### 10. Extracted game configurations.
#### 11. Fixed plane drifting issue after timeline stops.
#### 12. Applied singleton design pattern to `soundmanager`.

---

## <ins>5.0 Commit History</ins>
For a detailed commit history, please refer to the [commit log](https://github.com/jkgithub02/CW2024/commits).