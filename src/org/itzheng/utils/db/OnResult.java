package org.itzheng.utils.db;

public interface OnResult {
	void onSuccess(String result);

	void onError(Exception e);

}
