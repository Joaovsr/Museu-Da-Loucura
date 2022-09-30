package jvsr.pw.modelo;

public class RelatorioVisita {
	private String data;
	private int quantidadeDeVisitas;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public int getQuantidadeDeVisitas() {
		return quantidadeDeVisitas;
	}
	public void setQuantidadeDeVisitas(int quantidadeDeVisitas) {
		this.quantidadeDeVisitas = quantidadeDeVisitas;
	}
	public void adicionarQuantidadeDeVisitas(int quantidadeDeVisitas) {
		this.quantidadeDeVisitas += quantidadeDeVisitas;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RelatorioVisita)
			return data.equalsIgnoreCase( ((RelatorioVisita) obj).data );
		else 
			return false;
	}
}