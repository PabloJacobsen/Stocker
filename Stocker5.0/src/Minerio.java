public class Minerio {
	private long indice;
	private String minerio, operacao;
	private long kg;
	
	public Minerio(long indice,
				 String minerio,
				 String operacao,
				 long kg) {
		this.indice = indice;
		this.minerio = minerio;
		this.operacao = operacao;
		this.kg = kg;
	}
	public void somar(long kg) {
		this.operacao = "Soma";
		this.kg += kg;
	}
	public boolean remover(long kg) {
		if(kg<=this.kg) {
			this.kg -= kg;
			this.operacao = "Subtração";
			return true;
		} else {
			return false;
		}
		
	}
	public String getMinerio() {
		return minerio;
	}
	public String getOpercao() {
		return operacao;
	}
	public long getKg() {
		return kg;
	}
	public long getIndice() {
		return indice;
	}
	
	public String toString(){
		return (String.format("%d;%s;%s;%s",
				  this.indice, this.minerio, this.operacao, this.kg) );
	}
}