package net.itquiz.constants;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Artur Meshcheriakov
 */
public interface ApplicationConstants {

    short ADMIN_ROLE = 1;
    short ADVANCED_TUTOR_ROLE = 2;
    short TUTOR_ROLE = 3;
    short STUDENT_ROLE = 4;
    HashSet<Short> ROLES = new HashSet<>(
            Arrays.asList(new Short[]{ADMIN_ROLE, ADVANCED_TUTOR_ROLE, TUTOR_ROLE, STUDENT_ROLE}));

}
