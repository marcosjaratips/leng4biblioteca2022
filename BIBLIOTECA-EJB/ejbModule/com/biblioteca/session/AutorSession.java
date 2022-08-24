package com.biblioteca.session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import com.biblioteca.entidad.Autor;

@Stateless
public class AutorSession {

	//@PersistenceUnit (name = "BibliotecaPU")
	@PersistenceContext (name = "BibliotecaPU")
	EntityManager em;
	
	//@PersistenceUnit (name = "ServicioWebBancoPU")
	//EntityManager em2;
	
	//Consultar todos los autores
	public List<Autor> consultarAutores(){
		String jpql = "SELECT a FROM Autor a ORDER BY a.codigo";
		
		Query q = em.createQuery(jpql);
		List<Autor> autores = q.getResultList();
		
		return autores;
	}
	
	public List<Autor> consultarAutoresPorNombreV1(String nombre){
		String jpql = "SELECT a FROM Autor a "
						+ "WHERE UPPER(a.nombre) LIKE :n"
						+ "ORDER BY a.codigo";
		
		Query q = em.createQuery(jpql);
		q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		
		List<Autor> autores = q.getResultList();
		
		return autores;
	}
	
	public Map<String, Object> consultarAutoresPorNombre(String nombre){
		Map<String, Object> retorno = new HashMap<String, Object>(); // {}
		
		try {
			String jpql = "SELECT a FROM Autor a "
						+ "WHERE UPPER(a.nombre) LIKE :n "
						+ "ORDER BY a.codigo";
		
			Query q = em.createQuery(jpql);
			q.setParameter("n", "%" + nombre.toUpperCase() + "%");
		
			List<Autor> autores = q.getResultList();
			//
			retorno.put("success", true);
			retorno.put("result", autores);
		} catch (Exception e) {
			retorno.put("success", false);
			retorno.put("error", e.getMessage());
		}
		return retorno;
	}
	
	//Buscar un Autor por el codigo
	public Autor buscarPorCodigo(Integer codigo) {
		Autor autor = em.find(Autor.class, codigo);
		return autor;
	}
	
	//Inserta un autor en la Base de Datos
	//utilizando el Entity manager
	public Autor incluir(Autor autor) {
		em.persist(autor);	//insertar
		em.refresh(autor);	//consulta el dato insertado.
		return autor;
	}
	
	public Autor editar(Autor autor) {
		autor = em.merge(autor);
		return autor;
	}
	
	// Incluye o edita un autor dependiendo si exite o no.
	private Autor actualizar(Autor autor) {
		Autor autorActualizado = null;
		Autor autorBuscar = buscarPorCodigo(autor.getCodigo());
		if (autorBuscar == null) {
			autorActualizado = incluir(autor);
		} else {
			autorActualizado = editar(autor);
		}
		return autorActualizado;
	}
	
	public void eliminar(Integer codigo) {
		//Falta validar, que pasa si se quiere eliminar un codig
		//que no exite. { success: false, error:"Autor con #4 no existe"}
		Autor autorBuscar = em.find(Autor.class, codigo);
		em.remove(autorBuscar);
	}
	
}
