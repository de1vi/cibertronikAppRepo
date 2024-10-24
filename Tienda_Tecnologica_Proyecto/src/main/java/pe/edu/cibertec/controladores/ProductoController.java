package pe.edu.cibertec.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import pe.edu.cibertec.modelos.Categoria;
import pe.edu.cibertec.modelos.Producto;
import pe.edu.cibertec.repositorios.CategoriaRepository;
import pe.edu.cibertec.repositorios.ProductoRepository;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    // Mostrar formulario para agregar un nuevo producto
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevoProducto(Model model) {
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("producto", new Producto());
        model.addAttribute("categorias", categorias);
        return "form_producto"; // Nombre de la vista HTML para registro
    }

    // Guardar un nuevo producto
    @PostMapping("/nuevo")
    public String guardarProducto(@ModelAttribute("producto") Producto producto, RedirectAttributes redirectAttributes) {
        Producto productoGuardado = productoRepository.save(producto);
        redirectAttributes.addFlashAttribute("productoId", productoGuardado.getId()); // Guardar ID del nuevo producto
        return "redirect:/productos/nuevo"; // Redirigir a la misma página para mostrar el mensaje
    }

    // Mostrar formulario para editar un producto
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProducto(@PathVariable("id") Integer id, Model model) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("producto", producto);
        model.addAttribute("categorias", categorias);
        return "editar_producto"; // Nombre de la vista de edición
    }

 // Actualizar un producto existente
    @PostMapping("/editar/{id}")
    public String actualizarProducto(@PathVariable("id") Integer id, 
                                      @ModelAttribute("producto") Producto productoActualizado, 
                                      RedirectAttributes redirectAttributes) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        // Actualiza los campos del producto
        producto.setNombre(productoActualizado.getNombre());
        producto.setMarca(productoActualizado.getMarca());
        producto.setModelo(productoActualizado.getModelo());
        producto.setCategoria(productoActualizado.getCategoria());
        producto.setPrecio(productoActualizado.getPrecio());
        producto.setCantidad(productoActualizado.getCantidad());
        producto.setDescripcion(productoActualizado.getDescripcion());
        
        // Guarda el producto actualizado
        productoRepository.save(producto);
        
        // Añade el mensaje de éxito
        redirectAttributes.addFlashAttribute("success", true);
        
        // Redirigir a la vista de edición del producto
        return "redirect:/productos/editar/" + id; 
    }



 // Eliminar un producto
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("ID inválido: " + id));
        
        productoRepository.delete(producto);
        
        redirectAttributes.addFlashAttribute("success", "Producto eliminado con éxito.");
        return "redirect:/productos/buscar"; // Redirigir a la página de búsqueda
    }


    // Mostrar página de búsqueda
    @GetMapping("/buscar")
    public String mostrarPaginaBusqueda(Model model) {
        return "buscar_productos"; // Nombre de la vista de búsqueda
    }

    // Buscar productos
    @GetMapping("/buscar/resultados")
    public String buscarProductos(@RequestParam(name = "tipo", required = false) String tipo,
                                   @RequestParam(name = "dato", required = false) String dato,
                                   Model model) {
        List<Producto> productos;

        // Lógica para buscar productos según el tipo y dato proporcionado
        if (tipo != null && !tipo.isEmpty() && dato != null && !dato.isEmpty()) {
            switch (tipo) {
                case "nombre":
                    productos = productoRepository.findByNombreContaining(dato);
                    break;
                case "categoria":
                    productos = productoRepository.findByCategoria_NombreContaining(dato);
                    break;
                default:
                    productos = productoRepository.findAll();
                    break;
            }
        } else {
            productos = productoRepository.findAll(); // Si no hay búsqueda, listar todos
        }

        model.addAttribute("productos", productos);
        return "buscar_productos"; // Nombre de la vista HTML para mostrar resultados de búsqueda
    }
}
