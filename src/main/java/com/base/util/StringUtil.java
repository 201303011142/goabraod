package com.base.util;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.persistence.Table;

import org.omg.CORBA.portable.ApplicationException;
import org.springframework.core.io.ClassPathResource;

/**
 * 字符操作共通类.
 * 
 * @author weidong
 * 
 */
public class StringUtil {
	/**
	 * 将数组中的字符串转换成可供子查询的字符串,格式: '1','2','3','4'.
	 * 
	 * @param ids
	 * @return 指定格式的字符串
	 * @author weidong
	 */
	public static final String changeIdsArrToSubQueryStr(String[] ids) {
		String tempStr = "";
		if (ids == null || ids.length == 0) {
			return "''";
		}
		for (int i = 0; i < ids.length; i++) {
			if (i != ids.length - 1) { // 需要加,
				tempStr = tempStr + "'" + ids[i] + "',";
			} else {
				tempStr = tempStr + "'" + ids[i] + "'";
			}
		}
		return tempStr;
	}

	/**
	 * 将集合中的字符串转换成可供子查询的字符串,格式: 1,2,3,4
	 * 
	 * @param idsLst
	 *            存放ID的集合
	 * @return 指定格式的字符串
	 * @author weidong
	 */
	public static final String changeIdsListToSubQueryStr(List<String> idsLst) {
		String tempStr = "";
		for (int i = 0; i < idsLst.size(); i++) {
			if (i != idsLst.size() - 1) { // 需要加,
				tempStr = tempStr + "'" + idsLst.get(i) + "',";
			} else {
				tempStr = tempStr + "'" + idsLst.get(i) + "'";
			}
		}
		if ("".equals(tempStr)) {
			return "''";
		}
		return tempStr;
	}

	/**
	 * 判断字符串是否为空或NULL.
	 * 
	 * @param str
	 * @author weidong
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim())
				|| "undefined".equals(str.trim())) { // 为空
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 判断字符串数组是否为空或NULL.
	 * 
	 * @param str
	 * @author weidong
	 */
	public static boolean isStrArrEmpty(String[] arr) {
		if (arr == null || arr.length == 0) { // 为空
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 小数格式化.
	 * 
	 * @param f
	 *            要格式化的数字
	 * @param targetLen
	 *            需要格式化的位数
	 * @author weidong
	 */
	public static String formatFloatNum(Float f, Integer targetLen) {
		DecimalFormat format = null;
		if (targetLen == 0) { // 格式化为整数
			format = new DecimalFormat("#");
		} else {
			String s = "";
			for (int i = 0; i < targetLen; i++) {
				s += "#";
			}
			format = new DecimalFormat("#." + s);
		}
		return format.format(f);
	}

	/**
	 * 根据实体类名称获取所映射的表名.
	 * 
	 * @param entityClass
	 *            实体类
	 * @author weidong
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getTableNameByEntityClass(Class entityClass) {
		String tableName = "";
		Annotation[] annotations = entityClass.getAnnotations();
		for (Annotation an : annotations) {
			if (an instanceof Table) {
				tableName = ((Table) an).name();
				break;
			}
		}
		return tableName;
	}

	/**
	 * 从properties文件中读取指定KEY值.
	 * 
	 * @param propertiesFilePath
	 * @param key
	 * @return
	 * @throws Exception
	 * @author weidong
	 */
	public static String getValueFromProperties(String propertiesFilePath,
			String key) throws Exception {
		Properties prop = new Properties();
		try {
			ClassPathResource cr = new ClassPathResource(propertiesFilePath);
			InputStream in = cr.getInputStream();
			prop.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String value = prop.getProperty(key);
		return value;
	}

	/**
	 * 判断参数是否为整数.
	 * 
	 * @author weidong
	 */
	public static boolean isInteger(String param) {
		try {
			Integer.parseInt(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * 判断参数是否为浮点型.
	 * 
	 * @author weidong
	 */
	public static boolean isFloat(String param) {
		try {
			Float.parseFloat(param);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public static String isnull(String param) {
		if (isEmpty(param)) {
			return "";
		} else {
			return param;
		}
	}

	/**
	 * 去除SQL特殊字符.
	 * 
	 * @param paramStr
	 *            要过滤的字符串
	 * @author weidong
	 */
	public static String filterSQLCharacter(String paramStr) {
		if (!StringUtil.isEmpty(paramStr)) {
			return paramStr.replace("'", "").replace("*", "");
		} else {
			return null;
		}
	}

	public static String valueOf(Object paramStr) {
		if (paramStr == null) {
			return "";
		} else {
			return String.valueOf(paramStr);
		}
	}

	/**
	 * 
	 * @param paramStr
	 * @param sub
	 * @return
	 */
	public static String subString(Object paramStr, int sub) {
		String str = valueOf(paramStr);
		if (isEmpty(str) || str.length() < sub) {
			return str;
		} else {
			return str.substring(0, sub);
		}
	}

	/**
	 * 转义查询中的特殊字符(主要处理',_,%,[几个特殊字符).
	 * 
	 * @return 转义后的字符
	 * @author weidong
	 */
	public static String transferQueryLikeStr(String param) {
		if (!StringUtil.isEmpty(param)) {
			// 注意以下顺序,[的替换始终是放在最上面, 否则下面替换好的带[]的又会被替换掉了
			param = param.replace("[", "[[]");
			param = param.replace("'", "''");
			param = param.replace("_", "[_]");
			param = param.replace("%", "[%]");
		}
		return param;
	}

	/**
	 * 生成六位数字的随机数.
	 * 
	 * @param args
	 */
	public static String generateSixValidateCode() {
		int sixNum = new java.util.Random().nextInt(1000000);
		String resultCode = String.format("%06d", sixNum);
		return resultCode;
	}

	public static void validateNullException(String value, String errorMsg)
			throws ApplicationException {
		if (StringUtil.isEmpty(value)) { // 值为空
			throw new ApplicationException(errorMsg, null);
		}
	}


	public static final String createUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}


/*
 * 获取随机短信验证码
 */
	public static String getRandStr(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 26) + 'a');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	public static String getRandNum(int charCount) {
		String charValue = "";
		for (int i = 0; i < charCount; i++) {
			char c = (char) (randomInt(0, 10) + '0');
			charValue += String.valueOf(c);
		}
		return charValue;
	}

	public static int randomInt(int from, int to) {
		Random r = new Random();
		return from + r.nextInt(to - from);
	}
}
