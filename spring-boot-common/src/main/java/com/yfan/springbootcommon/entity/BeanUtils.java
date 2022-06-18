package com.yfan.springbootcommon.entity;


import net.sf.json.JSONObject;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map;


/**
 * 对象与对象深度拷贝，包括嵌套的复合类型，可以忽略类型，只要有相通的属性名
 * 数组也可以处理，即便dest是一个空数组
 * @author huanghz
 * @version 1.0
 */
public class BeanUtils {

	private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

	/**
	 * 
	 * @param dest 目标对象
	 * @param orig 原始对象
	 * @throws Exception
	 */
	public static void copy(Object dest, Object orig) throws Exception {
		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}
		
		Class origClass = orig.getClass();
		Class destClass = dest.getClass();
		if (origClass == String.class || origClass.isPrimitive()) {
			dest = orig;
		}
		if(orig.getClass().isArray()){
			Object[] destArr = (Object[]) dest;
			Object[] origArr = (Object[]) orig;
			Class elemenClass = destArr.getClass().getComponentType();
			
			for(int i=0;i<origArr.length;i++){
				if(destArr[i]==null){
					destArr[i] = elemenClass.newInstance();
				}
				
				copy(destArr[i], origArr[i]);
			}
		}
		String classLogInfo = "origClass:"+origClass.getName()+",destClass:"+destClass.getName()+",";
		PropertyDescriptor[] origDescriptors = PropertyUtils
				.getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if ("class".equals(name)) {
				continue;
			}
			Object value = null;
			if (PropertyUtils.isReadable(orig, name)
					&& PropertyUtils.isWriteable(dest, name)) {
				try {
					value = PropertyUtils.getSimpleProperty(orig, name);
					PropertyUtils.setSimpleProperty(dest, name, value);
				} catch (IllegalArgumentException e) {
					// 类型不同
					try {
						PropertyDescriptor targetDescriptor = PropertyUtils.getPropertyDescriptor(dest, name);
						Object new_value = targetDescriptor.getPropertyType().newInstance();
						copy(new_value, value);
//						LOG.info(new_value);
						PropertyUtils.setSimpleProperty(dest, name, new_value);
					}catch(IllegalArgumentException e1){
						//
					} 
					catch (IllegalAccessException e1) {
						throw e1;
					} catch (InvocationTargetException e1) {
						throw e1;
					} catch (NoSuchMethodException e1) {
						throw e1;
					} catch (InstantiationException e1) {
						throw e1;
					}
				} catch (NoSuchMethodException e) {
					throw e;
				} catch (IllegalAccessException e) {
					throw e;
				} catch (InvocationTargetException e) {
					throw e;
				}
			}
		}
	}

	/**
	 * map转对象
	 * 使用改方法所有的空字符值会变成null对象，一般可用
	 * @param
	 * @param
	 * @return
	 * @author fanzhihui
	 * @date 2016年10月11日 下午3:24:29
	 */
	public static Object mapToObject(Map map, Object obj) {
		Class<?> clz;
		try {
			//拿到类名
			clz = Class.forName(obj.getClass().getName());
			//拿到类所有字段
			Field[] fields = clz.getDeclaredFields();
			Field field = null;
			Method thisMetd = null;
			Object type = null;
			Iterator it = map.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				for (int j = 0; j < fields.length; j++) {
					// 当前字段
					field = fields[j];
					// 设置私有属性为可访问
					field.setAccessible(true);
					if (key.equalsIgnoreCase(field.getName())) {
						// 设值
						type = field.getType();
						//拿到字段对应的set方法，通常set后面第一个字符转为大写
						thisMetd = clz.getMethod(
								"set" + firstCaseToUpper(field.getName()),
								field.getType());
						if (null != map.get(key)) {
							//匹配我们系统常用的字段属性，可以追加
							if (type == BigDecimal.class) {
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new BigDecimal(
										map.get(key).toString()) : null);
							} else if(type== Short.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new Short(
										map.get(key).toString()) : null);
							}else if(type== Integer.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new Integer(
										map.get(key).toString()) : null);
							}else if(type== Long.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new Long(
										map.get(key).toString()) : null);
							}else if(type== BigInteger.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new BigInteger(
										map.get(key).toString()) : null);
							}else if(type== Boolean.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(map
										.get(key).toString()) ? new Boolean(
										map.get(key).toString()) : null);
							}else {
								thisMetd.invoke(obj, StringUtils.isNotBlank(map.get(key).toString()) ? map.get(key): null);
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
	/**
	 * @param src
	 *            源字符串
	 * @return 字符串，将src的第一个字母转换为大写，src为空时返回null
	 */
	public static String firstCaseToUpper(String src) {
		if (src != null) {
			StringBuffer sb = new StringBuffer(src);
			sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
			return sb.toString();
		} else {
			return null;
		}
	}
	/**
	 * json转对象
	 *  使用改方法所有的空字符值会变成null对象，一般可用
	 * @param json
	 * @param obj
	 * @return
	 * @author fanzhihui
	 * @date 2016年12月8日 上午9:22:13
	 */
	public static Object jsonToObject(JSONObject json, Object obj) {
		Class<?> clz;
		try {
			//拿到类名
			clz = Class.forName(obj.getClass().getName());
			//拿到类所有字段
			Field[] fields = clz.getDeclaredFields();
			Field field = null;
			Method thisMetd = null;
			Object type = null;
			Iterator it = json.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				for (int j = 0; j < fields.length; j++) {
					// 当前字段
					field = fields[j];
					// 设置私有属性为可访问
					field.setAccessible(true);
					if (key.equalsIgnoreCase(field.getName())) {
						// 设值
						type = field.getType();
						//拿到字段对应的set方法，通常set后面第一个字符转为大写
						thisMetd = clz.getMethod(
								"set" + firstCaseToUpper(field.getName()),
								field.getType());
						if (null != json.get(key)) {
							//匹配我们系统常用的字段属性，可以追加
							if (type == BigDecimal.class) {
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new BigDecimal(
										json.get(key).toString()) : null);
							} else if(type== Short.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new Short(
										json.get(key).toString()) : null);
							}else if(type== Integer.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new Integer(
										json.get(key).toString()) : null);
							}else if(type== Long.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new Long(
										json.get(key).toString()) : null);
							}else if(type== BigInteger.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new BigInteger(
										json.get(key).toString()) : null);
							}else if(type== Boolean.class){
								thisMetd.invoke(obj, StringUtils.isNotBlank(json
										.get(key).toString()) ? new Boolean(
										json.get(key).toString()) : null);
							}else {
								thisMetd.invoke(obj, StringUtils.isNotBlank(json.get(key).toString()) ? json.get(key): null);
							}
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
