package org.itzheng.servlet.global;

public class CheckResult {

	public int code;
	public int msg;

	public boolean isSuccess() {
		return code == Code.SUCCESS;
	}

	public static class Code {
		public static int SUCCESS = 0;
		/**
		 * 参数为空
		 */
		public static int PARAM_IS_EMPTY = -1;
	}
}