package clase.datos;

import java.util.ArrayList;

public class netbook {
private ArrayList<user> users;
public netbook(ArrayList<user> users) {
	this.users = users;
}
public ArrayList<user> getUsers() {
	return users;
}
public void setUsers(ArrayList<user> users) {
	this.users = users;
}
}
