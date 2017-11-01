package filegenerator.execution.utils;

import com.github.javafaker.Faker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 *
 * @author TheSadlig
 */
public class FakerWrapper {

    private static HashMap<FakeValueType, List<String>> cache = new HashMap<>();
    private static Faker faker = new Faker();
    private static Long cacheSize = 10000l;
    private static Random r = new Random();

    public static String generate(FakeValueType obfuscationType) {
        return generateOrUseCache(obfuscationType).replace(",", "");
    }

    private static String generateOrUseCache(FakeValueType obfuscationType) {
        List<String> list;
        if (obfuscationType.isUnique()) {
            return createNewValue(obfuscationType);
        }

        if (cache.containsKey(obfuscationType)) {
            list = cache.get(obfuscationType);
            if (list.size() >= cacheSize) {
                return list.get(r.nextInt(list.size()));
            }
        } else {
            list = new ArrayList<>();
            cache.put(obfuscationType, list);
        }

        list.add(createNewValue(obfuscationType));
        return list.get(list.size() - 1);
    }

    public static long getCacheSize() {
        return cacheSize;
    }

    public static void setCacheSize(Long cacheSize) {
        FakerWrapper.cacheSize = cacheSize;
    }

    private static String createNewValue(FakeValueType obfuscationType) {
        switch (obfuscationType) {
        case ADDRESS:
            return faker.address().fullAddress();
        case BIC:
            return faker.finance().bic();
        case NAME:
            return faker.name().name();
        case FULLNAME:
            return faker.harryPotter().character();
        case USERNAME:
            return faker.name().username();
        case EMAIL:
            return faker.internet().emailAddress();
        case COMPANYNAME:
            return faker.company().industry();
        case BS:
            return faker.company().bs();
        case PASSWORD:
            return faker.internet().password();
        case TELEPHONE:
            return faker.phoneNumber().cellPhone();
        case LOREM50:
            return faker.lorem().fixedString(50);
        case LOREM20:
            return faker.lorem().fixedString(20);
        case FILENAME:
            return faker.file().fileName();
        case IBAN:
            return faker.finance().iban();
        case CITY:
            return faker.address().cityName();
        case COUNTRY:
            return faker.address().country();
        case COUNTRYCODE:
            return faker.address().countryCode();
        case STREET:
            return faker.address().streetAddress();
        }
        return "'NONE'";
    }
}
