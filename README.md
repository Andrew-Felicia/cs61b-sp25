# BYOW Design Document

This ReadMe file is only for proj3.
BYOW -> build your own world.
I will keep maintain this project, because there's still a lot of interesting stuff to add.


---
The final effect, it's my first time to build a real project, i hope it will not be so bad.😘


<img width="1277" height="742" alt="Screenshot 2026-03-25 at 21 49 08" src="https://github.com/user-attachments/assets/3ce3af5d-f70d-44f1-b303-8dee8e4f7ef7" />



---
Files Structure:
```
proj3/
│
│--	src/
│	│
│	├─ Core/
│	│   ├─ Engine.java
│	│   ├─ Index.java
│	│   ├─ Interactive.java
│	│   ├─ Main.java
│	│   ├─ Room.java
│	│   ├─ StoreWorldData.java
│	│   └─ World.java
│	├─ tileengine/
│	│   ├─ TERenderer.java
│	│   ├─ TEtile.java
│	│   └─ Tileset.java 
│	└─ utils/
│	    └─ RandomUtils.java
│	
│--	Archive/
│   └─ save.txt
│   	
└─
```


## Classes and Data Structures

- Use 2D array to represent the world we are gonna generate, where # is wall, dot/quote is floor, and the empty square is the empty space(outside the world).
- Tileengine:use it to generate/render the world
- RandomUtils:use it to generate some real/pseudo random number.


### Index.java
The class using to represent the x-coordinate and y-coordinate of the tile.
Very simple class made to store indices for use in the TETiles array. As outlined in the project 3 spec, Index(0, 0) corresponds to the bottom-left corner of the world.

- x = the "x-coordinate," or the column of the tile's location in the array
- y = the "y-coordinate," or the row of the tile's location in the array


### Room.java
The class using to represent the rooms or hallways in the Tetile world.
Creates a room of random height and length. The method makeRoom() takes in the TETiles array and updates it by filling in the room in the proper indexes of the array.

- height = the height of the room
- length = the length of the room
- bottomLeft = the Index detailing the bottom-left corner of the room
- topLeft = the Index detailing the top-left corner of the room
- bottomRight the Index detailing the bottom-right corner of the room
- topRight = the Index detailing the top-right corner of the room


### World.java
The class using to generate the random Tetile world based on seed.
Contains the entire process for initializing a blank world, using the given seed to create a Random object, creating the first room to serve as the starting point, and then populating the world sufficiently with enough rooms and hallways. After each Room object is created, its makeRoom() method is called provided it doesn't violate the boundary conditions or overlap with other rooms.

- world = the TETile array containing all the tiles for the world
- *SEED = the long value to create the Random object with
- RANDOM = the Random object that will be created using SEED



### Engine.java
The class using to connect other classes and start the game(this game right now is not enjoyable, but I'm working on it.😘
This class cooperate with Interactive.java a lot, basicly it create the main menu, save or load the game, and starting the game.


### Interactive.java

This class handle with avatar stuff, HUD(heads up display), the light. You can change the class variables of light to alert the behavior of the lights,see more details in the class.😘



### StoreWorldData.java
This class is used to save the neccessary information to restore the world,  in other words, it's used to load the game.

### Main.java
The entrance of the program.



---

## Algorithms

The entire room generation process will ideally consist of something akin to graph traversal: we'll start by randomly generating one room, then make randomized rooms that connect to each side of the original. Every valid new room will be added to a LinkedList of rooms that still need to be "visited," so the entire process is like a breadth- first search, since we want to finish one Room before investigating all of its sides, and then all of its subrooms, and so on until the list of rooms is emptied.

---

