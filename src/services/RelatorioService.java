package services;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class RelatorioService implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String FOLDER_RELATORIOS = "/relatorios";
	private static final String SUBREPORT_DIR = "SUBREPORT_DIR";
	private static String SEPARATOR = File.separator;
	private String caminhoArquivoRelatorio = null;
	private JRExporter exporter = null;
	private String caminhoSubReportDir = "";
	private File arquivoGerado = null;
	
	public String gerarRelatorio(List<?> listDataBeanColletion, Map<String, Object> parametrosRelatorio,
			String nomeRelatorioJasper, String nomeRelatorioSaida, ServletContext servletContext, String tipoExportar) throws Exception {
		
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDataBeanColletion);
		
		String caminhoRelatorio = servletContext.getRealPath(FOLDER_RELATORIOS);
		
		File file = new File(caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper");
		
		if (caminhoRelatorio == null || (caminhoRelatorio != null && caminhoRelatorio.isEmpty()) || !file.exists()) {
			caminhoRelatorio = this.getClass().getResource(FOLDER_RELATORIOS).getPath();
			SEPARATOR = "";
		}
	
		parametrosRelatorio.put("REPORT_PARAMETERS_IMG", caminhoRelatorio);
		
		String caminhoArquivosJasper = caminhoRelatorio + SEPARATOR + nomeRelatorioJasper + ".jasper";
		
		JasperReport relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(caminhoArquivosJasper);
		
		caminhoSubReportDir = caminhoRelatorio + SEPARATOR;
		parametrosRelatorio.put(SUBREPORT_DIR, caminhoSubReportDir);
		
		JasperPrint impressoraJasper = JasperFillManager.fillReport(relatorioJasper, parametrosRelatorio, jrbcds);
		
		if (tipoExportar.equalsIgnoreCase("pdf")) {
			exporter = new JRPdfExporter();
		} else if (tipoExportar.equalsIgnoreCase("xls")) {
			exporter = new JRXlsExporter();
		}		
		
		caminhoArquivoRelatorio = caminhoRelatorio + SEPARATOR + nomeRelatorioSaida + "." + tipoExportar;
		
		arquivoGerado = new File(caminhoArquivoRelatorio);
		
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, impressoraJasper);
		exporter.setParameter(JRExporterParameter.OUTPUT_FILE, arquivoGerado);
		
		exporter.exportReport();
		
		arquivoGerado.deleteOnExit();
		
		return caminhoArquivoRelatorio;
	}
	
	
}
