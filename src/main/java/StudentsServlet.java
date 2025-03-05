

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
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StudentsServlet() {
        super();
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

	@SuppressWarnings("unchecked")
	public List<Etudiant> getStudents() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Etudiants_PU");
		EntityManager em = emf.createEntityManager();
		Query requete = em.createQuery("SELECT e FROM Etudiant e");
		return (List<Etudiant>) requete.getResultList();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String pathInfo = request.getPathInfo();
		PrintWriter out = response.getWriter();

		// Liste de tous les étudiants: GET /students
		if (pathInfo == null || pathInfo.equals("/")) {
			List<Etudiant> etudiants = this.getStudents();
			objectMapper.writeValue(out, etudiants);
			return;
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
