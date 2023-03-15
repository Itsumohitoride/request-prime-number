
public class PrinterI implements Demo.Printer
{
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String VALID_REQUEST = " VALID_REQUEST";
    public static final String INVALID_REQUEST = " INVALID_REQUEST";

    public boolean validateString(String guid, com.zeroc.Ice.Current current)
    {
        boolean verification = verifyGUIDFormat(guid);
        String serverMessage = "";

        if(verification){
            serverMessage = GREEN + guid;
            printRequest(serverMessage, VALID_REQUEST);
        }else {
            serverMessage = RED + guid;
            printRequest(serverMessage, INVALID_REQUEST);
        }
        return verification;
    }

    private void printRequest(String serverMessage, String typeRequest){
        System.out.println(serverMessage+typeRequest+WHITE);
    }

    private boolean verifyGUIDFormat(String guid){
        return guid.matches("^[a-f0-9]{8}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{12}$");
    }
    public int findClosestPrime(int number, com.zeroc.Ice.Current current){
        int prime = Integer.MIN_VALUE;
            do {
                prime = ++number;
            } while (!isPrime(prime));
        return prime;
    }

    private boolean isPrime(int number){
        boolean verification = true;

        for (int i = (int) Math.sqrt(number); i > 1 && verification; i--){
            if (number % i == 0){
                verification = false;
            }
        }
        return verification;
    };
}