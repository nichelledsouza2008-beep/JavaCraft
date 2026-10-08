import java.util.*;
import java.net.*;
import java.io.*;

public class JavaCraftfinal {
  private static final int AIR = 0;
  private static final int WOOD = 1;
  private static final int LEAVES = 2;
  private static final int STONE = 3;
  private static final int IRON_ORE = 4;
  private static final int BERRIES = 5;
  private static final int FIREPLACE = 6;
  private static int NEW_WORLD_WIDTH = 25;
  private static int NEW_WORLD_HEIGHT = 15;
  private static int EMPTY_BLOCK = 0;
  private static final int CRAFT_WOODEN_PLANKS = 100;
  private static final int CRAFT_STICK = 101;
  private static final int CRAFT_IRON_INGOT = 102;
  private static final int CRAFTED_WOODEN_PLANKS = 200;
  private static final int CRAFTED_STICK = 201;
  private static final int CRAFTED_IRON_INGOT = 202;
  private static final int CRAFTED_GUMMYBERRY_JUICE = 203;
  private static final int CRAFTED_POT = 204;
  private static final String ANSI_BROWN = "\u001B[33m";
  private static final String ANSI_RESET = "\u001B[0m";
  private static final String ANSI_GREEN = "\u001B[32m";
  private static final String ANSI_YELLOW = "\u001B[33m";
  private static final String ANSI_CYAN = "\u001B[36m";
  private static final String ANSI_RED = "\u001B[31m";
  private static final String ANSI_PURPLE = "\u001B[35m";
  private static final String ANSI_BLUE = "\u001B[34m";
  private static final String ANSI_GRAY = "\u001B[37m";
  private static final String ANSI_WHITE = "\u001B[97m";
  private static final String ANSI_ORANGE = "\u001B[38;5;214m"; // change colour

  private static final String BLOCK_NUMBERS_INFO = "Block Numbers:\n" +
      "0 - Empty block\n" +
      "1 - Wood block\n" +
      "2 - Leaves block\n" +
      "3 - Stone block\n" +
      "4 - Iron ore block\n" +
      "5 - Wooden Planks (Crafted Item)\n" +
      "6 - Stick (Crafted Item)\n" +
      "7 - Iron Ingot (Crafted Item)\n" +
      "8 - Berries block\n" +
      "9 - Pot (Crafted Item)" +
      "10- FirePlace block\n"; // berries
  private static int[][] world;
  private static int worldWidth;
  private static int worldHeight;
  private static int playerX;
  private static int playerY;
  private static List<Integer> inventory = new ArrayList<>();
  private static List<Integer> craftedItems = new ArrayList<>();
  private static boolean unlockMode = false;
  private static boolean secretDoorUnlocked = false;
  private static boolean inSecretArea = false;
  private static final int INVENTORY_SIZE = 100;


  public static void main(String[] args) {
    initGame(25, 15);
    generateWorld();
    System.out.println(ANSI_GREEN + "Welcome to Simple Minecraft!" + ANSI_RESET);
    System.out.println("Instructions:");
    System.out.println(" - Use 'W', 'A', 'S', 'D', or arrow keys to move the player.");
    System.out.println(" - Press 'M' to mine the block at your position and add it to your inventory.");
    System.out.println(" - Press 'P' to place a block from your inventory at your position.");
    System.out.println(" - Press 'C' to view crafting recipes and 'I' to interact with elements in the world.");
    System.out.println(" - Press 'Save' to save the game state and 'Load' to load a saved game state.");
    System.out.println(" - Press 'Exit' to quit the game.");
    System.out.println(" - Type 'Help' to display these instructions again.");
    System.out.println();
    Scanner scanner = new Scanner(System.in);
    System.out.print("Start the game? (Y/N): ");
    String startGameChoice = scanner.next().toUpperCase();
    if (startGameChoice.equals("Y")) {
      startGame();
    } else {
      System.out.println("Game not started. Goodbye!");
    }
    try {
      chatSession();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public static void initGame(int worldWidth, int worldHeight) {
    JavaCraftfinal.worldWidth = worldWidth;
    JavaCraftfinal.worldHeight = worldHeight;
    JavaCraftfinal.world = new int[worldWidth][worldHeight];
    playerX = worldWidth / 2;
    playerY = worldHeight / 2;
    inventory = new ArrayList<>();
    craftedItems = new ArrayList<>();
  }

  public static void generateWorld() {
    Random rand = new Random();
    for (int y = 0; y < worldHeight; y++) {
      for (int x = 0; x < worldWidth; x++) {
        int randValue = rand.nextInt(100);
        if (randValue < 20) {
          world[x][y] = WOOD;
        } else if (randValue < 35) {
          world[x][y] = LEAVES;
        } else if (randValue < 50) {
          world[x][y] = STONE;
        } else if (randValue < 70) {
          world[x][y] = IRON_ORE;
        } else if (randValue < 80) {
          world[x][y] = BERRIES;
        } else if (randValue < 90) {
          world[x][y] = FIREPLACE; // berries
        } else {
          world[x][y] = AIR;
        }
      }
    }
  }

  public static void displayWorld() {
    System.out.println(ANSI_CYAN + "World Map:" + ANSI_RESET);
    System.out.println("â•”â•â•" + "â•".repeat(worldWidth * 2 - 2) + "â•—");
    for (int y = 0; y < worldHeight; y++) {
      System.out.print("â•‘");
      for (int x = 0; x < worldWidth; x++) {
        if (x == playerX && y == playerY && !inSecretArea) {
          System.out.print(ANSI_GREEN + "P " + ANSI_RESET);
        } else if (x == playerX && y == playerY && inSecretArea) {
          System.out.print(ANSI_BLUE + "P " + ANSI_RESET);
        } else {
          System.out.print(getBlockSymbol(world[x][y]));
        }
      }
      System.out.println("â•‘");
    }
    System.out.println("â•šâ•â•" + "â•".repeat(worldWidth * 2 - 2) + "â•");
  }

  private static String getBlockSymbol(int blockType) {
    String blockColor;
    switch (blockType) {
      case AIR:
        return ANSI_RESET + "- ";
      case WOOD:
        blockColor = ANSI_RED;
        break;
      case LEAVES:
        blockColor = ANSI_GREEN;
        break;
      case STONE:
        blockColor = ANSI_BLUE;
        break;
      case IRON_ORE:
        blockColor = ANSI_WHITE;
        break;
      case BERRIES:
        blockColor = ANSI_PURPLE;
        break; // berries
      case FIREPLACE:
        blockColor = ANSI_ORANGE;
        break; // berries
      default:
        blockColor = ANSI_RESET;
        break;
    }
    return blockColor + getBlockChar(blockType) + " ";
  }

  private static char getBlockChar(int blockType) {
    switch (blockType) {
      case WOOD:
        return '\u2592';
      case LEAVES:
        return '\u00A7';
      case STONE:
        return '\u2593';
      case IRON_ORE:
        return '\u00B0';
      case BERRIES:
        return '\u0B83';
      case FIREPLACE:
        return '\u2668'; // berries
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
    while (true) {
      clearScreen();
      displayLegend();
      displayWorld();
      displayInventory();
      System.out.println(ANSI_CYAN
          + "Enter your action: 'WASD': Move, 'M': Mine, 'P': Place, 'C': Craft, 'I': Interact, 'Save': Save, 'Load': Load, 'Exit': Quit, 'Unlock': Unlock Secret Door"
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
      } else if (input.equalsIgnoreCase("chat")) {
        chatSession();
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
      } else {
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

  private static void fillInventory() {
    inventory.clear();
    for (int blockType = 1; blockType <= 6; blockType++) {
      for (int i = 0; i < INVENTORY_SIZE; i++) {
        inventory.add(blockType);
      }
    }
  }

  public static boolean isValidMessage(String message) {
    return DfaChat.simulateDFA(message);
  }
    /*if (!message.startsWith("@"))
      return false;
    int at = message.indexOf('@');
    if (message.charAt(at + 1) == '@')
      return false;

    int colon = message.indexOf(':');
    if (colon == -1)
      return false; // no colon at all
    if (message.charAt(colon + 1) == ':')
      return false;
    if (colon == 1)
      return false; // "@:" means the name is empty

    String name = message.substring(1, colon);
    if (name.contains(" "))
      return false;

    if (message.charAt(colon + 1) == ' ') {
      String text = message.substring(colon + 1);
      return !text.isEmpty();// there must be some text
    } else
      return false; */


  public static String extractMessage(String serverMessage) {
    int separator = serverMessage.indexOf(">>>");
    if (separator == -1)
      return serverMessage.trim();

    return serverMessage.substring(separator + 3).trim();
  }

  public static void chatSession() {
    try (
        final var inputFromUser = new Scanner(System.in);
        final var socket = new Socket("chat.bcs1110.svc.leastfixedpoint.nl", 5999);) {
      final var inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      final var output = new PrintWriter(socket.getOutputStream());
      output.println("F4EF9A36-5FCD-4D27-8A0A-FC7C77D3DBB2");
      output.flush();
      while (true) {
        // 1. Ping the server.
        output.println("");
        output.flush(); // Don't forget to actually send the output through!

        // 1(b). Collect responses until we see the one that specifically indicates
        // that the server has received and processed our "ping".
        while (true) {
          var fromServer = inputFromServer.readLine();
          if (fromServer == null)
            return; // We get null if the server disconnects.

          if (fromServer.equals("+")) {
            // Aha! The response to our ping! We're done with this round: move
            // on to step 2.
            break;
          }
          // Server status notices (for example, a user connecting) are not chat
          // messages and do not follow the chat-message format checked by the DFA.
          var decryptedMessage = Decrypt(fromServer);
          if (!decryptedMessage.contains(">>>")) {
            System.out.println(decryptedMessage);
            continue;
          }

          // Chat messages follow the server's "header >>> message" format.
          var chatMessage = extractMessage(decryptedMessage);
          if (isValidMessage(chatMessage)) {
            System.out.println("[decrypted]   "+chatMessage);
          } else {
            System.out.println("Received a message rejected by the DFA: " + chatMessage);//check
          }
        }

        var messageToSend = inputFromUser.nextLine();
        if (!messageToSend.equals("")) {
          if (isValidMessage(messageToSend)){
            // Validate before encrypting and sending the message.
            output.println("[encrypted]   "+Encrypt(messageToSend));
            output.flush();
          } else {
            System.out.println("Rejected: the format of the message must be @Name: message");
          }
        }

        // 2. Collect a line of input from the user.
        /*
         * var messageToSend = inputFromUser.nextLine();
         * if (!messageToSend.equals("")) {
         * // 3. If it WASN'T a blank line, send it: it will be interpreted as
         * // a chat message.
         * output.println(messageToSend);
         * 
         * // Q. What would happen if we DIDN'T take care not to send empty lines?
         * // Hint: what does our code do if it sees two "ping" responses ("+" lines) in
         * a row?
         * }
         */

        // 3(b). Loop back to step 1.
      }
    } catch (Throwable t) {
      // We are being very lazy here and not handling errors properly.
      // In a real program, we would want to handle errors relating to network
      // failure differently from errors relating to, say, errors we made in our
      // own program being signalled, or errors relating to collecting input from
      // the user.
      t.printStackTrace();
    }
  }

  private static void resetWorld() {
    generateEmptyWorld();
    playerX = worldWidth / 2;
    playerY = worldHeight / 2;
  }

  private static void generateEmptyWorld() { // 1
    world = new int[NEW_WORLD_WIDTH][NEW_WORLD_HEIGHT];
    int redBlock = 1;
    int whiteBlock = 4;
    int blueBlock = 3;
    int stripeHeight = NEW_WORLD_HEIGHT / 3; // Divide the height into three equal parts

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
    for (int y = stripeHeight * 2; y < NEW_WORLD_HEIGHT; y++) {
      for (int x = 0; x < NEW_WORLD_WIDTH; x++) {
        world[x][y] = blueBlock;
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

  private static void lookAround() { // 2
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
  }

  public static void movePlayer(String direction) { // 3
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

  public static void mineBlock() {
    int blockType = world[playerX][playerY];
    if ((blockType != AIR) && (blockType != BERRIES) && (blockType != FIREPLACE)) {
      inventory.add(blockType);
      world[playerX][playerY] = AIR;
      System.out.println("Mined " + getBlockName(blockType) + ".");
    } else if ((blockType == BERRIES) || (blockType == FIREPLACE)) {
      inventory.add(blockType);
      world[playerX][playerY] = AIR;
      System.out.println("Gathered " + getBlockName(blockType) + ".");
    } else {
      System.out.println("No block to mine here.");
    }
    waitForEnter();
  }

  public static void placeBlock(int blockType) {
    if (blockType >= 0 && blockType <= 11) {
      if (blockType <= 6) {
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

  private static int getBlockTypeFromCraftedItem(int craftedItem) {
    switch (craftedItem) {
      case CRAFTED_WOODEN_PLANKS:
        return 7;
      case CRAFTED_STICK:
        return 8;
      case CRAFTED_IRON_INGOT:
        return 9;
      case CRAFTED_GUMMYBERRY_JUICE:
        return 10;
      case CRAFTED_POT:
        return 11;
      default:
        return -1;
    }
  }

  private static int getCraftedItemFromBlockType(int blockType) {
    switch (blockType) {
      case 7:
        return CRAFTED_WOODEN_PLANKS;
      case 8:
        return CRAFTED_STICK;
      case 9:
        return CRAFTED_IRON_INGOT;
      case 10:
        return CRAFTED_GUMMYBERRY_JUICE;
      case 11:
        return CRAFTED_POT;
      default:
        return -1;
    }
  }

  public static void displayCraftingRecipes() {
    System.out.println("Crafting Recipes:");
    System.out.println("1. Craft Wooden Planks: 2 Wood");
    System.out.println("2. Craft Stick: 1 Wood");
    System.out.println("3. Craft Iron Ingot: 3 Iron Ore");
    System.out.println("4. Craft Pot: 10 Iron Ore + 1 Iron Ingot");
    System.out.println("5. Cook GummyBerry Juice: 3 Berries + 2 Stick + 1 Pot + 5 Sparks");
  }

  public static void craftItem(int recipe) { // 4
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
        craftPot();
        break;
      case 5:
        craftGummyBerryJuice();
        break;
      default:
        System.out.println("Invalid recipe number.");
    }
    waitForEnter();
  }

  public static void craftWoodenPlanks() {
    if (inventoryContains(WOOD, 2)) {
      removeItemsFromInventory(WOOD, 2);
      addCraftedItem(CRAFTED_WOODEN_PLANKS);
      System.out.println("Crafted Wooden Planks.");
    } else {
      System.out.println("Insufficient resources to craft Wooden Planks.");
    }
  }

  public static void craftStick() {
    if (inventoryContains(WOOD)) {
      removeItemsFromInventory(WOOD, 1);
      addCraftedItem(CRAFTED_STICK);
      System.out.println("Crafted Stick.");
    } else {
      System.out.println("Insufficient resources to craft Stick.");
    }
  }

  public static void craftPot() {
    if (inventoryContains(IRON_ORE, 10) && craftedItems.contains(CRAFTED_IRON_INGOT)) {
      removeItemsFromInventory(IRON_ORE, 10);
      removeItemsCraftedItems(CRAFTED_IRON_INGOT, 1);
      addCraftedItem(CRAFTED_POT);
      System.out.println("Crafted Pot.");
    } else {
      System.out.println("Insufficient resources to craft Pot.");
    }
  }

  public static void craftGummyBerryJuice() {
    if (inventoryContains(BERRIES, 3) && craftedItemContains(CRAFTED_STICK, 2) && craftedItemContains(CRAFTED_POT)
        && inventoryContains(FIREPLACE, 5)) {
      removeItemsFromInventory(BERRIES, 3); // does not work
      removeItemsCraftedItems(CRAFTED_STICK, 2);
      removeItemsCraftedItems(CRAFTED_POT, 1);
      removeItemsFromInventory(FIREPLACE, 5);
      addCraftedItem(CRAFTED_GUMMYBERRY_JUICE);
      System.out.println("Cooked GummyBerry Juice.");
    } else {
      System.out.println("Insufficient resources to cook GummyBerry Juice.");
    }
  }

  public static void craftIronIngot() {
    if (inventoryContains(IRON_ORE, 3)) {
      removeItemsFromInventory(IRON_ORE, 3);
      addCraftedItem(CRAFTED_IRON_INGOT);
      System.out.println("Crafted Iron Ingot.");
    } else {
      System.out.println("Insufficient resources to craft Iron Ingot.");
    }
  }

  // craft

  public static boolean craftedItemContains(int item) {
    return craftedItemContains(item, 1);
  }

  public static boolean craftedItemContains(int item, int count) {
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

  public static void removeItemsCraftedItems(int item, int count) {
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

  // inventory

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

  public static void addCraftedItem(int craftedItem) {
    if (craftedItems == null) {
      craftedItems = new ArrayList<>();
    }
    craftedItems.add(craftedItem);
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
        System.out.println("You gather stones from the ground.");
        inventory.add(STONE);
        break;
      case IRON_ORE:
        System.out.println("You mine iron ore from the ground.");
        inventory.add(IRON_ORE);
        break;
      case AIR:
        System.out.println("Nothing to interact with here.");
        break;
      case BERRIES:
        System.out
            .println("You have been eating gummy berries for the past 6 hours, maybe it's time to stop now......");
        inventory.add(BERRIES);
        break;
      case FIREPLACE:
        System.out.println("Oh too close! It's hot and sparkly!");
        break;
      default:
        System.out.println("Unrecognized block. Cannot interact.");
    }
    waitForEnter();
  }

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
      case BERRIES:
        return "Berries";
      case FIREPLACE:
        return "Sparks";
      default:
        return "Unknown";
    }
  }

  public static void displayLegend() {
    System.out.println(ANSI_BLUE + "Legend:");
    System.out.println(ANSI_WHITE + "-- - Empty block");
    System.out.println(ANSI_RED + "\u2592\u2592 - Wood block");
    System.out.println(ANSI_GREEN + "\u00A7\u00A7 - Leaves block");
    System.out.println(ANSI_BLUE + "\u2593\u2593 - Stone block");
    System.out.println(ANSI_WHITE + "\u00B0\u00B0- Iron ore block");
    System.out.println(ANSI_PURPLE + "\u0B83\u0B83 - Berry bush");
    System.out.println(ANSI_ORANGE + "\u2668\u2668- Sparks");
    System.out.println(ANSI_BLUE + "P - Player" + ANSI_RESET);
  }

  public static void displayInventory() {
    System.out.println("Inventory:");
    if (inventory.isEmpty()) {
      System.out.println(ANSI_YELLOW + "Empty" + ANSI_RESET);
    } else {
      int[] blockCounts = new int[7];
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

  private static String getBlockColor(int blockType) {
    switch (blockType) {
      case AIR:
        return "";
      case WOOD:
        return ANSI_RED;
      case LEAVES:
        return ANSI_GREEN;
      case STONE:
        return ANSI_GRAY;
      case IRON_ORE:
        return ANSI_YELLOW;
      case BERRIES:
        return ANSI_PURPLE;
      case FIREPLACE:
        return ANSI_ORANGE;
      default:
        return "";
    }
  }

  private static void waitForEnter() {
    System.out.println("Press Enter to continue...");
    Scanner scanner = new Scanner(System.in);
    scanner.nextLine();
  }

  private static String getCraftedItemName(int craftedItem) {
    switch (craftedItem) {
      case CRAFTED_WOODEN_PLANKS:
        return "Wooden Planks";
      case CRAFTED_STICK:
        return "Stick";
      case CRAFTED_IRON_INGOT:
        return "Iron Ingot";
      case CRAFTED_POT:
        return "Pot";
      case CRAFTED_GUMMYBERRY_JUICE:
        return "GummyBerry Juice";
      default:
        return "Unknown";
    }
  }

  private static String getCraftedItemColor(int craftedItem) {
    switch (craftedItem) {
      case CRAFTED_WOODEN_PLANKS:
      case CRAFTED_STICK:
      case CRAFTED_IRON_INGOT:
        return ANSI_BROWN;
      case CRAFTED_GUMMYBERRY_JUICE:
        return ANSI_PURPLE;
      case CRAFTED_POT:
        return ANSI_GRAY;
      default:
        return "";
    }
  }

  public static String Encrypt(String s) {
    String c = "";
    for (int i = 0; i < s.length(); i++) {
      char ch = s.charAt(i);
      if ((ch >= 'A' && ch <= 'M') || (ch >= 'a' && ch <= 'm')) {
        c += (char) (ch + 13);
      } else if ((ch >= 'N' && ch <= 'Z') || (ch >= 'n' && ch <= 'z')) {
        c += (char) (ch - 13);
      } else {
        c += ch;
      }
    }
    return c;
  }

  public static String Decrypt(String s) {
    // ROT13 is its own inverse.
    return Encrypt(s);
  }

}
