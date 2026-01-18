package functions.miscellaneous;


import net.datafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class RandomMethods {
    private final Random rnd;
    private final Faker faker = new Faker(new Locale("en"));

    public RandomMethods(){
        this.rnd = new Random();
    }

    public String firstName() { return faker.name().firstName(); }

    public String lastName() {
        return faker.name().lastName();
    }

    public String company() {
        return faker.company().name();
    }

    public String job(){ return faker.job().position();}

    public String additionalInfo(){
        return faker.chuckNorris().fact();
    }

    public String street() {
        return faker.address().streetAddress();
    }

    public String complement() {
        return faker.address().secondaryAddress();
    }

    public String city() {
        return faker.address().city();
    }

    public String zipCode() { return numberInString(5); } // don't use faker for this one because the zip format is in english

    public String birthYear() { return String.valueOf(faker.number().numberBetween(1900,1995)); }

    public String futureYear() { return String.valueOf(faker.number().numberBetween(2024,2034)); }

    public int month() {
        return faker.number().numberBetween(01,12);
    }

    public String day() { return String.valueOf(faker.number().numberBetween(1,28)); }

    public String cellPhoneNumber() { return faker.phoneNumber().cellPhone(); }

    public String string(int length) {
        String baseString = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        while(sb.length() < length){
            int rndIndex = rnd.nextInt(baseString.length());
            sb.append(baseString.charAt(rndIndex));
        }
        return sb.toString();
    }

    public String numberInString(int length){
        String baseString = "0123456789";
        StringBuilder sb = new StringBuilder();
        while(sb.length()<length){
            int rndIndex = rnd.nextInt(baseString.length());
            sb.append(baseString.charAt(rndIndex));
        }
    return sb.toString();
    }



}
