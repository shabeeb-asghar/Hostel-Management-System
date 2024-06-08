package Users_Package;

public class Student {
	private String Name;
    private String  Contact;
    private String password;
    private String  CNIC;
    private String email;
    public Student(String N, String email,String password, String c,String cnic2) {
		super();
		this.Name = N;
		this.Contact = c;
		this.password = password;
		CNIC = cnic2;
		this.email = email;
	}
	public String getName()
	{
		return Name;
	}
	public String getContact() {
		return Contact;
	}
	public void setName(String c) {
		this.Contact= c;
	}
	public String getPassword() {
		return password;
	}
    public String getCNIC() {
		return CNIC;
	}
    public void setCNIC(String C) {
		this.CNIC= C;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
    
    
}
