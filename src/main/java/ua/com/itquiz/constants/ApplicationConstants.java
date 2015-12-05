package ua.com.itquiz.constants;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Artur Meshcheriakov
 */
public interface ApplicationConstants {

    int ADMIN_ROLE = 1;
    int ADVANCED_TUTOR_ROLE = 2;
    int TUTOR_ROLE = 3;
    int STUDENT_ROLE = 4;
    HashSet<Integer> ROLES = new HashSet<>(
            Arrays.asList(new Integer[]{ADMIN_ROLE, ADVANCED_TUTOR_ROLE, TUTOR_ROLE, STUDENT_ROLE}));

}
