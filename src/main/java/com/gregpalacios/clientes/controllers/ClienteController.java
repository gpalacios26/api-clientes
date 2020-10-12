package com.gregpalacios.clientes.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gregpalacios.clientes.entities.Cliente;
import com.gregpalacios.clientes.entities.ClienteDetalle;
import com.gregpalacios.clientes.services.ClienteService;
import com.gregpalacios.clientes.utils.ExcelGenerator;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
	
	private String pathCliente = "D:\\VAR\\FILES\\CLIENTE\\";

	@Autowired
	private ClienteService clienteService;
	
	@GetMapping("/listar")
	public @ResponseBody Map<String, Object> getClientes() {
		
		boolean valido = true;
		// Validar los datos
		List<Cliente> clientes = clienteService.findAllActivos();
		if(clientes==null) {
			valido = false;
		}
		
		HashMap<String, Object> respuesta = new HashMap<>();
		
		if(valido) {
			respuesta.put("estado", "1");
			respuesta.put("clientes", clientes);
		} else {
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "No hay clientes registrados");
		}
		
		return respuesta;
	}
	
	@GetMapping("/obtener/{id}")
	public @ResponseBody Map<String, Object> getCliente(@PathVariable("id") Long id) {
	
		boolean valido = true;
		// Validar los datos
		Cliente clienteData = clienteService.findById(id);
		if(clienteData==null) {
			valido = false;
		}
		
		HashMap<String, Object> respuesta = new HashMap<>();
		
		if(valido) {
			respuesta.put("estado", "1");
			respuesta.put("cliente", clienteData);
		} else {
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "No existe el cliente");
		}
		
		return respuesta;
	}
	
	@PostMapping("/crear")
	public @ResponseBody Map<String, Object> createCliente(@RequestBody ClienteDetalle cliente_detalle) {
		
	Cliente cliente = cliente_detalle.getCliente();
	String archivo = cliente_detalle.getArchivo();
	String formato = cliente_detalle.getFormato();
			
	boolean valido = true;
	// Validar los datos
	if(cliente.getId()==null) {
		if(cliente.getName()!=null && cliente.getSurname()!=null && cliente.getEmail()!=null) {
			// Si existe la imagen se crea archivo desde base64
			if(archivo!=null && formato!=null) {
				String nombreArchivo = null;
				try {
					nombreArchivo = createFileFromBase64(archivo, formato);
				} catch (Exception e) {
					nombreArchivo = null;
				}
				cliente.setImage(nombreArchivo);
			}
			cliente.setFlg_status("1");
			cliente.setCreated_at(new Date());
		} else {
			valido = false;
		}
	} else {
		valido = false;
	}
		
	HashMap<String, Object> respuesta = new HashMap<>();
			
	if(valido) {
		try {
			clienteService.save(cliente);
			
			respuesta.put("estado", "1");
			respuesta.put("mensaje", "Cliente creado correctamente");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "Error al crear el cliente");
		}
	} else {
		respuesta.put("estado", "0");
		respuesta.put("mensaje", "Los datos ingresados no son válidos");
	}
		
		return respuesta;
	}
	
	@PutMapping("/actualizar")
	public @ResponseBody Map<String, Object> updateCliente(@RequestBody ClienteDetalle cliente_detalle) {
		
	Cliente cliente = cliente_detalle.getCliente();
	String archivo = cliente_detalle.getArchivo();
	String formato = cliente_detalle.getFormato();
			
	boolean valido = true;
	// Validar los datos
	if(cliente.getId()!=null) {
		Cliente clienteData = clienteService.findById(cliente.getId());
		if(clienteData!=null) {
			if(cliente.getName()!=null && cliente.getSurname()!=null && cliente.getEmail()!=null) {
				// Si existe la imagen se crea archivo desde base64
				if(archivo!=null && formato!=null) {
					String nombreArchivo = null;
					try {
						nombreArchivo = createFileFromBase64(archivo, formato);
					} catch (Exception e) {
						nombreArchivo = null;
					}
					cliente.setImage(nombreArchivo);
				} else {
					cliente.setImage(clienteData.getImage());
				}
				cliente.setFlg_status(clienteData.getFlg_status());
				cliente.setCreated_at(clienteData.getCreated_at());
				cliente.setUpdated_at(new Date());
			} else {
				valido = false;
			}
		} else {
			valido = false;
		}
	} else {
		valido = false;
	}
		
	HashMap<String, Object> respuesta = new HashMap<>();
			
	if(valido) {
		try {
			clienteService.save(cliente);
			
			respuesta.put("estado", "1");
			respuesta.put("mensaje", "Cliente actualizado correctamente");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "Error al actualizar el cliente");
		}
	} else {
		respuesta.put("estado", "0");
		respuesta.put("mensaje", "Los datos ingresados no son válidos");
	}
		
		return respuesta;
	}
	
	@DeleteMapping("/eliminar/{id}")
	public @ResponseBody Map<String, Object> deleteCliente(@PathVariable("id") Long id) {
	
		boolean valido = true;
		// Validar los datos
		Cliente clienteData = clienteService.findById(id);
		if(clienteData!=null) {
			clienteData.setFlg_status("0");
		} else {
			valido = false;
		}
		
		HashMap<String, Object> respuesta = new HashMap<>();
		
		if(valido) {
			try {
				clienteService.delete(clienteData);
				
				respuesta.put("estado", "1");
				respuesta.put("mensaje", "Cliente eliminado correctamente");
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				
				respuesta.put("estado", "0");
				respuesta.put("mensaje", "Error al eliminar el cliente");
			}
		} else {
			respuesta.put("estado", "0");
			respuesta.put("mensaje", "No existe el cliente para eliminar");
		}
		
		return respuesta;
	}
	
	@GetMapping("/descargar")
	public ResponseEntity<InputStreamResource> exportClientes() throws IOException {
        
		List<Cliente> clientes = clienteService.findAllActivos();
    
	    ByteArrayInputStream in = ExcelGenerator.usuariosToExcel(clientes);
	    
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Disposition", "attachment; filename=clientes.xlsx");
	    
	    return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
    }
	
	@GetMapping("/imagen/{name}")
	public ResponseEntity<InputStreamResource> getImagen(@PathVariable("name") String name) throws IOException {
	    
		File file = new File(pathCliente+name);
		InputStream in = new FileInputStream(file.getPath());
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename="+file.getName());
	    
		return ResponseEntity.ok().headers(headers).body(new InputStreamResource(in));
	}
	
	public String createFileFromBase64(String archivo, String formato) throws IOException {
		
		// El String Base64 lo decodificamos a un array de bytes
        byte[] archivoBytes = Base64.getDecoder().decode(archivo);
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String fileName = "file-"+timestamp.getTime()+"."+formato;
        
        File file = new File(pathCliente+fileName);
        OutputStream out = new FileOutputStream(file);
        out.write(archivoBytes);
        out.close();
        
        return fileName;
    }
}