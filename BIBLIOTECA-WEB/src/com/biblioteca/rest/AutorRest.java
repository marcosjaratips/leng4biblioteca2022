package com.biblioteca.rest;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Body;

import com.biblioteca.entidad.Autor;
import com.biblioteca.session.AutorSession;

@Path("/autor")
public class AutorRest {

	@EJB
	AutorSession as;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/consultar")
	public List<Autor> consultar() {
		return as.consultarAutores();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/consultar-por-nombre")
	//Parametros: """QueryParam""", PathParam, BodyParam
	//http://localhost:8080/BIBLIOTECA-WEB/rest/autor/consultar-por-nombre?q=Ana
	public Map<String, Object> consultarPorNombre(@QueryParam("q") String nombre) {
		return as.consultarAutoresPorNombre(nombre);
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/incluir")
	//BodyParam
	public Autor incluir(Autor autor) {
		return as.incluir(autor);
	}
	
	@DELETE
	@Path("/eliminar/{id}")
	//PathParam
	public void eliminar(@PathParam("id") Integer codigo) {
		as.eliminar(codigo);
	}
	
	//Tarea para hoy 
	//Implementar editar, actualizar, buscarPorCodigo
	
	//Para la otra semana
	//Implementar formato de respuesta estandar via MAP, como se hizo en el consultar por Nombre
	//Publicar en la plataforma el Diccionario de Datos 
	
	
	
	
}
