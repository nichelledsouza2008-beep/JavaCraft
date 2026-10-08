import java.io.*;
import java.net.*;
import java.util.*;

public class Chatv {
public static boolean isValidMessage(String msg) {
    int state = 0;
    for (int i = 0; i < msg.length(); i++) {
        char c = msg.charAt(i);
        if (state == 0) {
            if (c == '@') {
                state = 1;
            } else {
                return false;
            }
        } else if (state == 1) {
            if (c == ':' || c == ' ') {
                return false;
            } else {
                state = 2;
            }
        } else if (state == 2) {
            if (c == ':') {
                state = 3;
            } else if (c == ' ') {
                return false;
            }
        } else if (state == 3) {
            if (c == ' ') {
                state = 4;
            } else {
                return false;
            }
        } else if (state == 4) {
            state = 5;
        }
    }
    return state == 5;
}

    public static String encrypt(String word){
        String encryptedWord = "";
        for (int i =0; i < word.length();i++){
            int shifted_code = 3 + (int) word.charAt(i);
            char shifted_char = (char) shifted_code;
            encryptedWord += shifted_char;

        }
        return encryptedWord;
    }

    public static String deCrypt(String encWord){
        String decryptedWord = "";
        for (int b =0; b <encWord.length(); b++){
            int shifted_code = (int) encWord.charAt(b) -3;
            char shift_char = (char) shifted_code;
            decryptedWord += shift_char; 
            
        }
        return decryptedWord;
    }
    public static void chatSession(Scanner inputFromUser) {
        // A chat session starts by connecting to the server's TCP/IP socket.
        // We also create a new Scanner, to receive chat messages from the local user
        // in order to forward them to the server.
        //
        // By allocating these resources in a "try" statement, we ensure they
        // are released when control exits this procedure. See
        // https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
        //
        try (
            final var socket = new Socket("chat.bcs1110.svc.leastfixedpoint.nl", 5999);
        ) {
            // If control reaches here, the socket exists and is connected. We
            // extract an *input stream*, for reading messages from the server, and
            // an *output stream*, for sending messages to the server.
            final var inputFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            final var output = new PrintWriter(socket.getOutputStream());

            // Our chat system follows a very simple *communication protocol*.
            // https://en.wikipedia.org/wiki/Communication_protocol
            //
            // The first step in the protocol is for a connecting client to send
            // a "password" to access the server. It's not a big secret - it just
            // keeps opportunistic passers-by (e.g. AI crawler bots) out.
            output.println("F4EF9A36-5FCD-4D27-8A0A-FC7C77D3DBB2");
            output.flush();
            System.out.println("You are now in the chat. Type '/quit' to return to the game.");
            // Q. Why is this ^ `flush()` statement necessary?
            //    See https://stackoverflow.com/questions/2340106/what-is-the-purpose-of-flush-in-java-streams

            // After the password, the protocol proceeds *asynchronously*. The server
            // sends stuff to us when it has anything for us, and we send stuff to it
            // when we have anything to say. These can happen at any time. But in Java,
            // things happen one after the other! There's no notion of asynchronous
            // event! We *could* introduce threads or some other construct for representing
            // concurrent activity. (See https://en.wikipedia.org/wiki/Concurrency_(computer_science))
            //
            // But to keep it simple, THIS client program chooses to proceed in alternating
            // stages:
            //
            // 1 - "ping" the server, and print output we get back until we see the response to
            //     our "ping", indicating that there's nothing more to show just at the minute;
            // 2 - then, wait for input from the user.
            // 3 - send the message the user typed to the server, and loop back to the
            //     beginning again.
            //
            // We take special care not to send an empty line because that's what the protocol
            // uses to mean "ping"!
            //
            while (true) {
                // 1. Ping the server.
                output.println("");
                output.flush(); // Don't forget to actually send the output through!

                // 1(b). Collect responses until we see the one that specifically indicates
                // that the server has received and processed our "ping".
                while (true) {
                    var fromServer = inputFromServer.readLine();
                    if (fromServer == null) return; // We get null if the server disconnects.

                    if (fromServer.equals("+")) {
                        // Aha! The response to our ping! We're done with this round: move
                        // on to step 2.
                        break;
                    }
                    // Some other message from the server
                    int idx = fromServer.indexOf(": ");
                    if (idx >= 0) {
                        System.out.println(fromServer.substring(0, idx + 2)
                       + deCrypt(fromServer.substring(idx + 2)));
                    } else {
                         System.out.println(fromServer);  
                    }
                    // Some other message from the server: just print it.
                    System.out.println(fromServer);
                }

                // 2. Collect a line of input from the user.
                var messageToSend = inputFromUser.nextLine();
                if (messageToSend.equalsIgnoreCase("/quit")){
                    return;
                }
                if (!messageToSend.equals("")) {// 3. If it WASN'T a blank line, check the format, then send it.
                                                             
                if (isValidMessage(messageToSend)) {
                    int idx = messageToSend.indexOf(": ");
                    String header = messageToSend.substring(0, idx + 2);  
                    String text = messageToSend.substring(idx + 2);
                    output.println(header + encrypt(text));
                } else {
                     System.out.println("Incorrect format. Use: @name: text");
                }

                    // Q. What would happen if we DIDN'T take care not to send empty lines?
                    //    Hint: what does our code do if it sees two "ping" responses ("+" lines) in a row?
                }

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
}

