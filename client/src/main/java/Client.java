import java.net.Inet4Address;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import com.zeroc.Ice.ConnectionRefusedException;

public class Client {
    public static void main(String[] args) {

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "client.cfg")) {
            com.zeroc.Ice.ObjectPrx base = communicator.propertyToProxy("Service.Proxy");
            Demo.PrinterPrx printer = createBase(base);

            validatePrinter(printer);

            try {

                String[] parameters = args;

                parametersLengthCases(parameters, printer);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    private static void parametersLengthCases(String[] parameters, Demo.PrinterPrx printer){
        if(verifyParametersLenght(parameters, 0)){
            parametersNeeded();
        }else if(verifyParametersLenght(parameters, 1)){
            oneParameterCase(Integer.parseInt(parameters[0]), printer);
        }else if(verifyParametersLenght(parameters, 2)){
            twoParameterCase(parameters, printer);
        }
    }

    private static void oneParameterCase(int number, Demo.PrinterPrx printer){
        boolean isValidNumber = verifyNumber(number);
        if(isValidNumber){
            printer.validateString(generateGUID().toString());
        }else{
            incorrectNumber();
        }
    }

    private static void twoParameterCase(String[] parameters, Demo.PrinterPrx printer){
        int number = Integer.parseInt(parameters[0]);
        String fileDirection = parameters[1];
        boolean isValidNumber = false;
        boolean fileExist = false;

        try{
            isValidNumber = verifyNumber(number);
            fileExist = verifyFileExistence(fileDirection);

            if(isValidNumber && fileExist){
                String guid = getGUID(fileDirection);
                boolean hasGUIDFormat = printer.validateString(guid);

                if(hasGUIDFormat){
                    int prime = printer.findClosestPrime(number);
                    printNumber(prime);
                }else {
                    printNumber(1);
                }
            }
        }catch (IOException ioe){}

        if(!isValidNumber){
            incorrectNumber();
        }

        if(!fileExist){
            incorrectPath();
        }
    }

    private static boolean verifyFileExistence(String filePath) throws IOException{
        File file = new File(filePath);
        return file.exists();
    }

    private static String getGUID(String filePath) throws IOException{
        File file = new File(filePath);
        FileReader  fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String guid = br.readLine();
        return guid.toString();
    }

    private static void incorrectNumber(){
        System.out.println("El numero tiene que ser mayor que 1");
    }

    private static void incorrectPath(){
        System.out.println("El archivo especificado no existe");
    }

    private static void validatePrinter(Demo.PrinterPrx printer){
        if(printer == null){
            invalidProxy();
        }
    }

    private static Demo.PrinterPrx createBase(com.zeroc.Ice.ObjectPrx base) throws ConnectionRefusedException{
        Demo.PrinterPrx initializedBase = null;

        try{
            initializedBase = Demo.PrinterPrx.checkedCast(base);
        }catch (ConnectionRefusedException cre){
            connectionRefused();
        }

        return initializedBase;
    }

    private static void connectionRefused(){ System.out.println("El servidor se encuentra fuera de servicio");}

    private static void invalidProxy(){
        System.out.println("El proxy es invalido");
    }

    private static UUID generateGUID(){
        return UUID.randomUUID();
    }

    private static boolean verifyNumber(int number){
        return number > 1;
    }

    private static boolean verifyParametersLenght(String[] parameters, int length){
        return parameters.length == length;
    }

    private static void parametersNeeded(){
        System.out.println("Se requiere como minimo un numero entero positivo, y opcionalmente un nombre de archivo de texto local");
        System.out.println("Luis Miguel Ossa Arias - A00369982");
        System.out.println("Gianni stiven Benavides García - A00362358");

    }

    private static void printNumber(int number){
        System.out.println(number);
    }
}