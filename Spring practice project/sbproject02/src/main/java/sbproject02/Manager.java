package sbproject02;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component(value="m")
public class Manager {
	@Value("${id}")
	private int id;
	@Value("${name}")
	private String name;
	@Autowired(required = false)
	private Employee emp;
	
	




	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", emp=" + emp + "]";
	}






	public static void main(String [] args) {
		
		ApplicationContext ac=new AnnotationConfigApplicationContext(Myconfig.class);
		
	        Manager manager=ac.getBean("m", Manager.class);
	   System.out.println(manager);
		
	}
	

}
