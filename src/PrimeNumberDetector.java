import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimeNumberDetector {
    public static void main(String[] args) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        List<Thread> threads = new ArrayList<>();
        Thread monitor = new Thread(() -> {
            while (true) {
                printThreadStatus(threads);
                try {
                    Thread.sleep(5000); // check every 1 second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        monitor.setDaemon(true);
        monitor.start();

        while(true){
            System.out.println("Enter the number: \n");
            Integer input = sc.nextInt();
            if(input==0){
                System.out.println("Waiting for remaining threads to complete execution\n");
                waitForThreads(threads);
                System.out.println("Done with executing all thread! The number of primes calculated :"+threads.size());
                break;
            }
            Thread th = new Thread(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                int nthPrime = calcNthPrime(input);
                System.out.printf("%sth prime number is :%s\n", input,nthPrime);
            });
            threads.add(th);
            //th.setDaemon(true);
            th.start();
        }
    }

    private static void waitForThreads(List<Thread> threads) throws InterruptedException {
        for(Thread th : threads){
            th.join();
        }
    }
    private static void printThreadStatus(List<Thread> threads){
       threads
               .forEach(thread -> {
                   System.out.printf("\nThread %s is in %s state.\n",thread.getName(),thread.getState());
               });
    }

    private static int calcNthPrime(int input){
        int ithPrime = 1;
        int nthPrime = 0;
        while(nthPrime < input){
            ithPrime++;
            if(isPrimeDetector(ithPrime)){
                nthPrime++;
            }
        }
        return ithPrime;
    }

    private static boolean isPrimeDetector(Integer input){
        if (input < 2) return false;
        if (input == 2) return true;
        if (input % 2 == 0) return false;

        int sqrt = (int) Math.sqrt(input);
        for (int i = 3; i <= sqrt; i += 2) {
            if (input % i == 0) return false;
        }
        return true;
    }
}

