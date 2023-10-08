package org.generation.ecommerce;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

@SpringBootTest
@AutoConfigureMockMvc
class EcommerceApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Se prueba el endpoint /api/productos/1")
	void pruebaGet() throws Exception {
		this.mockMvc.perform(get("/api/productos/1"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(
				content().string(
						containsString("Bubulubu de chocolate con fresa")
						)
				);
	}//pruebaGet
	
	@Test
	@Disabled("Probado la primera vez, deshabilitado")
	@DisplayName("Se borra el producto  del endpoint /api/productos/2")
	void pruebaDelete() throws Exception{
		this.mockMvc.perform(delete("/api/productos/2"))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(
				content().string(
						containsString("Dulce sabor fresa con chile")
						)
				);
	}//pruebaDelete

}//class
