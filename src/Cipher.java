import java.util.*;

class Cipher {
    public static String Encrypt(String s) {
        String c = " ";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                if ((ch >= 'A' && ch <= 'M')||(ch >= 'a' && ch <= 'm')) {
                    c += (char) (ch + 13);
                } else if ((ch >= 'N' && ch <= 'Z')||(ch >= 'n' && ch <= 'z')){
                    c += (char) (ch - 13);
                }
            }
            else{
                c+=ch;
            }
        }
        return c;
    }
    public static String Decrypt(String s) {
        String c = " ";
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isLetter(ch)) {
                if ((ch >= 'A' && ch <= 'M')||(ch >= 'a' && ch <= 'm')) {
                    c += (char) (ch + 13);
                } else if ((ch >= 'N' && ch <= 'Z')||(ch >= 'n' && ch <= 'z')){
                    c += (char) (ch - 13);
                }
            }
            else{
                c+=ch;
            }
        }
        return c;
    }

    public static void main(String args[]) {

        Scanner in = new Scanner(System.in);
        System.out.println("Enter a sentence");// getting a sentence from the user
        String n = in.nextLine(); // hello
        System.out.println("Input:" + n);
        String ciph = Encrypt(n);
        System.out.println("Ciphertext:" + ciph);
    }
}
/*
 * int l=s.length(); //5
 * 
 * for (int i = 0; i < s.length(); i++)
 * System.out.println(i + " ==> " + (int) s.charAt(i));
 * 
 * String c ="";
 * for(int i=0;i<l;i++)
 * {
 * char ch=s.charAt(i);//h
 * if(Character.isLetter(ch))//true
 * {
 * if(Character.isUpperCase(ch))//false
 * {
 * if(ch>'M')
 * {
 * c+=(char)(ch-13);//to get the ciphered letter in uppercase
 * }
 * else
 * {
 * c+=(char)(ch+13);
 * }
 * System.out.print(c);
 * }
 * else
 * {
 * if(ch>'m')//false
 * {
 * c+=(char)(ch-13);//to get the ciphered letter in lowercase
 * }
 * else
 * {
 * c+=(char)(ch+13);//104+13=117='u' //hellouryyb
 * }
 * System.out.print(c);
 * } }
 * else
 * {
 * c=s+ch;
 * }
 * }
 * 
 * for (int i = 0; i < c.length(); i++)
 * System.out.println(i + " ==> " + (int) c.charAt(i));
 * }}
 */
