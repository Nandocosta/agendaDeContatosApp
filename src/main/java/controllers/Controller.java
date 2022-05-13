package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = {"/Controller" , "/main", "/insert", "/select","/update","/delete","/report"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getServletPath();
		
		System.out.println(action);
		if(action.equals("/main")){
			contatos(request,response);
		}else if(action.equals("/insert")) {
			novoContato(request, response);
		}else if(action.equals("/select")) {
			listarContato(request, response);
		}else if(action.equals("/update")) {
			editarContato(request, response);
		}else if(action.equals("/delete")) {
			removerContato(request, response);
		}else if(action.equals("/report")) {
			gerarRelatorio(request, response);
		}
		else {
			response.sendRedirect("index.html");
		}
	}
	
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<JavaBeans> lista = dao.listarContatos();
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
		
	}
	protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//setar as variaveis javabeans
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		dao.inserirContato(contato);
		response.sendRedirect("main");
	}
	protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recebimento do id do contato que será editado
		String idcon = request.getParameter("idcon");
		
		//Setar variável JavaBeans
		contato.setIdcon(idcon);
		
		//Executar o método selecionarContato (DAO)
		dao.selecionarContato(contato);
		
		//Setar os atributos do formulário com o conteúdo JavaBeans
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		
		//Encaminhar ao documento edita.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}
	protected void editarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//setar variavel JavaBeans
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		
		// executar o método alterarContato
		dao.alterarContato(contato);
		
		//Redirecionar para o documento agenda.jsp (atualisando as alterções)
		response.sendRedirect("main");
	}
	
	protected void removerContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Recebimento do id do contato a ser excluido (Validador.js)
		String idcon = request.getParameter("idcon");
		
		//Setar variável idcon JavaBeans
		contato.setIdcon(idcon);
		
		//execultar metodo deletarContato (DAO) passando o objeto cotato
		dao.deletarContato(contato);
		
		//Redirecionar para o documento agenda.jsp (atualisando as alterções)
		response.sendRedirect("main");	
	}
	
		//Gerar documento pdf
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Document documento = new Document();
		try {
		//Tipo de Conteúdo
			response.setContentType("apllication/pdf");
		//Nome do documento
			response.addHeader("Content-Disposition", "inline; filename=" + "contatos.pdf");
		//Criar o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
		//Abrir o documento -> conteúdo
			documento.open();
			documento.add(new Paragraph("Lista de contatos"));
			documento.add(new Paragraph(" "));
			//Criar um tabela
			PdfPTable tabela = new PdfPTable(3);
			//Cabeçalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("E-mail"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			//Popular tabela com os contatos
			ArrayList<JavaBeans> lista = dao.listarContatos();
			for(int i = 0 ; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			
			documento.add(tabela);
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
