package ua.com.itquiz.constants;

/**
 * 
 * @author Artur Meshcheriakov
 */
public enum Roles {

    ADMIN_ROLE(1),
    ADVANCED_TUTOR_ROLE(2),
    TUTOR_ROLE(3),
    STUDENT_ROLE(4);

    private int id;

    private Roles(int id) {
	this.id = id;
    }

    public int getID() {
	return id;
    }

}
