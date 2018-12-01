package br.com.fatecmc.eletivaweb.framework;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Zeller
 */
public class Jeyzon {
	
	// Resolve Recursao infinita de objetos
	private Set<Object> mappedFields;
	
	public Jeyzon() {
		mappedFields = new HashSet<Object>();
	}

	public String toString(Object object) {
		if (null == object)
			return "{}";
		String result = "{ " +  this.mapFields(object.getClass(), object) + " }";
		return result;

	}

	private String mapFields(Class<?> clazz, Object obj) {
		Class<?> entity = clazz;
		String result = "";
		while (null != entity && entity != Object.class) {
			for (Field field : entity.getDeclaredFields()) {
				try {
					field.setAccessible(true);
					if (null == field.get(obj) || null != field.getDeclaredAnnotation( IgnoreJeyzon.class ) )
						continue;
					if (field.getType().isPrimitive() 
							|| field.getType() == Number.class
							|| field.getType() == String.class 
							|| field.getType() == Class.class 
							|| field.getType() == LocalDate.class
							|| field.getType() == LocalDateTime.class) {

						result += "\"" + field.getName() + "\": \"" + field.get(obj) + "\", ";
					} else if ( field.getType() == Date.class ) {
						if ( null == field.getAnnotation( Formate.class ) ) {
							result += "\"" + field.getName() + "\": \"" + field.get(obj) + "\", ";
						} else {
							result += "\"" + field.getName() + "\" : \"" + new SimpleDateFormat(field.getAnnotation(Formate.class).padrao()).format( field.get(obj) ) + "\", ";
						}
					} else if (field.getType().isArray() ) {
						if ( ! mappedFields.contains(field.get(obj) ) ) {
							mappedFields.add(field.get(obj));
							result += "\"" + field.getName() + "\": [ ";
							for (int i = 0; i < Array.getLength(field.get(obj)); i++) {
								result += "{ " + mapFields(Array.get( field.get(obj), i ).getClass(), Array.get( field.get(obj), i ) )  + " }, ";
							}
							result = Array.getLength(field.get(obj)) > 1 ? result.substring(0, result.length() - 2) + " ], " : result + " ], ";
						} else {
							result += "\"" + field.getName() + "\": [ ], ";
						}
					} else {
						if ( ! mappedFields.contains(field.get(obj) ) ) {
							mappedFields.add( field.get(obj) );
							result += "\""+ field.getName() + "\": " +"{ " + mapFields( Class.forName( field.getType().getName() ), field.get( obj ) ) + " }, ";
						} else {
							result += "\""+ field.getName() + "\": { }, ";
						}
					}
				} catch (IllegalArgumentException | IllegalAccessException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			entity = entity.getSuperclass();
		}
		result = result.substring(0, result.length() - 2);
		return result;
	}
}
