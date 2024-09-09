package unius.core_uuid.util;

import java.util.UUID;

public class UuidUtils {

    private UuidUtils() {}

    public static String generateUuid() {
        String initUuid = UUID.randomUUID().toString();

        StringBuilder stringBuilder = new StringBuilder();

        for(int i=0; i<initUuid.length(); i++) {
            if(initUuid.charAt(i) == '-') {
                int randNum = (int) (Math.random() * 26);

                stringBuilder.append((char)('a' + randNum));
            } else {
                stringBuilder.append(initUuid.charAt(i));
            }
        }

        return stringBuilder.toString();
    }
}
