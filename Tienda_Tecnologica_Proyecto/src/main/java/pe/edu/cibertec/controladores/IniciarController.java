package pe.edu.cibertec.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IniciarController {
	 @GetMapping("/")
	    public String redirigirABuscarProducto() {
	        return "redirect:/productos/buscar"; // Redirigir a la vista de buscar empleados
	    }

}
