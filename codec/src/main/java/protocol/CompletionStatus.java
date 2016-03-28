package protocol;

public class CompletionStatus implements CompletionCallback {
	private boolean completed = false;
	
	public boolean isCompleted() {
		return completed;
	}

	@Override
	public void completed(boolean b) {
		completed = b;
	}
	
}