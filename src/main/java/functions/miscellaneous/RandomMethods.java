package functions.miscellaneous;


import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class RandomMethods {

    private static final ThreadLocal<Random> rnd =
            ThreadLocal.withInitial(Random::new);

    private static final ThreadLocal<Faker> faker =
            ThreadLocal.withInitial(() -> new Faker(Locale.ENGLISH));


    public static String firstName() { return faker.get().name().firstName(); }

    public static String lastName() {
        return faker.get().name().lastName();
    }

    public static String email() {
        return faker.get().internet().emailAddress();
    }

    public static String company() {
        return faker.get().company().name();
    }

    public static String job(){ return faker.get().job().position();}

    public static String additionalInfo(){
        return faker.get().chuckNorris().fact();
    }

    public static String street() {
        return faker.get().address().streetAddress();
    }

    public static String complement() {
        return faker.get().address().secondaryAddress();
    }

    public static String city() {
        return faker.get().address().city();
    }

    public static String zipCode() { return numberInString(5); } // don't use faker for this one because the zip format is in english

    public static String birthYear() { return String.valueOf(faker.get().number().numberBetween(1900,1995)); }

    public static String futureYear() { return String.valueOf(faker.get().number().numberBetween(2024,2034)); }

    public int month() {
        return faker.get().number().numberBetween(01,12);
    }

    public static String day() { return String.valueOf(faker.get().number().numberBetween(1,28)); }

    public static String cellPhoneNumber() { return faker.get().phoneNumber().cellPhone(); }

    public static String string(int length) {
        String baseString = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length){
            int rndIndex = rnd.get().nextInt(baseString.length());
            sb.append(baseString.charAt(rndIndex));
        }
        return sb.toString();
    }

    public static String numberInString(int length){
        String baseString = "0123456789";
        StringBuilder sb = new StringBuilder();
        while(sb.length()<length){
            int rndIndex = rnd.get().nextInt(baseString.length());
            sb.append(baseString.charAt(rndIndex));
        }
    return sb.toString();
    }



}
