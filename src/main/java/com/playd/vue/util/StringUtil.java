package com.playd.vue.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.playd.vue.common.constants.Constants;

/**
 * The Class StringUtil.
 */
public class StringUtil {
		
	/**
	 * 조회나, 수정form에 뿌려 줄때 값이 null이면 화면에 null이라고 찍히는 것을 없애기 위해사용한다
	 * @param	obj		대상 오브젝트
	 * @return	String	Null이 아닌 문자열
	 */
	public static String NVL(Object obj){
		if(obj != null && !"null".equals(obj) && !"".equals(obj))
			return obj.toString().trim();
		else
		return "";
	}


	/**
	 * 값이 null이면 화면에 default값 리턴
	 * @param	obj		대상 오브젝트
	 * @param	value	Null일 경우 대치할 문자열
	 * @return	String	Null이 아닌 문자열
	 */
	public static String NVL(Object obj,String value){
		if(obj != null  && !"null".equals(obj) && !"".equals(obj))
			return obj.toString();
		else
			return value;
	}

	/**
	 * 값이 null이면 화면에 default값 리턴
	 * @param	obj		대상 오브젝트
	 * @param	value	Null일 경우 대치할 int타입의 정수값
	 * @return	int		Null이 아닌 정수값
	 */
	public static int NVL(Object obj,int value){
		if(obj != null && !"null".equals(obj) && !"".equals(obj)) {
			try{
				return Integer.parseInt(obj.toString());
			}catch(NumberFormatException e){
				return 0;
			}
		}else
			return value;
	}

	/**
	 * 값이 null이면 화면에 default값 리턴
	 * @param	obj		대상 오브젝트
	 * @param	value	Null일 경우 대치할 double타입의 실수값
	 * @return	double	Null이 아닌 실수값
	 */
	public static double NVL(Object obj, double value){
		if(obj != null && !"null".equals(obj) && !"".equals(obj)) {
			try{
				return Double.parseDouble(obj.toString());
			}catch(NumberFormatException e){
				return 0;
			}
		}else
			return value;
	}

	/**
	 * 빈 문자열 <code>""</code>.
	 */
	public static final String EMPTY = "";


	/**
	 * 문자열이 지정한 길이를 초과했을때 지정한길이에다가 해당 문자열을 붙여주는 메서드.
	 * @param source 원본 문자열 배열
	 * @param output 더할문자열
	 * @param slength 지정길이
	 * @return 지정길이로 잘라서 더할분자열 합친 문자열
	 */
	public static String cutString(String source, String output, int slength) {
		String returnVal = null;
		if (source != null) {
			if (source.length() > slength) {
				returnVal = source.substring(0, slength) + output;
			} else
				returnVal = source;
		}
		return returnVal;
	}

	/**
	 * 문자열이 지정한 길이를 초과했을때 해당 문자열을 삭제하는 메서드
	 * @param source 원본 문자열 배열
	 * @param slength 지정길이
	 * @return 지정길이로 잘라서 더할분자열 합친 문자열
	 */
	public static String cutString(String source, int slength) {
		String result = null;
		if (source != null) {
			if (source.length() > slength) {
				result = source.substring(0, slength);
			} else
				result = source;
		}
		return result;
	}

	/**
	 * <p>
	 * String이 비었거나("") 혹은 null 인지 검증한다.
	 * </p>
	 *
	 * <pre>
	 *  StringUtil.isEmpty(null)      = true
	 *  StringUtil.isEmpty("")        = true
	 *  StringUtil.isEmpty(" ")       = false
	 *  StringUtil.isEmpty("bob")     = false
	 *  StringUtil.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str - 체크 대상 스트링오브젝트이며 null을 허용함
	 * @return <code>true</code> - 입력받은 String 이 빈 문자열 또는 null인 경우
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>기준 문자열에 포함된 모든 대상 문자(char)를 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.remove(null, *)       = null
	 * StringUtil.remove("", *)         = ""
	 * StringUtil.remove("queued", 'u') = "qeed"
	 * StringUtil.remove("queued", 'z') = "queued"
	 * </pre>
	 *
	 * @param str  입력받는 기준 문자열
	 * @param remove  입력받는 문자열에서 제거할 대상 문자열
	 * @return 제거대상 문자열이 제거된 입력문자열. 입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String remove(String str, char remove) {
		if (isEmpty(str) || str.indexOf(remove) == -1) {
			return str;
		}
		char[] chars = str.toCharArray();
		int pos = 0;
		for (int i = 0; i < chars.length; i++) {
			if (chars[i] != remove) {
				chars[pos++] = chars[i];
			}
		}
		return new String(chars, 0, pos);
	}

	/**
	 * <p>문자열 내부의 콤마 character(,)를 모두 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.removeCommaChar(null)       = null
	 * StringUtil.removeCommaChar("")         = ""
	 * StringUtil.removeCommaChar("asdfg,qweqe") = "asdfgqweqe"
	 * </pre>
	 *
	 * @param str 입력받는 기준 문자열
	 * @return " , "가 제거된 입력문자열
	 *  입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String removeCommaChar(String str) {
		return remove(str, ',');
	}

	/**
	 * <p>문자열 내부의 마이너스 character(-)를 모두 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.removeMinusChar(null)       = null
	 * StringUtil.removeMinusChar("")         = ""
	 * StringUtil.removeMinusChar("a-sdfg-qweqe") = "asdfgqweqe"
	 * </pre>
	 *
	 * @param str  입력받는 기준 문자열
	 * @return " - "가 제거된 입력문자열
	 *  입력문자열이 null인 경우 출력문자열은 null
	 */
	public static String removeMinusChar(String str) {
		return remove(str, '-');
	}


	/**
	 * 원본 문자열의 포함된 특정 문자열을 새로운 문자열로 변환하는 메서드
	 * @param source 원본 문자열
	 * @param subject 원본 문자열에 포함된 특정 문자열
	 * @param object 변환할 문자열
	 * @return sb.toString() 새로운 문자열로 변환된 문자열
	 */
	public static String replace(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		String srcStr  = source;

		while (srcStr.indexOf(subject) >= 0) {
			preStr = srcStr.substring(0, srcStr.indexOf(subject));
			nextStr = srcStr.substring(srcStr.indexOf(subject) + subject.length(), srcStr.length());
			srcStr = nextStr;
			rtnStr.append(preStr).append(object);
		}
		rtnStr.append(nextStr);
		return rtnStr.toString();
	}

	/**
	 * 원본 문자열의 포함된 특정 문자열 첫번째 한개만 새로운 문자열로 변환하는 메서드
	 * @param source 원본 문자열
	 * @param subject 원본 문자열에 포함된 특정 문자열
	 * @param object 변환할 문자열
	 * @return sb.toString() 새로운 문자열로 변환된 문자열 / source 특정문자열이 없는 경우 원본 문자열
	 */
	public static String replaceOnce(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		if (source.indexOf(subject) >= 0) {
			preStr = source.substring(0, source.indexOf(subject));
			nextStr = source.substring(source.indexOf(subject) + subject.length(), source.length());
			rtnStr.append(preStr).append(object).append(nextStr);
			return rtnStr.toString();
		} else {
			return source;
		}
	}

	/**
	 * <code>subject</code>에 포함된 각각의 문자를 object로 변환한다.
	 *
	 * @param source 원본 문자열
	 * @param subject 원본 문자열에 포함된 특정 문자열
	 * @param object 변환할 문자열
	 * @return sb.toString() 새로운 문자열로 변환된 문자열
	 */
	public static String replaceChar(String source, String subject, String object) {
		StringBuffer rtnStr = new StringBuffer();
		String preStr = "";
		String nextStr = source;
		String srcStr  = source;

		char chA;

		for (int i = 0; i < subject.length(); i++) {
			chA = subject.charAt(i);

			if (srcStr.indexOf(chA) >= 0) {
				preStr = srcStr.substring(0, srcStr.indexOf(chA));
				nextStr = srcStr.substring(srcStr.indexOf(chA) + 1, srcStr.length());
				srcStr = rtnStr.append(preStr).append(object).append(nextStr).toString();
			}
		}
		return srcStr;
	}

	/**
	 * <p><code>str</code> 중 <code>searchStr</code>의 시작(index) 위치를 반환.</p>
	 *
	 * <p>입력값 중 <code>null</code>이 있을 경우 <code>-1</code>을 반환.</p>
	 *
	 * <pre>
	 * StringUtil.indexOf(null, *)          = -1
	 * StringUtil.indexOf(*, null)          = -1
	 * StringUtil.indexOf("", "")           = 0
	 * StringUtil.indexOf("aabaabaa", "a")  = 0
	 * StringUtil.indexOf("aabaabaa", "b")  = 2
	 * StringUtil.indexOf("aabaabaa", "ab") = 1
	 * StringUtil.indexOf("aabaabaa", "")   = 0
	 * </pre>
	 *
	 * @param str  검색 문자열
	 * @param searchStr  검색 대상문자열
	 * @return 검색 문자열 중 검색 대상문자열이 있는 시작 위치 검색대상 문자열이 없거나 null인 경우 -1
	 */
	public static int indexOf(String str, String searchStr) {
		if (str == null || searchStr == null) {
			return -1;
		}
		return str.indexOf(searchStr);
	}


	/**
	 * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
	 * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
	 * <code>returStr</code>을 반환하며, 다르면  <code>defaultStr</code>을 반환한다.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.decode(null, null, "foo", "bar")= "foo"
	 * StringUtil.decode("", null, "foo", "bar") = "bar"
	 * StringUtil.decode(null, "", "foo", "bar") = "bar"
	 * StringUtil.decode("하이", "하이", null, "bar") = null
	 * StringUtil.decode("하이", "하이  ", "foo", null) = null
	 * StringUtil.decode("하이", "하이", "foo", "bar") = "foo"
	 * StringUtil.decode("하이", "하이  ", "foo", "bar") = "bar"
	 * </pre>
	 *
	 * @param sourceStr 비교할 문자열
	 * @param compareStr 비교 대상 문자열
	 * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
	 * @param defaultStr sourceStr와 compareStr의 값이 다를 때 반환할 문자열
	 * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
	 * <br/>다르면 defaultStr을 반환한다.
	*/
	public static String decode(String sourceStr, String compareStr, String returnStr, String defaultStr) {
		if (sourceStr == null && compareStr == null) {
			return returnStr;
		}

		if (sourceStr == null && compareStr != null) {
			return defaultStr;
		}

		if (sourceStr.trim().equals(compareStr)) {
			return returnStr;
		}

		return defaultStr;
	}

	/**
	 * <p>오라클의 decode 함수와 동일한 기능을 가진 메서드이다.
	 * <code>sourStr</code>과 <code>compareStr</code>의 값이 같으면
	 * <code>returStr</code>을 반환하며, 다르면  <code>sourceStr</code>을 반환한다.
	 * </p>
	 *
	 * <pre>
	 * StringUtil.decode(null, null, "foo") = "foo"
	 * StringUtil.decode("", null, "foo") = ""
	 * StringUtil.decode(null, "", "foo") = null
	 * StringUtil.decode("하이", "하이", "foo") = "foo"
	 * StringUtil.decode("하이", "하이 ", "foo") = "하이"
	 * StringUtil.decode("하이", "바이", "foo") = "하이"
	 * </pre>
	 *
	 * @param sourceStr 비교할 문자열
	 * @param compareStr 비교 대상 문자열
	 * @param returnStr sourceStr와 compareStr의 값이 같을 때 반환할 문자열
	 * @return sourceStr과 compareStr의 값이 동일(equal)할 때 returnStr을 반환하며,
	 * <br/>다르면 sourceStr을 반환한다.
	 */
	public static String decode(String sourceStr, String compareStr, String returnStr) {
		return decode(sourceStr, compareStr, returnStr, sourceStr);
	}
	
	 /**
	 * 객체가 null인지 확인하고 null인 경우 "" 로 바꾸는 메서드
	 * @param object 원본 객체
	 * @return resultVal 문자열
	 */
	public static String isNullToString(Object object) {
		String string = "";

		if (object != null) {
			string = object.toString().trim();
		}

		return string;
	}

	/**
	* @Method Name  : isNull
	* @Description 	: 빈값인지 확인
	*
	* @param str
	* @return
	 */
	public static boolean isNull(String str) {
		return (str == null || "".equals(str.trim()));
	}


	 /**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static String nullConvert(Object src) {
		//if (src != null && src.getClass().getName().equals("java.math.BigDecimal")) {
		if (src != null && src instanceof java.math.BigDecimal) {
			return ((BigDecimal)src).toString();
		}
	
		if (src == null || src.equals("null")) {
			return "";
		} else {
			return ((String)src).trim();
		}
	}

	 /**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static String nullConvert(String src) {
		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return "";
		} else {
			return src.trim();
		}
	}

	/**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;0&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;0&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static int zeroConvert(Object src) {
		if (src == null || src.equals("null")) {
			return 0;
		} else {
			return Integer.parseInt(((String)src).trim());
		}
	}

	/**
	 *<pre>
	 * 인자로 받은 String이 null일 경우 &quot;&quot;로 리턴한다.
	 * &#064;param src null값일 가능성이 있는 String 값.
	 * &#064;return 만약 String이 null 값일 경우 &quot;&quot;로 바꾼 String 값.
	 *</pre>
	 */
	public static int zeroConvert(String src) {
		if (src == null || src.equals("null") || "".equals(src) || " ".equals(src)) {
			return 0;
		} else {
			return Integer.parseInt(src.trim());
		}
	}

	/**
	 * <p>문자열에서 {@link Character#isWhitespace(char)}에 정의된
	 * 모든 공백문자를 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.removeWhitespace(null)         = null
	 * StringUtil.removeWhitespace("")           = ""
	 * StringUtil.removeWhitespace("abc")        = "abc"
	 * StringUtil.removeWhitespace("   ab  c  ") = "abc"
	 * </pre>
	 *
	 * @param str  공백문자가 제거도어야 할 문자열
	 * @return the 공백문자가 제거된 문자열, null이 입력되면 <code>null</code>이 리턴
	 */
	public static String removeWhitespace(String str) {
		if (isEmpty(str)) {
			return str;
		}
		int sz = str.length();
		char[] chs = new char[sz];
		int count = 0;
		for (int i = 0; i < sz; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				chs[count++] = str.charAt(i);
			}
		}
		if (count == sz) {
			return str;
		}

		return new String(chs, 0, count);
	}

	/**
	 * Html 코드가 들어간 문서를 표시할때 태그에 손상없이 보이기 위한 메서드
	 *
	 * @param strString
	 * @return HTML 태그를 치환한 문자열
	 */
	public static String checkHtmlView(String strString) {
		String strNew = "";

		try {
			StringBuffer strTxt = new StringBuffer("");

			char chrBuff;
			int len = strString.length();

			for (int i = 0; i < len; i++) {
				chrBuff = strString.charAt(i);

				switch (chrBuff) {
					case '<':
						strTxt.append("&lt;");
						break;
					case '>':
						strTxt.append("&gt;");
						break;
					case '"':
						strTxt.append("&quot;");
						break;
					case 10:
						strTxt.append("<br>");
						break;
					case ' ':
						strTxt.append("&nbsp;");
						break;
					//case '&' :
						//strTxt.append("&amp;");
						//break;
					default:
						strTxt.append(chrBuff);
				}
			}
			strNew = strTxt.toString();
		} catch (Exception ex) {
			return null;
		}
		return strNew;
	}


	/**
	 * 문자열을 지정한 분리자에 의해 배열로 리턴하는 메서드.
	 * @param source 원본 문자열
	 * @param separator 분리자
	 * @return result 분리자로 나뉘어진 문자열 배열
	 */
	public static String[] split(String source, String separator) throws NullPointerException {
		String[] returnVal = null;
		int cnt = 1;

		int index = source.indexOf(separator);
		int index0 = 0;
		while (index >= 0) {
			cnt++;
			index = source.indexOf(separator, index + 1);
		}
		returnVal = new String[cnt];
		cnt = 0;
		index = source.indexOf(separator);
		while (index >= 0) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		returnVal[cnt] = source.substring(index0);

		return returnVal;
	}

	/**
	 * <p>{@link String#toLowerCase()}를 이용하여 소문자로 변환한다.</p>
	 *
	 * <pre>
	 * StringUtil.lowerCase(null)  = null
	 * StringUtil.lowerCase("")    = ""
	 * StringUtil.lowerCase("aBc") = "abc"
	 * </pre>
	 *
	 * @param str 소문자로 변환되어야 할 문자열
	 * @return 소문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String lowerCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toLowerCase();
	}

	/**
	 * <p>{@link String#toUpperCase()}를 이용하여 대문자로 변환한다.</p>
	 *
	 * <pre>
	 * StringUtil.upperCase(null)  = null
	 * StringUtil.upperCase("")    = ""
	 * StringUtil.upperCase("aBc") = "ABC"
	 * </pre>
	 *
	 * @param str 대문자로 변환되어야 할 문자열
	 * @return 대문자로 변환된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String upperCase(String str) {
		if (str == null) {
			return null;
		}

		return str.toUpperCase();
	}

	/**
	 * <p>입력된 String의 앞쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.stripStart(null, *)          = null
	 * StringUtil.stripStart("", *)            = ""
	 * StringUtil.stripStart("abc", "")        = "abc"
	 * StringUtil.stripStart("abc", null)      = "abc"
	 * StringUtil.stripStart("  abc", null)    = "abc"
	 * StringUtil.stripStart("abc  ", null)    = "abc  "
	 * StringUtil.stripStart(" abc ", null)    = "abc "
	 * StringUtil.stripStart("yxabc  ", "xyz") = "abc  "
	 * </pre>
	 *
	 * @param str 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars 제거대상 문자열
	 * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String stripStart(String str, String stripChars) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return str;
		}
		int start = 0;
		if (stripChars == null) {
			while ((start != strLen) && Character.isWhitespace(str.charAt(start))) {
				start++;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((start != strLen) && (stripChars.indexOf(str.charAt(start)) != -1)) {
				start++;
			}
		}

		return str.substring(start);
	}


	/**
	 * <p>입력된 String의 뒤쪽에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.stripEnd(null, *)          = null
	 * StringUtil.stripEnd("", *)            = ""
	 * StringUtil.stripEnd("abc", "")        = "abc"
	 * StringUtil.stripEnd("abc", null)      = "abc"
	 * StringUtil.stripEnd("  abc", null)    = "  abc"
	 * StringUtil.stripEnd("abc  ", null)    = "abc"
	 * StringUtil.stripEnd(" abc ", null)    = " abc"
	 * StringUtil.stripEnd("  abcyx", "xyz") = "  abc"
	 * </pre>
	 *
	 * @param str 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars 제거대상 문자열
	 * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String stripEnd(String str, String stripChars) {
		int end;
		if (str == null || (end = str.length()) == 0) {
			return str;
		}

		if (stripChars == null) {
			while ((end != 0) && Character.isWhitespace(str.charAt(end - 1))) {
				end--;
			}
		} else if (stripChars.length() == 0) {
			return str;
		} else {
			while ((end != 0) && (stripChars.indexOf(str.charAt(end - 1)) != -1)) {
				end--;
			}
		}

		return str.substring(0, end);
	}

	/**
	 * <p>입력된 String의 앞, 뒤에서 두번째 인자로 전달된 문자(stripChars)를 모두 제거한다.</p>
	 *
	 * <pre>
	 * StringUtil.strip(null, *)          = null
	 * StringUtil.strip("", *)            = ""
	 * StringUtil.strip("abc", null)      = "abc"
	 * StringUtil.strip("  abc", null)    = "abc"
	 * StringUtil.strip("abc  ", null)    = "abc"
	 * StringUtil.strip(" abc ", null)    = "abc"
	 * StringUtil.strip("  abcyx", "xyz") = "  abc"
	 * </pre>
	 *
	 * @param str 지정된 문자가 제거되어야 할 문자열
	 * @param stripChars 제거대상 문자열
	 * @return 지정된 문자가 제거된 문자열, null이 입력되면 <code>null</code> 리턴
	 */
	public static String strip(String str, String stripChars) {
		if (isEmpty(str)) {
			return str;
		}

		String srcStr = str;
		srcStr = stripStart(srcStr, stripChars);
	
		return stripEnd(srcStr, stripChars);
	}

	/**
	 * 문자열을 지정한 분리자에 의해 지정된 길이의 배열로 리턴하는 메서드.
	 * @param source 원본 문자열
	 * @param separator 분리자
	 * @param arraylength 배열 길이
	 * @return 분리자로 나뉘어진 문자열 배열
	 */
	public static String[] split(String source, String separator, int arraylength) throws NullPointerException {
		String[] returnVal = new String[arraylength];
		int cnt = 0;
		int index0 = 0;
		int index = source.indexOf(separator);
		while (index >= 0 && cnt < (arraylength - 1)) {
			returnVal[cnt] = source.substring(index0, index);
			index0 = index + 1;
			index = source.indexOf(separator, index + 1);
			cnt++;
		}
		returnVal[cnt] = source.substring(index0);
		if (cnt < (arraylength - 1)) {
			for (int i = cnt + 1; i < arraylength; i++) {
				returnVal[i] = "";
			}
		}

		return returnVal;
	}

	/**
	 * 문자열 A에서 Z사이의 랜덤 문자열을 구하는 기능을 제공 시작문자열과 종료문자열 사이의 랜덤 문자열을 구하는 기능
	 * @param startChr - 첫 문자
	 * @param endChr - 마지막문자
	 * @return 랜덤문자
	 * @exception MyException
	 * @see
	*/
	public static String getRandomStr(char startChr, char endChr) {

		int randomInt;
		String randomStr = null;

		// 시작문자 및 종료문자를 아스키숫자로 변환한다.
		int startInt = Integer.valueOf(startChr);
		int endInt = Integer.valueOf(endChr);

		// 시작문자열이 종료문자열보가 클경우
		if (startInt > endInt) {
			throw new IllegalArgumentException("Start String: " + startChr + " End String: " + endChr);
		}

		try {
			// 랜덤 객체 생성
			SecureRandom rnd = new SecureRandom();

			do {
				// 시작문자 및 종료문자 중에서 랜덤 숫자를 발생시킨다.
				randomInt = rnd.nextInt(endInt + 1);
			} while (randomInt < startInt); // 입력받은 문자 'A'(65)보다 작으면 다시 랜덤 숫자 발생.
				// 랜덤 숫자를 문자로 변환 후 스트링으로 다시 변환
				randomStr = (char)randomInt + "";
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
		}
		// 랜덤문자열를 리턴
		return randomStr;
	}

	/**
	 * 문자열 a에서 z사이 및 0에서 0까지의 랜덤 문자열을 지정문자길이의 문자열을 구하는 기능
	 *
	 * @param length - 문자길이
	 * @return 랜덤문자
	 */
	public static String getRandomStr(int length) {

		char[] charaters = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuilder sb = new StringBuilder("");
		Random rn = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(charaters[rn.nextInt(charaters.length)]);
		}
		return sb.toString();
	}

	/**
	 * 문자열을 다양한 문자셋(EUC-KR[KSC5601],UTF-8..)을 사용하여 인코딩하는 기능 역으로 디코딩하여 원래의 문자열을
	 * 복원하는 기능을 제공함 String temp = new String(문자열.getBytes("바꾸기전 인코딩"),"바꿀 인코딩");
	 * String temp = new String(문자열.getBytes("8859_1"),"KSC5601"); => UTF-8 에서
	 * EUC-KR
	 * @param srcString - 문자열
	 * @param srcCharsetNm - 원래 CharsetNm
	 * @param charsetNm - CharsetNm
	 * @return 인(디)코딩 문자열
	 * @exception MyException
	 * @see
	*/
	public static String getEncdDcd(String srcString, String srcCharsetNm, String cnvrCharsetNm) {

		String rtnStr = null;

		if (srcString == null)
			return null;

		try {
			rtnStr = new String(srcString.getBytes(srcCharsetNm), cnvrCharsetNm);
		} catch (UnsupportedEncodingException e) {
			rtnStr = null;
		}
		return rtnStr;
	}

	/**
	 * 특수문자를 웹 브라우저에서 정상적으로 보이기 위해 특수문자를 처리('<' -> & lT)하는 기능이다
	 * @param 	srcString 		- '<'
	 * @return 	변환문자열('<' -> "&lt"
	 * @exception MyException
	 * @see
	*/
	public static String getSpclStrCnvr(String srcString) {

		String rtnStr = null;
	
		try {
			StringBuffer strTxt = new StringBuffer("");
	
			char chrBuff;
			int len = srcString.length();
	
			for (int i = 0; i < len; i++) {
			chrBuff = srcString.charAt(i);
	
			switch (chrBuff) {
				case '<':
					strTxt.append("&lt;");
					break;
				case '>':
					strTxt.append("&gt;");
					break;
				case '&':
					strTxt.append("&amp;");
					break;
				default:
					strTxt.append(chrBuff);
				}
			}
			rtnStr = strTxt.toString();
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
		}
	
		return rtnStr;
	}

	/**
	 * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
	 *
	 * @param
	 * @return Timestamp 값
	 * @exception MyException
	 * @see
	*/
	public static String getTimeStamp() {

		String rtnStr = null;

		//문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
		String pattern = "yyyyMMddhhmmssSSS";

		try {
			SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
			Timestamp ts = new Timestamp(System.currentTimeMillis());
	
			rtnStr = sdfCurrent.format(ts.getTime());
		} catch (Exception e) {
			//e.printStackTrace();
			throw new RuntimeException(e);	// 2011.10.10 보안점검 후속조치
		}
		return rtnStr;
	}

	/**
	 * html의 특수문자를 표현하기 위해
	 * @param srcString
	 * @return String
	 * @exception Exception
	 * @see
	 */
	public static String getHtmlStrCnvr(String srcString) {

		String tmpString = srcString;

		try {
			tmpString = tmpString.replaceAll("&lt;", "<");
			tmpString = tmpString.replaceAll("&gt;", ">");
			tmpString = tmpString.replaceAll("&amp;", "&");
			tmpString = tmpString.replaceAll("&nbsp;", " ");
			tmpString = tmpString.replaceAll("&apos;", "\'");
			tmpString = tmpString.replaceAll("&quot;", "\"");
		} catch (Exception ex) {
			//ex.printStackTrace();
			throw new RuntimeException(ex);	// 2011.10.10 보안점검 후속조치
		}

		return  tmpString;
	}

	/**
	 * <p>날짜 형식의 문자열 내부에 마이너스 character(-)를 추가한다.</p>
	 * <pre>
	 *   StringUtil.addMinusChar("20100901") = "2010-09-01"
	 * </pre>
	 * @param date  입력받는 문자열
	 * @return " - "가 추가된 입력문자열
	*/
	public static String addMinusChar(String date) {
		if(date.length() == 8)
			return date.substring(0,4).concat("-").concat(date.substring(4,6)).concat("-").concat(date.substring(6,8));
		else return "";
	}

	/**
	 * YHKANG :: 대상 문자열 마지막 3자리를 ***으로 표시.
	 * @param userid 문자열 입력
	 * @return 변환된 문자열 반환.
	 */
	public static String userIdLastThreeCharsHidden(String userid) {
		StringBuffer sb = new StringBuffer();
		String targetUserId = userid;
		String resultUserId = null;
		String hiddenString = "***";

		if (targetUserId == null || targetUserId.equals("")) {
			return "";
		}

		if(userid.length() >= 4) {
			sb.append(targetUserId.substring(0, targetUserId.length()-3)).append(hiddenString);
			resultUserId = sb.toString();
		} else {
			return "";
		}
		return resultUserId;
	}

	/**
	 * String 객체에 < 로 시작해서 > 로끝나는 태그를 제거 한다.
	 * html태그 뿐 아니라 xml태그도 제거
	 * <pre>
	 * 1. 개요     :
	 * 2. 처리내용 :
	 * </pre>
	 * @param htmlStr
	 * @return
	 */
	public static String removeHtml(String htmlStr){
		return htmlStr.replaceAll("\\<.*?\\>", "");
	}

	/**
	 * 문자열을 입력된 바이트 만큼 잘라서 반환한다.
	 * 만약 마지막 문자열이 한글 1byte 만 리턴될경우 이문자열은 포함되지 않는다.
	 * @param data 원본문자열
	 * @param bytes 짜를 byte length
	 * @return String
	*/
	public static String byteCut( String data , int bytes ) {
		if( data == null || data.length() == 0 ) return data;
		char c;
		int data_total_length_ = 0;
		StringBuffer buffer = new StringBuffer();
		for( int i = 0 ; i < data.length() ; i++ ) {
			c = data.charAt( i );
			data_total_length_ += String.valueOf( c ).getBytes().length;
			if( bytes < data_total_length_ ) break;
			buffer.append( c );
		}
		return buffer.toString();
	}

	/**
	 * 문자열을 숫자로 변환
	 * @param no
	 * @return
	*/
	public static int parseInt(String no) {
		int i = 0;
		if(no != null && !no.trim().equals("")){
			i = Integer.parseInt(no);
		}
		return i;
	}
	
	/**
	 * 문자열을 숫자로 변환(숫자외 문자제거 후 변환)
	 * @param no
	 * @return int
	*/
	public static int parseIntOnlyNumber(String no) {
		
		int i = 0;		
		
		try {
			
			
			if(no != null && !no.trim().equals("")){
				
				i =  Integer.parseInt(no.replaceAll("[^0-9]",""));		
		
			}
			
		} catch (Exception e) {
			
			i = 0;
		}
		
		
		return i;
	}
	
	/**
	 * 문자열을 숫자로 변환(숫자외 문자제거 후 변환)
	 * @param no
	 * @return double
	*/
	public static double parseIntOnlyNumberDouble(String no) {
		
		double i = 0;		
		
		try {
			
			
			if(no != null && !no.trim().equals("")){
				
				i =  Double.parseDouble(no.replaceAll("[^0-9|^/.]",""));		
		
			}
			
		} catch (Exception e) {
			
			i = 0;
		}
		
		
		return i;
	}
	
	

	public static String lpad(String str, String addStr, int len) {
		String result = str;
		for(int i=0; str != null && addStr != null && str.length() < len && (str+addStr).length() <= len && result.length() < len && i<len;i++){
			result = addStr+str;
		}
		return result;
	}

	public static String replaceComma(Object obj){
		String str="";

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			str = obj.toString().trim();
		}else{
			str = "";
		}

		str =  str.replace("\"", "replace-quotes-072");  // " -> replace-quotes-072
		str =  str.replace("'", "replace-one-quotes-282");  // " -> replace-one-quotes-282

		return str;
	}

	public static String getByteString(String i_sSource, String i_sLength) {
		return	getByteString(i_sSource, Integer.parseInt(i_sLength));
	}

	public static String getByteString(String i_sSource, int i_iLength) {
		String	result	= "";

		if ((null == i_sSource) || "".equals(i_sSource)) {
			return result;
		}

		if (i_iLength <= 0) {
			return i_sSource;
		}

		try {
			char 	cTmp;
			byte[]	bTmp;
			int		nowLength	= 0;
			int		strLemgth	= 0;

			for (int i = 0 ; i < i_sSource.length(); i++ ) {
				cTmp	= i_sSource.charAt(i);
				bTmp		= ("" + cTmp).getBytes("UTF-8");
				strLemgth	= bTmp.length;

				if (strLemgth == 3)
					nowLength += 2;
				else
					nowLength += strLemgth;

				if (nowLength <= i_iLength) {
					result	+= cTmp;
				} else {
					break;
				}
			}

			if (i_sSource.length() > result.length())
				result += "...";

		} catch (Exception e) {
			result	= i_sSource;
		}

		return result;
	}

	/**
	 *  문자열 특수문자변
	 * @param str
	 * @return
	 */
	public static String replaceTag(String str) {
		//str = checkNull(str);
		StringBuffer result = new StringBuffer();
		String[] replace = new String[6];
		replace[0] = "<";
		replace[1] = ">";
		replace[2] = "'";
		replace[3] = "\"";
		replace[4] = "(";
		replace[5] = ")";
		String a = "";
		for(int i = 0; i < str.length() ; i++){
			a = str.substring(i,i+1);
			for(int j = 0 ; j < replace.length ; j++ ){
				if(a.equals(replace[j])){
					switch(j){
						case 0: a="&lt;";   break;
						case 1: a="&gt;";   break;
						case 2: a="&#39;";   break;
						case 3: a="&quot;";   break;
						case 4: a="&#40;";   break;
						case 5: a="&#41;";   break;
					}
				}
			}
			result.append( a );
		}
		str = result.toString();
		return str;
	}

	//YHKANG :: 문자열에서 모든 HTML 태그를 삭제한다.
	public static String removeHtmlTag(String i_sHTML) {
		String result = "";
		if ((null == i_sHTML) || "".equals(i_sHTML)) {
			return "";
		}

		result	= i_sHTML.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll("\r", "");
		result = result.replaceAll("&nbsp;", " ");
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요     : 숫자 1000 단위마다 콤마
	 * 2. 처리내용 : 987987 -> 987,987
	 * </pre>
	 * @param obj
	 * @return
	*/
	public static String thousandComma(Object obj){
		String str="";
		int intTemp = 0;

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			str = obj.toString().trim();
		}else{
			str = "";
		}

		if(!"".equals(str)){
			intTemp = Integer.parseInt(str);
			DecimalFormat df = new DecimalFormat("#,##0");

			str = df.format(intTemp);
		}

		return str;
	}

	public static String thousandComma(Object obj, String value){
		String str="";
		int intTemp = 0;

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			str = obj.toString().trim();
		}else{
			str = value;
		}

		if(!"".equals(str)){
			intTemp = Integer.parseInt(str);
			DecimalFormat df = new DecimalFormat("#,##0");

			str = df.format(intTemp);
		}

		return str;
	}

	public static String replaceHtml(String htmlStr){
		return htmlStr.replaceAll("\\<.*?\\>", "");
	}

	/**
	 * <pre>
	 * 1. 개요     :
	 * 2. 처리내용 : String 안의 특정 태그 제거(&lt;, &rt;..etc)
	 * </pre>
	 * @param str
	 * @return
	*/
	public static String replaceTagToStr(String str){
		str = str.replace("&lt;", "<");
		str = str.replace("&gt;", ">");
		str = str.replace("&#39;", "'");
		str = str.replace("&quot", "\"");
		str = str.replace("&#40;", "(");
		str = str.replace("&#41;", ")");
		str = str.replace("&middot;", ".");
		str = str.replace("&sim;", "~");
		str = str.replace("&hellip;", "...");
		str = str.replace("&lsquo;", "'");
		str = str.replace("&rsquo;", "'");

		return str;
	}

	public static String removeQuotation(Object obj){

		String str="";

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			str = obj.toString().trim();
		}else{
			str = "";
		}

		str =  str.replaceAll("\"", "");
		str =  str.replaceAll("'", "");

		return str;
	}

	public static String removeHtmlTagNQuotation(Object obj){
		String result="";

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			result = obj.toString().trim();
		}else{
			result = "";
		}

		result	= result.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
		result = result.replaceAll(">", "&gt;");
		result = result.replaceAll("<", "&lt;");
		result = result.replaceAll("\r", "");
		result = result.replaceAll("&nbsp;", " ");
		result =  result.replaceAll("\"", "");
		result =  result.replaceAll("'", "");

		return result;
	}

	/**
	 * 대소문자 변화 String 변환
	*/
	public static String convert(String str) {
		String result="";

		for(char c : str.toCharArray()){
			//대문자 일경우 소문자로 변경
			if( Character.isUpperCase(c) ){
				result+= Character.toLowerCase(c);
			//소문자일 경우 대문자로 변경
			}else if(Character.isLowerCase(c)){
				result+= Character.toUpperCase(c);
			//알파벳이 아닌경우 그냥 출력
			}else{
				result+= c;
			}
		}
		return result;
	}

	/**
	 * <pre>
	 * 1. 개요     :
	 * 2. 처리내용 :
	 * </pre>
	 * @param str
	 * @return boolean
	*/
	public static boolean isNumberCheck(String str){
		try{
			Double.parseDouble(str);
			return true;
		}catch(NumberFormatException e){
			return false;
		}
	}

	/**
	 * <pre>
	 * 1. 개요     : 파라미터로 받은 obj안의  " 와' 를 공백으로 치환
	 * 2. 처리내용 :
	 * </pre>
	 * @param obj
	 * @return
	*/
	public static String replaceQuotes(Object obj){
		String str="";

		if(obj != null && !"null".equals(obj) && !"".equals(obj)){
			str = obj.toString().trim();
		}else{
			str = "";
		}

		str =  str.replace("\"", "");
		str =  str.replace("'", "");

		return str;
	}

	/**
	 * <pre>
	 * 1. 개요     : parameter가 특정 대상범위에 있는지 체크 한다.
	 * 2. 처리내용 : ex) String oriParam = "a";
	 *  String[] compare = {"a", "b", "c", "d"};
	 *  이때 oriParamdl compare중 하나인지 확인
	 * </pre>
	 * @param oriParam
	 * @param compareStrings
	 * @return
	 */
	public static boolean stringIn(String oriParam, String... compareStrings ){
		if(isEmpty(oriParam) || compareStrings == null || compareStrings.length == 0){
			return false;
		}
		for(int i=0;i<compareStrings.length;i++){
			if(oriParam.equals(compareStrings[i])) return true;
		}
		return false;
	}

	public static String setCookie(HttpServletResponse response,String id, String value, String domain, int expires){
		Cookie cookie = new Cookie(id, value);
		cookie.setPath("/");
		cookie.setDomain(domain);
		cookie.setMaxAge(expires); //100분
		response.addCookie(cookie);
		
		return value;
	}

	public static String setCookie(HttpServletResponse response,String id, String value, int expires){
		Cookie cookie = new Cookie(id, value);
		cookie.setPath("/");
		cookie.setMaxAge(expires); //100분
		response.addCookie(cookie);
		
		return value;
	}

	public static void delCookie(HttpServletResponse response,String id){
		Cookie cookie = new Cookie(id, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);	 
	}

	public static String getCookie(HttpServletRequest request, String pm_name){
		String sCookie = null;

		Cookie[] cookies = request.getCookies();

		if(cookies != null) {
			for(int i=0; i < cookies.length; i++){
				if(cookies[i].getName().equals(pm_name)){
					sCookie = cookies[i].getValue();
					break;
				}
			}
		}

		return sCookie;
	}
	
	/**
	 * hex to byte[] : 16진수 문자열을 바이트 배열로 변환한다.
	 *
	 * @param hex    hex string
	 * @return
	*/
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 * @param ba byte[]
	 * @return
	*/
	public static String byteArrayToHex(byte[] ba) {
		if (ba == null || ba.length == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();
	}
	
	/**
	 * AES 방식의 암호화
	 *
	 * @param message
	 * @return
	 * @throws Exception
	*/
	public static String encrypt(String message) throws Exception {
		// use key coss2
		javax.crypto.spec.SecretKeySpec skeySpec = new javax.crypto.spec.SecretKeySpec(Constants.aesKey.getBytes(), "AES");
		// Instantiate the cipher
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
		cipher.init(javax.crypto.Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(message.getBytes());
		return byteArrayToHex(encrypted);
	}
	
	/**
	 * AES 방식의 복호화
	 * @param message
	 * @return
	 * @throws Exception
	*/
	public static String decrypt(String encrypted) throws Exception {
		if(encrypted==null) return "";
		// use key coss2
		javax.crypto.spec.SecretKeySpec skeySpec = new javax.crypto.spec.SecretKeySpec(Constants.aesKey.getBytes(), "AES");
		javax.crypto.Cipher cipher = javax.crypto.Cipher.getInstance("AES");
		cipher.init(javax.crypto.Cipher.DECRYPT_MODE, skeySpec);
		byte[] original = cipher.doFinal(hexToByteArray(encrypted));
		String originalString = new String(original);
		return originalString;
	}
	
	/**
	 * SHA256 방식의 암호화
	 * @param msg
	 * @return
	 */
	public static String encryptSha256(String msg)  {
		String sRtn = null;
		try {
		    MessageDigest digest = MessageDigest.getInstance("SHA-256");
		    digest.reset();
		    digest.update(msg.getBytes( "UTF-8" )); // 인코딩으로 변환
            // byte[]  를 문자열로 변환하는 부분
		    sRtn = String.format("%064x", new java.math.BigInteger(1, digest.digest()));
		} catch (Exception e) {
			sRtn= "";
		    e.printStackTrace();
		}
		return sRtn;
	}
	
	
	//csv 콤마로 구분하여 짜르기
	//안에 내용중 콤마가 있는 경우는 " 쌍따옴표로 감싸기 떄문에, 이 처리를 해주엇다.
	public static String[] csvSplit(String str){
		String[] resultStr=null;
		String result="";
		
		String[] a=str.split(",");
		int cnt=0;
		String temp="";
		for(int i=0;i<a.length;i++){
			if(a[i].indexOf("\"")==0){
				if(a[i].lastIndexOf("\"")==a[i].length()-1){
					result+=a[i].replaceAll("\"","");
				}else{
					cnt++;
					temp+=a[i].replaceAll("\"","");
				}
			}else if(a[i].lastIndexOf("\"")==a[i].length()-1){
				if(cnt>0){
					result+=temp+","+a[i].replaceAll("\"","");
					cnt=0;
					temp="";
				}
			}else{
				if(cnt>0){
					cnt++;
					temp+=","+a[i].replaceAll("\"","");
				}else{
					result+=a[i];
				}
			}
			if(i!=a.length-1 && cnt==0)result+="|,|";
		}
		
	//	System.out.println(result);
		resultStr=result.split("\\|,\\|");
		
		
		return resultStr;
	}
		
	//csv 탭으로 구분하여 짜르기
	public static String[] csvSplitTab(String str){
		
		String result="";
		
		String[] a=str.split("\t");
		String[] resultStr=new String[a.length];
		for(int i=0;i<a.length;i++){
			if(a[i].indexOf("\"")==0 && a[i].lastIndexOf("\"")==a[i].length()-1){ // 양쪽 끝에 쌍따옴표 제거
				resultStr[i]=a[i].replaceAll("\"","");	 		
			}else{
				resultStr[i]=a[i];
			}
		}
		
		return resultStr;
	}
	
	public static String getUrlPath(String url){      
		String match = "((\\/[^\\s/\\/]+)*)?";
		url =url.replaceAll(match, " ");
		return url;
	}
 	
	public static String getGausTitle() {
		String resultStr="";
		try {
			Properties props = PropertiesLoaderUtils.loadAllProperties("property/system.properties");
			resultStr = props.getProperty("system.default.gausDomain");
			return resultStr;
		} catch(Exception ex) {
			return "GAUS";
		}
		
	}
	
	public static String getRemoteIp(HttpServletRequest request) {
		String clientIp = request.getHeader("HTTP_X_FORWARDED_FOR");
		if(null == clientIp || clientIp.length() == 0
		   || clientIp.toLowerCase().equals("unknown")){
		  clientIp = request.getHeader("REMOTE_ADDR");
		}
		 
		if(null == clientIp || clientIp.length() == 0
		   || clientIp.toLowerCase().equals("unknown")){
		  clientIp = request.getRemoteAddr();
		}
		return clientIp;
	}
	
	// XSS 방지 스크립트 
    public static String cleanXSS(String value) {
		value = value.replaceAll("<", "& lt;").replaceAll(">", "& gt;");
		value = value.replaceAll("\\(", "& #40;").replaceAll("\\)", "& #41;");
		value = value.replaceAll("'", "& #39;");
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("script", "");
		return value;
	}


	// HttpURLConnection를 실행하는 함수
	public static void restPost(HttpServletRequest request, String requestUrl, JSONObject jsonObject, String reqMethod) {
		try {
			
			
			String bearerToken = request.getHeader("Authorization");
			String domain =request.getRequestURL().toString().replace(request.getRequestURI(), "");
			String apiURL = domain+requestUrl;
			
			String body = jsonObject.toString();
			
			String header = bearerToken;
			URL url = new URL(apiURL);
			
			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setRequestMethod(reqMethod);
			con.setRequestProperty("Authorization", header);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept-Charset", "UTF-8");
			// post request
			
			con.setDoOutput(true);
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.write(body.getBytes());
			wr.flush();
			wr.close();
			int responseCode = con.getResponseCode();
			BufferedReader br;
			if(responseCode==200) { // 정상 호출
			    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
			} else {  // 에러 발생
			    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
			}
			String inputLine;
			StringBuffer returnStr = new StringBuffer();
			while ((inputLine = br.readLine()) != null) {
				returnStr.append(inputLine);
			}
			br.close();
			System.out.println(returnStr.toString());
			
		} catch (Throwable e) {
			System.out.println(e);
		}
	}
	
	//valid chk function
	


	public static boolean validChk(Object obj, String variableNm) throws Exception {
		
		boolean returnVal = false;
		if(obj instanceof Integer) {
			if((int)obj == 0) {
				throw new Exception(variableNm+" is empty");
			} else {
				returnVal = true;
			}
		} else if(obj instanceof Long) {
			if((long)obj == 0) {
				throw new Exception(variableNm+" is empty");
			} else {
				returnVal =true;
			}
		} else if(obj instanceof Float) {
			if((float)obj == 0) {
				throw new Exception(variableNm+" is empty");
			} else {
				returnVal = true;
			}
		} else if(obj instanceof String) {
			if(obj == null || "".equals(obj)) {
				throw new Exception(variableNm+" is empty");
			} else {
				returnVal = true;
			}
		}
		return returnVal;
	}

	
	public static HashMap<String, Object> getUserToken(HttpServletRequest request){
		HashMap <String, Object> returnMap = new HashMap<String, Object>();
		String access_token = request.getHeader("Authorization");
			
		if(access_token !=null) {
			String[] access_arr= access_token.split(" ");
			if(access_arr.length==2) {
				String access_type=access_arr[0];
				String access_value=access_arr[1];
				returnMap.put("access_type", access_type);
				returnMap.put("access_value", access_value);	
			}			
		}

		return returnMap;
	}

	
	//y,n으로만 입력될 경우 기본값세팅(값이 비었을경우 : n처리)
	public static String defaultStrYn(String strYn) {
		if(StringUtil.isEmpty(strYn)){
			return "n";
		}
		
		if(!"y".equals(strYn)&&!"n".equals(strYn)) {
			return "n";
		}
		return strYn;
	}

	// 날짜의 분기값 구하기
	public static int getDateQuarter(String yyyyMMdd) {
		String strMonth = yyyyMMdd.substring(4,6);
		int quarter = 0;
		double quarter_cal = (double) Double.parseDouble(strMonth)/ 3;
		quarter = (int)Math.ceil(quarter_cal);
		return quarter;
	}

	//분기의 마지막날 구하기
	public static String getDateQuarterEndDate(String yyyyMMdd) {
		String quaterEndDate = "";
		String strYear = yyyyMMdd.substring(0,4);
		String strMonth = yyyyMMdd.substring(4,6);
		int quarter = 0;
		double quarter_cal = (double) Double.parseDouble(strMonth)/ 3;
		quarter = (int)Math.ceil(quarter_cal);

		switch(quarter){
			case 1:
				quaterEndDate = strYear + "0331";
				break;
			case 2:
				quaterEndDate = strYear + "0630";
				break;
			case 3:
				quaterEndDate = strYear + "0930";
				break;
			case 4:
				quaterEndDate = strYear + "1231";
				break;
			default:
				break;
		}
		return quaterEndDate;
	}

	//두 날짜 사이의 일수 구하기
	public static int getBetweenDays(String startDateStr, String endDateStr) {
		int diffDays = 0;
		if(!"".equals(startDateStr) && !"".equals(endDateStr)){

			int startYear = Integer.parseInt(startDateStr.substring(0,4));
			int startMonth = Integer.parseInt(startDateStr.substring(4,6));
			int startDay = Integer.parseInt(startDateStr.substring(6,8));

			int endYear = Integer.parseInt(endDateStr.substring(0,4));
			int endMonth = Integer.parseInt(endDateStr.substring(4,6));
			int endDay = Integer.parseInt(endDateStr.substring(6,8));

			LocalDate startDate = LocalDate.of(startYear, startMonth, startDay);
			LocalDate endDate = LocalDate.of(endYear, endMonth, endDay);
			
			diffDays = (int)ChronoUnit.DAYS.between(startDate, endDate);
			

		}
		return diffDays;
	}
	


	
}
