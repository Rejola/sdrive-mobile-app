package com.creatah.automobile.dealer.listener;

public interface UiUpdateListener {
	void OnUpdateUI(Object... object);
	
	void OnFailure(String errorMessage);
}
