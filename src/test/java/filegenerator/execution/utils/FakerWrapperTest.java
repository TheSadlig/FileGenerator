package filegenerator.execution.utils;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author TheSadlig
 */
public class FakerWrapperTest {

    private void checkGivenValueType(FakeValueType valueType) {
        String generated = FakerWrapper.generate(valueType);
        Assert.assertNotNull(generated);
        Assert.assertNotEquals(generated, FakerWrapper.generate(valueType));
    }

    @Test
    public void overflowCacheSize() {
        FakerWrapper.setCacheSize(1l);
        Assert.assertEquals(1, FakerWrapper.getCacheSize());

        String generated1 = FakerWrapper.generate(FakeValueType.EMAIL);
        String generated2 = FakerWrapper.generate(FakeValueType.BIC);
        String generated3 = FakerWrapper.generate(FakeValueType.BIC);

        Assert.assertNotEquals(generated1, generated2);
        Assert.assertNotEquals(generated1, generated3);
        Assert.assertEquals(generated2, generated3);

        FakerWrapper.setCacheSize(1000l);
    }

    @Test
    public void generateAddressTest() {
        checkGivenValueType(FakeValueType.ADDRESS);
    }

    @Test
    public void generateBICTest() {
        checkGivenValueType(FakeValueType.BIC);
    }

    @Test
    public void generateBSTest() {
        checkGivenValueType(FakeValueType.BS);
    }

    @Test
    public void generateCITYTest() {
        checkGivenValueType(FakeValueType.CITY);
    }

    @Test
    public void generateCompanyNameTest() {
        checkGivenValueType(FakeValueType.COMPANYNAME);
    }

    @Test
    public void generateCountryTest() {
        checkGivenValueType(FakeValueType.COUNTRY);
    }

    @Test
    public void generateCountryCodeTest() {
        checkGivenValueType(FakeValueType.COUNTRYCODE);
    }

    @Test
    public void generateEmailTest() {
        checkGivenValueType(FakeValueType.EMAIL);
    }

    @Test
    public void generateFileNameTest() {
        checkGivenValueType(FakeValueType.FILENAME);
    }

    @Test
    public void generateFullNameTest() {
        checkGivenValueType(FakeValueType.FULLNAME);
    }

    @Test
    public void generateIbanTest() {
        checkGivenValueType(FakeValueType.IBAN);
    }

    @Test
    public void generateLorem20Test() {
        checkGivenValueType(FakeValueType.LOREM20);
    }

    @Test
    public void generateLorem50Test() {
        checkGivenValueType(FakeValueType.LOREM50);
    }

    @Test
    public void generateNameTest() {
        checkGivenValueType(FakeValueType.NAME);
    }

    @Test
    public void generatePasswordTest() {
        checkGivenValueType(FakeValueType.PASSWORD);
    }

    @Test
    public void generateStreetTest() {
        checkGivenValueType(FakeValueType.STREET);
    }

    @Test
    public void generateTelephoneTest() {
        checkGivenValueType(FakeValueType.TELEPHONE);
    }

    @Test
    public void generateUsernameTest() {
        checkGivenValueType(FakeValueType.USERNAME);
    }
}
