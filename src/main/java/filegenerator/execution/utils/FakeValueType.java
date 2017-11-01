package filegenerator.execution.utils;

/**
 *
 * @author TheSadlig
 */
public enum FakeValueType {
    NAME,
    ADDRESS,
    BIC,
    FULLNAME,
    USERNAME(true),
    PASSWORD,
    EMAIL,
    COMPANYNAME,
    TELEPHONE,
    LOREM50,
    LOREM20,
    FILENAME,
    IBAN,
    CITY,
    COUNTRY,
    COUNTRYCODE,
    STREET,
    BS;

    // if true, no caching will take place to avoid duplicates
    private final boolean isUnique;

    private FakeValueType() {
        this.isUnique = false;
    }

    private FakeValueType(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean isUnique() {
        return isUnique;
    }
}
