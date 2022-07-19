import java.util.Scanner;
import java.util.Arrays;


public class RleProgram {
    //toHexString method
    public static  String toHexString(byte[] data){

        int lengthData = data.length;

        String hexDecimal ="";

        for(int i = 0; i <lengthData ; i++  ){

//            System.out.println(data[i]);

            char[] hex = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};


            int num = data[i]%16;

            hexDecimal = hexDecimal + hex[num];

        }


        return hexDecimal;

    }
    //countRuns method
    public static  int countRuns(byte[] data){
        int lengthData = data.length;

       int count = 1;
       int e = 1;

       int firstNumber = data[0];


        for(int i = 1; i <lengthData ; i++  ){

            if(data[i]!=firstNumber) {

                firstNumber = data[i];

               count ++;

               e=0;


            } else{
                e++;
                if (e > 15){
                    count++;
                    e = 0;
                }

            }




        }

        return count;

    }

    //encodeRle method
    public static  byte[] encodeRle(byte[] data) {

        int lengthData = data.length;
        if (lengthData==0){
            return null;
        }

        byte[] result = new byte[countRuns(data) * 2];

        byte firstNumber = data[0];

        byte count = 0;
        byte e = 1;

        int index = 0;

        for (int i = 0; i < lengthData; i++) {


            if (data[i] == firstNumber && count!=15) {

                count++;
                if(i==lengthData-1){

                    result[index++] = count;
                    result[index++] = firstNumber;

                    firstNumber = data[i];

                }

            } else {


                result[index++] = count;
                result[index++] = firstNumber;

                firstNumber = data[i];

                count = 1;
                if (i==lengthData-1){
                    result[index++] = count;
                    result[index++] = firstNumber;
                }
            }


        }

        return result;
    }
    //getDecodedLength method
    public static int getDecodedLength(byte[] rleData){
        int lengthData = rleData.length;
        int sum = 0;
        for(int i = 0; i<lengthData; i=i+2){
           sum+=rleData[i];
        }
        return sum;
    }
    //decodeRle method
    public static byte[] decodeRle(byte[] rleData){
        int lengthData = rleData.length;
        int sum = 0;
        for(int i = 0; i<lengthData; i=i+2){
            sum+=rleData[i];
        }

        int indexValue =0;
        byte [] resultA = new byte[sum];
        for (int i = 0; i<lengthData; i=i+2){
            for(int j = 0; j<rleData[i]; j++){
                resultA[indexValue++] = rleData[i+1];
            }
        }
        return resultA;
    }
    //stringToData method
    public static byte[] stringToData(String dataString){
        int lengthData = dataString.length();
        byte [] data = new byte[lengthData];
        short numStore = 0;
        int digit = 0;
        for(int i = 0; i<lengthData; i++){
            if (dataString.charAt(i)>='0'&& dataString.charAt(i)<='9'){
                data[i] = Byte.parseByte(dataString.charAt(i)+"");
            }
            else if(dataString.charAt(i)>='a' && dataString.charAt(i)<='f') {
                data[i] = Byte.parseByte((dataString.charAt(i) - 87)+ "");
            }




        }
        return data;
    }

    //toRleString method
    public static String toRleString(byte[]rleData) {
        int lengthData = rleData.length;

        String hexDecimal = "";

        for (int i = 0; i < lengthData; i++) {

            if (i%2==0){
                hexDecimal += rleData[i];
            }
            else {
                char[] hex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


                int num = rleData[i] % 16;
                if (i != lengthData - 1) {
                    hexDecimal = hexDecimal + hex[num] + ":";
                }
                else {
                    hexDecimal = hexDecimal + hex[num];
                }

            }

        }

          return hexDecimal;

        }
        //stringToRle method
    public static byte[] stringToRle(String rleString){
        int lengthData = rleString.length();
        String str = "";
        String array[]  = rleString.split(":");
        int length = array.length *2;
        int count = 0;
        byte [] data = new byte[length];
        for (int i = 0; i<array.length; i++){
            if (array[i].length() == 3){
                data[count] = Byte.parseByte(array[i].substring(0,2));
               data[count+1] = Byte.parseByte(array[i].substring(2, 3), 16);
               count++;
               count++;
            }
            if (array[i].length() == 2) {
                data[count] = Byte.parseByte(array[i].substring(0, 1));
                data[count + 1] = Byte.parseByte(array[i].substring(1, 2), 16);
                count++;
                count++;
            }

        }
        return data;
    }
    //main method
    public static void main(String[] args) {

        boolean menu = true; //variable for menu to run
        int a = 0; //variable for menu option
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the RLE image encoder!");
        System.out.println("Displaying Spectrum Image:");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);

        String b; //variable for input

        byte [] imageFile = null; //image file array

        //menu
        while (menu == true) {
            System.out.print("RLE Menu\n");
            System.out.println("--------");
            System.out.println("0. Exit");
            System.out.println("1. Load File");
            System.out.println("2. Load Test Image");
            System.out.println("3. Read RLE String");
            System.out.println("4. Read RLE Hex String");
            System.out.println("5. Read Data Hex String");
            System.out.println("6. Display Image");
            System.out.println("7. Display RLE String");
            System.out.println("8. Display Hex RLE Data");
            System.out.println("9. Display Hex Flat Data");
            System.out.print("Select a Menu Option: \n");
            a = scanner.nextInt();

            //first option loads a file
            if (a == 1) {
                System.out.print("Enter name of file to load: ");
                b = scanner.next();
                imageFile = ConsoleGfx.loadFile(b);

            }
            //second option loads the test image
            if (a == 2) {
                imageFile = ConsoleGfx.testImage;
                System.out.println("Test image data loaded.");

            }
            //third option reads a RLE string
            if (a == 3){
                System.out.println("Enter an RLE string to be decoded: ");
                b = scanner.next();
                imageFile = decodeRle(stringToRle(b));
            }
            //fourth option reads a RLE hex String
            if (a == 4){
                System.out.println("Enter the hex string holding RLE data:");
                b = scanner.next();
                imageFile = decodeRle(stringToData(b));

            }
            //fifth option reads a data hex string
            if (a == 5){
                System.out.println("Enter the hex string holding flat data:");
                b = scanner.next();
                imageFile = stringToData(b);

            }
            //sixth option displays an image
            if (a == 6) {
                System.out.println("Displaying image...");
                ConsoleGfx.displayImage(imageFile);

            }
            //seventh option displays a RLE string
            if (a == 7){
                System.out.println("RLE representation: " + toRleString(encodeRle(imageFile)));

            }
            //eighth option displays Hex RLE data
            if (a == 8){
                System.out.println("Rle Hex Values: " + toHexString(encodeRle(imageFile)));
            }
            //ninth option displays Hex Flat data
            if (a == 9){
                System.out.println("Flat hex values: " + toHexString(imageFile));

            }
            //option zero for exit
            if (a==0){
                menu=false;
            }

        }
    }




}

