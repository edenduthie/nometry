package nometry.entity;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;

public class NometryEntity 
{
    public String retrieveHashKey() {throw new RuntimeException("Method not implemented");}
    public String retrieveRangeKey() {throw new RuntimeException("Method not implemented");}
    
    @Override
    public boolean equals(Object other)
    {
    	return compareProperties(other,this);
    }
    
    public boolean compareProperties(Object o1, Object o2)
    {
    	if( o1 == null || o2 == null )
    	{
    		if( o1 == null && o2 == null ) return true;
    		else return false;
    	}
    	
    	if( o1.getClass().getName() != o2.getClass().getName() ) return false;
    	
    	try 
    	{
			BeanInfo beanInfo = Introspector.getBeanInfo(o1.getClass());
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors())
			{
				Object value1 = propertyDesc.getReadMethod().invoke(o1);
				Object value2 = propertyDesc.getReadMethod().invoke(o2);
				if( value1 == null || value2 == null ) return false;
				if( !value1.equals(value2) ) return false;
			}
			return true;
		} 
    	catch (IntrospectionException e) 
    	{
			return false;
		} 
    	catch (IllegalArgumentException e) {
			return false;
		} 
    	catch (IllegalAccessException e) {
			return false;
		} 
    	catch (InvocationTargetException e) {
			return false;
		}
    }
}
