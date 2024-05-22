package sbproject02;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Employee {
	@Value("0")
 private int id;
	@Value("chetan")
 private String name;
 @Autowired
 private Manager manager;
 @Autowired
 List<String>list;
@Override
public String toString() {
	return "Employee [id=" + id + ", name=" + name + ", list=" + list + "]";
}


}
