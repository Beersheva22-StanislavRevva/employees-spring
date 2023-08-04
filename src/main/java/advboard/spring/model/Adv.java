package advboard.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Adv {
	private String id;
	private String name;
	private String category;
	private int price;
	private String catFields;

}
