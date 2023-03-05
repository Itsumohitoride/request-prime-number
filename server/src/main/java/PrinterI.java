
public class PrinterI implements Demo.Printer
{
    public static final String WHITE = "\u001B[37m";
    public static final String GREEN = "\u001B[32m";
    public static final String RED = "\u001B[31m";
    public static final String VALID_REQUEST = " VALID_REQUEST";
    public static final String INVALID_REQUEST = " INVALID_REQUEST";

    public boolean validateString(String guid, com.zeroc.Ice.Current current)
    {
        boolean verification = verifyGUIDFormat(guid, current);

        if(verification){
            validRequest(guid,current);
        }else {
            invalidRequest(guid, current);
        }
        return verification;
    }

    public void validRequest(String message, com.zeroc.Ice.Current current){
        System.out.println(GREEN+message+VALID_REQUEST+WHITE);
    }

    public void invalidRequest(String message, com.zeroc.Ice.Current current){
        System.out.println(RED+message+INVALID_REQUEST+WHITE);
    }

    public boolean verifyGUIDFormat(String guid, com.zeroc.Ice.Current current){
        return guid.matches("^[a-f0-9]{8}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{4}[-]{1}[a-f0-9]{12}$");
    }
    public int findClosestPrime(int number, com.zeroc.Ice.Current current){
        int prime = Integer.MIN_VALUE;
            do {
                prime = ++number;
            } while (!isPrime(prime,current));
        return prime;
    }

    public boolean isPrime(int number, com.zeroc.Ice.Current current){
        boolean verification = true;

        for (int i = (int) Math.sqrt(number); i > 1 && verification; i--){
            if (number % i == 0){
                verification = false;
            }
        }
        return verification;
    };
}