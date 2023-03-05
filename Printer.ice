module Demo
{
    interface Printer
    {
        bool verifyGUIDFormat(string guid);
        int findClosestPrime(int number);
        bool isPrime(int number);
        bool validateString(string s);
        void validRequest(string message);
        void invalidRequest(string message);
    }
}