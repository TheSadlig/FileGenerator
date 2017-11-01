package filegenerator.execution.functions;

import filegenerator.filegenerator.StringUtils;

/**
 *
 * @author TheSadlig
 */
public enum RandomStringType {
    SMALL("small", "abcdefghijklmnopqrstuvwxyz0123456789"),
    MEDIUM("medium", RandomStringType.SMALL.getTypeContent() + "ïæâàNëùèû'êéçôœîé0123456789!@#$%^&*()_+}{|\".,/[]-<>éăêàABCDEFGHIJKMOPQRSTUVWXYZÏÆÂÀËÙÈÛÊÉÇÔŒÎÉÉĂÊ"),
    FULL("small", RandomStringType.MEDIUM.getTypeContent());

    private final String typeName;
    private final String typeContent;

    private RandomStringType(String typeName, String typeContent) {
        this.typeContent = typeContent += typeContent.toUpperCase();
        this.typeName = typeName;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public String getTypeContent() {
        return this.typeContent;
    }

    public static RandomStringType getRandomStringTypeEnum(String typeName) {
        if (SMALL.typeName.equals(typeName)) {
            return SMALL;
        } else if (MEDIUM.typeName.equals(typeName)) {
            return MEDIUM;
        } else if (FULL.typeName.equals(typeName)) {
            return FULL;
        }
        return null;
    }

    public String getRandomString(long size) {

        String result = "";
        int i = 0;
        byte[] bs = new byte[this.typeContent.length()];
        for (byte b : this.typeContent.getBytes()) {
            char c = (char) b;
            if (result.indexOf(c) == -1) {
                result = new String(bs);
                bs[i] = b;
                ++i;
            }
        }

        return StringUtils.generateRandomString(size, this.typeContent);
    }
}
