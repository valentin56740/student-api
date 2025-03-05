
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import data.Etudiant;

/**
 * Servlet implementation class StudentsServlet
 */
@WebServlet(name = "students", urlPatterns = { "/students" })
public class StudentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private EntityManager em;

	/**
	 * @see HttpServlet#HttpServlet()
	 */

	public StudentsServlet() {
		super();
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Etudiants_PU");
		this.em = emf.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	public List<Etudiant> getStudents() {
		Query requete = em.createQuery("SELECT e FROM Etudiant e");
		return (List<Etudiant>) requete.getResultList();
	}

	public Etudiant getStudent(int numero) {
		Query requete = em.createQuery("SELECT e FROM Etudiant e WHERE e.numero = :numero");
		requete.setParameter("numero", numero);
		@SuppressWarnings("unchecked")
		List<Etudiant> resultList = requete.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}
	
	public Etudiant addSudent(int numero) {
		Query requete = em.createQuery("SELECT e FROM Etudiant e WHERE e.numero = :numero");
		requete.setParameter("numero", numero);
		@SuppressWarnings("unchecked")
		List<Etudiant> resultList = requete.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}
	
	
	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		try {

			if (request.getParameter("id") == null) {
				List<Etudiant> etudiants = this.getStudents();
				objectMapper.writeValue(out, etudiants);
				return;
			}

			Integer numero = Integer.valueOf(request.getParameter("id"));
			Etudiant etudiant = this.getStudent(numero);
			try {

				if (etudiant != null) {
					objectMapper.writeValue(out, etudiant);
				} else {
					response.setStatus(HttpServletResponse.SC_NOT_FOUND);
					out.println("{\"error\": \"Etudiant non trouvé\"}");
				}
			} catch (NumberFormatException e) {
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				out.println("{\"error\": \"ID d'étudiant non valide\"}");
			}
		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			out.println("{\"error\": \"" + e.getMessage() + "\"}");
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
