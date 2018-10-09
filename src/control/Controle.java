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
import entity.Clientes_Concentrador;
import entity.Dados;
import entity.Facturation;
import entity.Hidrometro;
import entity.Importacao;
import persistence.Clientes_ConcentradorDao;
import persistence.FacturationDao;
import persistence.HidrometroDao;
import persistence.ImportacaoDao;

@WebServlet("/Controle")
public class Controle extends HttpServlet {

	private String filename;
	private String path;
	private List<Dados> dd_atualizados;
	SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd");

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

		String Nome_Cliente = null;
		String Enderenco = null;
		Integer Cod_cli = null;

		Consumo c = new Consumo();

		List<Facturation> f = new ArrayList<Facturation>();
		List<Hidrometro> h = new ArrayList<Hidrometro>();
		List<Dados> dados = new ArrayList<Dados>();

		try {

			Clientes_Concentrador cli = new Clientes_ConcentradorDao().logar(login, senha);

	

			if (cli != null) {
				
				Nome_Cliente = cli.getCliente().getNomfant_apel();
				Enderenco = cli.getCliente().getEndereco();
				Cod_cli = cli.getCliente().getCodigo();

				dados = c.retorna_token(login, senha);

				Collections.sort(dados);

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

				try {

					h = new HidrometroDao().findhidroCli(cli.getCliente().getCodigo());
					f = new FacturationDao().findFactCli(cli.getCliente().getCodigo());

					
				} catch (Exception e) {
					// TODO: handle exception
				}

				for (int i = 0; i < dd_atualizados.size(); i++) {

					if (!f.isEmpty()) {

						for (Facturation fac : f) {

							if (fac.getNum_medidor().contains(dd_atualizados.get(i).getNumHidrometro())) {

								String result = out.format(in.parse(f.get(0).getData_levant().toString()));

								dd_atualizados.get(i).setData_hist(result);
								dd_atualizados.get(i).setIndice_antigo(fac.getIndice());
								dd_atualizados.get(i).setConsumo(
										(double) (dd_atualizados.get(i).getIndice_atual() - fac.getIndice()));

								dd_atualizados.get(i).setLocalizacao(fac.getLocaligacao());
								dd_atualizados.get(i).setCodigo(fac.getCod_cad01().getCodigo());

								
							}

						}

					} else {

						for (Hidrometro hidro : h) {

							if (hidro.getNum_hidro().contains(dd_atualizados.get(i).getNumHidrometro())) {

								String result = out.format(in.parse(dd_atualizados.get(i).getData()));

								dd_atualizados.get(i).setData_hist(result);
								dd_atualizados.get(i).setIndice_antigo(dd_atualizados.get(i).getIndice_atual());
								dd_atualizados.get(i).setConsumo(0.);

								dd_atualizados.get(i).setLocalizacao(hidro.getLocal());
								dd_atualizados.get(i).setCodigo(hidro.getCliente().getCodigo());

						

							}

						}

					}

					

					ServletContext context = request.getServletContext();
					path = context.getRealPath("/");

					System.out.println(path);

					file = new File(path + dados.get(0).getIdXML_TAB() + ".txt");

					FileWriter writer = new FileWriter(file);

					writer.write(Parametros.cabecalho);
					writer.write(System.getProperty("line.separator"));

					for (int j = 0; j < dd_atualizados.size(); j++) {

						String dt = dd_atualizados.get(i).getData().substring(0, 10);
						System.out.println("data : " + dt);

						SimpleDateFormat in = new SimpleDateFormat("yyyy-MM-dd");
						SimpleDateFormat out = new SimpleDateFormat("dd-MM-yyyy");

						String result = out.format(out.parse(dt));

						writer.write(dd_atualizados.get(j).getLocalizacao() + "\t"
								+ dd_atualizados.get(j).getIndice_atual() + "\t"
								+ dd_atualizados.get(j).getIndice_antigo() + "\t" + dd_atualizados.get(j).getConsumo()
								+ "\t" + dd_atualizados.get(j).getHaDesmontagem() + "\t"
								+ dd_atualizados.get(j).getHaVazamento() + "\t"
								+ dd_atualizados.get(j).getHouveVazamento() + "\t"
								+ dd_atualizados.get(j).getMedidorBloqueado() + "\t" + dd_atualizados.get(j).getCodigo()
								+ "\t" + dd_atualizados.get(j).getNumHidrometro() + "\t" + result + "\t"
								+ dd_atualizados.get(j).getData_hist() + "\t" + 8 + "\t" + 0 + "\t"
								+ dd_atualizados.get(j).getHouveDesmontagem() + "\t"
								+ dd_atualizados.get(j).getRetornoAgua());
						writer.write(System.getProperty("line.separator"));

					}
					
					writer.close();
					
				}
					

					request.getSession().setAttribute("CONDO", Nome_Cliente);
					request.getSession().setAttribute("ENDE", Enderenco);
					request.getSession().setAttribute("COD_CLI", Cod_cli);
					request.setAttribute("dados", dd_atualizados);

					request.getRequestDispatcher("medicao.jsp").forward(request, response);

			

			} else {

				request.setAttribute("msg", "Usu�rio ou senha inv�lidos");

				request.getRequestDispatcher("inicio.jsp").forward(request, response);

				login = "";
				senha = "";

			}

		} catch (Exception e) {
			// TODO: handle exception
		}

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

			// ImportacaoDao id = new ImportacaoDao();

			// id.gravar(imp);

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
				f.setCod_cad01(c);

				fact.add(f);
			}

			new FacturationDao().gravar(fact, imp);

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