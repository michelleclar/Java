package org.carl.eazy;

public class 素数之积 {
  public static void main(String[] args) {
    System.out.println(canBeRepresentedByTwoPrimes(27));
  }

  public static boolean canBeRepresentedByTwoPrimes(int num) {
    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (isPrime(i) && num % i == 0 && isPrime(num / i)) return true;
    }
    return false;
  }

  public static boolean isPrime(int num) {
    if (num <= 1) {
      return false;
    }
    for (int i = 2; i <= Math.sqrt(num); i++) {
      if (num % i == 0) return false;
    }
    return true;
  }

  public static void primeFactorization(int num) {
    for (int i = 2; i <= num; i++) {
      while (num % i == 0) {
        System.out.print(i + " ");
        num /= i;
      }
    }
  }
}
