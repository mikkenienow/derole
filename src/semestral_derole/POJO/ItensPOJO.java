package semestral_derole.POJO;

public class ItensPOJO {
    double valoritem;
    int Id_Item, criado;
    String titulo, descricao, pagante;
    public int getCriado() {
        return criado;
    }

    public void setCriado(int criado) {
        this.criado = criado;
    }

    public int getId_Item() {
        return Id_Item;
    }

    public void setId_Item(int Id_Item) {
        this.Id_Item = Id_Item;
    }

    public double getValoritem() {
        return valoritem;
    }

    public void setValoritem(double valoritem) {
        this.valoritem = valoritem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPagante() {
        return pagante;
    }

    public void setPagante(String pagante) {
        this.pagante = pagante;
    }
}
