import java.io.*; // connects classes used for the data input and output
import java.util.*; // contains a lot of instruemnts such as Scanner, random number generator. 

public class JavaCraftv {
  private static final int AIR = 0;
  private static final int WOOD = 1;
  private static final int LEAVES = 2;
  private static final int STONE = 3;
  private static final int IRON_ORE = 4;
  private static final int GOLD_ORE = 5;
  private static final int DIAMOND_ORE = 6;
  private static final int APPLE = 7;
  private static final int GRASS = 8;

   /*Material identificators( each material has a unique code e.g. AIR =0) these material 
   identificators cannot be changed, they are constants.*/
  private static int NEW_WORLD_WIDTH = 30;
  private static int NEW_WORLD_HEIGHT = 20;
  private static int EMPTY_BLOCK = 0;
  private static final int CRAFT_WOODEN_PLANKS = 100;
  private static final int CRAFT_STICK = 101;
  private static final int CRAFT_IRON_INGOT = 102;
  private static final int CRAFTED_WOODEN_PLANKS = 200;
  private static final int CRAFTED_STICK = 201;
  private static final int CRAFTED_IRON_INGOT = 202;
  private static final int CRAFTED_GOLDEN_APPLE = 203;
  private static final int CRAFTED_DIAMOND = 204;
  private static final int CRAFTED_DIAMOND_PICKAXE = 205;
  private static final int CRAFTED_GOLD_INGOT = 206;
  private static final String ANSI_BROWN = "\u001B[38;5;137m";
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_GRAY = "\u001B[90m";
  private static final String ANSI_WHITE = "\u001B[97m";
  private static final String ANSI_DARK_GREEN = "\u001B[38;5;22m";
  private static final String ANSI_GOLD = "\u001B[38;5;220m";
  private static final String BLOCK_NUMBERS_INFO = "Block Numbers:\n" +
      "0 - Empty block\n" +
      "1 - Wood block\n" +
      "2 - Leaves block\n" +
      "3 - Stone block\n" +
      "4 - Iron ore block\n" +
      "5 - Gold ore block\n" +
      "6 - diamond ore block\n" +
      "7 - apple\n" +
      "8 - Grass block\n" +
      "9 - Wooden Planks (Crafted Item)\n" +
      "10 - Stick (Crafted Item)\n" +
      "11 - Iron Ingot (Crafted Item)\n" +
      "12 - Golden Ignot (Crafted Item)\n"+
      "13 - Diamond (Crafted Item)\n" +
      "14- Diamond  Pickaxe (Crafted Item)\n" +
      "15 - Golden Apple (Crafted Item)\n" ;
      


  private static int[][] world; //two dimension array, used to store all the information about the world
  private static int worldWidth;
  private static int worldHeight;
  private static int playerX; // player coordinates
  private static int playerY;
  private static List<Integer> inventory = new ArrayList<>(); //dynamic inventory array, which, in comparison with the basic array may expand during the game
  private static List<Integer> craftedItems = new ArrayList<>(); // separate list for crafted items
  private static boolean unlockMode = false;
  private static boolean secretDoorUnlocked = false;
  private static boolean inSecretArea = false;
  private static final int INVENTORY_SIZE = 200;

  public static void main(String[] args) {
    initGame(30, 20);
    generateWorld();
    System.out.println(ANSI_GREEN + "Welcome to Simple Minecraft!" + ANSI_RESET);
    System.out.println("Instructions:");
    System.out.println(" - Use 'W', 'A', 'S', 'D', or arrow keys to move the player.");
    System.out.println(" - Press 'M' to mine the block at your position and add it to your inventory.");
    System.out.println(" - Press 'P' to place a block from your inventory at your position.");
    System.out.println(" - Press 'C' to view crafting recipes and 'I' to interact with elements in the world.");
    System.out.println(" - Press 'Save' to save the game state and 'Load' to load a saved game state.");
    System.out.println(" - Press 'Exit' to quit the game.");
    System.out.println(" - Type 'Chat' to enable the chat.");
    System.out.println(" - Type 'Help' to display these instructions again.");

    System.out.println();
    Scanner scanner = new Scanner(System.in);
    System.out.print("Start the game? (Y/N): ");
    String startGameChoice = scanner.next().toUpperCase();
    if (startGameChoice.equals("Y")) {
      startGame();
    } else {
      System.out.println("Game not started. Goodbye!");
      /* When running the code, this function asks a user, whether he wants to start a game and gives 
      two input options: to start a game or not. If the users input is  Y, then the game starts. If the users input is N, then the code outputs
      "Game not started. Goodbye!" ands code ends.  */
    }
  }

  public static void initGame(int worldWidth, int worldHeight) {
    JavaCraftv.worldWidth = worldWidth; // initializes the world width
    JavaCraftv.worldHeight = worldHeight; //initializes the world height
    JavaCraftv.world = new int[worldWidth][worldHeight]; // generates an empty world 
    playerX = worldWidth / 2;
    playerY = worldHeight / 2;
    inventory = new ArrayList<>(); // creates a new, empty inventory, which is expandable
  }// initializes game, makes player spawn exactly at the center of the map 

  public static void generateWorld() {
    Random rand = new Random();
    for (int y = 0; y < worldHeight; y++) {
      for (int x = 0; x < worldWidth; x++) {
        if(y>worldHeight/2){
        int randValue = rand.nextInt(100);
        if (randValue < 7) {
          world[x][y] = IRON_ORE;
          } else if (randValue < 14) {
          world[x][y] = GOLD_ORE;  
        } else if (randValue < 23) {
          world[x][y] = DIAMOND_ORE;
        } else{
          world[x][y] = STONE;
        }
       }
       if(y<worldHeight/2){
        int randValue = rand.nextInt(100);

         if (randValue < 20) {
          world[x][y] = WOOD;
        } else if (randValue < 35) {
          world[x][y] = LEAVES;  
        } 
        else if (randValue < 45) {
          world[x][y] = APPLE;
        } else {
          world[x][y] = AIR;
        }
      }
      if(y==worldHeight/2){
        world[x][y] = GRASS;
        
    }
  }
}
  }/* This function basically generates the world. The nested for loop here is responsible for filling in each block 
  on the map with a random material(wood, leaves, etc). Firstly the outside loop starts, which sets the first coordinate y. 
  Secondly, the inside loop starts working , which goes through every coordinate until the end is reached 24 column.
  For each coordinate the random generator generates a value, which is responsible for a material that is going to be in this block. 
  For example, y=0, x = 24 and rng generated a number 48. Then it is Stone that is going to be on that coordinate. */

  public static void displayWorld() {
    System.out.println(ANSI_CYAN + "World Map:" + ANSI_RESET);
    System.out.println("╔══" + "═".repeat(worldWidth * 2 - 2) + "╗");
    for (int y = 0; y < worldHeight; y++) {
      System.out.print("║");
      for (int x = 0; x < worldWidth; x++) {
        if (x == playerX && y == playerY && !inSecretArea) {
          System.out.print(ANSI_PURPLE + "P " + ANSI_RESET);
        } else if (x == playerX && y == playerY && inSecretArea) {
          System.out.print(ANSI_PURPLE + "P " + ANSI_RESET);
        } else {
          System.out.print(getBlockSymbol(world[x][y]));
        }
      }
      System.out.println("║");
    }
    System.out.println("╚══" + "═".repeat(worldWidth * 2 - 2) + "╝");
  } /*This method firstly prints out the string "World Map:" and it has a cyan color. Secondly, this method creates the visual part
  of the map. It shows the boundaries. Last but not least, the loop in the method is responsible for visualising the player's icon
  with a letter P. If the player is not in a particular block, then the materials are shown.  */

  private static String getBlockSymbol(int blockType) {
    String blockColor;
    switch (blockType) {
      case AIR:
        return ANSI_RESET + "- ";
      case WOOD:
        blockColor = ANSI_BROWN;
        break;
      case LEAVES:
        blockColor = ANSI_GREEN;
        break;
      case STONE:
        blockColor = ANSI_GRAY;
        break;
      case IRON_ORE:
        blockColor = ANSI_WHITE;
        break;
        case GOLD_ORE:
        blockColor = ANSI_YELLOW;
        break;
      case DIAMOND_ORE:
        blockColor = ANSI_CYAN;
        break;
      case APPLE:
        blockColor = ANSI_RED;
        break;
      case GRASS:
        blockColor = ANSI_DARK_GREEN;
        break;
      default:
        blockColor = ANSI_RESET;
        break;
    }
    return blockColor + getBlockChar(blockType) + " ";
  } /*This method works as a factory, where the materials are being assembled, block color paints the material
    and getBlockChar(blocktype) assigns a unique symbol to a particular block. */
  private static char getBlockChar(int blockType) {
    switch (blockType) {
      case WOOD:
        return '\u2588';
      case LEAVES:
        return '\u2593';
      case STONE:
        return '\u2588';
      case GRASS:
        return '\u2588';
      case IRON_ORE:
        return '\u00B0';
      case GOLD_ORE:
        return '\u00B0';
      case DIAMOND_ORE:
        return '\u00B0';
      case APPLE:
        return '@';
      default:
        return '-';
    }
  }

  public static void startGame() {
    Scanner scanner = new Scanner(System.in);
    boolean unlockMode = false;
    boolean craftingCommandEntered = false;
    boolean miningCommandEntered = false;
    boolean movementCommandEntered = false;
    boolean openCommandEntered = false;
    // booleans here are needed to remember, what was the previous step of a player
    while (true) { // That is the main game loop, which means that each line of code here will work until the exit button is pressed.
      clearScreen();
      displayLegend();
      displayWorld();
      displayInventory();
      System.out.println(ANSI_CYAN
          + "Enter your action: 'WASD': Move, 'M': Mine, 'P': Place, 'C': Craft, 'I': Interact, 'Save': Save, 'Load': Load, 'Exit': Quit, 'Unlock': Unlock Secret Door, 'Chat': To enter the Chat"
          + ANSI_RESET);
      String input = scanner.next().toLowerCase();
      if (input.equalsIgnoreCase("w") || input.equalsIgnoreCase("up") ||
          input.equalsIgnoreCase("s") || input.equalsIgnoreCase("down") ||
          input.equalsIgnoreCase("a") || input.equalsIgnoreCase("left") ||
          input.equalsIgnoreCase("d") || input.equalsIgnoreCase("right")) {
        if (unlockMode) {
          movementCommandEntered = true;
        }
        movePlayer(input);
      } else if (input.equalsIgnoreCase("m")) {
        if (unlockMode) {
          miningCommandEntered = true;
        }
        mineBlock();
      } else if (input.equalsIgnoreCase("p")) {
        displayInventory();
        System.out.print("Enter the block type to place: ");
        int blockType = scanner.nextInt();
        placeBlock(blockType);
      } else if (input.equalsIgnoreCase("c")) {
        displayCraftingRecipes();
        System.out.print("Enter the recipe number to craft: ");
        int recipe = scanner.nextInt();
        craftItem(recipe);
      } else if (input.equalsIgnoreCase("i")) {
        interactWithWorld();
      } else if (input.equalsIgnoreCase("save")) {
        System.out.print("Enter the file name to save the game state: ");
        String fileName = scanner.next();
        saveGame(fileName);
      } else if (input.equalsIgnoreCase("load")) {
        System.out.print("Enter the file name to load the game state: ");
        String fileName = scanner.next();
        loadGame(fileName);
      } else if (input.equalsIgnoreCase("exit")) {
        System.out.println("Exiting the game. Goodbye!");
        break;
      } else if (input.equalsIgnoreCase("look")) {
        lookAround();
      } else if (input.equalsIgnoreCase("unlock")) {
        unlockMode = true;
      } else if (input.equalsIgnoreCase("open")) {
        if (unlockMode && craftingCommandEntered && miningCommandEntered && movementCommandEntered) {
          secretDoorUnlocked = true;
          resetWorld();
          System.out.println("Secret door unlocked!");
          waitForEnter();
        } else {
          System.out.println("Invalid passkey. Try again!");
          waitForEnter();
          unlockMode = false;
          craftingCommandEntered = false;
          miningCommandEntered = false;
          movementCommandEntered = false;
          openCommandEntered = false;
        }
      }else if (input.equalsIgnoreCase("chat")) {
            scanner.nextLine();
            openChat(scanner);
        }
       else {
        System.out.println(ANSI_YELLOW + "Invalid input. Please try again." + ANSI_RESET);
      }
      if (unlockMode) {
        if (input.equalsIgnoreCase("c")) {
          craftingCommandEntered = true;
        } else if (input.equalsIgnoreCase("m")) {
          miningCommandEntered = true;
        } else if (input.equalsIgnoreCase("open")) {
          openCommandEntered = true;
        }
      }
      if (secretDoorUnlocked) {
        clearScreen();
        System.out.println("You have entered the secret area!");
        inSecretArea = true;
        resetWorld();
        secretDoorUnlocked = false;
        fillInventory();
        waitForEnter();
      }
    }
  } 
   /* This block of code is responsible for reacting to users input. When a command is typed as an input and entered,
   the game reacts. For example w, a, s, d or up, down ,left, right are responsible for moving, so when one of the characters are entered
   a function is then triggered. Moreover, in this code block, there is a hidden easter egg, which is called secretdoor. This secretdoor
   may be opened if 3 commands are entered and the unlockMode is true.  */

  private static void fillInventory() {
    inventory.clear();
    for (int blockType = 1; blockType <= 8; blockType++) {
      for (int i = 0; i < INVENTORY_SIZE; i++) {
        inventory.add(blockType);
        /* After entering the secret room, this method firstly clears players inventory, then it maxes out the amount of first block,
        then the second one etc etc. */
      }
    }
  }

  private static void resetWorld() {
    generateEmptyWorld();
    playerX = worldWidth / 2;
    playerY = worldHeight / 2 + 1;
  } 

  private static void generateEmptyWorld() {
    worldWidth = NEW_WORLD_WIDTH;
    worldHeight = NEW_WORLD_HEIGHT;
    world = new int[NEW_WORLD_WIDTH][NEW_WORLD_HEIGHT];
    int redBlock = 1;
    int whiteBlock = 4;
    int blueBlock = 3;
    int yellowBlock = 5;
    int brownBlock = 6;
    int purpleBlock = 7;
    int cyanBlock = 8;
  
    int stripeHeight = NEW_WORLD_HEIGHT / 8; // Divide the height into three equal parts

    // Fill the top stripe with red blocks
    for (int y = 0; y < stripeHeight; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = redBlock;
      }
    }

    // Fill the middle stripe with white blocks
    for (int y = stripeHeight; y < stripeHeight * 2; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = whiteBlock;
      }
    }

    // Fill the bottom stripe with blue blocks
    for (int y = stripeHeight * 2; y < stripeHeight * 3; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = blueBlock;
      }
    }
    for (int y = stripeHeight * 3; y < stripeHeight * 4; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = yellowBlock;
      }
    }

    // Fill the middle stripe with white blocks
    for (int y = stripeHeight * 4; y < stripeHeight * 5; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = brownBlock;
      }
    }

    // Fill the bottom stripe with blue blocks
    for (int y = stripeHeight * 5; y < stripeHeight * 6; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = purpleBlock;
      }
    }
    for (int y = stripeHeight * 6; y < stripeHeight * 7; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = redBlock;
      }
    }

    // Fill the middle stripe with white blocks
    for (int y = stripeHeight * 7; y < NEW_WORLD_HEIGHT; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = cyanBlock;
      }
    }
  }

  private static void clearScreen() {
    try {
      if (System.getProperty("os.name").contains("Windows")) {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
      } else {
        System.out.print("\033[H\033[2J");
        System.out.flush();
      }
    } catch (IOException | InterruptedException ex) {
      ex.printStackTrace();
    }
  }
  /* This function is responsible for clearing previous output in the terminal, so that the visual part of the game always stays at
  one place and doesn't scroll down. If the OS is windows, then program addresses the system and asks to execute a cls command.
   If it's not windows, program prints out a special code based command that immediately clears the whole text.
  As a backup if an OS doesn't let user to clean the terminal, it just moves on without crashing the game   */

  private static void lookAround() {
    System.out.println("You look around and see:");
    for (int y = Math.max(0, playerY - 1); y <= Math.min(playerY + 1, worldHeight - 1); y++) {
      for (int x = Math.max(0, playerX - 1); x <= Math.min(playerX + 1, worldWidth - 1); x++) {
        if (x == playerX && y == playerY) {
          System.out.print(ANSI_GREEN + "P " + ANSI_RESET);
        } else {
          System.out.print(getBlockSymbol(world[x][y]));
        }
      }
      System.out.println();
    }
    System.out.println();
    waitForEnter();
  } /* This function shows what is surrounding a player. The outside loop starts out with one block above the player and ends up one block
  below the player. Inside loop starts one block to the left of the player and ends up one block to the right of the player
  the math function here is significant. It prevents code from crashing if a player is behind the boundaries of the map */

  public static void movePlayer(String direction) {
    switch (direction.toUpperCase()) {
      case "W":
      case "UP":
        if (playerY > 0) {
          playerY--;
        }
        break;
      case "S":
      case "DOWN":
        if (playerY < worldHeight - 1) {
          playerY++;
        }
        break;
      case "A":
      case "LEFT":
        if (playerX > 0) {
          playerX--;
        }
        break;
      case "D":
      case "RIGHT":
        if (playerX < worldWidth - 1) {
          playerX++;
        }
        break;
      default:
        break;
    }
  }
  /*
   This move method is responsible for player's movement. Basically, if the player's location
   does not exceed the maps borders, it allows the player to move by 1 block. 
   */

  public static void mineBlock() {
    int blockType = world[playerX][playerY];
    if (blockType != AIR) {
      inventory.add(blockType);
      world[playerX][playerY] = AIR;
      System.out.println("Mined " + getBlockName(blockType) + ".");
    } else {
      System.out.println("No block to mine here.");
    }
    waitForEnter();
  } 
  /*
  This method is responsible for putting mined blocks in the inventory and 
  removing them from the world. If the mined block is not an air block, 
  then the program outputs the mined block type as well as puts it in the inventory.
  If it's an air block, then it outputs that there is no block to mine there.
  */

  public static void placeBlock(int blockType) {
    if (blockType >= 0 && blockType <= 15) {
      if (blockType <= 8) {
        if (inventory.contains(blockType)) {
          inventory.remove(Integer.valueOf(blockType));
          world[playerX][playerY] = blockType;
          System.out.println("Placed " + getBlockName(blockType) + " at your position.");
        } else {
          System.out.println("You don't have " + getBlockName(blockType) + " in your inventory.");
        }
      } else {
        int craftedItem = getCraftedItemFromBlockType(blockType);
        if (craftedItems.contains(craftedItem)) {
          craftedItems.remove(Integer.valueOf(craftedItem));
          world[playerX][playerY] = blockType;
          System.out.println("Placed " + getCraftedItemName(craftedItem) + " at your position.");
        } else {
          System.out.println("You don't have " + getCraftedItemName(craftedItem) + " in your crafted items.");
        }
      }
    } else {
      System.out.println("Invalid block number. Please enter a valid block number.");
      System.out.println(BLOCK_NUMBERS_INFO);
    }
    waitForEnter();
  }
/*
This method is responsible for putting a block on the position where the player stands. 
If a valid block type is typed in, it first checks if the player has it in their inventory. 
If they do, the block is removed from the inventory and placed on the coordinates where the player stands. 
Same for the crafted materials. The only difference is that crafted materials' unique numbers 
start from 5 and they are checked in a different list. If a user's input is an integer that 
is less than 0 or more than 7, the program outputs that the user entered an invalid block number.
*/
  private static int getBlockTypeFromCraftedItem(int craftedItem) {
    switch (craftedItem) {
      case 9:
        return CRAFTED_WOODEN_PLANKS;
      case 10:
        return CRAFTED_STICK;
      case 11:
        return CRAFTED_IRON_INGOT;
      case 12:
        return CRAFTED_GOLD_INGOT;
      case 13:
        return CRAFTED_DIAMOND;
      case 14:
        return CRAFTED_DIAMOND_PICKAXE;
      case 15:
        return CRAFTED_GOLDEN_APPLE;
      default:
        return -1;
    }
  }
  /*
  This method reassigns a unique crafted item id to one specific block type with a specific 4<ID<=7. As crafted items have their own unique IDs
  ,but are placed on the map as a specific block type, this method assigns a value to a crafted block .
  */

  private static int getCraftedItemFromBlockType(int blockType) {
    switch (blockType) {
      case 9:
        return CRAFTED_WOODEN_PLANKS;
      case 10:
        return CRAFTED_STICK;
      case 11:
        return CRAFTED_IRON_INGOT;
      case 12:
        return CRAFTED_GOLD_INGOT;
      case 13:
        return CRAFTED_DIAMOND;
      case 14:
        return CRAFTED_DIAMOND_PICKAXE;
      case 15:
        return CRAFTED_GOLDEN_APPLE;
      default:
        return -1;
    }
  }
/*
This method checks, what blocktype is a users input 5 or 6 or 7. For example if 5 is a players input, then method checks, whether 
a player has this particular type of crafted block in their inventory.
*/
  public static void displayCraftingRecipes() {
    System.out.println("Crafting Recipes:");
    System.out.println("1. Craft 4 Wooden Planks: 1 Wood");
    System.out.println("2. Craft 4 Sticks: 2 Wooden Planks");
    System.out.println("3. Craft Iron Ingot: 1 Iron Ore");
    System.out.println("4. Craft Gold Ingot: 1 Gold Ore");
    System.out.println("5. Craft diamond: 1 Diamond Ore");
    System.out.println("6. Craft Diamond Pickaxe: 3 diamonds and 2 sticks");
    System.out.println("7. Craft olden apple: 1 apple and 4 gold ingot");
  }
  // This method is responsible for displaying crafting recipes to a player. It basically prints out an amount of materials 
  // required to craft a particular item.

  public static void craftItem(int recipe) {
    switch (recipe) {
      case 1:
        craftWoodenPlanks();
        break;
      case 2:
        craftStick();
        break;
      case 3:
        craftIronIngot();
        break;
      case 4:
        craftGoldIngot();
        break;
      case 5:
        craftDiamond();
        break;
      case 6:
        craftDiamondPickaxe();
        break;
      case 7:
        craftGoldenApple();
        break;
      default:
        System.out.println("Invalid recipe number.");
    }
    waitForEnter();
  }
  /*
  This method assigns an input value that triggers another methods responsible for crafting items(Firstly checking, whether a player has 
  has enough material required for crafting, then it removes materials from inventory(regarding which item player wants to craft)
  and then adds a crafted item into players inventory.)
  */

  public static void craftWoodenPlanks() {
    if (inventoryContains(WOOD, 1)) {
      removeItemsFromInventory(WOOD, 1);
      addCraftedItem(CRAFTED_WOODEN_PLANKS, 4);
      System.out.println("Crafted 4 Wooden Planks.");
    } else {
      System.out.println("Insufficient resources to craft Wooden Planks.");
    }
  }

  public static void craftStick() { 
    if (craftedItemsContains(CRAFTED_WOODEN_PLANKS, 2)) {
      removeItemsFromCraftedItems(CRAFTED_WOODEN_PLANKS, 2);
      addCraftedItem(CRAFTED_STICK, 4);
      System.out.println("Crafted Stick.");
    } else {
      System.out.println("Insufficient resources to craft Stick.");
    }
  }

  public static void craftIronIngot() {
    if (inventoryContains(IRON_ORE, 1)) {
      removeItemsFromInventory(IRON_ORE, 1);
      addCraftedItem(CRAFTED_IRON_INGOT, 1);
      System.out.println("Crafted Iron Ingot.");
    } else {
      System.out.println("Insufficient resources to craft Iron Ingot.");
    }
  }
  public static void craftGoldIngot() {
    if (inventoryContains(GOLD_ORE, 1)) {
      removeItemsFromInventory(GOLD_ORE, 1);
      addCraftedItem(CRAFTED_GOLD_INGOT, 1);
      System.out.println("Crafted Gold Ingot.");
    } else {
      System.out.println("Insufficient resources to craft Gold Ingot.");
    }
  }
  public static void craftDiamond() {
    if (inventoryContains(DIAMOND_ORE, 1)) {
      removeItemsFromInventory(DIAMOND_ORE, 1);
      addCraftedItem(CRAFTED_DIAMOND, 1);
      System.out.println("Crafted Diamond.");
    } else {
      System.out.println("Insufficient resources to craft Diamond.");
    }
  }
  public static void craftDiamondPickaxe() {
    if (craftedItemsContains(CRAFTED_DIAMOND, 3) && craftedItemsContains(CRAFTED_STICK, 2)) {
      removeItemsFromCraftedItems(CRAFTED_DIAMOND, 3);
      removeItemsFromCraftedItems(CRAFTED_STICK, 2);
      addCraftedItem(CRAFTED_DIAMOND_PICKAXE, 1);
      System.out.println("Crafted Diamond Pickaxe.");
    } else {
      System.out.println("Insufficient resources to craft Diamond Pickaxe.");
    }
  }
  public static void craftGoldenApple() {
    if (craftedItemsContains(CRAFTED_GOLD_INGOT, 4) && inventoryContains(APPLE, 1)) {
      removeItemsFromCraftedItems(CRAFTED_GOLD_INGOT, 4);
      removeItemsFromInventory(APPLE, 1);
      addCraftedItem(CRAFTED_GOLDEN_APPLE, 1);
      System.out.println("Crafted Golden Apple.");
    } else {
      System.out.println("Insufficient resources to craft Golden Apple.");
    }
  }

  public static boolean inventoryContains(int item) {
    return inventory.contains(item);
  } 

  public static boolean inventoryContains(int item, int count) {
    int itemCount = 0;
    for (int i : inventory) {
      if (i == item) {
        itemCount++;
        if (itemCount == count) {
          return true;
        }
      }
    }
    return false;
  }
/*
This method checks if a player has a certain amount of materials in the inventory. The for each loop here is used to go through
every material in the inventory list. If it finds a certain material it increase a value of itemCount variable by one. If itemCount
matches the quantity (count )required to craft the item, the loop returns true. Otherwise it returns false.
*/
  public static void removeItemsFromInventory(int item, int count) {
    int removedCount = 0;
    Iterator<Integer> iterator = inventory.iterator();
    while (iterator.hasNext()) {
      int i = iterator.next();
      if (i == item) {
        iterator.remove();
        removedCount++;
        if (removedCount == count) {
          break;
        }
      }
    }
  }
/* 
This method removes a specific amount of materials from the player's inventory . It checks each item, and if it matches the target item
, it removes it and increments the removedcount. Once required amount of materials has been removed, the loop breaks. 
*/
  public static void addCraftedItem(int craftedItem, int count) {
    if (craftedItems == null) {
      craftedItems = new ArrayList<>();
    }
    for (int i = 0; i < count; i++) {
        craftedItems.add(craftedItem);
    }
  }
  // This metho adds a new crafted item into the craftedItems inventory list.
  public static boolean craftedItemsContains(int item, int count) {
    int itemCount = 0;

    for (int i : craftedItems) {
        if (i == item) {
            itemCount++;

            if (itemCount == count) {
                return true;
            }
        }
    }
    return false;
}
  public static void removeItemsFromCraftedItems(int item, int count) {
    int removedCount = 0;
    Iterator<Integer> iterator = craftedItems.iterator();

    while (iterator.hasNext()) {
        int i = iterator.next();

        if (i == item) {
            iterator.remove();
            removedCount++;

            if (removedCount == count) {
                break;
            }
        }
    }
}
  public static void interactWithWorld() {
    int blockType = world[playerX][playerY];
    switch (blockType) {
      case WOOD:
        System.out.println("You gather wood from the tree.");
        inventory.add(WOOD);
        break;
      case LEAVES:
        System.out.println("You gather leaves from the tree.");
        inventory.add(LEAVES);
        break;
      case STONE:
        System.out.println("You gather stones from the cave.");
        inventory.add(STONE);
        break;
      case IRON_ORE:
        System.out.println("You gather iron ore from the cave.");
        inventory.add(IRON_ORE);
        break;
      case GOLD_ORE:
        System.out.println("You gather Gold Ore from the cave.");
        inventory.add(GOLD_ORE);
        break;
      case DIAMOND_ORE:
        System.out.println("You gather diamond Ore from the cave.");
        inventory.add(DIAMOND_ORE);
        break;
      case APPLE:
        System.out.println("You gather apple from the tree.");
        inventory.add(APPLE);
        break;
      case GRASS: 
        System.out.println("You gather grass from the ground.");
        inventory.add(GRASS);
        break;
      case AIR:
        System.out.println("Nothing to interact with here.");
        break;
      default:
        System.out.println("Unrecognized block. Cannot interact.");
    }
    waitForEnter();
  }
/*
This method creates an interaction function. It checks the block located at the player's current coordinates,
and using a switch statement it identifies the block type and adds the corresponding material into inventory. 
In comparison with mining, this function does not remove a block from the map , so a player can farm infinitely.
*/
  public static void saveGame(String fileName) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
      // Serialize game state data and write to the file
      outputStream.writeInt(NEW_WORLD_WIDTH);
      outputStream.writeInt(NEW_WORLD_HEIGHT);
      outputStream.writeObject(world);
      outputStream.writeInt(playerX);
      outputStream.writeInt(playerY);
      outputStream.writeObject(inventory);
      outputStream.writeObject(craftedItems);
      outputStream.writeBoolean(unlockMode);

      System.out.println("Game state saved to file: " + fileName);
    } catch (IOException e) {
      System.out.println("Error while saving the game state: " + e.getMessage());
    }
    waitForEnter();
  }


    public static void loadGame(String fileName) {
    // Implementation for loading the game state from a file goes here
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
      // Deserialize game state data from the file and load it into the program
      NEW_WORLD_WIDTH = inputStream.readInt();
      NEW_WORLD_HEIGHT = inputStream.readInt();
      world = (int[][]) inputStream.readObject();
      playerX = inputStream.readInt();
      playerY = inputStream.readInt();
      inventory = (List<Integer>) inputStream.readObject();
      craftedItems = (List<Integer>) inputStream.readObject();
      unlockMode = inputStream.readBoolean();

      System.out.println("Game state loaded from file: " + fileName);
    } catch (IOException | ClassNotFoundException e) {
      System.out.println("Error while loading the game state: " + e.getMessage());
    }
    waitForEnter();
  }

  private static String getBlockName(int blockType) {
    switch (blockType) {
      case AIR:
        return "Empty Block";
      case WOOD:
        return "Wood";
      case LEAVES:
        return "Leaves";
      case STONE:
        return "Stone";
      case IRON_ORE:
        return "Iron Ore";
      case GOLD_ORE:
        return "Gold Ore";
      case DIAMOND_ORE:
        return "Diamond Ore";
      case APPLE:
        return "Apple";
      case GRASS:
        return "Grass block";
      default:
        return "Unknown";
    }
  }

  public static void displayLegend() {
    System.out.println(ANSI_GOLD + "Legend:");
    System.out.println(ANSI_WHITE + "-- - Empty block");
    System.out.println(ANSI_BROWN + "\u2588\u2588 - Wood block");
    System.out.println(ANSI_GREEN + "\u2593\u2593 - Leaves block");
    System.out.println(ANSI_GRAY + "\u2588\u2588 - Stone block");
    System.out.println(ANSI_WHITE + "\u00B0\u00B0- Iron ore block");
    System.out.println(ANSI_YELLOW + "\u00B0\u00B0 - Gold ore block");
    System.out.println(ANSI_CYAN + "\u00B0\u00B0 - Diamond Ore block");
    System.out.println(ANSI_DARK_GREEN + "\u2588\u2588 - Grass Block");
    System.out.println(ANSI_RED + "@ - Apple");
    System.out.println(ANSI_PURPLE + "P - Player" + ANSI_RESET);
  }

  public static void displayInventory() {
    System.out.println("Inventory:");
    if (inventory.isEmpty()) {
      System.out.println(ANSI_YELLOW + "Empty" + ANSI_RESET);
    } else {
      int[] blockCounts = new int[9];
      for (int i = 0; i < inventory.size(); i++) {
        int block = inventory.get(i);
        blockCounts[block]++;
      }
      for (int blockType = 1; blockType < blockCounts.length; blockType++) {
        int occurrences = blockCounts[blockType];
        if (occurrences > 0) {
          System.out.println(getBlockName(blockType) + " - " + occurrences);
        }
      }
    }
    
    System.out.println("Crafted Items:");
    if (craftedItems == null || craftedItems.isEmpty()) {
      System.out.println(ANSI_YELLOW + "None" + ANSI_RESET);
    } else {
      for (int item : craftedItems) {
        System.out.print(getCraftedItemColor(item) + getCraftedItemName(item) + ", " + ANSI_RESET);
      }
      System.out.println();
    }
    System.out.println();
  }
  /*
    This method displays the main inventory in a readable format, not raw, long list of every collected item and crafted item.
    It iterates through inventory to count all the items and then iterates through the counted materials in a temporary array
    to print a human readable inventory list.
    */

  private static String getBlockColor(int blockType) {
    switch (blockType) {
      case AIR:
        return "";
      case WOOD:
        return ANSI_BROWN;
      case LEAVES:
        return ANSI_GREEN;
      case STONE:
        return ANSI_GRAY;
      case IRON_ORE:
        return ANSI_WHITE;
      case GOLD_ORE:
        return ANSI_YELLOW;
      case DIAMOND_ORE:
        return ANSI_CYAN;
      case APPLE:
        return ANSI_RED;
      case GRASS:
        return ANSI_DARK_GREEN;
      default:
        return "";
    }
  }

  private static void waitForEnter() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }
  private static void openChat(Scanner scanner){
    clearScreen();
    Chatv.chatSession(scanner);
    System.out.println("Returning to the game");
    waitForEnter();
  }
  private static String getCraftedItemName(int craftedItem) {
    switch (craftedItem) {
      case CRAFTED_WOODEN_PLANKS:
        return "Wooden Planks";
      case CRAFTED_STICK:
        return "Stick";
      case CRAFTED_IRON_INGOT:
        return "Iron Ingot";
      case CRAFTED_DIAMOND:
        return "Diamond";
      case CRAFTED_GOLD_INGOT:
        return "Gold Ingot";
      case CRAFTED_DIAMOND_PICKAXE:
        return "Diamond Pickaxe";
      case CRAFTED_GOLDEN_APPLE:
        return "Golden Apple";
      default:
        return "Unknown";
    }
  }

  private static String getCraftedItemColor(int craftedItem) {
    switch (craftedItem) {
      case CRAFTED_WOODEN_PLANKS:
      case CRAFTED_STICK:
      case CRAFTED_IRON_INGOT:
      case CRAFTED_DIAMOND:
      case CRAFTED_GOLD_INGOT:
      case CRAFTED_DIAMOND_PICKAXE:
      case CRAFTED_GOLDEN_APPLE:

        return ANSI_GOLD;
      default:
        return "";
    }
  }


}