package sbproject02;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan(basePackages = "sbproject02")
@PropertySource("a.properties")
public class Myconfig {

	@Bean
	public List<String> getList(){
	
	return Arrays.asList("vipin","chetan");
	}
}
