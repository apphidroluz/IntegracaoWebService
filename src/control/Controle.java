package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.HibernateException;
import org.json.JSONException;

import entity.Clientes;
import entity.Dados;
import entity.Facturation;
import entity.Hidrometro;
import entity.Importacao;
import persistence.FacturationDao;
import persistence.HidrometroDao;
import persistence.ImportacaoDao;

@WebServlet("/Controle")
public class Controle extends HttpServlet {

	private String filename;
	private String path;
	private List<Dados> dd_atualizados;

	private File file;

	SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = request.getParameter("cmd");

		if (cmd.equalsIgnoreCase("exportar")) {
			exportar(request, response);
		} else if (cmd.equalsIgnoreCase("sair")) {
			sair(request, response);
		} else if (cmd.equalsIgnoreCase("consulta_importacao")) {
			consulta_importacao(request, response);
		} else if (cmd.equalsIgnoreCase("excluir")) {
			excluir(request, response);
		}

	}

	private void exportar(HttpServletRequest request, HttpServletResponse response) {
		try {
			cadastra_fac(dd_atualizados);
			String nome = file.getName();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", "attachment; filename='" + nome + "'");
			OutputStream output = response.getOutputStream();
			Files.copy(file.toPath(), output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String cmd = request.getParameter("cmd");
		System.out.println(cmd);
		if (cmd.equalsIgnoreCase("logar")) {
			try {
				logar(request, response);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

	}

	protected String logar(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException {

		String login = new String(request.getParameter("login"));
		String senha = new String(request.getParameter("senha"));

		Consumo c = new Consumo();
		
		List<Hidrometro> h = new ArrayList<Hidrometro>();

		try {
			List<Dados> dados = c.retorna_token(login, senha);

			System.out.println(dados);
			
			Collections.sort(dados);
			
			System.out.println(dados);

			dd_atualizados = new ArrayList();

			for (int i = 0; i < dados.size(); i++) {

				if (dd_atualizados.isEmpty()) {

					dd_atualizados.add(dados.get(i));

				} else {

					int count = 0;

					for (Dados d : dd_atualizados) {

						if (dados.get(i).getNumHidrometro().equalsIgnoreCase(d.getNumHidrometro())) {

							count++;
						}

					}

					if (count == 0) {

						dd_atualizados.add(dados.get(i));
					}

				}

			}

			for (int i = 0; i < dd_atualizados.size(); i++) {

				try {

					 h = new HidrometroDao().findHidro(dd_atualizados.get(i).getNumHidrometro());
					 
					 System.out.println(h);

					if (h.size() > 0) {

						dd_atualizados.get(i).setLocalizacao(h.get(0).getLocal());
						dd_atualizados.get(i).setCodigo(h.get(0).getCliente().getCodigo());

					}

				} catch (HibernateException e) {

					e.printStackTrace();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}

			for (int i = 0; i < dd_atualizados.size(); i++) {

				try {
					List<Facturation> f = new FacturationDao().findFact(dd_atualizados.get(i).getNumHidrometro());
					
				System.out.println(f);

					SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");

					if (f.isEmpty()) {

						String result = out.format(in.parse(dd_atualizados.get(i).getData()));

						dd_atualizados.get(i).setData_hist(result);
						dd_atualizados.get(i).setIndice_antigo(dd_atualizados.get(i).getIndice_atual());
						dd_atualizados.get(i).setConsumo(0.);
					

					} else {

						String result = out.format(in.parse(f.get(0).getData_levant().toString()));

						dd_atualizados.get(i).setData_hist(result);
						dd_atualizados.get(i).setIndice_antigo(f.get(0).getIndice());
						dd_atualizados.get(i)
								.setConsumo((double) (dd_atualizados.get(i).getIndice_atual() - f.get(0).getIndice()));
					}

				} catch (HibernateException e) {

					e.printStackTrace();
				} catch (SQLException e) {

					e.printStackTrace();
				}

			}

			ServletContext context = request.getServletContext();
			path = context.getRealPath("/");

			System.out.println(path);

			file = new File(path + dados.get(0).getIdXML_TAB() + ".txt");

			FileWriter writer = new FileWriter(file);

			writer.write(Parametros.cabecalho);
			writer.write(System.getProperty("line.separator"));

			for (int i = 0; i < dd_atualizados.size(); i++) {

				String dt = dd_atualizados.get(i).getData().substring(0, 10);
				System.out.println("data : " + dt);

				SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat out = new SimpleDateFormat("dd-MM-yyyy");

				String result = out.format(out.parse(dt));

				writer.write(dd_atualizados.get(i).getLocalizacao() + "\t" + dd_atualizados.get(i).getIndice_atual()
						+ "\t" + dd_atualizados.get(i).getIndice_antigo() + "\t" + dd_atualizados.get(i).getConsumo()
						+ "\t" + dd_atualizados.get(i).getHaDesmontagem() + "\t"
						+ dd_atualizados.get(i).getHaVazamento() + "\t" + dd_atualizados.get(i).getHouveVazamento()
						+ "\t" + dd_atualizados.get(i).getMedidorBloqueado() + "\t" + dd_atualizados.get(i).getCodigo()
						+ "\t" + dd_atualizados.get(i).getNumHidrometro() + "\t" + result + "\t"
						+ dd_atualizados.get(i).getData_hist() + "\t" + 8 + "\t" + 0 + "\t"
						+ dd_atualizados.get(i).getHouveDesmontagem() + "\t" + dd_atualizados.get(i).getRetornoAgua());
				writer.write(System.getProperty("line.separator"));

			}
			writer.close();

			request.getSession().setAttribute("CONDO", h.get(0).getCliente().getNomfant_apel());
			request.getSession().setAttribute("ENDE", h.get(0).getCliente().getEndereco() + ", " + h.get(0).getCliente().getBairro() 
					+ ", " + h.get(0).getCliente().getCidade());
			request.getSession().setAttribute("COD_CLI", h.get(0).getCliente().getCodigo());
			request.setAttribute("dados", dd_atualizados);
		} catch (JSONException e) {

			e.printStackTrace();
		}

		request.getRequestDispatcher("medicao.jsp").forward(request, response);

		return "";
	}

	private void cadastra_fac(List<Dados> dados) throws ServletException, IOException {

		List<Facturation> fact = new ArrayList<Facturation>();

		try {

			String Data_impo = dados.get(0).getData().substring(0, 10);

			SimpleDateFormat in2 = new SimpleDateFormat("dd-MM-yyyy");

			Date data_imp = in2.parse(Data_impo);

			Importacao imp = new Importacao();

			Clientes c = new Clientes();
			c.setCodigo(dados.get(0).getCodigo());

			imp.setCliente(c);
			imp.setData_imp(data_imp);

			//ImportacaoDao id = new ImportacaoDao();

			//id.gravar(imp);

			for (int i = 0; i < dados.size(); i++) {

				String Data2 = dados.get(i).getData().substring(0, 10);

				SimpleDateFormat in = new SimpleDateFormat("dd-MM-yyyy");

				Date dt = in.parse(Data2);

				System.out.println(dados.get(i).getData_hist());
				Date dt_hist = SDF.parse(dados.get(i).getData_hist());

				System.out.println(dt_hist);

				Facturation f = new Facturation();

				f.setIndice(dados.get(i).getIndice_atual());
				f.setLocaligacao(dados.get(i).getLocalizacao());
				f.setData_levant(dt);
				f.setImportacao(imp);
				f.setNum_medidor(dados.get(i).getNumHidrometro());
				f.setDesmontagem(dados.get(i).getHaDesmontagem());
				f.setTeve_desmontagem(dados.get(i).getHouveDesmontagem());
				f.setVazamento(dados.get(i).getHaVazamento());
				f.setTeve_vazamento(dados.get(i).getHouveVazamento());
				f.setMedidor_bloqueado(dados.get(i).getMedidorBloqueado());
				f.setRetorno_agua(dados.get(i).getRetornoAgua());
				f.setData_hist(dt_hist);
				f.setIndice_antigo(dados.get(i).getIndice_antigo());
				f.setConsumo(dados.get(i).getConsumo());

				new FacturationDao().gravar(f, imp);
			}

		} catch (Exception ex) {

			ex.printStackTrace();

		}

	}

	private void consulta_importacao(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String codigo = new String(request.getParameter("codigo"));
			
			Integer cod_cli = Integer.parseInt(codigo);
			
			ImportacaoDao i = new ImportacaoDao();
			
			List<Importacao> importacoes = i.findImp(cod_cli);
			
			
			request.setAttribute("dados", importacoes);
						
			request.getRequestDispatcher("consulta.jsp").forward(request, response);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void excluir(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			String idImp = new String(request.getParameter("id_IMP"));
			Integer cod_cli = Integer.parseInt(idImp);
			
			ImportacaoDao i = new ImportacaoDao();
			i.delete(cod_cli);
			
			
			
			request.getRequestDispatcher("consulta.jsp").forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void sair(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		file.delete();

		request.getSession().invalidate();

		request.getRequestDispatcher("inicio.jsp").forward(request, response);

	}

}