package semestral_derole.POJO;

public class UsuariosPOJO {
    int Id_Usuario, nivel;

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }
    String nome,datadenascimento,email,celular,senhavelha,senha,CPF,genero,datadecadastro;

    public String getSenhavelha() {
        return senhavelha;
    }

    public void setSenhavelha(String senhavelha) {
        this.senhavelha = senhavelha;
    }

    public String getDatadecadastro() {
        return datadecadastro;
    }

    public void setDatadecadastro(String datadecadastro) {
        this.datadecadastro = datadecadastro;
    }

    public String getCPF() {
        return CPF;
    }

    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
    

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDatadenascimento() {
        return datadenascimento;
    }

    public void setDatadenascimento(String datadenascimento) {
        this.datadenascimento = datadenascimento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    
    
}
