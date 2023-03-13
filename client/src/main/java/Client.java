import java.net.Inet4Address;
import java.util.UUID;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Client {
    public static void main(String[] args) {

        try (com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args, "client.cfg")) {
            com.zeroc.Ice.ObjectPrx base = communicator.propertyToProxy("Service.Proxy");
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            if (printer == null) {
                throw new Error("Invalid proxy");
            }
            try {

                String[] parameters = args;

                parametersLengthCases(parameters, printer);

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static void parametersLengthCases(String[] parameters, Demo.PrinterPrx printer){
        if(verifyParametersLenght(parameters, 0)){
            parametersNeeded();
        }else if(verifyParametersLenght(parameters, 1)){
            oneParameterCase(Integer.parseInt(parameters[0]), printer);
        }else if(verifyParametersLenght(parameters, 2)){
            twoParameterCase(parameters, printer);
        }
    }

    public static void oneParameterCase(int number, Demo.PrinterPrx printer){
        boolean isValidNumber = verifyNumber(number);
        if(isValidNumber){
            printer.validateString(generateGUID().toString());
        }else{
            incorrectNumber();
        }
    }

    public static void twoParameterCase(String[] parameters, Demo.PrinterPrx printer){
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

    public static boolean verifyFileExistence(String filePath) throws IOException{
        File file = new File(filePath);
        return file.exists();
    }

    public static String getGUID(String filePath) throws IOException{
        File file = new File(filePath);
        FileReader  fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String guid = br.readLine();
        return guid.toString();
    }

    public static void incorrectNumber(){
        System.out.println("El numero tiene que ser mayor que 1");
    }

    public static void incorrectPath(){
        System.out.println("El archivo especificado no existe");
    }

    public static UUID generateGUID(){
        return UUID.randomUUID();
    }

    public static boolean verifyNumber(int number){
        return number > 1;
    }

    public static boolean verifyParametersLenght(String[] parameters, int length){
        return parameters.length == length;
    }

    public static void parametersNeeded(){
        System.out.println("Se requiere como minimo un numero entero positivo, y opcionalmente un nombre de archivo de texto local");
        System.out.println("Luis Miguel Ossa Arias - A00369982");
        System.out.println("Gianni Stiven Benavides - A00362358");
    }

    public static void printNumber(int number){
        System.out.println(number);
    }
}