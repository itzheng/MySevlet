package org.itzheng.table;
public class Users {
	public static String TABLE_NAME = "Users";
	public static String PRIMARY_KEY = "fID";
	public static final String fUserName = "fUserName";
	public static final String fPassword = "fPassword";

//	public static CheckResult checkRegister(Map<String, Object> fieldsValues) {
//		CheckResult checkResult = new CheckResult();
//		if (ArrayUtils.isEmpty(fieldsValues)) {
//			checkResult.code = CheckResult.Code.PARAM_IS_EMPTY;
//			return checkResult;
//		}
//		if (StrUtils.isEmpty(fieldsValues.get(fUserName))) {
//			checkResult.code = CheckResult.Code.PARAM_IS_EMPTY;
//			// 用户名不能为空
//			return checkResult;
//		}
//		return checkResult;
//	}
}
