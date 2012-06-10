package bean;

public class AuthenticationResult
{
	private String message; 
	
	public AuthenticationResult(String message)
	{
		this.message = message; 
	}
	
	public String getMessage() { return message; } 
}
