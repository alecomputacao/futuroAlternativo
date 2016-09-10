package app.control;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import app.model.Aluno;
import app.model.Documento;
import app.model.Turma;
import app.util.ListUtil;
import app.util.Status;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;
import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author arturc
 *
 */
@ManagedBean(name = "relatorioBean")
@SessionScoped
public class RelatorioBean implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{alunoBean}")
	private AlunoBean alunoBean;

	@ManagedProperty(value = "#{documentoBean}")
	private DocumentoBean documentoBean;

	@ManagedProperty(value = "#{eventoBean}")
	private EventoBean eventoBean;

	@ManagedProperty(value = "#{turmaBean}")
	private TurmaBean turmaBean;

	private List<Turma> turmas;

	private static Logger LOGGER = Logger.getLogger(RelatorioBean.class);

	public String carregarRelatorios() {
		LOGGER.info("Carregando informacoes para relatorios");
		turmaBean.buscarPorStatus(Status.ATIVO.toString());
		turmas = turmaBean.getTurmas();
		return "relatorios?faces-redirect=true";
	}

	/**
	 *
	 * @param nomeRelatorio
	 */
	public void relatorioAlunoSimples(String nomeRelatorio, Turma turma) {
		LOGGER.info("Iniciando a geracao do relatorio Aluno Simples");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			LOGGER.info("Preenchendo informacoes");
			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()), col.column("CPF", "cpf", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Data Nascimento", "datanasc", type.dateType()),
							col.column("", "item1", type.stringType()), col.column("", "item2", type.stringType()),
							col.column("", "item3", type.stringType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text(nomeRelatorio + " turma: " + turma.getCodigo()).setStyle(titleStyle)
											.setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow()
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceRelatorioAlunoSimples(turma.getCodigo()))
					// create and show report
					.toPdf(baos);

			LOGGER.info("Gerando PDF");
			baos.toByteArray();
			res.setContentType("application/pdf");
			// Codigo abaixo gerar o relatorio e disponibiliza diretamente na
			// pagina
			res.setHeader("Content-disposition", "inline;filename=" + nomeRelatorio + ".pdf");
			// Codigo abaixo gerar o relatorio e disponibiliza para o
			// cliente baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				LOGGER.error("Nao foi possivel criar o relatorio", e);
				error("Nao foi possivel criar o relatorio");
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			LOGGER.info("Relatorio concluido");
		} catch (DRException e) {
			LOGGER.error("Erro ao gerar relatorio", e);
			error(e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceRelatorioAlunoSimples(String codigoTurma) {
		DRDataSource dataSource = new DRDataSource("nome", "cpf", "matricula", "datanasc", "item1", "item2", "item3");
		this.alunoBean.buscarPorTurma(codigoTurma);
		if (ListUtil.isValid(alunoBean.getAlunos())) {
			for (Aluno aluno : this.alunoBean.getAlunos()) {
				Documento cpf = null;
				this.documentoBean.buscarPorPessoa(aluno.getPessoa());
				for (Documento d : this.documentoBean.getDocumentos()) {
					if (d.getTipo().equals("CPF")) {
						cpf = d;
						break;
					}

				}
				dataSource.add(aluno.getPessoa().getNome(), cpf.getNumero(), aluno.getMatricula(),
						aluno.getPessoa().getDataNasc(), null, null, null);
			}
			this.alunoBean.getAlunos().clear();
		}

		return dataSource;
	}

	/**
	 * Relatorio de faltas cometidas por entrar no segundo horario ou sair antes
	 * do horario final
	 */
	public void relatorioFaltaHorarios() {
		LOGGER.info("Iniciando a geracao do relatorio penalidades horarios");
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			LOGGER.info("Preenchendo informacoes");
			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Qtd Faltas/Horario", "qtd", type.integerType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text("Faltas cometidas em rela��o aos Hor�rios Entrada/Sa�da")
											.setStyle(titleStyle).setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow(2)
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceFaltaHorarios())
					// create and show report
					.toPdf(baos);

			baos.toByteArray();
			res.setContentType("application/pdf");
			// Codigo abaixo gerar o relaorio e disponibiliza diretamente na
			// pagina
			res.setHeader("Content-disposition", "inline;filename=faltas_x_horarios.pdf");
			// Codigo abaixo gerar o relaorio e disponibiliza para o cliente
			// baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				LOGGER.error("Nao foi possivel cirar o relatorio", e);
				error("Nao foi possivel cirar o relatorio");
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			LOGGER.info("Saiu do relatorio simples");
		} catch (DRException e) {
			LOGGER.error("Erro ao gerar relatorio", e);
			error("Erro ao gerar relatorio: " + e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceFaltaHorarios() {
		this.alunoBean.buscarTodosPorStatus(Status.ATIVO.toString());

		DRDataSource dataSource = new DRDataSource("nome", "matricula", "qtd");
		for (Aluno aluno : this.alunoBean.getAlunos()) {
			int qtd = this.eventoBean.buscarFaltasHorariosPorPessoa(aluno.getPessoa());

			dataSource.add(aluno.getPessoa().getNome(), aluno.getMatricula(), qtd);

		}
		this.alunoBean.getAlunos().clear();
		this.alunoBean.limparAluno();
		this.eventoBean.getEventos().clear();
		System.out.println("Fim relatorio faltas horario");
		return dataSource;
	}

	/**
	 * @param data
	 *            - the date to have search present list
	 */
	public void relatorioPresencaPorData(Date data) {
		LOGGER.info("Iniciando a geracao do relatorio Presenca por data");
		String dataTmp = null;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		dataTmp = sdf.format(data);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		HttpServletResponse res = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		try {

			StyleBuilder boldStyle = DynamicReports.stl.style().bold();
			StyleBuilder boldCenteredStyle = DynamicReports.stl.style(boldStyle)
					.setHorizontalAlignment(HorizontalAlignment.CENTER);

			StyleBuilder titleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setVerticalAlignment(VerticalAlignment.MIDDLE).setFontSize(15);

			StyleBuilder columnTitleStyle = DynamicReports.stl.style(boldCenteredStyle)
					.setBorder(DynamicReports.stl.pen1Point()).setBackgroundColor(Color.LIGHT_GRAY);

			StyleBuilder columnStyle = DynamicReports.stl.style().setHorizontalAlignment(HorizontalAlignment.CENTER)
					.setBorder(DynamicReports.stl.pen1Point());

			LOGGER.info("Preenchendo relatorio");
			report().setColumnTitleStyle(columnTitleStyle)
					// add columns
					.columns(
							// title, field name data type
							col.column("Nome", "nome", type.stringType()), col.column("CPF", "cpf", type.stringType()),
							col.column("Matricula", "matricula", type.stringType()),
							col.column("Status", "status", type.stringType()))
					.setColumnStyle(columnStyle)
					// shows report title
					// .highlightDetailEvenRows()
					.title(// shows report title
							cmp.horizontalList()
									.add(cmp.text("Lista de presen�a " + dataTmp).setStyle(titleStyle)
											.setHorizontalAlignment(HorizontalAlignment.LEFT),
											cmp.text("Futuro-Alternativo").setStyle(titleStyle)
													.setHorizontalAlignment(HorizontalAlignment.RIGHT))
									.newRow()
									.add(cmp.filler()
											.setStyle(DynamicReports.stl.style()
													.setTopBorder(DynamicReports.stl.pen2Point()))
											.setFixedHeight(10)))

					// shows number of page at page footer
					.pageFooter(cmp.pageXofY().setStyle(boldCenteredStyle))
					// set datasource
					.setDataSource(criaDataSourceRelatorioPresenca(data))
					// create and show report
					.toPdf(baos);
			baos.toByteArray();
			res.setContentType("application/pdf");
			// Codigo abaixo gerar o relatorio e disponibiliza diretamente na
			// pagina
			res.setHeader("Content-disposition", "inline;filename=Lista de presenca.pdf");
			// Código abaixo gerar o relatorio e disponibiliza para o cliente
			// baixar ou salvar
			// res.setHeader("Content-disposition",
			// "attachment;filename=arquivo.pdf");
			try {
				res.getOutputStream().write(baos.toByteArray());
			} catch (IOException e) {
				LOGGER.error("Nao foi possivel criar o relatorio", e);
				error("Nao foi possivel criar o relatorio: " + e.getMessage());
			}
			res.getCharacterEncoding();
			FacesContext.getCurrentInstance().responseComplete();

			LOGGER.info("Finalizando a geracao do relatorio Presenca por data");
		} catch (DRException e) {
			LOGGER.error("Nao foi possivel criar o relatorio", e);
			error("Nao foi possivel criar o relatorio: " + e.getMessage());
		}

	}

	/**
	 *
	 * @return
	 */
	private JRDataSource criaDataSourceRelatorioPresenca(Date data) {
		DRDataSource dataSource = new DRDataSource("nome", "cpf", "matricula", "status");
		this.alunoBean.buscarTodosPorStatus(Status.ATIVO.toString());
		for (Aluno aluno : this.alunoBean.getAlunos()) {
			// preenchendo documento
			Documento cpf = null;
			this.documentoBean.buscarPorPessoa(aluno.getPessoa());
			for (Documento d : this.documentoBean.getDocumentos()) {
				if (d.getTipo().equals("CPF")) {
					cpf = d;
					break;
				}
			}

			// Definindo presen�a ou falta
			String presenca = null;
			this.eventoBean.buscarPorPessoaEData(aluno.getPessoa(), data);
			if (this.eventoBean.getEventos().isEmpty() || this.eventoBean.getEventos() == null) {
				presenca = "FALTOU";
			} else {
				presenca = "PRESENTE";
			}
			System.out.println(presenca);
			dataSource.add(aluno.getPessoa().getNome(), cpf.getNumero(), aluno.getMatricula(), presenca);
		}
		this.alunoBean.getAlunos().clear();
		return dataSource;
	}

	public List<Turma> getTurmas() {
		return turmas;
	}

	public void setTurmas(List<Turma> turmas) {
		this.turmas = turmas;
	}

	public AlunoBean getAlunoBean() {
		return alunoBean;
	}

	public void setAlunoBean(AlunoBean alunoBean) {
		this.alunoBean = alunoBean;
	}

	public DocumentoBean getDocumentoBean() {
		return documentoBean;
	}

	public void setDocumentoBean(DocumentoBean documentoBean) {
		this.documentoBean = documentoBean;
	}

	public EventoBean getEventoBean() {
		return eventoBean;
	}

	public void setEventoBean(EventoBean eventoBean) {
		this.eventoBean = eventoBean;
	}

	public TurmaBean getTurmaBean() {
		return turmaBean;
	}

	public void setTurmaBean(TurmaBean turmaBean) {
		this.turmaBean = turmaBean;
	}

	/**
	 *
	 * @param message
	 */
	public void info(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, " Info", message));
	}

	/**
	 *
	 * @param message
	 */
	public void warn(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, " Aten��o!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void error(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, " Erro!", message));
	}

	/**
	 *
	 * @param message
	 */
	public void fatal(String message) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, " Fatal!", message));
	}
}
