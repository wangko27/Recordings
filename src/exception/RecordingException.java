package exception;

public class RecordingException extends Exception 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RecordingException(Exception src)
	{
		super(src); 
	}
	
	public RecordingException(String message, Exception src)
	{
		super(message, src); 
	}
}
