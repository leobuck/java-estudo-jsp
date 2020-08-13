package bean;

import java.util.ArrayList;
import java.util.List;

public class ProjetoBean {

	private Long id;
	
	private String nome;
	
	private List<ProjetoSeriesBean> series = new ArrayList<ProjetoSeriesBean>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<ProjetoSeriesBean> getSeries() {
		return series;
	}

	public void setSeries(List<ProjetoSeriesBean> series) {
		this.series = series;
	}
	
}
