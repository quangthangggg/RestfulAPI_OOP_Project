package restautotest;

public abstract class APINeedTesting {
	protected int codeResponse;
	protected String messageResponse;
	protected String dataResponse;
	
	public abstract String creRequest(String... request);
	public abstract void callAPI(String request);
}
