package util;

public interface HttpCallbackListener {
	
	void onFinish(String reponse);
	
	void onError(Exception ex);

}
