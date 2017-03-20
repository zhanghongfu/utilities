package dolf.zhang.utilities.string;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;


public class ObjectUtilities {
    private ObjectUtilities() {
    }



    public static boolean isDifferent(Object o1, Object o2) {
        return !(o1 == null && o2 == null) && (o1 == null || !o1.equals(o2));
    }

    public static boolean hasEmpty(Object... objects) {
        for (Object obj : objects) {
            if (null == obj) {
                return true;
            }
            if (obj instanceof Collection && CollectionUtils.isEmpty((Collection) obj)) {
                return true;
            } else if (obj instanceof Map && MapUtils.isEmpty((Map) obj)) {
                return true;
            } else if (obj instanceof String && StringUtils.isEmpty((String) obj)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        isTrue(false, "fk");
    }




    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

}
