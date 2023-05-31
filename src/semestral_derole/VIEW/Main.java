package semestral_derole.VIEW;


import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import semestral_derole.DAO.EventosDAO;
import semestral_derole.DAO.EventosItemsDAO;
import semestral_derole.DAO.ItensDAO;
import semestral_derole.DAO.UsuariosBuscaDAO;
import semestral_derole.DAO.UsuariosDAO;
import semestral_derole.DAO.UsuariosEventosDAO;
import semestral_derole.POJO.EventosItensPOJO;
import semestral_derole.POJO.EventosPOJO;
import semestral_derole.POJO.ItensPOJO;
import semestral_derole.POJO.UsuariosBuscaPOJO;
import semestral_derole.POJO.UsuariosEventosPOJO;
import semestral_derole.POJO.UsuariosPOJO;

public class Main extends javax.swing.JFrame {
    public int mouseX, mouseY;
    public int mouseCX, mouseCY;
    public UsuariosPOJO usuariospojo = new UsuariosPOJO();
    public UsuariosDAO usuariosdao = new UsuariosDAO();
    public UsuariosBuscaPOJO usuariosbuscapojo = new UsuariosBuscaPOJO();
    public UsuariosBuscaDAO usuariosbuscadao = new UsuariosBuscaDAO();
    public EventosPOJO eventospojo = new EventosPOJO();
    public EventosDAO eventosdao = new EventosDAO();
    public UsuariosEventosPOJO usuarioseventospojo = new UsuariosEventosPOJO();
    public UsuariosEventosDAO usuarioseventosdao = new UsuariosEventosDAO();
    public ItensDAO itensdao = new ItensDAO();
    public ItensPOJO itenspojo = new ItensPOJO();
    public EventosItemsDAO eventositensdao = new EventosItemsDAO();
    public EventosItensPOJO eventositenspojo = new EventosItensPOJO();
    public int TelaAtual = 0;
    public int Usuario = -1;
    public UsuariosPOJO usuarioLogado = null;
    public int NivelUsuario = -1;
    public int HostEvento = -1;
    public int EventoSelecionado = -1;
    public int ItemSelecionado = -1;
    public double CustoTotalEventoSelecionado=0.0;
    public int ParticipantesTotalEventoSelecionado=-1;
    public boolean teste = false;
    public ArrayList<String> L_nome_email = new ArrayList<>(Arrays.asList("","","","","",""));
    public ArrayList<String> L_email = new ArrayList<>(Arrays.asList("0","1","2","3","4","5"));

    public Main() {
        initComponents();
        DevTools.setVisible(false);
        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/background.png")));
        Fundo.setLocation(-4, 0);
        Fundo.setSize(new Dimension(330,630));
        Color c=new Color(1f,0f,0f,.0f );
        setBackground(c);
        setVisible(true);
        setSize(365, 680);
        setLocationRelativeTo(null);
    }

    public boolean VerificarEmail (JTextField email){
        // este metodo verifica se no campo digitado tem um e-mail ou telefone
        System.out.println("Valor a ser Verificado: "+email.getText());
        String vlemail = email.getText();
        int et = email.getText().length();
        boolean arroba = false;
        boolean ponto = false;
        //boolean posponto = false;
        boolean resultado = false;
        
        for (int i=0;i<et;i++){
            if (vlemail.substring(i,i+1).equals("@")){
                System.out.println("Tem @");
                arroba = true;
            }
            if (vlemail.substring(i,i+1).equals(".")){
                System.out.println("Tem '.'");
                ponto = true;
            }
            if (i>=1){
            if (vlemail.substring(i-1,i+1).equals("..")){
                ponto = false;                
            }/*else {
                System.out.println("não tem ponto duplo");
                posponto = true;*/
            }
        }
        if (arroba&&ponto){
            resultado = true;
        } else {
            resultado = false;
        }
    return resultado;}
    public boolean VerNumero(JTextField campo){
        boolean resultado = false;
        if(campo.getText().matches("[0-9]*")){
            resultado=true;
        }
    return resultado;}
    public boolean ConsultaUsuario(JTextField usuario){
        boolean resultado=false;
        usuariospojo=usuariosdao.VerificarDuplicidade("email",usuario.getText());
        
        if (usuariospojo.getId_Usuario()!= 0){
            resultado=true;
            usuariospojo=usuariosdao.VerificarDuplicidade("celular",usuario.getText());
            if(usuariospojo.getId_Usuario()!= 0){
                resultado=true;
                if (usuariospojo.getNivel()>1){
                    resultado=false;
                }
            }
            if (usuariospojo.getNivel()>1){
                    resultado=false;
            }
        }
        
        
    return resultado;}
    
    public boolean ConsultaFinalLogin(JTextField usuario){
        boolean resultado=false;
        if(VerificarEmail(usuario)){
            resultado = true;
        } else {
            if (VerNumero(usuario)){
                resultado = true;
            } else {
                resultado = false;
            }
        }
    return resultado;}
    public boolean ConsultaSenha(JTextField usuario, JTextField senha){
        boolean resultado=false;
        usuariospojo=usuariosdao.VerificarSenha(usuario.getText(), senha.getText());
        if (usuariospojo.getId_Usuario()!=0){
            resultado = true;
            PainelCT(TLogin);
            ImagemFundo8.setName("Ok");
            MsgLogin.setText("");
            MsgLogin.setVisible(false);
        } else {
            resultado = false;
            ImagemFundo8.setName("Erro");
            PainelCT(TLogin);
            MsgLogin.setText("Senha errada.");
            MsgLogin.setVisible(true);
        }
    return resultado;}
    public Double ConverterDouble(String valor){
        Double resultado=0.0;
        String montar="";
        for (int i=0;i<valor.length();i++ ){
            if(valor.substring(i,i+1).equals(",")){
                montar = montar+".";
            }else{
            montar = montar + valor.substring(i,i+1);
            }
        }
        resultado = Double.parseDouble(montar);
    return resultado;}
    public int ContarVirgula(String valor){
        int resultado=0;
        for (int i = 0; i<valor.length();i++){
            if(valor.substring(i,i+1).equals(",")){
                resultado=i;
            }
        }
    return resultado;}
    public boolean VerificarObrigatorio(String valor){
        boolean resultado=false;
        for (int i = 0; i<valor.length();i++){
            if(valor.substring(i,i+1).equals("*")){
                resultado=true;
            }
        }
    return resultado;}
    
    //metodos de tabela
    public void TabelaTamanho(){
        int[] columnsWidth = {160, 60, 70, 0};
        //int i = 0;
        for (int i=0;i<4;i++) {
            TableColumn column = T_Home_Eventos.getColumnModel().getColumn(i);
            column.setMinWidth(columnsWidth[i]);
            column.setMaxWidth(columnsWidth[i]);
            column.setPreferredWidth(columnsWidth[i]);
        }
    }
    public void LimparBusca(JTable tabela){
        int numeroLinhas=tabela.getRowCount();
        for (int i=numeroLinhas;i>0;i--){
            DefaultTableModel dtm = (DefaultTableModel)tabela.getModel();
            dtm.removeRow(0);
        }
    }
    public void BuscarMeusEventos(JTable tabela, int busca, String modo){
        LimparBusca(tabela);
        String data = Padraodata(pegardataatual());
        //evento
        System.out.println(usuariospojo.getId_Usuario());
        
        List lista = eventosdao.BuscarEventos(busca,modo,data);
        for (int i=0;i<lista.size();i++){
            eventospojo = (EventosPOJO)lista.get(i);
            DefaultTableModel dtm = (DefaultTableModel)tabela.getModel();
            dtm.addRow(new Object[]{eventospojo.getTitulo(), eventospojo.getHorario().substring(0, 5),
            eventospojo.getData(), eventospojo.getId_Evento()});
        }
    }
    
    public void BuscarItensDeEventos(JTable tabela, int busca){
        double CustoTotal=0.0;
        LimparBusca(tabela);
        String data = Padraodata(pegardataatual());
        //itens
        List lista = itensdao.BuscarItensDeEventos(busca);
        for (int i=0;i<lista.size();i++){
            itenspojo = (ItensPOJO)lista.get(i);
            DefaultTableModel dtm = (DefaultTableModel)tabela.getModel();
            dtm.addRow(new Object[]{itenspojo.getTitulo(), itenspojo.getValoritem(),
            itenspojo.getId_Item()});
            CustoTotal = CustoTotal + itenspojo.getValoritem();
 
        }
        CustoTotalEventoSelecionado = CustoTotal;
        R_EvtHome_CustoTotal.setText("R$"+(CustoTotal + "").replace(".", ","));
    }
    public void BuscarParticipantesDeEvento(JTable tabela, int busca){
        int ParticipantesTotal=0;
        LimparBusca(tabela);
        String data = Padraodata(pegardataatual());
        //itens
        List lista = usuariosbuscadao.BuscarUsuariosEventos(busca);
        for (int i=0;i<lista.size();i++){
            usuariosbuscapojo = (UsuariosBuscaPOJO)lista.get(i);
            int sc = usuariosbuscapojo.getStatus_convite();
            String sc_msg="";
            switch (sc){
                case 0 : sc_msg="Administrador";break;
                case 1 : sc_msg="Pendente";break;
                case 2 : sc_msg="Participante";break;
            }
            DefaultTableModel dtm = (DefaultTableModel)tabela.getModel();
            dtm.addRow(new Object[]{usuariosbuscapojo.getNome(), sc_msg,
            usuariosbuscapojo.getId_Usuario(), usuariosbuscapojo.getPago()});
            ParticipantesTotal++;
            teste =false;
            if(teste){
                dtm.addRow(new Object[]{"André", "Convidado"});
                dtm.addRow(new Object[]{"Felipe", "Convidado"});
                dtm.addRow(new Object[]{"Amelia", "Convidado"});
                dtm.addRow(new Object[]{"Pedro", "Convidado"});
                dtm.addRow(new Object[]{"Ana Caroline", "Convidado"});
                dtm.addRow(new Object[]{"Joice", "Convidado"});
                dtm.addRow(new Object[]{"Felicia", "Convidado"});
                dtm.addRow(new Object[]{"fernando", "Convidado"});
                dtm.addRow(new Object[]{"Adriano", "Convidado"});
            }
        }
        ParticipantesTotalEventoSelecionado = ParticipantesTotal;
        String testeText = (teste)? ("Meus custos no evento R$ " + (100.00/4)).replace(".", ","): "";
        R_EvtHome_CustoIndividual.setText(testeText);
        R_EvtHome_MembrosTotal.setText("Total de participantes no evento: " + (ParticipantesTotal + ((teste)?9:0)));
    }    
    
    
    //metodos de tela
    public void TrocaTela(JPanel panel, int tela){
        Component comp[] = panel.getComponents();
        for (Component comp1 : comp){
            if((comp1).getName()!=null){
                String cp = comp1.getName();
                if ((comp1).getName().equals("Tela"+tela)){
                    (comp1).setVisible(true);
                    SetarFundo((JPanel)comp1);
                    System.out.println("Achou " + tela);
                    comp1.setLocation(0, 30);
                    comp1.setSize(new Dimension(330,800));
                    comp1.setPreferredSize(new Dimension(330,800));
                } else {
                    (comp1).setVisible(false);
                }
            }
        }
        tela = tela -1; 
        if(tela==0){//1=Desktop
            TelaAtual=tela;
        }
        if(tela==1){//2=Paleta
            TelaAtual=tela;
        }
        if(tela==2){//3=Login
            TelaAtual=tela;
        }
        if(tela==3){//4=cadastro criar
            TelaAtual=tela;
        }
        if(tela==4){//5=cadastro editar
            TelaAtual=tela;
        }
        if(tela==5){//6=home
            TelaAtual=tela;
            BuscarMeusEventos(T_Home_Eventos,Usuario,"Meus Eventos");
            AlternarGuia(CampoGuia, 1);
        }
        if(tela==6){//7 = evento criar
            TelaAtual=tela;
        }
        if(tela==7){//8 = evento editar
            TelaAtual=tela;
        }
        if(tela==8){//9 = evento home
            TelaAtual=tela;
            CarregarEvento();
        }
        if(tela==9){//10 = item criar
            TelaAtual=tela;
            CarregarItem();
        }
        if(tela==10){//11 = item editar
            TelaAtual=tela;
            CarregarItem();
        }
        if(tela==11){//12 = convidar
            TelaAtual=tela;
            OcultarCamposConvite("Todos",B_Convite_Cancelar,B_Convite_Convidar);
        }
        if(tela==100){ //100=Paleta
            TelaAtual=tela;
            PainelCT(Paleta);
        }
        MobileMuckUp.setVisible(false);
        MobileMuckUp.setVisible(true);
    }
    public void SetarFundo(JPanel panel){
        Component comp1[] = panel.getComponents();
        for (Component comp_1 : comp1){
                if (comp_1 instanceof JLabel){
                    if(((JLabel)comp_1).getText() != null){
                    if (((JLabel)comp_1).getText().equals("Fundo")){
                        ((JLabel)comp_1).setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GradientBackground.png")));
                        ((JLabel)comp_1).setLocation(0, 0);
                        ((JLabel)comp_1).setSize(330,630);
                    }}
        }
    }
    }
    public void Atualizador(){
        //todos os metodos que devem ser ativos precisam ser colocados aqui
        FecharSelecao(CS, Lista, CampoSelecao);
        FecharSelecao(CS2, Lista2, CampoSelecao2);
    }
    public void TrocarCampo(JPanel painel){
        PainelCT(painel);
        //painel.transferFocus();
        //transferFocusDownCycle();
    }
    public void ValoresAceitos (String valores,java.awt.event.KeyEvent evt){
        if (!valores.contains(evt.getKeyChar()+"")){
            evt.consume();
        }
}
    public void CampoUsuario(){
        PreencherCampo(CTO_Log_Usuario, "Insira seu e-mail ou telefone");

        if(VerificarEmail(CTO_Log_Usuario)){
            FundoCTOUsuario.setName("Ok");
            PainelCT(TLogin);
        } else {
            if (VerNumero(CTO_Log_Usuario)){
                FundoCTOUsuario.setName("Ok");
                PainelCT(TLogin);
            } else {
                FundoCTOUsuario.setName("Erro");
                PainelCT(TLogin);
            }
        }
        if (ConsultaUsuario(CTO_Log_Usuario)){ //verificar se usuario é cadastrado
            BCadastrarOULogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/BotaoGrande_Azul.png")));
            BCadastrarOULogin.setText("Login");
        }else{
            BCadastrarOULogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/BotaoGrande_Verde.png")));
            BCadastrarOULogin.setText("Cadastrar");
        }
}
    public void CarregarEvento(){
        //Carregar informações do evento
        System.out.println("Carregando evento");
        System.out.println(EventoSelecionado);
        eventospojo=eventosdao.BuscarEventoUnico(EventoSelecionado);
        EvtHome_Titulo_Evento.setText(eventospojo.getTitulo());
        int host = eventospojo.getHost();
        usuariosbuscapojo=usuariosbuscadao.BuscarUsuarioUnico(host);
        EvtHome_Host.setText("Administrador: "+usuariosbuscapojo.getNome());
        EvtHome_Hora.setText((eventospojo.getHorario().substring(0,5)));
        EvtHome_Data.setText(DataTela(eventospojo.getData()));
        EvtHome_Descricao.setText(eventospojo.getDescricao());
        BuscarItensDeEventos(T_EvtHome_Itens,EventoSelecionado);
        BuscarParticipantesDeEvento(T_EvtHome_Participantes,EventoSelecionado);
        HostEvento= eventospojo.getHost();
    }
    
    public void CarregarItem(){
        //Carregar informações do evento
        System.out.println("Carregando item");
        System.out.println(ItemSelecionado);
        itenspojo= itensdao.BuscarItemUnico(ItemSelecionado);
        BOX_CTO_CadItem_Titulo.setText(itenspojo.getTitulo());
        BOX_CTO_CadItem_Descricao.setText(itenspojo.getDescricao());
        BOX_CTO_CadItem_Valor.setText(itenspojo.getValoritem() + "".replace(".", ","));
        BOX_CT_CadItem_Pagante.setText(itenspojo.getPagante());
        LimparBusca(jTable4);
        DefaultTableModel dtm = (DefaultTableModel)jTable4.getModel();
        //só para fins de protótipo
        if(itenspojo.getTitulo().equals("Churrasco de boi") && teste){
            dtm.addRow(new Object[]{"André"});
            dtm.addRow(new Object[]{"Felipe"});
            dtm.addRow(new Object[]{"Amelia"});
            dtm.addRow(new Object[]{"Pedro"});
            dtm.addRow(new Object[]{"Ana Caroline"});
            dtm.addRow(new Object[]{"Joice"});
        } else if(teste) {
            dtm.addRow(new Object[]{usuariosbuscapojo.getNome()});
            dtm.addRow(new Object[]{"Felicia"});
            dtm.addRow(new Object[]{"fernando"});
            dtm.addRow(new Object[]{"Adriano"});
        }
        
    }
    public void TrocarGuiaEventoHome(int op){
        if (op==1){
            System.out.println("descricao");
            PDescricaoEvento.setLocation(0, 30);
            PValoresEvento.setLocation(0, 100);
            PDescricaoEvento.setVisible(true);
            PValoresEvento.setVisible(false);
            B_EvtHome_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 12));
            B_EvtHome_Valores.setFont(new java.awt.Font("Segoe UI", 0, 12));
            B_EvtHome_Valores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaInativa.png")));
            B_EvtHome_Descricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaAtiva.png")));
        }
        if (op==2){
            System.out.println("valores");
            PDescricaoEvento.setLocation(0, 100);
            PValoresEvento.setLocation(0, 30);
            PDescricaoEvento.setVisible(false);
            PValoresEvento.setVisible(true);
            B_EvtHome_Descricao.setName("setada");
            B_EvtHome_Valores.setName("naosetada");
            B_EvtHome_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 12));
            B_EvtHome_Valores.setFont(new java.awt.Font("Segoe UI", 0, 12));
            B_EvtHome_Valores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaAtiva.png")));
            B_EvtHome_Descricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaInativa.png")));
        }
    }
    //tela de convite
    public void OcultarCamposConvite(String modo, JButton b1, JButton b2){
        if(modo.equals("Todos")){
            CT_Convite_Email2.setVisible(false);
            CT_Convite_Email3.setVisible(false);
            CT_Convite_Email4.setVisible(false);
            CT_Convite_Email5.setVisible(false);
            CT_Convite_Email6.setVisible(false);
        }
        switch (modo){
            case "Todos" : ;break;
            case "2" : CT_Convite_Email2.setVisible(true);break;
            case "3" : CT_Convite_Email3.setVisible(true);break;
            case "4" : CT_Convite_Email4.setVisible(true);break;
            case "5" : CT_Convite_Email5.setVisible(true);break;
            case "6" : CT_Convite_Email6.setVisible(true);break;
        }
        MoverBotoesConvite(modo, b1, b2);
    }
    public void MoverBotoesConvite(String modo, JButton b1, JButton b2){
        
        switch (modo){
            case "Todos" : b1.setLocation(20,125); b2.setLocation(170,125); break;
            case "2" : b1.setLocation(20,190); b2.setLocation(170,190); break;
            case "3" : b1.setLocation(20,255); b2.setLocation(170,255); break;
            case "4" : b1.setLocation(20,320); b2.setLocation(170,320); break;
            case "5" : b1.setLocation(20,385); b2.setLocation(170,385); break;
            case "6" : b1.setLocation(20,450); b2.setLocation(170,450); break;
        }
    }
    public boolean EmailCheck(JTextField campo, int op){
	boolean saida = false;
        String email = campo.getText();
        op = op-1;
        System.out.println(op+1);
                System.out.println("e-mail atual "+L_email.get(op));
		if(L_email.get(op).equals(""+op)){
                    System.out.println("sem email ");
                    System.out.println("Verificando se é email");
                    saida=false;
                    System.out.println(VerificarEmail(campo));
			if (VerificarEmail(campo)){
                            System.out.println("é email!");
                            System.out.println("verificar se há cadastro");
                                usuariosbuscapojo=usuariosbuscadao.VerificarDuplicidade("email", email);
				if (usuariosbuscapojo.getId_Usuario() != 0){
					System.out.println("Tem cadastro!");
                                        L_email.remove(op);
                                        L_nome_email.remove(op);
                                        L_email.add(op, campo.getText());
                                        L_nome_email.add(op, usuariosbuscapojo.getNome());
                                        System.out.println("E-mail: "+L_email.get(op));
                                        System.out.println("Nome: "+L_nome_email.get(op));
                                        System.out.println("setar campo com nome do cadastro");
					campo.setText(usuariosbuscapojo.getNome());
					campo.setEnabled(false);
					campo.transferFocus();
					saida=true;
				}if(usuariosbuscapojo.getId_Usuario() == 0){
                                        System.out.println("não há cadastro");
                                        L_email.remove(op);
					L_email.add(op,campo.getText());
                                        System.out.println("Salvando o email: "+L_email.get(op));
					campo.setText("Agora digite um apelido");
					campo.setEnabled(true);
					saida=true;
                                }
				}
		}
                System.out.println("Verificando se algum nome foi salvo");
		if(L_nome_email.get(op).equals("")){
                        System.out.println("Nenhum nome!");
			saida = false;
		}
                System.out.println("Verificando booleanos __________________________");
                System.out.println(!campo.getText().equals("Agora digite um apelido"));
                System.out.println(!campo.getText().equals(""));
                System.out.println(campo.getText() != null);
                System.out.println(!L_email.get(op).equals(""));
                System.out.println(L_email.get(op));
                System.out.println("Verificando booleanos __________________________");
                
		if(!campo.getText().equals("Agora digite um apelido") && !campo.getText().equals("") && campo.getText() != null && !L_email.get(op).equals(""+op)) {
                        L_nome_email.remove(op);
                        L_nome_email.add(op, campo.getText());
                        System.out.println("Nome encontrado: "+L_nome_email.get(op));
                        campo.setEnabled(false);
			saida = true;
                        campo.transferFocus();
                }
return saida;}
    public String GerarCodigoDeConfirmacao(String data){
        Random gerar = new Random();
        String codigo="";
        ArrayList<String> valores1 = new ArrayList<>(Arrays.asList("a3","es","g5","vb","3r","aw","fv","sE","7U","9o"));
        ArrayList<String> valores2 = new ArrayList<>(Arrays.asList("q0","D3","cz","E1","o8","5n","1b","49","f6","hB"));
        ArrayList<String> valores3 = new ArrayList<>(Arrays.asList("Dl","g2","vj","Lx","c9","j7","0c","sX","zq","n9"));
        ArrayList<String> valores4 = new ArrayList<>(Arrays.asList("k8","x3","S6","cn","mX","a7","lv","Cr","4j","lp"));
        ArrayList<String> valores5 = new ArrayList<>(Arrays.asList("hA","Vp","sw","la","8c","7q","gl","qo","cz","Xe"));
        
        for (int i = 0; i<data.length();i++){
            if(i<=3){
                int v = Integer.parseInt(data.substring(i,i+1));
                int r = gerar.nextInt(4);
                ArrayList<String> base = new ArrayList<>();
                switch (r){
                    case 0 : base = valores1;break;
                    case 1 : base = valores2;break;
                    case 2 : base = valores3;break;
                    case 3 : base = valores4;break;
                    case 4 : base = valores5;break;
                }
                codigo = codigo + base.get(v);
            }
            if(i>=4 && i<=5){
                int v = Integer.parseInt(data.substring(i,i+1));
                int r = gerar.nextInt(4);
                ArrayList<String> base = new ArrayList<>();
                switch (r){
                    case 0 : base = valores1;break;
                    case 1 : base = valores2;break;
                    case 2 : base = valores3;break;
                    case 3 : base = valores4;break;
                    case 4 : base = valores5;break;
                }
                codigo = codigo + base.get(v);
            }
            if(i>5){
                int v = Integer.parseInt(data.substring(i,i+1));
                int r = gerar.nextInt(4);
                ArrayList<String> base = new ArrayList<>();
                switch (r){
                    case 0 : base = valores1;break;
                    case 1 : base = valores2;break;
                    case 2 : base = valores3;break;
                    case 3 : base = valores4;break;
                    case 4 : base = valores5;break;
                }
                codigo = codigo + base.get(v);
            }
        }
    return codigo;}
    
    public void PreenchimentoRapido(){
        //1= Desktop
        //2= Paleta
        //3= Login
        //4= cadastro criar
        //5= cadastro editar
        //6= home
        //7= evento criar
        //8= evento editar
        //9= evento home
        //10= add item
        //11= convidar
        //100= Paleta
        
        int tela = TelaAtual+1;
        System.out.println(TelaAtual);
        if (tela == 3){
            CTO_Log_Usuario. setText("rodrigo@derole.com.br");
            CTO_Log_Senha.setText("12345");
            PainelCT(TLogin);
            CampoUsuario();
        }
        if(tela == 4){
            BOX_CTO_Cad_Email.setText("adm@derole.com.br");
            BOX_CT_Cad_Celular.setText("47911223344");
            BOX_CTO_Cad_Nome.setText("Administrador");
            BOX_CTO_Cad_Senha.setText("12345");
            BOX_CTO_Cad_SenhaConfirma.setText("12345");
            BOX_CT_Cad_DataNascimento.setText("2021-12-03");
            CSSelecionado6.setText("Não binário");
            PainelCT(TCadastro);
        }
        if(tela == 7){
            BOX_CTO_CadEvt_Titulo.setText("Evento");
            BOX_CTO_CadEvt_Descricao.setText("Evento para teste de cadastramento");
            BOX_CTO_CadEvt_Data.setText("2021-12-25");
            BOX_CT_CadEvt_Horario.setText("12:00");
            BOX_CS_CadEvt_Idade.setText("Livre");
            BOX_CS_CadEvt_Visibilidade.setText("Oculto");
            PainelCT(TEvento_Criar);
        }
        if(tela == 10){
            BOX_CTO_CadItem_Titulo.setText("Item");
            BOX_CTO_CadItem_Descricao.setText("Item para teste");
            BOX_CTO_CadItem_Valor.setText("100,00");
            BOX_CT_CadItem_Pagante.setText("Olivia");
            PainelCT(TEvento_Criar);
        }
    }
    
        //metodos de tela - LOGIN
    public int TelaLoginStatus(){
        String Usuario = CTO_Log_Usuario.getText();
        char[] s = CTO_Log_Senha.getPassword();
        String Senha = String.valueOf(s);
        int Status = 0;
        
        if(Usuario.equals("Insira seu e-mail ou telefone") || Usuario.equals("") || Usuario == null){
            
        } else{
            if (ConsultaFinalLogin(CTO_Log_Usuario)){
            Status = Status +1;
            }
        }
        if(Senha.equals("") && Senha == null){
            
        } else{
            Status = Status +2;
        }
        return Status;
    }
    public void LoginParaCadastro(){
        String usuario = CTO_Log_Usuario.getText();
        char[] s = CTO_Log_Senha.getPassword();
        String senha = String.valueOf(s);
        if (!VerificarEmail(CTO_Log_Usuario)){
            BOX_CTO_Cad_Email.setText("E-mail");
            if (VerNumero(CTO_Log_Usuario)){
                BOX_CT_Cad_Celular.setText(usuario);
                BG_CT_Cad_Celular.setName("Ok");
            } else{
                BOX_CT_Cad_Celular.setText("E-mail");
            }
        }else{
            BOX_CTO_Cad_Email.setText(usuario);
            BG_CTO_Cad_Email.setName("Ok");
            
        }
        BOX_CTO_Cad_Senha.setText(senha);
        BG_CTO_Cad_Senha.setName("Ok");
        PainelCT(TCadastro);
    }
    
    //metodos de componentes personalizados
    public void LimparCampo(JTextField campo, String texto, boolean forcar){
        if (!forcar){
        if (campo.getText().equals(texto)){
        campo.setText("");
        }} else {
            campo.setText("");
        }
    }
    public void PreencherCampo(JTextField campo, String texto){
        if (campo.getText().equals("")){
        campo.setText(texto);
        }
    }
    public void CaixaSelecao (JPanel lista,JLabel campo, JPanel CS){
        lista.setVisible(!lista.isVisible());  
        if(CS.getName().equals("F")){
            campo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSA_Cinza.png")));
            CS.setSize(170,150);
            CS.setName("A");
        }else{
        if (CS.getName().equals("A")){
            campo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png")));
            CS.setSize(170,50);
            CS.setName("F");
        }}
        
    }
    public void FecharSelecao (JPanel panel, JPanel lista, JLabel camposelecao){
        if(panel.getName().equals("A")){
                CaixaSelecao(lista, camposelecao, panel);
                panel.setSize(170,50);}
                panel.setName("F");
    }
    
    //metodos de datas
    public String pegardataatual (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        String data = dtf.format(LocalDateTime.now());
    return data;}
    public String VerificarData (JTextField entrada,boolean set){
	String data = pegardataatual();
        String dia = "0"; 
	String mes = "0";
	String ano = "0";
	String ent = entrada.getText();
	String saida = "";
	int et = entrada.getText().length(); //et Entrada Tamanho
        
        int[] meses = {1,2,3,4,5,6,7,8,9,10,11,12};
        int[] diasdomes = {31,28,31,30,31,30,30,31,30,31,30,31};
        if (ent.substring(2,3).equals("/") && ent.substring(5,6).equals("/")){
            saida=ent;
        } else {
        if (et == 6) {
		dia = ent.substring(0,2);
		mes = ent.substring(2,4);
		ano = "20"+ent.substring(4,6);
	}
	if (et == 8) {
		dia = ent.substring(0,2);
		mes = ent.substring(2,4);
		ano = ent.substring(4,8);
	}
        
        boolean diav=false;
        boolean mesv=false;
        boolean anov=true;
        int diaV=Integer.parseInt(dia);
        int mesV=Integer.parseInt(mes);
        
        if(et<6 || et >8||et==7||diaV>31||mesV>12||diaV==0||mesV==0){
            saida="";
        }else{
        if ((Integer.parseInt(ano)%4)==0){
            diasdomes [1] = 29;
            if (diaV>diasdomes [mesV]){
                    diav=false;
                } else{
                    diav=true;
                }
        } else {
            if (diaV>diasdomes [mesV-1]){
                    diav=false;
                } else{
                    diav=true;
        }
        }
        if (mesV<12||mesV>0){
            mesv=true;
        }
        
        if (set) {
            if (Integer.parseInt(data)> Integer.parseInt(ano+mes+dia)){
                diav=false;
                mesv=false;
                anov=false;
            }
        }
        
        if (diav&&mesv&&anov) {
            saida = dia+"/"+mes+"/"+ano;
        }else{
            saida="";
        }}}  
return saida;}
    public String Padraodata(String data){
        String saida = "";
        
        /**
         * 20211203
         * 01234567
         * 12/04/1995
         * 0123456789
        */
        if(data.length()==8){
            saida = data.substring(0,4)+"-"+data.substring(4,6)+"-"+data.substring(6,8);
        }
        if(data.length()==10){
            saida = data.substring(6,10)+"-"+data.substring(3,5)+"-"+data.substring(0,2);
        }
        
        
    return saida;}
    public String DataTela(String data){
        //2021-01-01
        String resultado= data.substring(8,10)+"/"+data.substring(5,7)+"/"+data.substring(0,4);
    return resultado;}
   
    
    
    //metodos de alterações de tela
    public void AlternarGuia(JLabel campo, int pos){
        
        switch(pos){
            case 1 : campo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GUIAS/Guias-01.png")));break;
            case 2 : campo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GUIAS/Guias-02.png")));break;
            case 3 : campo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GUIAS/Guias-03.png")));break;
        }
        
    }
    
    public void AlterarComponente (JPanel panel,boolean obrigatorio,String tipo){
        Component comp[] = panel.getComponents();
        for (Component comp1 : comp){
            if (comp1 instanceof JLabel){
                    MudarCampo(((JLabel)comp1), obrigatorio,tipo);
}
}
}
    
    public void PainelCT(JPanel panel){
        Component comp[] = panel.getComponents();
        for (Component comp1 : comp){
            if((comp1).getName()!=null){
                String cp = comp1.getName();
                if ((comp1).getName().equals("CampoTexto")){
                    AlterarComponente((JPanel)comp1,false,"CT");
                }
                if ((comp1).getName().equals("CampoTexto*")){
                    AlterarComponente((JPanel)comp1,true,"CT");
                }
                if ((comp1).getName().equals("CampoTextoLongo")){
                    AlterarComponente((JPanel)comp1,false,"CTL");
                }
                if ((comp1).getName().equals("CampoTextoLongo*")){
                    AlterarComponente((JPanel)comp1,true,"CTL");
                }
                
        }
    }}
    
    public void MudarCampo(JLabel label,boolean obrigatorio,String tipo){
        if(tipo.equals("CT")){
            if(!obrigatorio){
                switch(label.getName()) {
                    case "Ativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png")));break;
                    case "Inativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png")));break;
                    case "Erro" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Erro.png")));break;
                    case "Ok" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ok.png")));break;
                    case "Alerta" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Alerta.png")));break;
                }}
            if(obrigatorio){
                switch(label.getName()) {
                    case "Ativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTObrigatorio_Ativo.png")));break;
                    case "Inativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTObrigatorio_Inativo.png")));break;
                    case "Erro" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTObrigatorio_Erro.png")));break;
                    case "Ok" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTObrigatorio_Ok.png")));break;
                    case "Alerta" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CTObrigatorio/CT_Alerta.png")));break;
                }}}
            if(tipo.equals("CTL")){
                if(!obrigatorio){
                switch(label.getName()) {
                    case "Ativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongo_Ativo.png")));break;
                    case "Erro" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongo_Erro.png")));break;
                    }}
                if(obrigatorio){
                switch(label.getName()) {
                    case "Ativo" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongoObrigatorio_Ativo.png")));break;
                    case "Erro" : label.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongoObrigatorio_Erro.png")));break;
                    }}
        }}
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        MobileMuckUp = new javax.swing.JLabel();
        PainelPrincipal = new javax.swing.JPanel();
        DevTools = new javax.swing.JPanel();
        BSair = new javax.swing.JButton();
        Navegar = new javax.swing.JComboBox<>();
        BPreencherRapido = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        TLogin = new javax.swing.JPanel();
        Fundo3 = new javax.swing.JLabel();
        CTOUsuario = new javax.swing.JPanel();
        CTO_Log_Usuario = new javax.swing.JTextField();
        FundoCTOUsuario = new javax.swing.JLabel();
        CTOSenha = new javax.swing.JPanel();
        CTO_Log_Senha = new javax.swing.JPasswordField();
        ImagemFundo8 = new javax.swing.JLabel();
        BCadastrarOULogin = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        MsgLogin = new javax.swing.JLabel();
        B_Log_EsqueciSenha = new javax.swing.JLabel();
        Background2 = new javax.swing.JLabel();
        Desktop = new javax.swing.JPanel();
        BIniciarApp = new javax.swing.JButton();
        Fundo = new javax.swing.JLabel();
        Paleta = new javax.swing.JPanel();
        Fundo1 = new javax.swing.JLabel();
        Rotulo = new javax.swing.JLabel();
        CTO = new javax.swing.JPanel();
        CampoTexto = new javax.swing.JTextField();
        ImagemFundo = new javax.swing.JLabel();
        CTO1 = new javax.swing.JPanel();
        CampoSenha = new javax.swing.JPasswordField();
        ImagemFundo2 = new javax.swing.JLabel();
        CS = new javax.swing.JPanel();
        Lista = new javax.swing.JPanel();
        Opc1 = new javax.swing.JButton();
        Opc2 = new javax.swing.JButton();
        Opc3 = new javax.swing.JButton();
        Opc4 = new javax.swing.JButton();
        CaixaBotton = new javax.swing.JLabel();
        CaixaMeio = new javax.swing.JLabel();
        CaixaTop2 = new javax.swing.JLabel();
        CSSelecionado = new javax.swing.JLabel();
        AbrirSelecao = new javax.swing.JButton();
        CampoSelecao = new javax.swing.JLabel();
        CS2 = new javax.swing.JPanel();
        Lista2 = new javax.swing.JPanel();
        Opc1_2 = new javax.swing.JButton();
        Opc2_2 = new javax.swing.JButton();
        Opc3_2 = new javax.swing.JButton();
        Opc4_2 = new javax.swing.JButton();
        CaixaBotton2 = new javax.swing.JLabel();
        CaixaMeio2 = new javax.swing.JLabel();
        CaixaTop4 = new javax.swing.JLabel();
        CSSelecionado2 = new javax.swing.JLabel();
        AbrirSelecao2 = new javax.swing.JButton();
        CampoSelecao2 = new javax.swing.JLabel();
        CTO2 = new javax.swing.JPanel();
        CampoTextoLongo = new javax.swing.JScrollPane();
        TesteTexto = new javax.swing.JTextArea();
        ImagemFundo3 = new javax.swing.JLabel();
        BTestes = new javax.swing.JButton();
        Background = new javax.swing.JLabel();
        TCadastro = new javax.swing.JPanel();
        Fundo4 = new javax.swing.JLabel();
        CTO_Cad_Email = new javax.swing.JPanel();
        BOX_CTO_Cad_Email = new javax.swing.JTextField();
        BG_CTO_Cad_Email = new javax.swing.JLabel();
        CT_Cad_Celular = new javax.swing.JPanel();
        BOX_CT_Cad_Celular = new javax.swing.JTextField();
        BG_CT_Cad_Celular = new javax.swing.JLabel();
        CTO_Cad_Nome = new javax.swing.JPanel();
        BOX_CTO_Cad_Nome = new javax.swing.JTextField();
        BG_CTO_Cad_Nome = new javax.swing.JLabel();
        CTO_Cad_Senha = new javax.swing.JPanel();
        BOX_CTO_Cad_Senha = new javax.swing.JPasswordField();
        BG_CTO_Cad_Senha = new javax.swing.JLabel();
        CTO_Cad_SenhaConfirma = new javax.swing.JPanel();
        BOX_CTO_Cad_SenhaConfirma = new javax.swing.JPasswordField();
        BG_CTO_Cad_SenhaConfirma = new javax.swing.JLabel();
        CT_Cad_DataNascimento = new javax.swing.JPanel();
        BOX_CT_Cad_DataNascimento = new javax.swing.JTextField();
        BG_CT_Cad_DataNascimento = new javax.swing.JLabel();
        CS_Cad_Genero = new javax.swing.JPanel();
        Lista6 = new javax.swing.JPanel();
        Opc13 = new javax.swing.JButton();
        Opc14 = new javax.swing.JButton();
        Opc15 = new javax.swing.JButton();
        Opc16 = new javax.swing.JButton();
        CaixaBotton6 = new javax.swing.JLabel();
        CaixaMeio6 = new javax.swing.JLabel();
        CaixaTop8 = new javax.swing.JLabel();
        CSSelecionado6 = new javax.swing.JLabel();
        AbrirSelecao6 = new javax.swing.JButton();
        BG_CS_Cad_Genero = new javax.swing.JLabel();
        B_Cad_LimparTudo = new javax.swing.JButton();
        B_Cad_Cancelar = new javax.swing.JButton();
        B_Cad_Cadastrar = new javax.swing.JButton();
        MSGCad = new javax.swing.JLabel();
        Background3 = new javax.swing.JLabel();
        TCadastro_Editar = new javax.swing.JPanel();
        Background4 = new javax.swing.JLabel();
        THome = new javax.swing.JPanel();
        Guias = new javax.swing.JPanel();
        B_Home_Realizado = new javax.swing.JButton();
        B_Home_Comunidade = new javax.swing.JButton();
        B_Home_MeusEventos = new javax.swing.JButton();
        CampoGuia = new javax.swing.JLabel();
        Fundo6 = new javax.swing.JLabel();
        CTO10 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        BOX_CT_Home_Procurar = new javax.swing.JTextField();
        ImagemFundo15 = new javax.swing.JLabel();
        B_Home_NovoEvento = new javax.swing.JButton();
        B_Home_Participar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        T_Home_Eventos = new javax.swing.JTable();
        Background5 = new javax.swing.JLabel();
        TEvento_Criar = new javax.swing.JPanel();
        Fundo7 = new javax.swing.JLabel();
        CTO13 = new javax.swing.JPanel();
        BOX_CTO_CadEvt_Titulo = new javax.swing.JTextField();
        ImagemFundo20 = new javax.swing.JLabel();
        CTO3 = new javax.swing.JPanel();
        CampoTextoLongo1 = new javax.swing.JScrollPane();
        BOX_CTO_CadEvt_Descricao = new javax.swing.JTextArea();
        ImagemFundo4 = new javax.swing.JLabel();
        CT18 = new javax.swing.JPanel();
        BOX_CTO_CadEvt_Data = new javax.swing.JTextField();
        ImagemFundo45 = new javax.swing.JLabel();
        CT7 = new javax.swing.JPanel();
        BOX_CT_CadEvt_Horario = new javax.swing.JTextField();
        ImagemFundo19 = new javax.swing.JLabel();
        CS4 = new javax.swing.JPanel();
        BOX_CS_CadEvt_Idade = new javax.swing.JLabel();
        AbrirSelecao4 = new javax.swing.JButton();
        CampoSelecao4 = new javax.swing.JLabel();
        CS3 = new javax.swing.JPanel();
        BOX_CS_CadEvt_Visibilidade = new javax.swing.JLabel();
        AbrirSelecao3 = new javax.swing.JButton();
        CampoSelecao3 = new javax.swing.JLabel();
        B_CadEvt_Criar = new javax.swing.JButton();
        B_CadEvt_Cancelar = new javax.swing.JButton();
        Background6 = new javax.swing.JLabel();
        TEvento_Editar = new javax.swing.JPanel();
        Fundo8 = new javax.swing.JLabel();
        Background7 = new javax.swing.JLabel();
        TEvento_Home = new javax.swing.JPanel();
        PDescricaoValores = new javax.swing.JPanel();
        B_EvtHome_Descricao = new javax.swing.JButton();
        B_EvtHome_Valores = new javax.swing.JButton();
        PDescricaoEvento = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        EvtHome_Descricao = new javax.swing.JTextArea();
        PValoresEvento = new javax.swing.JPanel();
        R_EvtHome_CustoTotal = new javax.swing.JLabel();
        R_EvtHome_MembrosTotal = new javax.swing.JLabel();
        R_EvtHome_CustoIndividual = new javax.swing.JLabel();
        Rotulo8 = new javax.swing.JLabel();
        jscrollll = new javax.swing.JScrollPane();
        T_EvtHome_Participantes = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        T_EvtHome_Itens = new javax.swing.JTable();
        Rotulo9 = new javax.swing.JLabel();
        EvtHome_Data = new javax.swing.JLabel();
        B_EvtHome_Convidar = new javax.swing.JButton();
        B_EvtHome_Ver = new javax.swing.JButton();
        B_EvtHome_Sair = new javax.swing.JButton();
        B_EvtHome_Novo = new javax.swing.JButton();
        EvtHome_Titulo_Evento = new javax.swing.JLabel();
        EvtHome_Host = new javax.swing.JLabel();
        EvtHome_Hora = new javax.swing.JLabel();
        Background8 = new javax.swing.JLabel();
        TItens_Adicionar = new javax.swing.JPanel();
        B_CadItem_Criar = new javax.swing.JButton();
        Fundo10 = new javax.swing.JLabel();
        CT10 = new javax.swing.JPanel();
        BOX_CT_CadItem_Pagante = new javax.swing.JTextField();
        ImagemFundo28 = new javax.swing.JLabel();
        BotaoM9 = new javax.swing.JButton();
        CTO19 = new javax.swing.JPanel();
        BOX_CTO_CadItem_Titulo = new javax.swing.JTextField();
        ImagemFundo29 = new javax.swing.JLabel();
        CT20 = new javax.swing.JPanel();
        BOX_CTO_CadItem_Valor = new javax.swing.JTextField();
        ImagemFundo51 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable4 = new javax.swing.JTable();
        CTO8 = new javax.swing.JPanel();
        CampoTextoLongo2 = new javax.swing.JScrollPane();
        BOX_CTO_CadItem_Descricao = new javax.swing.JTextArea();
        ImagemFundo5 = new javax.swing.JLabel();
        Background9 = new javax.swing.JLabel();
        TItens_VerEditar = new javax.swing.JPanel();
        Fundo11 = new javax.swing.JLabel();
        Background10 = new javax.swing.JLabel();
        TConvidar = new javax.swing.JPanel();
        Fundo12 = new javax.swing.JLabel();
        Rotulo11 = new javax.swing.JLabel();
        CT_Convite_Email1 = new javax.swing.JPanel();
        CTBOX_Convite_Email1 = new javax.swing.JTextField();
        BG_Convite_Email1 = new javax.swing.JLabel();
        CT_Convite_Email2 = new javax.swing.JPanel();
        CTBOX_Convite_Email2 = new javax.swing.JTextField();
        BG_Convite_Email2 = new javax.swing.JLabel();
        CT_Convite_Email3 = new javax.swing.JPanel();
        CTBOX_Convite_Email3 = new javax.swing.JTextField();
        BG_Convite_Email3 = new javax.swing.JLabel();
        CT_Convite_Email4 = new javax.swing.JPanel();
        CTBOX_Convite_Email4 = new javax.swing.JTextField();
        BG_Convite_Email4 = new javax.swing.JLabel();
        CT_Convite_Email5 = new javax.swing.JPanel();
        CTBOX_Convite_Email5 = new javax.swing.JTextField();
        BG_Convite_Email5 = new javax.swing.JLabel();
        CT_Convite_Email6 = new javax.swing.JPanel();
        CTBOX_Convite_Email6 = new javax.swing.JTextField();
        BG_Convite_Email6 = new javax.swing.JLabel();
        B_Convite_Cancelar = new javax.swing.JButton();
        B_Convite_Convidar = new javax.swing.JButton();
        Background11 = new javax.swing.JLabel();
        TSolicitacoes = new javax.swing.JPanel();
        Fundo13 = new javax.swing.JLabel();
        Rotulo12 = new javax.swing.JLabel();
        Rotulo14 = new javax.swing.JLabel();
        BotaoM23 = new javax.swing.JButton();
        BotaoM24 = new javax.swing.JButton();
        BotaoM25 = new javax.swing.JButton();
        BotaoM26 = new javax.swing.JButton();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable6 = new javax.swing.JTable();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable9 = new javax.swing.JTable();
        Background12 = new javax.swing.JLabel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 32767));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });
        getContentPane().setLayout(null);

        MobileMuckUp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/Mobile.png"))); // NOI18N
        MobileMuckUp.setDoubleBuffered(true);
        MobileMuckUp.setFocusable(false);
        getContentPane().add(MobileMuckUp);
        MobileMuckUp.setBounds(0, 0, 365, 650);

        PainelPrincipal.setBackground(new java.awt.Color(255, 255, 255));

        BSair.setBackground(new java.awt.Color(204, 0, 51));
        BSair.setFont(new java.awt.Font("Tahoma", 1, 8)); // NOI18N
        BSair.setForeground(new java.awt.Color(255, 255, 255));
        BSair.setText("X");
        BSair.setBorderPainted(false);
        BSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BSairActionPerformed(evt);
            }
        });

        Navegar.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "DESKTOP", "PALETA", "LOGIN", "CADASTRO", "CADASTRO EDIT", "HOME", "EVENTO CRIAR", "EVENTO EDITAR", "EVENTO HOME", "ITENS ADD", "ITENS VER", "CONVIDAR", "SOLICITAÇÕES" }));
        Navegar.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                NavegarItemStateChanged(evt);
            }
        });

        BPreencherRapido.setBackground(new java.awt.Color(51, 255, 51));
        BPreencherRapido.setText("Preencher rapido");
        BPreencherRapido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BPreencherRapidoActionPerformed(evt);
            }
        });

        jLabel3.setText("2");

        jLabel4.setText("3");

        jLabel5.setText("4");

        jLabel6.setText("5");

        jLabel7.setText("6");

        jLabel8.setText("7");

        jLabel9.setText("8");

        jLabel10.setText("9");

        jLabel11.setText("10");

        jLabel12.setText("11");

        jLabel13.setText("12");

        jLabel14.setText("13");

        javax.swing.GroupLayout DevToolsLayout = new javax.swing.GroupLayout(DevTools);
        DevTools.setLayout(DevToolsLayout);
        DevToolsLayout.setHorizontalGroup(
            DevToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DevToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Navegar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BPreencherRapido, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BSair)
                .addGap(246, 246, 246)
                .addComponent(jLabel3)
                .addGap(340, 340, 340)
                .addComponent(jLabel4)
                .addGap(323, 323, 323)
                .addComponent(jLabel5)
                .addGap(335, 335, 335)
                .addComponent(jLabel6)
                .addGap(325, 325, 325)
                .addComponent(jLabel7)
                .addGap(318, 318, 318)
                .addComponent(jLabel8)
                .addGap(354, 354, 354)
                .addComponent(jLabel9)
                .addGap(310, 310, 310)
                .addComponent(jLabel10)
                .addGap(327, 327, 327)
                .addComponent(jLabel11)
                .addGap(344, 344, 344)
                .addComponent(jLabel12)
                .addGap(302, 302, 302)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel14)
                .addGap(153, 153, 153))
        );
        DevToolsLayout.setVerticalGroup(
            DevToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DevToolsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DevToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DevToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(BSair)
                        .addComponent(Navegar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(BPreencherRapido))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DevToolsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8)
                        .addComponent(jLabel9)
                        .addComponent(jLabel10)
                        .addComponent(jLabel11)
                        .addComponent(jLabel12)
                        .addComponent(jLabel13)
                        .addComponent(jLabel14))))
        );

        TLogin.setBackground(new java.awt.Color(255, 255, 255));
        TLogin.setName("Tela3"); // NOI18N
        TLogin.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TLogin.add(Fundo3);
        Fundo3.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        CTOUsuario.setName("CampoTexto*"); // NOI18N
        CTOUsuario.setOpaque(false);
        CTOUsuario.setLayout(null);

        CTO_Log_Usuario.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTO_Log_Usuario.setText("Insira seu e-mail ou telefone");
        CTO_Log_Usuario.setBorder(null);
        CTO_Log_Usuario.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CTO_Log_UsuarioFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                CTO_Log_UsuarioFocusLost(evt);
            }
        });
        CTO_Log_Usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTO_Log_UsuarioActionPerformed(evt);
            }
        });
        CTOUsuario.add(CTO_Log_Usuario);
        CTO_Log_Usuario.setBounds(10, 10, 250, 30);

        FundoCTOUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        FundoCTOUsuario.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        FundoCTOUsuario.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        FundoCTOUsuario.setName("Ativo"); // NOI18N
        CTOUsuario.add(FundoCTOUsuario);
        FundoCTOUsuario.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TLogin.add(CTOUsuario);
        CTOUsuario.setBounds(20, 230, 290, 60);

        CTOSenha.setName("CampoTexto*"); // NOI18N
        CTOSenha.setOpaque(false);
        CTOSenha.setLayout(null);

        CTO_Log_Senha.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTO_Log_Senha.setText("Senha123@#");
        CTO_Log_Senha.setBorder(null);
        CTO_Log_Senha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                CTO_Log_SenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                CTO_Log_SenhaFocusLost(evt);
            }
        });
        CTO_Log_Senha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTO_Log_SenhaActionPerformed(evt);
            }
        });
        CTOSenha.add(CTO_Log_Senha);
        CTO_Log_Senha.setBounds(10, 10, 250, 30);

        ImagemFundo8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo8.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo8.setName("Ativo"); // NOI18N
        CTOSenha.add(ImagemFundo8);
        ImagemFundo8.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TLogin.add(CTOSenha);
        CTOSenha.setBounds(20, 300, 290, 70);

        BCadastrarOULogin.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BCadastrarOULogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/BotaoGrande_Verde.png"))); // NOI18N
        BCadastrarOULogin.setText("Cadastrar");
        BCadastrarOULogin.setBorder(null);
        BCadastrarOULogin.setBorderPainted(false);
        BCadastrarOULogin.setContentAreaFilled(false);
        BCadastrarOULogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BCadastrarOULogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BCadastrarOULoginActionPerformed(evt);
            }
        });
        TLogin.add(BCadastrarOULogin);
        BCadastrarOULogin.setBounds(20, 410, 290, 53);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/logo150.png"))); // NOI18N
        TLogin.add(jLabel1);
        jLabel1.setBounds(90, 50, 150, 150);

        MsgLogin.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        MsgLogin.setForeground(new java.awt.Color(204, 0, 0));
        MsgLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MsgLogin.setText("Msg de status");
        MsgLogin.setText("");
        TLogin.add(MsgLogin);
        MsgLogin.setBounds(30, 370, 260, 25);

        B_Log_EsqueciSenha.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        B_Log_EsqueciSenha.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        B_Log_EsqueciSenha.setText("Esqueci minha senha.");
        TLogin.add(B_Log_EsqueciSenha);
        B_Log_EsqueciSenha.setBounds(40, 470, 260, 20);

        Background2.setText("Fundo");
        Background2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background2MouseEntered(evt);
            }
        });
        TLogin.add(Background2);
        Background2.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        Desktop.setBackground(new java.awt.Color(255, 255, 255));
        Desktop.setName("Tela"); // NOI18N
        Desktop.setLayout(null);

        BIniciarApp.setBackground(new java.awt.Color(204, 204, 255));
        BIniciarApp.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/derolelogo.png"))); // NOI18N
        BIniciarApp.setBorderPainted(false);
        BIniciarApp.setContentAreaFilled(false);
        BIniciarApp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BIniciarAppActionPerformed(evt);
            }
        });
        Desktop.add(BIniciarApp);
        BIniciarApp.setBounds(130, 200, 60, 50);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/background.png"))); // NOI18N
        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/background.png")));
        Desktop.add(Fundo);
        Fundo.setBounds(0, 0, 330, 620);
        Fundo.setBounds(0, 0, 330, 684);
        Fundo.setVisible(true);

        Paleta.setBackground(new java.awt.Color(255, 255, 255));
        Paleta.setName("Tela"); // NOI18N
        Paleta.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        Paleta.add(Fundo1);
        Fundo1.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        Rotulo.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo.setText("novo horario");
        Paleta.add(Rotulo);
        Rotulo.setBounds(30, 340, 260, 25);

        CTO.setName("CampoTexto*"); // NOI18N
        CTO.setOpaque(false);
        CTO.setLayout(null);

        CampoTexto.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CampoTexto.setText("0,00");
        CampoTexto.setBorder(null);
        CampoTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoTextoActionPerformed(evt);
            }
        });
        CampoTexto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CampoTextoKeyTyped(evt);
            }
        });
        CTO.add(CampoTexto);
        CampoTexto.setBounds(10, 10, 250, 30);

        ImagemFundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo.setName("Ativo"); // NOI18N
        CTO.add(ImagemFundo);
        ImagemFundo.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        Paleta.add(CTO);
        CTO.setBounds(20, 270, 290, 60);

        CTO1.setName("CampoTexto"); // NOI18N
        CTO1.setOpaque(false);
        CTO1.setLayout(null);

        CampoSenha.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CampoSenha.setText("Senha");
        CampoSenha.setBorder(null);
        CTO1.add(CampoSenha);
        CampoSenha.setBounds(10, 10, 270, 30);

        ImagemFundo2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo2.setName("Erro"); // NOI18N
        CTO1.add(ImagemFundo2);
        ImagemFundo2.setBounds(0, 0, 290, 70);
        //MudarCampo(CTO, ImagemFundo);

        Paleta.add(CTO1);
        CTO1.setBounds(20, 190, 290, 90);

        CS.setName("F"); // NOI18N
        CS.setOpaque(false);
        CS.setLayout(null);

        Lista.setVisible(false);
        Lista.setBackground(new java.awt.Color(255, 255, 255));
        Lista.setOpaque(false);
        Lista.setLayout(null);

        Opc1.setBackground(new java.awt.Color(250, 250, 250));
        Opc1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc1.setText("Opc1");
        Opc1.setToolTipText("");
        Opc1.setBorder(null);
        Opc1.setBorderPainted(false);
        Opc1.setContentAreaFilled(false);
        Opc1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Opc1FocusGained(evt);
            }
        });
        Opc1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc1MouseExited(evt);
            }
        });
        Opc1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc1ActionPerformed(evt);
            }
        });
        Lista.add(Opc1);
        Opc1.setBounds(10, 10, 90, 15);

        Opc2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc2.setText("Opc2");
        Opc2.setToolTipText("");
        Opc2.setBorder(null);
        Opc2.setBorderPainted(false);
        Opc2.setContentAreaFilled(false);
        Opc2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc2MouseExited(evt);
            }
        });
        Opc2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc2ActionPerformed(evt);
            }
        });
        Lista.add(Opc2);
        Opc2.setBounds(10, 30, 90, 15);

        Opc3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc3.setText("Opc3");
        Opc3.setToolTipText("");
        Opc3.setBorder(null);
        Opc3.setBorderPainted(false);
        Opc3.setContentAreaFilled(false);
        Opc3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc3MouseExited(evt);
            }
        });
        Opc3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc3ActionPerformed(evt);
            }
        });
        Lista.add(Opc3);
        Opc3.setBounds(10, 50, 90, 15);

        Opc4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc4.setText("Opc4");
        Opc4.setToolTipText("");
        Opc4.setBorder(null);
        Opc4.setBorderPainted(false);
        Opc4.setContentAreaFilled(false);
        Opc4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc4MouseExited(evt);
            }
        });
        Opc4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc4ActionPerformed(evt);
            }
        });
        Lista.add(Opc4);
        Opc4.setBounds(10, 70, 90, 15);

        CaixaBotton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaBotton.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Lista.add(CaixaBotton);
        CaixaBotton.setBounds(0, 86, 106, 15);

        CaixaMeio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaMeio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CaixaMeio.png"))); // NOI18N
        CaixaMeio.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista.add(CaixaMeio);
        CaixaMeio.setBounds(0, 10, 106, 80);

        CaixaTop2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaTop2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaTop2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista.add(CaixaTop2);
        CaixaTop2.setBounds(0, 0, 106, 15);

        CS.add(Lista);
        Lista.setBounds(7, 43, 120, 110);

        CSSelecionado.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CS.add(CSSelecionado);
        CSSelecionado.setBounds(10, 5, 90, 30);

        AbrirSelecao.setBorder(null);
        AbrirSelecao.setBorderPainted(false);
        AbrirSelecao.setContentAreaFilled(false);
        AbrirSelecao.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirSelecaoMouseClicked(evt);
            }
        });
        AbrirSelecao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirSelecaoActionPerformed(evt);
            }
        });
        CS.add(AbrirSelecao);
        AbrirSelecao.setBounds(110, 0, 20, 38);

        CampoSelecao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png"))); // NOI18N
        CS.add(CampoSelecao);
        CampoSelecao.setBounds(0, 0, 150, 53);

        Paleta.add(CS);
        CS.setBounds(10, 500, 150, 50);

        CS2.setName("F"); // NOI18N
        CS2.setOpaque(false);
        CS2.setLayout(null);

        Lista2.setVisible(false);
        Lista2.setBackground(new java.awt.Color(255, 255, 255));
        Lista2.setOpaque(false);
        Lista2.setLayout(null);

        Opc1_2.setBackground(new java.awt.Color(250, 250, 250));
        Opc1_2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc1_2.setText("Opc1");
        Opc1_2.setToolTipText("");
        Opc1_2.setBorder(null);
        Opc1_2.setBorderPainted(false);
        Opc1_2.setContentAreaFilled(false);
        Opc1_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc1_2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Opc1_2FocusGained(evt);
            }
        });
        Opc1_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc1_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc1_2MouseExited(evt);
            }
        });
        Opc1_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc1_2ActionPerformed(evt);
            }
        });
        Lista2.add(Opc1_2);
        Opc1_2.setBounds(10, 10, 90, 15);

        Opc2_2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc2_2.setText("Opc2");
        Opc2_2.setToolTipText("");
        Opc2_2.setBorder(null);
        Opc2_2.setBorderPainted(false);
        Opc2_2.setContentAreaFilled(false);
        Opc2_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc2_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc2_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc2_2MouseExited(evt);
            }
        });
        Opc2_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc2_2ActionPerformed(evt);
            }
        });
        Lista2.add(Opc2_2);
        Opc2_2.setBounds(10, 30, 90, 15);

        Opc3_2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc3_2.setText("Opc3");
        Opc3_2.setToolTipText("");
        Opc3_2.setBorder(null);
        Opc3_2.setBorderPainted(false);
        Opc3_2.setContentAreaFilled(false);
        Opc3_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc3_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc3_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc3_2MouseExited(evt);
            }
        });
        Opc3_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc3_2ActionPerformed(evt);
            }
        });
        Lista2.add(Opc3_2);
        Opc3_2.setBounds(10, 50, 90, 15);

        Opc4_2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc4_2.setText("Opc4");
        Opc4_2.setToolTipText("");
        Opc4_2.setBorder(null);
        Opc4_2.setBorderPainted(false);
        Opc4_2.setContentAreaFilled(false);
        Opc4_2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc4_2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc4_2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc4_2MouseExited(evt);
            }
        });
        Opc4_2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc4_2ActionPerformed(evt);
            }
        });
        Lista2.add(Opc4_2);
        Opc4_2.setBounds(10, 70, 90, 15);

        CaixaBotton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaBotton2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Lista2.add(CaixaBotton2);
        CaixaBotton2.setBounds(0, 86, 106, 15);

        CaixaMeio2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaMeio2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CaixaMeio.png"))); // NOI18N
        CaixaMeio2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista2.add(CaixaMeio2);
        CaixaMeio2.setBounds(0, 10, 106, 80);

        CaixaTop4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaTop4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaTop4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista2.add(CaixaTop4);
        CaixaTop4.setBounds(0, 0, 106, 15);

        CS2.add(Lista2);
        Lista2.setBounds(7, 43, 120, 110);

        CSSelecionado2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CS2.add(CSSelecionado2);
        CSSelecionado2.setBounds(10, 5, 90, 30);

        AbrirSelecao2.setBorder(null);
        AbrirSelecao2.setBorderPainted(false);
        AbrirSelecao2.setContentAreaFilled(false);
        AbrirSelecao2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirSelecao2ActionPerformed(evt);
            }
        });
        CS2.add(AbrirSelecao2);
        AbrirSelecao2.setBounds(110, 0, 20, 38);

        CampoSelecao2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png"))); // NOI18N
        CS2.add(CampoSelecao2);
        CampoSelecao2.setBounds(0, 0, 140, 53);

        Paleta.add(CS2);
        CS2.setBounds(160, 500, 150, 50);

        CTO2.setName("CampoTextoLongo*"); // NOI18N
        CTO2.setOpaque(false);
        CTO2.setLayout(null);

        CampoTextoLongo.setBorder(null);
        CampoTextoLongo.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TesteTexto.setColumns(20);
        TesteTexto.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        TesteTexto.setLineWrap(true);
        CampoTextoLongo.setViewportView(TesteTexto);

        CTO2.add(CampoTextoLongo);
        CampoTextoLongo.setBounds(10, 10, 250, 80);

        ImagemFundo3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongo_Ativo.png"))); // NOI18N
        ImagemFundo3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo3.setName("Ativo"); // NOI18N
        CTO2.add(ImagemFundo3);
        ImagemFundo3.setBounds(0, 0, 290, 110);
        //MudarCampo(CTO, ImagemFundo);

        Paleta.add(CTO2);
        CTO2.setBounds(20, 80, 290, 110);
        CampoTextoLongo.setVisible(false);
        CampoTextoLongo.setVisible(true);

        BTestes.setText("Testar");
        BTestes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTestesActionPerformed(evt);
            }
        });
        Paleta.add(BTestes);
        BTestes.setBounds(30, 380, 72, 23);

        Background.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GradientBackground.png")));
        Background.setText("Fundo");
        Background.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BackgroundMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                BackgroundMouseEntered(evt);
            }
        });
        Paleta.add(Background);
        Background.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TCadastro.setBackground(new java.awt.Color(255, 255, 255));
        TCadastro.setName("Tela4"); // NOI18N
        TCadastro.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TCadastro.add(Fundo4);
        Fundo4.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        CTO_Cad_Email.setName("CampoTexto*"); // NOI18N
        CTO_Cad_Email.setOpaque(false);
        CTO_Cad_Email.setLayout(null);

        BOX_CTO_Cad_Email.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_Cad_Email.setText("E-mail");
        BOX_CTO_Cad_Email.setBorder(null);
        BOX_CTO_Cad_Email.setNextFocusableComponent(BOX_CT_Cad_Celular);
        BOX_CTO_Cad_Email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_EmailFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_EmailFocusLost(evt);
            }
        });
        BOX_CTO_Cad_Email.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOX_CTO_Cad_EmailActionPerformed(evt);
            }
        });
        BOX_CTO_Cad_Email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CTO_Cad_EmailKeyPressed(evt);
            }
        });
        CTO_Cad_Email.add(BOX_CTO_Cad_Email);
        BOX_CTO_Cad_Email.setBounds(10, 10, 250, 30);

        BG_CTO_Cad_Email.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CTO_Cad_Email.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CTO_Cad_Email.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CTO_Cad_Email.setName("Ativo"); // NOI18N
        CTO_Cad_Email.add(BG_CTO_Cad_Email);
        BG_CTO_Cad_Email.setBounds(0, 0, 290, 60);
        //precisa validar o e-mail

        TCadastro.add(CTO_Cad_Email);
        CTO_Cad_Email.setBounds(20, 30, 290, 60);

        CT_Cad_Celular.setName("CampoTexto"); // NOI18N
        CT_Cad_Celular.setOpaque(false);
        CT_Cad_Celular.setLayout(null);

        BOX_CT_Cad_Celular.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CT_Cad_Celular.setText("Celular");
        BOX_CT_Cad_Celular.setBorder(null);
        BOX_CT_Cad_Celular.setNextFocusableComponent(AbrirSelecao);
        BOX_CT_Cad_Celular.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BOX_CT_Cad_CelularFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CT_Cad_CelularFocusLost(evt);
            }
        });
        BOX_CT_Cad_Celular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOX_CT_Cad_CelularActionPerformed(evt);
            }
        });
        BOX_CT_Cad_Celular.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CT_Cad_CelularKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BOX_CT_Cad_CelularKeyTyped(evt);
            }
        });
        CT_Cad_Celular.add(BOX_CT_Cad_Celular);
        BOX_CT_Cad_Celular.setBounds(10, 10, 250, 30);

        BG_CT_Cad_Celular.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CT_Cad_Celular.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CT_Cad_Celular.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CT_Cad_Celular.setName("Ativo"); // NOI18N
        CT_Cad_Celular.add(BG_CT_Cad_Celular);
        BG_CT_Cad_Celular.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TCadastro.add(CT_Cad_Celular);
        CT_Cad_Celular.setBounds(20, 90, 290, 70);

        CTO_Cad_Nome.setName("CampoTexto*"); // NOI18N
        CTO_Cad_Nome.setOpaque(false);
        CTO_Cad_Nome.setLayout(null);

        BOX_CTO_Cad_Nome.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_Cad_Nome.setText("Nome");
        BOX_CTO_Cad_Nome.setBorder(null);
        BOX_CTO_Cad_Nome.setNextFocusableComponent(BOX_CTO_Cad_Senha);
        BOX_CTO_Cad_Nome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_NomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_NomeFocusLost(evt);
            }
        });
        BOX_CTO_Cad_Nome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CTO_Cad_NomeKeyPressed(evt);
            }
        });
        CTO_Cad_Nome.add(BOX_CTO_Cad_Nome);
        BOX_CTO_Cad_Nome.setBounds(10, 10, 250, 30);

        BG_CTO_Cad_Nome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CTO_Cad_Nome.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CTO_Cad_Nome.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CTO_Cad_Nome.setName("Ativo"); // NOI18N
        CTO_Cad_Nome.add(BG_CTO_Cad_Nome);
        BG_CTO_Cad_Nome.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TCadastro.add(CTO_Cad_Nome);
        CTO_Cad_Nome.setBounds(20, 150, 290, 60);

        CTO_Cad_Senha.setName("CampoTexto*"); // NOI18N
        CTO_Cad_Senha.setOpaque(false);
        CTO_Cad_Senha.setLayout(null);

        BOX_CTO_Cad_Senha.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_Cad_Senha.setText("asdfsdsdg");
        BOX_CTO_Cad_Senha.setBorder(null);
        BOX_CTO_Cad_Senha.setNextFocusableComponent(BOX_CTO_Cad_SenhaConfirma);
        BOX_CTO_Cad_Senha.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_SenhaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_SenhaFocusLost(evt);
            }
        });
        BOX_CTO_Cad_Senha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CTO_Cad_SenhaKeyPressed(evt);
            }
        });
        CTO_Cad_Senha.add(BOX_CTO_Cad_Senha);
        BOX_CTO_Cad_Senha.setBounds(10, 10, 250, 30);

        BG_CTO_Cad_Senha.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CTO_Cad_Senha.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CTO_Cad_Senha.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CTO_Cad_Senha.setName("Ativo"); // NOI18N
        CTO_Cad_Senha.add(BG_CTO_Cad_Senha);
        BG_CTO_Cad_Senha.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TCadastro.add(CTO_Cad_Senha);
        CTO_Cad_Senha.setBounds(20, 210, 290, 60);

        CTO_Cad_SenhaConfirma.setName("CampoTexto*"); // NOI18N
        CTO_Cad_SenhaConfirma.setOpaque(false);
        CTO_Cad_SenhaConfirma.setLayout(null);

        BOX_CTO_Cad_SenhaConfirma.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_Cad_SenhaConfirma.setBorder(null);
        BOX_CTO_Cad_SenhaConfirma.setNextFocusableComponent(BOX_CT_Cad_DataNascimento);
        BOX_CTO_Cad_SenhaConfirma.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CTO_Cad_SenhaConfirmaFocusLost(evt);
            }
        });
        BOX_CTO_Cad_SenhaConfirma.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CTO_Cad_SenhaConfirmaKeyPressed(evt);
            }
        });
        CTO_Cad_SenhaConfirma.add(BOX_CTO_Cad_SenhaConfirma);
        BOX_CTO_Cad_SenhaConfirma.setBounds(10, 10, 250, 30);

        BG_CTO_Cad_SenhaConfirma.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CTO_Cad_SenhaConfirma.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CTO_Cad_SenhaConfirma.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CTO_Cad_SenhaConfirma.setName("Ativo"); // NOI18N
        CTO_Cad_SenhaConfirma.add(BG_CTO_Cad_SenhaConfirma);
        BG_CTO_Cad_SenhaConfirma.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TCadastro.add(CTO_Cad_SenhaConfirma);
        CTO_Cad_SenhaConfirma.setBounds(20, 270, 290, 60);

        CT_Cad_DataNascimento.setName("CampoTexto*"); // NOI18N
        CT_Cad_DataNascimento.setOpaque(false);
        CT_Cad_DataNascimento.setLayout(null);

        BOX_CT_Cad_DataNascimento.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CT_Cad_DataNascimento.setText("Data de nascimento");
        BOX_CT_Cad_DataNascimento.setBorder(null);
        BOX_CT_Cad_DataNascimento.setNextFocusableComponent(AbrirSelecao6);
        BOX_CT_Cad_DataNascimento.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                BOX_CT_Cad_DataNascimentoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                BOX_CT_Cad_DataNascimentoFocusLost(evt);
            }
        });
        BOX_CT_Cad_DataNascimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BOX_CT_Cad_DataNascimentoKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BOX_CT_Cad_DataNascimentoKeyTyped(evt);
            }
        });
        CT_Cad_DataNascimento.add(BOX_CT_Cad_DataNascimento);
        BOX_CT_Cad_DataNascimento.setBounds(10, 10, 250, 30);

        BG_CT_Cad_DataNascimento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_CT_Cad_DataNascimento.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_CT_Cad_DataNascimento.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_CT_Cad_DataNascimento.setName("Ativo"); // NOI18N
        CT_Cad_DataNascimento.add(BG_CT_Cad_DataNascimento);
        BG_CT_Cad_DataNascimento.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TCadastro.add(CT_Cad_DataNascimento);
        CT_Cad_DataNascimento.setBounds(20, 330, 290, 60);

        CS_Cad_Genero.setName("F"); // NOI18N
        CS_Cad_Genero.setOpaque(false);
        CS_Cad_Genero.setLayout(null);

        Lista6.setVisible(false);
        Lista6.setBackground(new java.awt.Color(255, 255, 255));
        Lista6.setOpaque(false);
        Lista6.setLayout(null);

        Opc13.setBackground(new java.awt.Color(250, 250, 250));
        Opc13.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc13.setText("Não informar");
        Opc13.setToolTipText("");
        Opc13.setBorder(null);
        Opc13.setBorderPainted(false);
        Opc13.setContentAreaFilled(false);
        Opc13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc13.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                Opc13FocusGained(evt);
            }
        });
        Opc13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc13MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc13MouseExited(evt);
            }
        });
        Opc13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc13ActionPerformed(evt);
            }
        });
        Lista6.add(Opc13);
        Opc13.setBounds(10, 10, 90, 15);

        Opc14.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc14.setText("Feminino");
        Opc14.setToolTipText("");
        Opc14.setBorder(null);
        Opc14.setBorderPainted(false);
        Opc14.setContentAreaFilled(false);
        Opc14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc14MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc14MouseExited(evt);
            }
        });
        Opc14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc14ActionPerformed(evt);
            }
        });
        Lista6.add(Opc14);
        Opc14.setBounds(10, 30, 90, 15);

        Opc15.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc15.setText("Masculino");
        Opc15.setToolTipText("");
        Opc15.setBorder(null);
        Opc15.setBorderPainted(false);
        Opc15.setContentAreaFilled(false);
        Opc15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc15MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc15MouseExited(evt);
            }
        });
        Opc15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc15ActionPerformed(evt);
            }
        });
        Lista6.add(Opc15);
        Opc15.setBounds(10, 50, 90, 15);

        Opc16.setFont(new java.awt.Font("Segoe UI Semilight", 0, 11)); // NOI18N
        Opc16.setText("Não binário");
        Opc16.setToolTipText("");
        Opc16.setBorder(null);
        Opc16.setBorderPainted(false);
        Opc16.setContentAreaFilled(false);
        Opc16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Opc16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Opc16MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Opc16MouseExited(evt);
            }
        });
        Opc16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Opc16ActionPerformed(evt);
            }
        });
        Lista6.add(Opc16);
        Opc16.setBounds(10, 70, 90, 15);

        CaixaBotton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaBotton6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Lista6.add(CaixaBotton6);
        CaixaBotton6.setBounds(0, 86, 106, 15);

        CaixaMeio6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaMeio6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CaixaMeio.png"))); // NOI18N
        CaixaMeio6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista6.add(CaixaMeio6);
        CaixaMeio6.setBounds(0, 10, 106, 80);

        CaixaTop8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CaixaTop8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/Caixa.png"))); // NOI18N
        CaixaTop8.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        Lista6.add(CaixaTop8);
        CaixaTop8.setBounds(0, 0, 106, 15);

        CS_Cad_Genero.add(Lista6);
        Lista6.setBounds(7, 43, 120, 110);

        CSSelecionado6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CS_Cad_Genero.add(CSSelecionado6);
        CSSelecionado6.setBounds(10, 5, 90, 30);

        AbrirSelecao6.setBorder(null);
        AbrirSelecao6.setBorderPainted(false);
        AbrirSelecao6.setContentAreaFilled(false);
        AbrirSelecao6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AbrirSelecao6MouseClicked(evt);
            }
        });
        AbrirSelecao6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirSelecao6ActionPerformed(evt);
            }
        });
        CS_Cad_Genero.add(AbrirSelecao6);
        AbrirSelecao6.setBounds(110, 0, 20, 38);

        BG_CS_Cad_Genero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png"))); // NOI18N
        BG_CS_Cad_Genero.setName(""); // NOI18N
        CS_Cad_Genero.add(BG_CS_Cad_Genero);
        BG_CS_Cad_Genero.setBounds(0, 0, 150, 53);

        TCadastro.add(CS_Cad_Genero);
        CS_Cad_Genero.setBounds(20, 400, 160, 50);

        B_Cad_LimparTudo.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        B_Cad_LimparTudo.setText("Limpar tudo");
        B_Cad_LimparTudo.setBorder(null);
        B_Cad_LimparTudo.setBorderPainted(false);
        B_Cad_LimparTudo.setContentAreaFilled(false);
        B_Cad_LimparTudo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Cad_LimparTudo.setPreferredSize(new java.awt.Dimension(74, 53));
        TCadastro.add(B_Cad_LimparTudo);
        B_Cad_LimparTudo.setBounds(210, 420, 100, 20);

        B_Cad_Cancelar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Cad_Cancelar.setForeground(new java.awt.Color(250, 250, 250));
        B_Cad_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_vermelho.png"))); // NOI18N
        B_Cad_Cancelar.setText("Cancelar");
        B_Cad_Cancelar.setBorder(null);
        B_Cad_Cancelar.setBorderPainted(false);
        B_Cad_Cancelar.setContentAreaFilled(false);
        B_Cad_Cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Cad_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Cad_CancelarActionPerformed(evt);
            }
        });
        TCadastro.add(B_Cad_Cancelar);
        B_Cad_Cancelar.setBounds(20, 480, 132, 50);

        B_Cad_Cadastrar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Cad_Cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_verde.png"))); // NOI18N
        B_Cad_Cadastrar.setText("Cadastrar");
        B_Cad_Cadastrar.setBorder(null);
        B_Cad_Cadastrar.setBorderPainted(false);
        B_Cad_Cadastrar.setContentAreaFilled(false);
        B_Cad_Cadastrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Cad_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Cad_CadastrarActionPerformed(evt);
            }
        });
        TCadastro.add(B_Cad_Cadastrar);
        B_Cad_Cadastrar.setBounds(170, 480, 140, 50);

        MSGCad.setVisible(false);
        MSGCad.setFont(new java.awt.Font("Yu Gothic UI Light", 0, 12)); // NOI18N
        MSGCad.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        MSGCad.setText("precisa configurar as trocas de campo via TAB");
        TCadastro.add(MSGCad);
        MSGCad.setBounds(40, 460, 250, 16);

        Background3.setText("Fundo");
        Background3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background3MouseEntered(evt);
            }
        });
        TCadastro.add(Background3);
        Background3.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TCadastro_Editar.setBackground(new java.awt.Color(255, 255, 255));
        TCadastro_Editar.setName("Tela"); // NOI18N
        TCadastro_Editar.setLayout(null);

        Background4.setText("Fundo");
        Background4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background4MouseEntered(evt);
            }
        });
        TCadastro_Editar.add(Background4);
        Background4.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        THome.setBackground(new java.awt.Color(255, 255, 255));
        THome.setName("Tela6"); // NOI18N
        THome.setLayout(null);

        Guias.setOpaque(false);
        Guias.setLayout(null);

        B_Home_Realizado.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 12)); // NOI18N
        B_Home_Realizado.setText("Realizado");
        B_Home_Realizado.setBorder(null);
        B_Home_Realizado.setBorderPainted(false);
        B_Home_Realizado.setContentAreaFilled(false);
        B_Home_Realizado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Home_RealizadoActionPerformed(evt);
            }
        });
        Guias.add(B_Home_Realizado);
        B_Home_Realizado.setBounds(200, 10, 90, 20);

        B_Home_Comunidade.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 12)); // NOI18N
        B_Home_Comunidade.setText("Comunidade");
        B_Home_Comunidade.setBorder(null);
        B_Home_Comunidade.setBorderPainted(false);
        B_Home_Comunidade.setContentAreaFilled(false);
        B_Home_Comunidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Home_ComunidadeActionPerformed(evt);
            }
        });
        Guias.add(B_Home_Comunidade);
        B_Home_Comunidade.setBounds(100, 10, 90, 20);

        B_Home_MeusEventos.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 12)); // NOI18N
        B_Home_MeusEventos.setText("Meus eventos");
        B_Home_MeusEventos.setBorder(null);
        B_Home_MeusEventos.setBorderPainted(false);
        B_Home_MeusEventos.setContentAreaFilled(false);
        B_Home_MeusEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Home_MeusEventosActionPerformed(evt);
            }
        });
        Guias.add(B_Home_MeusEventos);
        B_Home_MeusEventos.setBounds(0, 10, 90, 20);

        CampoGuia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/GUIAS/Guias-01.png"))); // NOI18N
        CampoGuia.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Guias.add(CampoGuia);
        CampoGuia.setBounds(0, 10, 290, 30);

        THome.add(Guias);
        Guias.setBounds(20, 130, 290, 40);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        THome.add(Fundo6);
        Fundo6.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        CTO10.setName("CampoTexto"); // NOI18N
        CTO10.setOpaque(false);
        CTO10.setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/Lupa.png"))); // NOI18N
        jLabel2.setToolTipText("");
        jLabel2.setName("Lupa"); // NOI18N
        CTO10.add(jLabel2);
        jLabel2.setBounds(235, 0, 50, 50);

        BOX_CT_Home_Procurar.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CT_Home_Procurar.setText("Procurar eventos");
        BOX_CT_Home_Procurar.setBorder(null);
        BOX_CT_Home_Procurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOX_CT_Home_ProcurarActionPerformed(evt);
            }
        });
        CTO10.add(BOX_CT_Home_Procurar);
        BOX_CT_Home_Procurar.setBounds(10, 10, 220, 30);

        ImagemFundo15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo15.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo15.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo15.setName("Ativo"); // NOI18N
        CTO10.add(ImagemFundo15);
        ImagemFundo15.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        THome.add(CTO10);
        CTO10.setBounds(20, 75, 310, 60);

        B_Home_NovoEvento.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Home_NovoEvento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_verde.png"))); // NOI18N
        B_Home_NovoEvento.setText("Novo");
        B_Home_NovoEvento.setBorder(null);
        B_Home_NovoEvento.setBorderPainted(false);
        B_Home_NovoEvento.setContentAreaFilled(false);
        B_Home_NovoEvento.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Home_NovoEvento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Home_NovoEventoActionPerformed(evt);
            }
        });
        THome.add(B_Home_NovoEvento);
        B_Home_NovoEvento.setBounds(240, 485, 70, 40);

        B_Home_Participar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Home_Participar.setForeground(new java.awt.Color(255, 255, 255));
        B_Home_Participar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_azul.png"))); // NOI18N
        B_Home_Participar.setText("Part / ver");
        B_Home_Participar.setBorder(null);
        B_Home_Participar.setBorderPainted(false);
        B_Home_Participar.setContentAreaFilled(false);
        B_Home_Participar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Home_Participar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Home_ParticiparActionPerformed(evt);
            }
        });
        THome.add(B_Home_Participar);
        B_Home_Participar.setBounds(100, 485, 140, 40);

        jScrollPane7.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        T_Home_Eventos.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        T_Home_Eventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Evento", "Hora", "Data", "id_evento"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        T_Home_Eventos.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        T_Home_Eventos.setColumnSelectionAllowed(true);
        T_Home_Eventos.setRowHeight(25);
        T_Home_Eventos.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(T_Home_Eventos);
        T_Home_Eventos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (T_Home_Eventos.getColumnModel().getColumnCount() > 0) {
            T_Home_Eventos.getColumnModel().getColumn(0).setResizable(false);
            T_Home_Eventos.getColumnModel().getColumn(0).setPreferredWidth(130);
            T_Home_Eventos.getColumnModel().getColumn(1).setResizable(false);
            T_Home_Eventos.getColumnModel().getColumn(1).setPreferredWidth(60);
            T_Home_Eventos.getColumnModel().getColumn(2).setResizable(false);
            T_Home_Eventos.getColumnModel().getColumn(2).setPreferredWidth(100);
            T_Home_Eventos.getColumnModel().getColumn(3).setResizable(false);
            T_Home_Eventos.getColumnModel().getColumn(3).setPreferredWidth(0);
        }

        THome.add(jScrollPane7);
        jScrollPane7.setBounds(20, 145, 291, 320);

        Background5.setText("Fundo");
        Background5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background5MouseEntered(evt);
            }
        });
        THome.add(Background5);
        Background5.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TEvento_Criar.setBackground(new java.awt.Color(255, 255, 255));
        TEvento_Criar.setName("Tela7"); // NOI18N
        TEvento_Criar.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TEvento_Criar.add(Fundo7);
        Fundo7.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        CTO13.setName("CampoTexto*"); // NOI18N
        CTO13.setOpaque(false);
        CTO13.setLayout(null);

        BOX_CTO_CadEvt_Titulo.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadEvt_Titulo.setText("Titulo do evento");
        BOX_CTO_CadEvt_Titulo.setBorder(null);
        BOX_CTO_CadEvt_Titulo.setName("Titulo do evento"); // NOI18N
        BOX_CTO_CadEvt_Titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOX_CTO_CadEvt_TituloActionPerformed(evt);
            }
        });
        CTO13.add(BOX_CTO_CadEvt_Titulo);
        BOX_CTO_CadEvt_Titulo.setBounds(10, 10, 250, 30);

        ImagemFundo20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo20.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo20.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo20.setName("Ativo"); // NOI18N
        CTO13.add(ImagemFundo20);
        ImagemFundo20.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TEvento_Criar.add(CTO13);
        CTO13.setBounds(20, 50, 310, 60);

        CTO3.setName("CampoTextoLongo*"); // NOI18N
        CTO3.setOpaque(false);
        CTO3.setLayout(null);

        CampoTextoLongo1.setBorder(null);
        CampoTextoLongo1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        BOX_CTO_CadEvt_Descricao.setColumns(20);
        BOX_CTO_CadEvt_Descricao.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadEvt_Descricao.setLineWrap(true);
        BOX_CTO_CadEvt_Descricao.setText("Descrição");
        BOX_CTO_CadEvt_Descricao.setName("Descrição"); // NOI18N
        CampoTextoLongo1.setViewportView(BOX_CTO_CadEvt_Descricao);

        CTO3.add(CampoTextoLongo1);
        CampoTextoLongo1.setBounds(10, 10, 250, 80);

        ImagemFundo4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongo_Ativo.png"))); // NOI18N
        ImagemFundo4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo4.setName("Ativo"); // NOI18N
        CTO3.add(ImagemFundo4);
        ImagemFundo4.setBounds(0, 0, 290, 100);
        //MudarCampo(CTO, ImagemFundo);

        TEvento_Criar.add(CTO3);
        CTO3.setBounds(20, 110, 290, 110);
        CampoTextoLongo.setVisible(false);
        CampoTextoLongo.setVisible(true);

        CT18.setName("CampoTexto*"); // NOI18N
        CT18.setOpaque(false);
        CT18.setLayout(null);

        BOX_CTO_CadEvt_Data.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadEvt_Data.setText("Data");
        BOX_CTO_CadEvt_Data.setBorder(null);
        BOX_CTO_CadEvt_Data.setName("Data"); // NOI18N
        CT18.add(BOX_CTO_CadEvt_Data);
        BOX_CTO_CadEvt_Data.setBounds(10, 10, 250, 30);

        ImagemFundo45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo45.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo45.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo45.setName("Ativo"); // NOI18N
        CT18.add(ImagemFundo45);
        ImagemFundo45.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TEvento_Criar.add(CT18);
        CT18.setBounds(20, 220, 310, 60);

        CT7.setName("CampoTexto"); // NOI18N
        CT7.setOpaque(false);
        CT7.setLayout(null);

        BOX_CT_CadEvt_Horario.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CT_CadEvt_Horario.setText("Horário");
        BOX_CT_CadEvt_Horario.setBorder(null);
        BOX_CT_CadEvt_Horario.setName("Horário"); // NOI18N
        CT7.add(BOX_CT_CadEvt_Horario);
        BOX_CT_CadEvt_Horario.setBounds(10, 10, 250, 30);

        ImagemFundo19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo19.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo19.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo19.setName("Ativo"); // NOI18N
        CT7.add(ImagemFundo19);
        ImagemFundo19.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TEvento_Criar.add(CT7);
        CT7.setBounds(20, 280, 310, 60);

        CS4.setName("F"); // NOI18N
        CS4.setOpaque(false);
        CS4.setLayout(null);

        BOX_CS_CadEvt_Idade.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CS_CadEvt_Idade.setText("Livre");
        BOX_CS_CadEvt_Idade.setName("Idade"); // NOI18N
        CS4.add(BOX_CS_CadEvt_Idade);
        BOX_CS_CadEvt_Idade.setBounds(10, 5, 90, 30);

        AbrirSelecao4.setBorder(null);
        AbrirSelecao4.setBorderPainted(false);
        AbrirSelecao4.setContentAreaFilled(false);
        AbrirSelecao4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirSelecao4ActionPerformed(evt);
            }
        });
        CS4.add(AbrirSelecao4);
        AbrirSelecao4.setBounds(110, 0, 20, 38);

        CampoSelecao4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png"))); // NOI18N
        CS4.add(CampoSelecao4);
        CampoSelecao4.setBounds(0, 0, 140, 53);

        TEvento_Criar.add(CS4);
        CS4.setBounds(20, 340, 140, 140);

        CS3.setName("F"); // NOI18N
        CS3.setOpaque(false);
        CS3.setLayout(null);

        BOX_CS_CadEvt_Visibilidade.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CS_CadEvt_Visibilidade.setText("Visível");
        BOX_CS_CadEvt_Visibilidade.setName("Visibilidade"); // NOI18N
        CS3.add(BOX_CS_CadEvt_Visibilidade);
        BOX_CS_CadEvt_Visibilidade.setBounds(10, 5, 90, 30);

        AbrirSelecao3.setBorder(null);
        AbrirSelecao3.setBorderPainted(false);
        AbrirSelecao3.setContentAreaFilled(false);
        AbrirSelecao3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AbrirSelecao3ActionPerformed(evt);
            }
        });
        CS3.add(AbrirSelecao3);
        AbrirSelecao3.setBounds(110, 0, 20, 38);

        CampoSelecao3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CAIXASELECAO/CSF_Cinza.png"))); // NOI18N
        CS3.add(CampoSelecao3);
        CampoSelecao3.setBounds(0, 0, 140, 53);

        TEvento_Criar.add(CS3);
        CS3.setBounds(170, 340, 150, 140);

        B_CadEvt_Criar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_CadEvt_Criar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_verde.png"))); // NOI18N
        B_CadEvt_Criar.setText("Criar");
        B_CadEvt_Criar.setBorder(null);
        B_CadEvt_Criar.setBorderPainted(false);
        B_CadEvt_Criar.setContentAreaFilled(false);
        B_CadEvt_Criar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        B_CadEvt_Criar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_CadEvt_Criar.setPreferredSize(new java.awt.Dimension(74, 53));
        B_CadEvt_Criar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_CadEvt_CriarActionPerformed(evt);
            }
        });
        TEvento_Criar.add(B_CadEvt_Criar);
        B_CadEvt_Criar.setBounds(170, 480, 140, 50);

        B_CadEvt_Cancelar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_CadEvt_Cancelar.setForeground(new java.awt.Color(255, 255, 255));
        B_CadEvt_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_vermelho.png"))); // NOI18N
        B_CadEvt_Cancelar.setText("Cancelar");
        B_CadEvt_Cancelar.setBorder(null);
        B_CadEvt_Cancelar.setBorderPainted(false);
        B_CadEvt_Cancelar.setContentAreaFilled(false);
        B_CadEvt_Cancelar.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        B_CadEvt_Cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_CadEvt_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_CadEvt_CancelarActionPerformed(evt);
            }
        });
        TEvento_Criar.add(B_CadEvt_Cancelar);
        B_CadEvt_Cancelar.setBounds(20, 480, 140, 50);

        Background6.setText("Fundo");
        Background6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background6MouseEntered(evt);
            }
        });
        TEvento_Criar.add(Background6);
        Background6.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TEvento_Editar.setBackground(new java.awt.Color(255, 255, 255));
        TEvento_Editar.setName("Tela"); // NOI18N
        TEvento_Editar.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TEvento_Editar.add(Fundo8);
        Fundo8.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        Background7.setText("Fundo");
        Background7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background7MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background7MouseEntered(evt);
            }
        });
        TEvento_Editar.add(Background7);
        Background7.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TEvento_Home.setBackground(new java.awt.Color(255, 255, 255));
        TEvento_Home.setName("Tela9"); // NOI18N
        TEvento_Home.setLayout(null);

        PDescricaoValores.setOpaque(false);
        PDescricaoValores.setLayout(null);

        B_EvtHome_Descricao.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        //jButton1.setFont(new java.awt.Font("Segoe UI", 1, 12));
        B_EvtHome_Descricao.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaAtiva.png"))); // NOI18N
        B_EvtHome_Descricao.setText("Descrição");
        B_EvtHome_Descricao.setBorder(null);
        B_EvtHome_Descricao.setBorderPainted(false);
        B_EvtHome_Descricao.setContentAreaFilled(false);
        B_EvtHome_Descricao.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Descricao.setName("setada"); // NOI18N
        B_EvtHome_Descricao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_DescricaoActionPerformed(evt);
            }
        });
        PDescricaoValores.add(B_EvtHome_Descricao);
        B_EvtHome_Descricao.setBounds(0, 0, 80, 30);

        B_EvtHome_Valores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/GuiaCinzaInativa.png"))); // NOI18N
        B_EvtHome_Valores.setText("Valores");
        B_EvtHome_Valores.setBorder(null);
        B_EvtHome_Valores.setBorderPainted(false);
        B_EvtHome_Valores.setContentAreaFilled(false);
        B_EvtHome_Valores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Valores.setName("naosetada"); // NOI18N
        B_EvtHome_Valores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_ValoresActionPerformed(evt);
            }
        });
        PDescricaoValores.add(B_EvtHome_Valores);
        B_EvtHome_Valores.setBounds(80, 0, 70, 30);

        PDescricaoEvento.setBackground(new java.awt.Color(220, 221, 220));
        PDescricaoEvento.setLayout(null);

        jScrollPane1.setBorder(null);
        jScrollPane1.setOpaque(false);

        EvtHome_Descricao.setBackground(new java.awt.Color(220, 221, 220));
        EvtHome_Descricao.setColumns(20);
        EvtHome_Descricao.setFont(new java.awt.Font("Segoe UI Light", 0, 12)); // NOI18N
        EvtHome_Descricao.setRows(1);
        EvtHome_Descricao.setText("Descrição");
        EvtHome_Descricao.setBorder(null);
        jScrollPane1.setViewportView(EvtHome_Descricao);

        PDescricaoEvento.add(jScrollPane1);
        jScrollPane1.setBounds(10, 0, 280, 60);

        PDescricaoValores.add(PDescricaoEvento);
        PDescricaoEvento.setBounds(0, 30, 290, 90);

        PValoresEvento.setVisible(false);
        PValoresEvento.setBackground(new java.awt.Color(220, 221, 220));
        PValoresEvento.setLayout(null);

        R_EvtHome_CustoTotal.setText("Custos totais");
        PValoresEvento.add(R_EvtHome_CustoTotal);
        R_EvtHome_CustoTotal.setBounds(10, 40, 230, 16);

        R_EvtHome_MembrosTotal.setText("Nº Participantes");
        PValoresEvento.add(R_EvtHome_MembrosTotal);
        R_EvtHome_MembrosTotal.setBounds(10, 0, 230, 16);

        R_EvtHome_CustoIndividual.setText("Custos totais");
        PValoresEvento.add(R_EvtHome_CustoIndividual);
        R_EvtHome_CustoIndividual.setBounds(10, 20, 230, 16);

        PDescricaoValores.add(PValoresEvento);
        PValoresEvento.setBounds(0, 110, 290, 90);

        TEvento_Home.add(PDescricaoValores);
        PDescricaoValores.setBounds(20, 80, 290, 90);

        Rotulo8.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo8.setText("Participantes");
        Rotulo8.setName("Rotulo"); // NOI18N
        TEvento_Home.add(Rotulo8);
        Rotulo8.setBounds(20, 362, 250, 25);

        jscrollll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        T_EvtHome_Participantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nome", "Status", "id_usuario", "Pago"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        T_EvtHome_Participantes.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        T_EvtHome_Participantes.getTableHeader().setReorderingAllowed(false);
        jscrollll.setViewportView(T_EvtHome_Participantes);
        if (T_EvtHome_Participantes.getColumnModel().getColumnCount() > 0) {
            T_EvtHome_Participantes.getColumnModel().getColumn(0).setResizable(false);
            T_EvtHome_Participantes.getColumnModel().getColumn(0).setPreferredWidth(200);
            T_EvtHome_Participantes.getColumnModel().getColumn(1).setResizable(false);
            T_EvtHome_Participantes.getColumnModel().getColumn(1).setPreferredWidth(90);
            T_EvtHome_Participantes.getColumnModel().getColumn(2).setResizable(false);
            T_EvtHome_Participantes.getColumnModel().getColumn(2).setPreferredWidth(0);
            T_EvtHome_Participantes.getColumnModel().getColumn(3).setResizable(false);
            T_EvtHome_Participantes.getColumnModel().getColumn(3).setPreferredWidth(0);
        }

        TEvento_Home.add(jscrollll);
        jscrollll.setBounds(20, 390, 290, 120);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        T_EvtHome_Itens.setFont(new java.awt.Font("Segoe UI Light", 0, 18)); // NOI18N
        T_EvtHome_Itens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Item", "Valor", "Id_Item"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        T_EvtHome_Itens.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        T_EvtHome_Itens.setRowHeight(25);
        jScrollPane3.setViewportView(T_EvtHome_Itens);
        if (T_EvtHome_Itens.getColumnModel().getColumnCount() > 0) {
            T_EvtHome_Itens.getColumnModel().getColumn(0).setResizable(false);
            T_EvtHome_Itens.getColumnModel().getColumn(0).setPreferredWidth(210);
            T_EvtHome_Itens.getColumnModel().getColumn(1).setResizable(false);
            T_EvtHome_Itens.getColumnModel().getColumn(1).setPreferredWidth(80);
            T_EvtHome_Itens.getColumnModel().getColumn(2).setResizable(false);
            T_EvtHome_Itens.getColumnModel().getColumn(2).setPreferredWidth(0);
        }

        TEvento_Home.add(jScrollPane3);
        jScrollPane3.setBounds(20, 200, 290, 120);

        Rotulo9.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo9.setText("Gastos");
        Rotulo9.setName("Rotulo"); // NOI18N
        TEvento_Home.add(Rotulo9);
        Rotulo9.setBounds(20, 172, 250, 25);

        EvtHome_Data.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        EvtHome_Data.setText("Data");
        EvtHome_Data.setName("DescricaoEvento"); // NOI18N
        TEvento_Home.add(EvtHome_Data);
        EvtHome_Data.setBounds(20, 50, 80, 20);

        B_EvtHome_Convidar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_EvtHome_Convidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_verde.png"))); // NOI18N
        B_EvtHome_Convidar.setText("Convidar");
        B_EvtHome_Convidar.setBorder(null);
        B_EvtHome_Convidar.setBorderPainted(false);
        B_EvtHome_Convidar.setContentAreaFilled(false);
        B_EvtHome_Convidar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        B_EvtHome_Convidar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Convidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_ConvidarActionPerformed(evt);
            }
        });
        TEvento_Home.add(B_EvtHome_Convidar);
        B_EvtHome_Convidar.setBounds(170, 515, 140, 40);

        B_EvtHome_Ver.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_EvtHome_Ver.setForeground(new java.awt.Color(255, 255, 255));
        B_EvtHome_Ver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_azul.png"))); // NOI18N
        B_EvtHome_Ver.setText("Ver");
        B_EvtHome_Ver.setBorder(null);
        B_EvtHome_Ver.setBorderPainted(false);
        B_EvtHome_Ver.setContentAreaFilled(false);
        B_EvtHome_Ver.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Ver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_VerActionPerformed(evt);
            }
        });
        TEvento_Home.add(B_EvtHome_Ver);
        B_EvtHome_Ver.setBounds(170, 325, 70, 36);

        B_EvtHome_Sair.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_EvtHome_Sair.setForeground(new java.awt.Color(255, 255, 255));
        B_EvtHome_Sair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_vermelho.png"))); // NOI18N
        B_EvtHome_Sair.setText("Sair");
        B_EvtHome_Sair.setBorder(null);
        B_EvtHome_Sair.setBorderPainted(false);
        B_EvtHome_Sair.setContentAreaFilled(false);
        B_EvtHome_Sair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Sair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_SairActionPerformed(evt);
            }
        });
        TEvento_Home.add(B_EvtHome_Sair);
        B_EvtHome_Sair.setBounds(240, 13, 70, 36);

        B_EvtHome_Novo.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_EvtHome_Novo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_verde.png"))); // NOI18N
        B_EvtHome_Novo.setText("Novo");
        B_EvtHome_Novo.setBorder(null);
        B_EvtHome_Novo.setBorderPainted(false);
        B_EvtHome_Novo.setContentAreaFilled(false);
        B_EvtHome_Novo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_EvtHome_Novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EvtHome_NovoActionPerformed(evt);
            }
        });
        TEvento_Home.add(B_EvtHome_Novo);
        B_EvtHome_Novo.setBounds(240, 325, 70, 36);

        EvtHome_Titulo_Evento.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        EvtHome_Titulo_Evento.setText("Título do evento");
        EvtHome_Titulo_Evento.setName("DescricaoEvento"); // NOI18N
        TEvento_Home.add(EvtHome_Titulo_Evento);
        EvtHome_Titulo_Evento.setBounds(20, 10, 220, 30);

        EvtHome_Host.setFont(new java.awt.Font("Segoe UI Semilight", 0, 14)); // NOI18N
        EvtHome_Host.setText("Administrador");
        EvtHome_Host.setName("DescricaoEvento"); // NOI18N
        TEvento_Home.add(EvtHome_Host);
        EvtHome_Host.setBounds(20, 30, 220, 20);

        EvtHome_Hora.setFont(new java.awt.Font("Segoe UI Semilight", 1, 14)); // NOI18N
        EvtHome_Hora.setText("Hora");
        EvtHome_Hora.setName("DescricaoEvento"); // NOI18N
        TEvento_Home.add(EvtHome_Hora);
        EvtHome_Hora.setBounds(100, 50, 60, 20);

        Background8.setText("Fundo");
        Background8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background8MouseEntered(evt);
            }
        });
        TEvento_Home.add(Background8);
        Background8.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TItens_Adicionar.setBackground(new java.awt.Color(255, 255, 255));
        TItens_Adicionar.setName("Tela10"); // NOI18N
        TItens_Adicionar.setLayout(null);

        B_CadItem_Criar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_CadItem_Criar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_verde.png"))); // NOI18N
        B_CadItem_Criar.setText("Criar");
        B_CadItem_Criar.setBorder(null);
        B_CadItem_Criar.setBorderPainted(false);
        B_CadItem_Criar.setContentAreaFilled(false);
        B_CadItem_Criar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        B_CadItem_Criar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_CadItem_Criar.setPreferredSize(new java.awt.Dimension(74, 53));
        B_CadItem_Criar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_CadItem_CriarActionPerformed(evt);
            }
        });
        TItens_Adicionar.add(B_CadItem_Criar);
        B_CadItem_Criar.setBounds(173, 480, 140, 50);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TItens_Adicionar.add(Fundo10);
        Fundo10.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        CT10.setName("CampoTexto"); // NOI18N
        CT10.setOpaque(false);
        CT10.setLayout(null);

        BOX_CT_CadItem_Pagante.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CT_CadItem_Pagante.setText("Pagante");
        BOX_CT_CadItem_Pagante.setBorder(null);
        CT10.add(BOX_CT_CadItem_Pagante);
        BOX_CT_CadItem_Pagante.setBounds(10, 10, 250, 30);

        ImagemFundo28.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo28.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo28.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo28.setName("Ativo"); // NOI18N
        CT10.add(ImagemFundo28);
        ImagemFundo28.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TItens_Adicionar.add(CT10);
        CT10.setBounds(20, 250, 310, 60);

        BotaoM9.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BotaoM9.setForeground(new java.awt.Color(255, 255, 255));
        BotaoM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_vermelho.png"))); // NOI18N
        BotaoM9.setText("Cancelar");
        BotaoM9.setBorder(null);
        BotaoM9.setBorderPainted(false);
        BotaoM9.setContentAreaFilled(false);
        BotaoM9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        BotaoM9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BotaoM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoM9ActionPerformed(evt);
            }
        });
        TItens_Adicionar.add(BotaoM9);
        BotaoM9.setBounds(20, 480, 140, 50);

        CTO19.setName("CampoTexto*"); // NOI18N
        CTO19.setOpaque(false);
        CTO19.setLayout(null);

        BOX_CTO_CadItem_Titulo.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadItem_Titulo.setText("Título do item");
        BOX_CTO_CadItem_Titulo.setBorder(null);
        BOX_CTO_CadItem_Titulo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BOX_CTO_CadItem_TituloActionPerformed(evt);
            }
        });
        CTO19.add(BOX_CTO_CadItem_Titulo);
        BOX_CTO_CadItem_Titulo.setBounds(10, 10, 250, 30);

        ImagemFundo29.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo29.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo29.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo29.setName("Ativo"); // NOI18N
        CTO19.add(ImagemFundo29);
        ImagemFundo29.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TItens_Adicionar.add(CTO19);
        CTO19.setBounds(20, 30, 310, 60);

        CT20.setName("CampoTexto*"); // NOI18N
        CT20.setOpaque(false);
        CT20.setLayout(null);

        BOX_CTO_CadItem_Valor.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadItem_Valor.setText("Valor");
        BOX_CTO_CadItem_Valor.setBorder(null);
        BOX_CTO_CadItem_Valor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                BOX_CTO_CadItem_ValorKeyTyped(evt);
            }
        });
        CT20.add(BOX_CTO_CadItem_Valor);
        BOX_CTO_CadItem_Valor.setBounds(10, 10, 250, 30);

        ImagemFundo51.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        ImagemFundo51.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo51.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo51.setName("Ativo"); // NOI18N
        CT20.add(ImagemFundo51);
        ImagemFundo51.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TItens_Adicionar.add(CT20);
        CT20.setBounds(20, 190, 310, 60);

        jTable4.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nome"
            }
        ));
        jScrollPane4.setViewportView(jTable4);

        TItens_Adicionar.add(jScrollPane4);
        jScrollPane4.setBounds(20, 310, 290, 160);

        CTO8.setName("CampoTextoLongo*"); // NOI18N
        CTO8.setOpaque(false);
        CTO8.setLayout(null);

        CampoTextoLongo2.setBorder(null);
        CampoTextoLongo2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        BOX_CTO_CadItem_Descricao.setColumns(20);
        BOX_CTO_CadItem_Descricao.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        BOX_CTO_CadItem_Descricao.setLineWrap(true);
        BOX_CTO_CadItem_Descricao.setText("Descrição do item");
        CampoTextoLongo2.setViewportView(BOX_CTO_CadItem_Descricao);

        CTO8.add(CampoTextoLongo2);
        CampoTextoLongo2.setBounds(10, 10, 250, 80);

        ImagemFundo5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CTLongo_Ativo.png"))); // NOI18N
        ImagemFundo5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        ImagemFundo5.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        ImagemFundo5.setName("Ativo"); // NOI18N
        CTO8.add(ImagemFundo5);
        ImagemFundo5.setBounds(0, 0, 290, 100);
        //MudarCampo(CTO, ImagemFundo);

        TItens_Adicionar.add(CTO8);
        CTO8.setBounds(20, 90, 290, 110);
        CampoTextoLongo.setVisible(false);
        CampoTextoLongo.setVisible(true);

        Background9.setText("Fundo");
        Background9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background9MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background9MouseEntered(evt);
            }
        });
        TItens_Adicionar.add(Background9);
        Background9.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TItens_VerEditar.setBackground(new java.awt.Color(255, 255, 255));
        TItens_VerEditar.setName("Tela"); // NOI18N
        TItens_VerEditar.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TItens_VerEditar.add(Fundo11);
        Fundo11.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        Background10.setText("Fundo");
        Background10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background10MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background10MouseEntered(evt);
            }
        });
        TItens_VerEditar.add(Background10);
        Background10.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TConvidar.setBackground(new java.awt.Color(255, 255, 255));
        TConvidar.setName("Tela12"); // NOI18N
        TConvidar.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TConvidar.add(Fundo12);
        Fundo12.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        Rotulo11.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo11.setText("Convidar participantes");
        TConvidar.add(Rotulo11);
        Rotulo11.setBounds(20, 30, 260, 25);

        CT_Convite_Email1.setName("CampoTexto"); // NOI18N
        CT_Convite_Email1.setOpaque(false);
        CT_Convite_Email1.setLayout(null);

        CTBOX_Convite_Email1.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email1.setText("Insira o e-mail");
        CTBOX_Convite_Email1.setBorder(null);
        CTBOX_Convite_Email1.setNextFocusableComponent(CTBOX_Convite_Email2);
        CTBOX_Convite_Email1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email1KeyPressed(evt);
            }
        });
        CT_Convite_Email1.add(CTBOX_Convite_Email1);
        CTBOX_Convite_Email1.setBounds(10, 10, 270, 30);

        BG_Convite_Email1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email1.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email1.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email1.setName("Ativo"); // NOI18N
        CT_Convite_Email1.add(BG_Convite_Email1);
        BG_Convite_Email1.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email1);
        CT_Convite_Email1.setBounds(20, 60, 300, 60);

        CT_Convite_Email2.setName("CampoTexto"); // NOI18N
        CT_Convite_Email2.setOpaque(false);
        CT_Convite_Email2.setLayout(null);

        CTBOX_Convite_Email2.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email2.setText("Insira o e-mail");
        CTBOX_Convite_Email2.setBorder(null);
        CTBOX_Convite_Email2.setNextFocusableComponent(CTBOX_Convite_Email3);
        CTBOX_Convite_Email2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CTBOX_Convite_Email2ActionPerformed(evt);
            }
        });
        CTBOX_Convite_Email2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email2KeyPressed(evt);
            }
        });
        CT_Convite_Email2.add(CTBOX_Convite_Email2);
        CTBOX_Convite_Email2.setBounds(10, 10, 270, 30);

        BG_Convite_Email2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email2.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email2.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email2.setName("Ativo"); // NOI18N
        CT_Convite_Email2.add(BG_Convite_Email2);
        BG_Convite_Email2.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email2);
        CT_Convite_Email2.setBounds(20, 125, 300, 60);

        CT_Convite_Email3.setName("CampoTexto"); // NOI18N
        CT_Convite_Email3.setOpaque(false);
        CT_Convite_Email3.setLayout(null);

        CTBOX_Convite_Email3.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email3.setText("Insira o e-mail");
        CTBOX_Convite_Email3.setBorder(null);
        CTBOX_Convite_Email3.setNextFocusableComponent(CTBOX_Convite_Email4);
        CTBOX_Convite_Email3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email3KeyPressed(evt);
            }
        });
        CT_Convite_Email3.add(CTBOX_Convite_Email3);
        CTBOX_Convite_Email3.setBounds(10, 10, 270, 30);

        BG_Convite_Email3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email3.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email3.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email3.setName("Ativo"); // NOI18N
        CT_Convite_Email3.add(BG_Convite_Email3);
        BG_Convite_Email3.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email3);
        CT_Convite_Email3.setBounds(20, 190, 300, 60);

        CT_Convite_Email4.setName("CampoTexto"); // NOI18N
        CT_Convite_Email4.setOpaque(false);
        CT_Convite_Email4.setLayout(null);

        CTBOX_Convite_Email4.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email4.setText("Insira o e-mail");
        CTBOX_Convite_Email4.setBorder(null);
        CTBOX_Convite_Email4.setNextFocusableComponent(CTBOX_Convite_Email5);
        CTBOX_Convite_Email4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email4KeyPressed(evt);
            }
        });
        CT_Convite_Email4.add(CTBOX_Convite_Email4);
        CTBOX_Convite_Email4.setBounds(10, 10, 270, 30);

        BG_Convite_Email4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email4.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email4.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email4.setName("Ativo"); // NOI18N
        CT_Convite_Email4.add(BG_Convite_Email4);
        BG_Convite_Email4.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email4);
        CT_Convite_Email4.setBounds(20, 255, 300, 60);

        CT_Convite_Email5.setName("CampoTexto"); // NOI18N
        CT_Convite_Email5.setOpaque(false);
        CT_Convite_Email5.setLayout(null);

        CTBOX_Convite_Email5.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email5.setText("Insira o e-mail");
        CTBOX_Convite_Email5.setBorder(null);
        CTBOX_Convite_Email5.setNextFocusableComponent(CTBOX_Convite_Email6);
        CTBOX_Convite_Email5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email5KeyPressed(evt);
            }
        });
        CT_Convite_Email5.add(CTBOX_Convite_Email5);
        CTBOX_Convite_Email5.setBounds(10, 10, 270, 30);

        BG_Convite_Email5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email5.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email5.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email5.setName("Ativo"); // NOI18N
        CT_Convite_Email5.add(BG_Convite_Email5);
        BG_Convite_Email5.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email5);
        CT_Convite_Email5.setBounds(20, 320, 300, 60);

        CT_Convite_Email6.setName("CampoTexto"); // NOI18N
        CT_Convite_Email6.setOpaque(false);
        CT_Convite_Email6.setLayout(null);

        CTBOX_Convite_Email6.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        CTBOX_Convite_Email6.setText("Insira o e-mail");
        CTBOX_Convite_Email6.setBorder(null);
        CTBOX_Convite_Email6.setNextFocusableComponent(B_Convite_Convidar);
        CTBOX_Convite_Email6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CTBOX_Convite_Email6KeyPressed(evt);
            }
        });
        CT_Convite_Email6.add(CTBOX_Convite_Email6);
        CTBOX_Convite_Email6.setBounds(10, 10, 270, 30);

        BG_Convite_Email6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Ativo.png"))); // NOI18N
        BG_Convite_Email6.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        BG_Convite_Email6.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/CT/CT_Inativo.png"))); // NOI18N
        BG_Convite_Email6.setName("Ativo"); // NOI18N
        CT_Convite_Email6.add(BG_Convite_Email6);
        BG_Convite_Email6.setBounds(0, 0, 290, 60);
        //MudarCampo(CTO, ImagemFundo);

        TConvidar.add(CT_Convite_Email6);
        CT_Convite_Email6.setBounds(20, 385, 300, 60);

        B_Convite_Cancelar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Convite_Cancelar.setForeground(new java.awt.Color(250, 250, 250));
        B_Convite_Cancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_vermelho.png"))); // NOI18N
        B_Convite_Cancelar.setText("Cancelar");
        B_Convite_Cancelar.setBorder(null);
        B_Convite_Cancelar.setBorderPainted(false);
        B_Convite_Cancelar.setContentAreaFilled(false);
        B_Convite_Cancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Convite_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Convite_CancelarActionPerformed(evt);
            }
        });
        TConvidar.add(B_Convite_Cancelar);
        B_Convite_Cancelar.setBounds(20, 480, 132, 50);

        B_Convite_Convidar.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        B_Convite_Convidar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_medio_fino_verde.png"))); // NOI18N
        B_Convite_Convidar.setText("Convidar");
        B_Convite_Convidar.setBorder(null);
        B_Convite_Convidar.setBorderPainted(false);
        B_Convite_Convidar.setContentAreaFilled(false);
        B_Convite_Convidar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        B_Convite_Convidar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_Convite_ConvidarActionPerformed(evt);
            }
        });
        TConvidar.add(B_Convite_Convidar);
        B_Convite_Convidar.setBounds(170, 480, 140, 50);

        Background11.setText("Fundo");
        Background11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background11MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background11MouseEntered(evt);
            }
        });
        TConvidar.add(Background11);
        Background11.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        TSolicitacoes.setBackground(new java.awt.Color(255, 255, 255));
        TSolicitacoes.setName("Tela13"); // NOI18N
        TSolicitacoes.setLayout(null);

        Fundo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/IOSHomeScreen (330 x 574 px).png")));
        TSolicitacoes.add(Fundo13);
        Fundo13.setBounds(0, 0, 330, 0);
        Fundo.setBounds(0, 0, 330, 574);

        Rotulo12.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo12.setText("Meus convites:");
        TSolicitacoes.add(Rotulo12);
        Rotulo12.setBounds(20, 290, 250, 25);

        Rotulo14.setFont(new java.awt.Font("Segoe UI Semilight", 0, 18)); // NOI18N
        Rotulo14.setText("Meus eventos:");
        TSolicitacoes.add(Rotulo14);
        Rotulo14.setBounds(20, 50, 250, 25);

        BotaoM23.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BotaoM23.setForeground(new java.awt.Color(255, 255, 255));
        BotaoM23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_vermelho.png"))); // NOI18N
        BotaoM23.setText("Deletar");
        BotaoM23.setBorder(null);
        BotaoM23.setBorderPainted(false);
        BotaoM23.setContentAreaFilled(false);
        BotaoM23.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TSolicitacoes.add(BotaoM23);
        BotaoM23.setBounds(170, 485, 70, 36);

        BotaoM24.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BotaoM24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_verde.png"))); // NOI18N
        BotaoM24.setText("Aceitar");
        BotaoM24.setBorder(null);
        BotaoM24.setBorderPainted(false);
        BotaoM24.setContentAreaFilled(false);
        BotaoM24.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TSolicitacoes.add(BotaoM24);
        BotaoM24.setBounds(240, 485, 70, 36);

        BotaoM25.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BotaoM25.setForeground(new java.awt.Color(255, 255, 255));
        BotaoM25.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_vermelho.png"))); // NOI18N
        BotaoM25.setText("Deletar");
        BotaoM25.setBorder(null);
        BotaoM25.setBorderPainted(false);
        BotaoM25.setContentAreaFilled(false);
        BotaoM25.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TSolicitacoes.add(BotaoM25);
        BotaoM25.setBounds(170, 245, 70, 36);

        BotaoM26.setFont(new java.awt.Font("Segoe UI Semilight", 1, 18)); // NOI18N
        BotaoM26.setIcon(new javax.swing.ImageIcon(getClass().getResource("/semestral_derole/IMAGENS/BOTOES/Botao_pequeno_fino_verde.png"))); // NOI18N
        BotaoM26.setText("Aceitar");
        BotaoM26.setBorder(null);
        BotaoM26.setBorderPainted(false);
        BotaoM26.setContentAreaFilled(false);
        BotaoM26.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        TSolicitacoes.add(BotaoM26);
        BotaoM26.setBounds(240, 245, 70, 36);

        jTable6.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(jTable6);

        TSolicitacoes.add(jScrollPane6);
        jScrollPane6.setBounds(20, 80, 290, 160);

        jTable9.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane9.setViewportView(jTable9);

        TSolicitacoes.add(jScrollPane9);
        jScrollPane9.setBounds(20, 320, 290, 160);

        Background12.setText("Fundo");
        Background12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        Background.setText("");
        Background12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Background12MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Background12MouseEntered(evt);
            }
        });
        TSolicitacoes.add(Background12);
        Background12.setBounds(0, 550, 330, 20);
        Background.setBounds(0, 0, 330, 570);

        javax.swing.GroupLayout PainelPrincipalLayout = new javax.swing.GroupLayout(PainelPrincipal);
        PainelPrincipal.setLayout(PainelPrincipalLayout);
        PainelPrincipalLayout.setHorizontalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelPrincipalLayout.createSequentialGroup()
                .addComponent(Desktop, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelPrincipalLayout.createSequentialGroup()
                        .addComponent(Paleta, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TCadastro_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(THome, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEvento_Criar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEvento_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TEvento_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TItens_Adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TItens_VerEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TConvidar, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TSolicitacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 330, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PainelPrincipalLayout.createSequentialGroup()
                        .addComponent(DevTools, javax.swing.GroupLayout.PREFERRED_SIZE, 3848, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70)
                        .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        PainelPrincipalLayout.setVerticalGroup(
            PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PainelPrincipalLayout.createSequentialGroup()
                .addGroup(PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PainelPrincipalLayout.createSequentialGroup()
                        .addGroup(PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(DevTools, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(filler1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(PainelPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Paleta, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TCadastro_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(THome, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TEvento_Criar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TEvento_Editar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TEvento_Home, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TItens_Adicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TItens_VerEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TConvidar, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TSolicitacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(Desktop, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PainelCT(TLogin);
        PainelCT(Paleta);
        PainelCT(TCadastro);
        PainelCT(TCadastro_Editar);
        PainelCT(THome);
        PainelCT(TEvento_Criar);
        PainelCT(TEvento_Editar);
        PainelCT(TEvento_Home);
        PainelCT(TItens_Adicionar);
        PainelCT(TItens_VerEditar);
        PainelCT(TConvidar);

        /*
        0 - dono do evento
        1 - convite enviado
        2 - convite aceito
        3 - convite recusado
        */
        PainelCT(TSolicitacoes);

        getContentPane().add(PainelPrincipal);
        PainelPrincipal.setBounds(20, 15, 330, 617);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        mouseX = evt.getX();
        mouseY = evt.getY();
    
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        int x = this.getX() + evt.getX() - mouseX;
        int y = this.getY() + evt.getY() - mouseY;
        
        this.setLocation(x, y);
    }//GEN-LAST:event_formMouseDragged

    private void BSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BSairActionPerformed
        dispose();
    }//GEN-LAST:event_BSairActionPerformed

    private void BIniciarAppActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BIniciarAppActionPerformed
        TrocaTela(PainelPrincipal, 3);
    }//GEN-LAST:event_BIniciarAppActionPerformed

    private void CampoTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CampoTextoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CampoTextoActionPerformed

    private void Opc2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc2ActionPerformed
        CSSelecionado.setText("Opc2");
        CaixaSelecao(Lista, CampoSelecao, CS);
    }//GEN-LAST:event_Opc2ActionPerformed

    private void Opc3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc3ActionPerformed
        CSSelecionado.setText("Opc3");
        FecharSelecao(CS, Lista, CampoSelecao);
    }//GEN-LAST:event_Opc3ActionPerformed

    private void Opc1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Opc1FocusGained
       
    }//GEN-LAST:event_Opc1FocusGained

    private void Opc1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc1ActionPerformed
        CSSelecionado.setText("Opc1");
        FecharSelecao(CS, Lista, CampoSelecao);
    }//GEN-LAST:event_Opc1ActionPerformed

    private void Opc1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc1MouseEntered
    Opc1.setText(" > "+Opc1.getText());
    }//GEN-LAST:event_Opc1MouseEntered

    private void Opc1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc1MouseExited
     Opc1.setText(Opc1.getText().substring(3));
    }//GEN-LAST:event_Opc1MouseExited

    private void Opc2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc2MouseEntered
    Opc2.setText(" > "+Opc2.getText());
    }//GEN-LAST:event_Opc2MouseEntered

    private void Opc2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc2MouseExited
    Opc2.setText(Opc2.getText().substring(3));
    }//GEN-LAST:event_Opc2MouseExited

    private void Opc3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc3MouseEntered
        Opc3.setText(" > "+Opc3.getText());
    }//GEN-LAST:event_Opc3MouseEntered

    private void Opc3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc3MouseExited
        Opc3.setText(Opc3.getText().substring(3));
    }//GEN-LAST:event_Opc3MouseExited

    private void Opc4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc4MouseEntered
        Opc4.setText(" > "+Opc4.getText());
    }//GEN-LAST:event_Opc4MouseEntered

    private void Opc4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc4MouseExited
        Opc4.setText(Opc4.getText().substring(3));
    }//GEN-LAST:event_Opc4MouseExited

    private void Opc4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc4ActionPerformed
        CSSelecionado.setText("Opc4");
        FecharSelecao(CS, Lista, CampoSelecao);
    }//GEN-LAST:event_Opc4ActionPerformed

    private void AbrirSelecaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirSelecaoActionPerformed
        
        FecharSelecao(CS2, Lista2, CampoSelecao2);
        CaixaSelecao(Lista, CampoSelecao, CS);
    }//GEN-LAST:event_AbrirSelecaoActionPerformed

    private void Opc1_2FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Opc1_2FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_Opc1_2FocusGained

    private void Opc1_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc1_2MouseEntered
        Opc1_2.setText(" > "+Opc1_2.getText());
    }//GEN-LAST:event_Opc1_2MouseEntered

    private void Opc1_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc1_2MouseExited
        Opc1_2.setText(Opc1_2.getText().substring(3));
    }//GEN-LAST:event_Opc1_2MouseExited

    private void Opc1_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc1_2ActionPerformed
        CSSelecionado2.setText("Opc1");
        CaixaSelecao(Lista2, CampoSelecao2,CS2);
    }//GEN-LAST:event_Opc1_2ActionPerformed

    private void Opc2_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc2_2MouseEntered
        Opc2_2.setText(" > "+Opc2_2.getText());
    }//GEN-LAST:event_Opc2_2MouseEntered

    private void Opc2_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc2_2MouseExited
        Opc2_2.setText(Opc2_2.getText().substring(3));
    }//GEN-LAST:event_Opc2_2MouseExited

    private void Opc2_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc2_2ActionPerformed
        CSSelecionado2.setText("Opc2");
        CaixaSelecao(Lista2, CampoSelecao2,CS2);
    }//GEN-LAST:event_Opc2_2ActionPerformed

    private void Opc3_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc3_2MouseEntered
        Opc3_2.setText(" > "+Opc3_2.getText());
    }//GEN-LAST:event_Opc3_2MouseEntered

    private void Opc3_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc3_2MouseExited
        Opc3_2.setText(Opc3_2.getText().substring(3));
    }//GEN-LAST:event_Opc3_2MouseExited

    private void Opc3_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc3_2ActionPerformed
        CSSelecionado2.setText("Opc3");
        CaixaSelecao(Lista2, CampoSelecao2,CS2);
    }//GEN-LAST:event_Opc3_2ActionPerformed

    private void Opc4_2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc4_2MouseEntered
        Opc4_2.setText(" > "+Opc4_2.getText());
    }//GEN-LAST:event_Opc4_2MouseEntered

    private void Opc4_2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc4_2MouseExited
        Opc4_2.setText(Opc4_2.getText().substring(3));
    }//GEN-LAST:event_Opc4_2MouseExited

    private void Opc4_2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc4_2ActionPerformed
        CSSelecionado2.setText("Opc4");
        CaixaSelecao(Lista2, CampoSelecao2,CS2);
    }//GEN-LAST:event_Opc4_2ActionPerformed

    private void AbrirSelecao2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirSelecao2ActionPerformed
        FecharSelecao(CS, Lista, CampoSelecao);
        CaixaSelecao(Lista2, CampoSelecao2,CS2);
        CS2.setName("A");
        CS2.setSize(170,150);
    }//GEN-LAST:event_AbrirSelecao2ActionPerformed

    private void BackgroundMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundMouseEntered

    }//GEN-LAST:event_BackgroundMouseEntered

    private void AbrirSelecaoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirSelecaoMouseClicked

    }//GEN-LAST:event_AbrirSelecaoMouseClicked

    private void BackgroundMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BackgroundMouseClicked
        FecharSelecao(CS, Lista, CampoSelecao);
        FecharSelecao(CS2, Lista2, CampoSelecao2);
    }//GEN-LAST:event_BackgroundMouseClicked

    private void CTO_Log_UsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTO_Log_UsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTO_Log_UsuarioActionPerformed

    private void Background2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background2MouseClicked

    private void Background2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background2MouseEntered

    private void BOX_CTO_Cad_EmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_EmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_Cad_EmailActionPerformed

    private void Opc13FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_Opc13FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_Opc13FocusGained

    private void Opc13MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc13MouseEntered
        Opc13.setText(" > "+Opc13.getText());
    }//GEN-LAST:event_Opc13MouseEntered
    
    private void Opc13MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc13MouseExited
        Opc13.setText(Opc13.getText().substring(3));
    }//GEN-LAST:event_Opc13MouseExited

    private void Opc13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc13ActionPerformed
        CSSelecionado6.setText("Não informar");
        FecharSelecao(CS_Cad_Genero, Lista6, BG_CS_Cad_Genero);
    }//GEN-LAST:event_Opc13ActionPerformed

    private void Opc14MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc14MouseEntered
        Opc14.setText(" > "+Opc14.getText());
    
    }//GEN-LAST:event_Opc14MouseEntered

    private void Opc14MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc14MouseExited
        Opc14.setText(Opc14.getText().substring(3));
    }//GEN-LAST:event_Opc14MouseExited
        
    private void Opc14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc14ActionPerformed
        CSSelecionado6.setText("Feminino");
        FecharSelecao(CS_Cad_Genero, Lista6, BG_CS_Cad_Genero);
    }//GEN-LAST:event_Opc14ActionPerformed

    private void Opc15MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc15MouseEntered
        Opc15.setText(" > "+Opc15.getText());
    }//GEN-LAST:event_Opc15MouseEntered

    private void Opc15MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc15MouseExited
        Opc15.setText(Opc15.getText().substring(3));
    }//GEN-LAST:event_Opc15MouseExited

    private void Opc15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc15ActionPerformed
        CSSelecionado6.setText("Masculino");
        FecharSelecao(CS_Cad_Genero, Lista6, BG_CS_Cad_Genero);
    }//GEN-LAST:event_Opc15ActionPerformed

    private void Opc16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc16MouseEntered
        Opc16.setText(" > "+Opc16.getText());
    }//GEN-LAST:event_Opc16MouseEntered

    private void Opc16MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Opc16MouseExited
        Opc16.setText(Opc16.getText().substring(3));
    }//GEN-LAST:event_Opc16MouseExited

    private void Opc16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Opc16ActionPerformed
        CSSelecionado6.setText("Não binario");
        FecharSelecao(CS_Cad_Genero, Lista6, BG_CS_Cad_Genero);
    }//GEN-LAST:event_Opc16ActionPerformed

    private void AbrirSelecao6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AbrirSelecao6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_AbrirSelecao6MouseClicked

    private void AbrirSelecao6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirSelecao6ActionPerformed
        CaixaSelecao(Lista6, BG_CS_Cad_Genero, CS_Cad_Genero);
    }//GEN-LAST:event_AbrirSelecao6ActionPerformed

    private void Background3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background3MouseClicked

    private void Background3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background3MouseEntered

    private void Background4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background4MouseClicked

    private void Background4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background4MouseEntered

    private void BOX_CT_Home_ProcurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOX_CT_Home_ProcurarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CT_Home_ProcurarActionPerformed

    private void Background5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background5MouseClicked

    private void Background5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background5MouseEntered

    private void Background6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background6MouseClicked

    private void Background6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background6MouseEntered

    private void Background7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background7MouseClicked

    private void Background7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background7MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background7MouseEntered

    private void Background8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background8MouseClicked

    private void Background8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background8MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background8MouseEntered

    private void Background9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background9MouseClicked

    private void Background9MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background9MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background9MouseEntered

    private void Background10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background10MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background10MouseClicked

    private void Background10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background10MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background10MouseEntered

    private void Background11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background11MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background11MouseClicked

    private void Background11MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background11MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background11MouseEntered

    private void Background12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_Background12MouseClicked

    private void Background12MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Background12MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Background12MouseEntered

    private void NavegarItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_NavegarItemStateChanged
        TrocaTela(PainelPrincipal, Navegar.getSelectedIndex());
        System.out.println(Navegar.getSelectedIndex());
    }//GEN-LAST:event_NavegarItemStateChanged

    private void B_Cad_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Cad_CancelarActionPerformed
        TrocaTela(PainelPrincipal, 3);
    }//GEN-LAST:event_B_Cad_CancelarActionPerformed

    private void B_Cad_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Cad_CadastrarActionPerformed
        //verificacamposobrigatorios
        int COCad = 0, VerDup = 0;
        String senha="", confirmasenha="";
        String email= BOX_CTO_Cad_Email.getText();
        String celular = BOX_CT_Cad_Celular.getText();
        
        
        char[] s = BOX_CTO_Cad_Senha.getPassword();
        senha = String.valueOf(s);
        
        char[] sc = BOX_CTO_Cad_SenhaConfirma.getPassword();
        confirmasenha = String.valueOf(sc);
        //apagar campos não obrigatórios sem preenchimento do usuario
        
        //campos obrigatorios
        if (BOX_CTO_Cad_Email.getText().equals("E-mail") || BOX_CTO_Cad_Email.getText().equals("") || BOX_CTO_Cad_Email.getText() == null){
            COCad = 1;
        }
        if (BOX_CTO_Cad_Nome.getText().equals("Nome") || BOX_CTO_Cad_Nome.getText().equals("") || BOX_CTO_Cad_Nome.getText() == null){
            COCad = 1;
        }
        if (!senha.equals(confirmasenha)){
            COCad = 1;
            BG_CTO_Cad_SenhaConfirma.setName("Erro");
            MSGCad.setText("Confirmação de senha não confere.");
            MSGCad.setVisible(true);
        } else {
            MSGCad.setVisible(false);
        }
        PainelCT(TCadastro);
        //execução do codigo de cadastramento
        System.out.println(COCad);
        //MSGCad.setText("");
        //MSGCad.setVisible(false);
        
        
        usuariospojo=usuariosdao.VerificarDuplicidade("email", email);
        if (usuariospojo.getId_Usuario()!=0){
            System.out.println("Verificando email");
            VerDup = VerDup+1;
            MSGCad.setText("Email já cadastrado");
            MSGCad.setVisible(true);
            COCad=1;
            }
        
        usuariospojo=usuariosdao.VerificarDuplicidade("celular", celular);
        System.out.println(usuariospojo.getId_Usuario());
        if (usuariospojo.getId_Usuario()!=0){
            System.out.println("Verificando celular");
            VerDup= VerDup+2;
            MSGCad.setText("Celular já cadastrado");
            MSGCad.setVisible(true);
            COCad=1;
        }
        
        switch (VerDup){
            case 1 : MSGCad.setText("Email já cadastrado");break;
            case 2 : MSGCad.setText("Celular já cadastrado");break;
            case 3 : MSGCad.setText("Email e celular já cadastrados");break;
            case 0 : MSGCad.setText("");break;
        }
        COCad = COCad + VerDup;
        if (COCad==0){
            MSGCad.setVisible(false);
        System.out.println("Cadastrando");
        //String nome,datadenascimento,email,celular,senha,CPF,genero,datadecadastro;
        usuariospojo.setNome(BOX_CTO_Cad_Nome.getText());
        usuariospojo.setDatadenascimento(Padraodata(BOX_CT_Cad_DataNascimento.getText()));
        usuariospojo.setEmail(BOX_CTO_Cad_Email.getText());
        usuariospojo.setCelular(BOX_CT_Cad_Celular.getText());
        usuariospojo.setSenhavelha("descontinuado");
        usuariospojo.setSenha(BOX_CTO_Cad_SenhaConfirma.getText());
        usuariospojo.setGenero(CSSelecionado6.getText());
        usuariospojo.setDatadecadastro(Padraodata(pegardataatual()));
        usuariospojo.setNivel(1);
        usuariosdao.inserirUsuarios(usuariospojo);
        CTO_Log_Usuario.setText(BOX_CTO_Cad_Email.getText());
        CTO_Log_Senha.setText(BOX_CTO_Cad_SenhaConfirma.getText());
        TrocaTela(PainelPrincipal, 3);
        PainelCT(TLogin);
        }
        
        
        
        
        
        
    }//GEN-LAST:event_B_Cad_CadastrarActionPerformed

    private void B_Home_RealizadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Home_RealizadoActionPerformed
        BuscarMeusEventos(T_Home_Eventos,Usuario,"Finalizados");
        AlternarGuia(CampoGuia, 3);
    }//GEN-LAST:event_B_Home_RealizadoActionPerformed

    private void B_Home_ComunidadeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Home_ComunidadeActionPerformed
        BuscarMeusEventos(T_Home_Eventos,Usuario,"Comunidade");
        AlternarGuia(CampoGuia, 2);
    }//GEN-LAST:event_B_Home_ComunidadeActionPerformed

    private void B_Home_MeusEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Home_MeusEventosActionPerformed
        BuscarMeusEventos(T_Home_Eventos,Usuario,"Meus Eventos");
        AlternarGuia(CampoGuia, 1);
    }//GEN-LAST:event_B_Home_MeusEventosActionPerformed

    private void BOX_CTO_CadEvt_TituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOX_CTO_CadEvt_TituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_CadEvt_TituloActionPerformed

    private void BOX_CTO_CadItem_TituloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOX_CTO_CadItem_TituloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_CadItem_TituloActionPerformed

    private void AbrirSelecao3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirSelecao3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbrirSelecao3ActionPerformed

    private void AbrirSelecao4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AbrirSelecao4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_AbrirSelecao4ActionPerformed

    private void CTO_Log_UsuarioFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTO_Log_UsuarioFocusLost
        CampoUsuario();
    }//GEN-LAST:event_CTO_Log_UsuarioFocusLost

    private void CTO_Log_UsuarioFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTO_Log_UsuarioFocusGained
        LimparCampo(CTO_Log_Usuario, "Insira seu e-mail ou telefone",false);
    }//GEN-LAST:event_CTO_Log_UsuarioFocusGained

    private void CTO_Log_SenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTO_Log_SenhaFocusGained
        LimparCampo(CTO_Log_Senha, "senha", true);
    }//GEN-LAST:event_CTO_Log_SenhaFocusGained

    private void B_Convite_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Convite_CancelarActionPerformed
        TrocaTela(PainelPrincipal, 9);
    }//GEN-LAST:event_B_Convite_CancelarActionPerformed

    private void B_Convite_ConvidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Convite_ConvidarActionPerformed
        // nivel;
        // String nome,datadenascimento,email,celular,senhavelha,senha,CPF,genero,datadecadastro;

        if(/*tudo ok*/ true){
        for (int i =0;i<6;i++){
            
            if (!L_nome_email.get(i).equals("")){
            System.out.println("Convidando :"+L_nome_email.get(i));
            usuariosbuscapojo=usuariosbuscadao.VerificarDuplicidade("email", L_email.get(i));
            if (usuariosbuscapojo.getId_Usuario()==0){
            usuariosbuscapojo.setNome(L_nome_email.get(i));
            usuariosbuscapojo.setEmail(L_email.get(i));
            usuariosbuscapojo.setSenhavelha("Descontinuado");
            usuariosbuscapojo.setSenha(GerarCodigoDeConfirmacao(pegardataatual()));
            usuariosbuscapojo.setDatadecadastro(pegardataatual());
            usuariosbuscapojo.setNivel(2);
            usuariosbuscadao.inserirUsuariosConvidados(usuariosbuscapojo);
            usuariosbuscapojo=usuariosbuscadao.VerificarDuplicidade("email", L_email.get(i));
            }
            
            usuarioseventospojo.setId_Usuario(usuariosbuscapojo.getId_Usuario());
            usuarioseventospojo.setId_Evento(EventoSelecionado);
            usuarioseventospojo.setStatus_convite(1);
            usuarioseventospojo.setPago(false);

            usuarioseventosdao.inserirUsuarioEvento(usuarioseventospojo);
        }}
        TrocaTela(PainelPrincipal, 9);
        }
        
    }//GEN-LAST:event_B_Convite_ConvidarActionPerformed

    private void BCadastrarOULoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BCadastrarOULoginActionPerformed
        
        if(TelaLoginStatus()==3){
            if (BCadastrarOULogin.getText().equals("Cadastrar")){
                TrocaTela(PainelPrincipal, 4);
                LoginParaCadastro();
            }
            if (BCadastrarOULogin.getText().equals("Login") && ConsultaSenha(CTO_Log_Usuario, CTO_Log_Senha) ){
                Usuario = usuariospojo.getId_Usuario();
                NivelUsuario = usuariospojo.getNivel();
                TrocaTela(PainelPrincipal, 6);
                
                
            }}else{
            switch(TelaLoginStatus()){
                case 0 : MsgLogin.setText("Preencha: usuario e senha.");break;
                case 1 : MsgLogin.setText("Preencha: senha.");break;
                case 2 : MsgLogin.setText("Preencha: usuario.");break;
                case 3 : MsgLogin.setText("");break;
            
            }
        }
        
    }//GEN-LAST:event_BCadastrarOULoginActionPerformed

    private void CTO_Log_SenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_CTO_Log_SenhaFocusLost
        char[] s = CTO_Log_Senha.getPassword();
        String senha = String.valueOf(s);
        
        if(senha==null && senha.equals("Senha123@#")){
            ImagemFundo8.setName("Erro");
            PainelCT(TLogin);
        }
                
                
                
                
                
    }//GEN-LAST:event_CTO_Log_SenhaFocusLost

    private void BOX_CT_Cad_CelularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_CelularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CT_Cad_CelularActionPerformed

    private void CTO_Log_SenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTO_Log_SenhaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTO_Log_SenhaActionPerformed

    private void BOX_CT_Cad_CelularKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_CelularKeyTyped
            ValoresAceitos("1234567890", evt);
    }//GEN-LAST:event_BOX_CT_Cad_CelularKeyTyped

    private void BOX_CT_Cad_DataNascimentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_DataNascimentoKeyTyped
            ValoresAceitos("1234567890", evt);
    }//GEN-LAST:event_BOX_CT_Cad_DataNascimentoKeyTyped

    private void BOX_CTO_Cad_EmailKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_EmailKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_Cad_EmailKeyPressed

    private void BOX_CT_Cad_CelularKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_CelularKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);;
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CT_Cad_CelularKeyPressed

    private void BOX_CTO_Cad_NomeKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_NomeKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);;
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_Cad_NomeKeyPressed

    private void BOX_CTO_Cad_SenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_SenhaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);;
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_Cad_SenhaKeyPressed

    private void BOX_CTO_Cad_SenhaConfirmaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_SenhaConfirmaKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);;
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CTO_Cad_SenhaConfirmaKeyPressed

    private void BOX_CT_Cad_DataNascimentoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_DataNascimentoKeyPressed
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               TrocarCampo(TCadastro);
               BOX_CT_Cad_DataNascimento.setText(VerificarData(BOX_CT_Cad_DataNascimento, false));
        }         // TODO add your handling code here:
    }//GEN-LAST:event_BOX_CT_Cad_DataNascimentoKeyPressed

    private void BOX_CTO_Cad_EmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_EmailFocusLost
        TrocarCampo(TCadastro);
        PreencherCampo(BOX_CTO_Cad_Email, "E-mail");
    }//GEN-LAST:event_BOX_CTO_Cad_EmailFocusLost

    private void BOX_CT_Cad_CelularFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_CelularFocusLost
        TrocarCampo(TCadastro);
        PreencherCampo(BOX_CT_Cad_Celular, "Celular");
    }//GEN-LAST:event_BOX_CT_Cad_CelularFocusLost

    private void BOX_CTO_Cad_NomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_NomeFocusLost
        TrocarCampo(TCadastro);
        PreencherCampo(BOX_CTO_Cad_Nome, "Nome");
    }//GEN-LAST:event_BOX_CTO_Cad_NomeFocusLost

    private void BOX_CTO_Cad_SenhaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_SenhaFocusLost
        TrocarCampo(TCadastro);
    }//GEN-LAST:event_BOX_CTO_Cad_SenhaFocusLost

    private void BOX_CTO_Cad_SenhaConfirmaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_SenhaConfirmaFocusLost
        TrocarCampo(TCadastro);
    }//GEN-LAST:event_BOX_CTO_Cad_SenhaConfirmaFocusLost

    private void BOX_CT_Cad_DataNascimentoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_DataNascimentoFocusLost
        //TrocarCampo(TCadastro);
        PreencherCampo(BOX_CT_Cad_DataNascimento, "Data de nascimento");
        BOX_CT_Cad_DataNascimento.setText(VerificarData(BOX_CT_Cad_DataNascimento, false));
    }//GEN-LAST:event_BOX_CT_Cad_DataNascimentoFocusLost

    private void BOX_CT_Cad_CelularFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_CelularFocusGained
        LimparCampo(BOX_CT_Cad_Celular, "Celular",false);
    }//GEN-LAST:event_BOX_CT_Cad_CelularFocusGained

    private void BOX_CTO_Cad_EmailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_EmailFocusGained
        LimparCampo(BOX_CTO_Cad_Email, "E-mail",false);
    }//GEN-LAST:event_BOX_CTO_Cad_EmailFocusGained

    private void BOX_CTO_Cad_NomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_NomeFocusGained
        LimparCampo(BOX_CTO_Cad_Nome, "Nome",false);
    }//GEN-LAST:event_BOX_CTO_Cad_NomeFocusGained

    private void BOX_CTO_Cad_SenhaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CTO_Cad_SenhaFocusGained

    }//GEN-LAST:event_BOX_CTO_Cad_SenhaFocusGained

    private void BOX_CT_Cad_DataNascimentoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_BOX_CT_Cad_DataNascimentoFocusGained
        LimparCampo(BOX_CT_Cad_DataNascimento, "Data de nascimento",false);
    }//GEN-LAST:event_BOX_CT_Cad_DataNascimentoFocusGained

    private void BTestesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTestesActionPerformed
        Rotulo.setText(GerarCodigoDeConfirmacao(pegardataatual()));
        
        
    }//GEN-LAST:event_BTestesActionPerformed

    private void BPreencherRapidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BPreencherRapidoActionPerformed
        PreenchimentoRapido();
    }//GEN-LAST:event_BPreencherRapidoActionPerformed

    private void B_Home_NovoEventoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Home_NovoEventoActionPerformed
        TrocaTela(PainelPrincipal, 7);
    }//GEN-LAST:event_B_Home_NovoEventoActionPerformed

    private void B_CadEvt_CriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_CadEvt_CriarActionPerformed
        
        if(true/*verificar conformidade*/){
        eventospojo.setTitulo(BOX_CTO_CadEvt_Titulo.getText());
        eventospojo.setDescricao(BOX_CTO_CadEvt_Descricao.getText());
        //local
        eventospojo.setData(BOX_CTO_CadEvt_Data.getText());
        eventospojo.setHorario(BOX_CT_CadEvt_Horario.getText());
        switch(BOX_CS_CadEvt_Idade.getText()){
            case "Livre" : eventospojo.setAdulto(0);break;
            case "+18" : eventospojo.setAdulto(18);break;
        }
        eventospojo.setHost(Usuario);
        switch(BOX_CS_CadEvt_Visibilidade.getText()){
            case "Oculto" : eventospojo.setVisibilidade(0);break;
            case "Privado" : eventospojo.setVisibilidade(1);break;
            case "Publico" : eventospojo.setVisibilidade(2);break;
        }
        eventospojo.setDatacriacao(Padraodata(pegardataatual()));
        eventosdao.inserirEvento(eventospojo);
        // relacionais
        usuarioseventospojo.setId_Usuario(usuariospojo.getId_Usuario());
        usuarioseventospojo.setId_Evento(eventosdao.BuscarUltimoEvento(Usuario));
        usuarioseventospojo.setPago(false);
        usuarioseventosdao.inserirUsuarioEvento(usuarioseventospojo);
        TrocaTela(PainelPrincipal,6);
        }
    }//GEN-LAST:event_B_CadEvt_CriarActionPerformed

    private void B_CadItem_CriarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_CadItem_CriarActionPerformed
        
        
        if(/*tudo ok*/true){
            itenspojo.setTitulo(BOX_CTO_CadItem_Titulo.getText());
            itenspojo.setDescricao(BOX_CTO_CadItem_Descricao.getText());
            //tratamento do valor DOUBLE para aceitar "," como separador
            itenspojo.setValoritem(ConverterDouble(BOX_CTO_CadItem_Valor.getText()));
            itenspojo.setPagante(BOX_CT_CadItem_Pagante.getText());
            itenspojo.setCriado(Usuario);
            itensdao.inserirItem(itenspojo);
            
            //cadastro na associativa
            eventositenspojo.setId_Item(itensdao.BuscarUltimoItem(Usuario));
            eventositenspojo.setId_Evento(EventoSelecionado);
            eventositensdao.inserirEventoItem(eventositenspojo);
            TrocaTela(PainelPrincipal, 9);
        }
    }//GEN-LAST:event_B_CadItem_CriarActionPerformed

    private void B_EvtHome_NovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_NovoActionPerformed
            TrocaTela(PainelPrincipal,10);
    }//GEN-LAST:event_B_EvtHome_NovoActionPerformed

    private void B_Home_ParticiparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_Home_ParticiparActionPerformed
        //precisa pegar o evento, verificar se o usuario participa ou não
        // usuariopojo=usuariodao.BuscarContatoUnico(Integer.parseInt(""+TTabela.getValueAt(TTabela.getSelectedRow(), 0)));
        
        if (/*se usuario participa*/true){
            EventoSelecionado= Integer.parseInt(""+T_Home_Eventos.getValueAt(T_Home_Eventos.getSelectedRow(), 3));
            TrocaTela(PainelPrincipal,9);
            
            
        }
    }//GEN-LAST:event_B_Home_ParticiparActionPerformed

    private void B_EvtHome_DescricaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_DescricaoActionPerformed
        TrocarGuiaEventoHome(1);
    }//GEN-LAST:event_B_EvtHome_DescricaoActionPerformed

    private void B_EvtHome_ValoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_ValoresActionPerformed
        TrocarGuiaEventoHome(2);
    }//GEN-LAST:event_B_EvtHome_ValoresActionPerformed

    private void CampoTextoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CampoTextoKeyTyped
        int virgula = ContarVirgula(CampoTexto.getText());//10 11 12
        if(virgula == 0){
            ValoresAceitos("0123456789,", evt);
        }
        if (virgula !=0){
            if(CampoTexto.getText().length() == virgula + 3){
                ValoresAceitos("", evt);
            }
            ValoresAceitos("0123456789", evt);
        }
    }//GEN-LAST:event_CampoTextoKeyTyped

    private void BOX_CTO_CadItem_ValorKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BOX_CTO_CadItem_ValorKeyTyped
int virgula = ContarVirgula(BOX_CTO_CadItem_Valor.getText());//10 11 12
        if(virgula == 0){
            ValoresAceitos("0123456789,", evt);
        }
        if (virgula !=0){
            if(BOX_CTO_CadItem_Valor.getText().length() == virgula + 3){
                ValoresAceitos("", evt);
            }
            ValoresAceitos("0123456789", evt);
        }
    }//GEN-LAST:event_BOX_CTO_CadItem_ValorKeyTyped

    private void B_CadEvt_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_CadEvt_CancelarActionPerformed

    }//GEN-LAST:event_B_CadEvt_CancelarActionPerformed

    private void CTBOX_Convite_Email2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CTBOX_Convite_Email2ActionPerformed

    private void CTBOX_Convite_Email1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email1KeyPressed
        int sessao=1;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email1,sessao)){
                OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
        }}
        
    }//GEN-LAST:event_CTBOX_Convite_Email1KeyPressed

    private void CTBOX_Convite_Email2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email2KeyPressed
        int sessao=2;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email2,sessao)){
                OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
        }}
    }//GEN-LAST:event_CTBOX_Convite_Email2KeyPressed

    private void CTBOX_Convite_Email3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email3KeyPressed
        int sessao=3;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email3,sessao)){
                OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
        }}
    }//GEN-LAST:event_CTBOX_Convite_Email3KeyPressed

    private void CTBOX_Convite_Email4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email4KeyPressed
        int sessao=4;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email4,sessao)){
                OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
        }}
    }//GEN-LAST:event_CTBOX_Convite_Email4KeyPressed

    private void CTBOX_Convite_Email5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email5KeyPressed
        int sessao=5;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email5,sessao)){
                OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
        }}
    }//GEN-LAST:event_CTBOX_Convite_Email5KeyPressed

    private void CTBOX_Convite_Email6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CTBOX_Convite_Email6KeyPressed
        int sessao=1;
        if(evt.getKeyCode() == KeyEvent.VK_ENTER){
               if(EmailCheck(CTBOX_Convite_Email6,sessao)){
                //OcultarCamposConvite(""+(sessao+1),B_Convite_Cancelar,B_Convite_Convidar);
                System.out.println(L_nome_email.get(sessao));
                System.out.println(L_email.get(sessao));
        }}
    }//GEN-LAST:event_CTBOX_Convite_Email6KeyPressed

    private void BotaoM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotaoM9ActionPerformed
        TrocaTela(PainelPrincipal, 9);
    }//GEN-LAST:event_BotaoM9ActionPerformed

    private void B_EvtHome_ConvidarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_ConvidarActionPerformed

        if(Usuario == HostEvento){
            TrocaTela(PainelPrincipal, 12);
        }  
    }//GEN-LAST:event_B_EvtHome_ConvidarActionPerformed

    private void B_EvtHome_SairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_SairActionPerformed
        TrocaTela(PainelPrincipal, 6);
    }//GEN-LAST:event_B_EvtHome_SairActionPerformed

    private void B_EvtHome_VerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EvtHome_VerActionPerformed
        ItemSelecionado = (int)T_EvtHome_Itens.getValueAt(T_EvtHome_Itens.getSelectedRow(), 2);
        TrocaTela(PainelPrincipal, 10);
    }//GEN-LAST:event_B_EvtHome_VerActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AbrirSelecao;
    private javax.swing.JButton AbrirSelecao2;
    private javax.swing.JButton AbrirSelecao3;
    private javax.swing.JButton AbrirSelecao4;
    private javax.swing.JButton AbrirSelecao6;
    private javax.swing.JButton BCadastrarOULogin;
    private javax.swing.JLabel BG_CS_Cad_Genero;
    private javax.swing.JLabel BG_CTO_Cad_Email;
    private javax.swing.JLabel BG_CTO_Cad_Nome;
    private javax.swing.JLabel BG_CTO_Cad_Senha;
    private javax.swing.JLabel BG_CTO_Cad_SenhaConfirma;
    private javax.swing.JLabel BG_CT_Cad_Celular;
    private javax.swing.JLabel BG_CT_Cad_DataNascimento;
    private javax.swing.JLabel BG_Convite_Email1;
    private javax.swing.JLabel BG_Convite_Email2;
    private javax.swing.JLabel BG_Convite_Email3;
    private javax.swing.JLabel BG_Convite_Email4;
    private javax.swing.JLabel BG_Convite_Email5;
    private javax.swing.JLabel BG_Convite_Email6;
    private javax.swing.JButton BIniciarApp;
    private javax.swing.JLabel BOX_CS_CadEvt_Idade;
    private javax.swing.JLabel BOX_CS_CadEvt_Visibilidade;
    private javax.swing.JTextField BOX_CTO_CadEvt_Data;
    private javax.swing.JTextArea BOX_CTO_CadEvt_Descricao;
    private javax.swing.JTextField BOX_CTO_CadEvt_Titulo;
    private javax.swing.JTextArea BOX_CTO_CadItem_Descricao;
    private javax.swing.JTextField BOX_CTO_CadItem_Titulo;
    private javax.swing.JTextField BOX_CTO_CadItem_Valor;
    private javax.swing.JTextField BOX_CTO_Cad_Email;
    private javax.swing.JTextField BOX_CTO_Cad_Nome;
    private javax.swing.JPasswordField BOX_CTO_Cad_Senha;
    private javax.swing.JPasswordField BOX_CTO_Cad_SenhaConfirma;
    private javax.swing.JTextField BOX_CT_CadEvt_Horario;
    private javax.swing.JTextField BOX_CT_CadItem_Pagante;
    private javax.swing.JTextField BOX_CT_Cad_Celular;
    private javax.swing.JTextField BOX_CT_Cad_DataNascimento;
    private javax.swing.JTextField BOX_CT_Home_Procurar;
    private javax.swing.JButton BPreencherRapido;
    private javax.swing.JButton BSair;
    private javax.swing.JButton BTestes;
    private javax.swing.JButton B_CadEvt_Cancelar;
    private javax.swing.JButton B_CadEvt_Criar;
    private javax.swing.JButton B_CadItem_Criar;
    private javax.swing.JButton B_Cad_Cadastrar;
    private javax.swing.JButton B_Cad_Cancelar;
    private javax.swing.JButton B_Cad_LimparTudo;
    private javax.swing.JButton B_Convite_Cancelar;
    private javax.swing.JButton B_Convite_Convidar;
    private javax.swing.JButton B_EvtHome_Convidar;
    private javax.swing.JButton B_EvtHome_Descricao;
    private javax.swing.JButton B_EvtHome_Novo;
    private javax.swing.JButton B_EvtHome_Sair;
    private javax.swing.JButton B_EvtHome_Valores;
    private javax.swing.JButton B_EvtHome_Ver;
    private javax.swing.JButton B_Home_Comunidade;
    private javax.swing.JButton B_Home_MeusEventos;
    private javax.swing.JButton B_Home_NovoEvento;
    private javax.swing.JButton B_Home_Participar;
    private javax.swing.JButton B_Home_Realizado;
    private javax.swing.JLabel B_Log_EsqueciSenha;
    private javax.swing.JLabel Background;
    private javax.swing.JLabel Background10;
    private javax.swing.JLabel Background11;
    private javax.swing.JLabel Background12;
    private javax.swing.JLabel Background2;
    private javax.swing.JLabel Background3;
    private javax.swing.JLabel Background4;
    private javax.swing.JLabel Background5;
    private javax.swing.JLabel Background6;
    private javax.swing.JLabel Background7;
    private javax.swing.JLabel Background8;
    private javax.swing.JLabel Background9;
    private javax.swing.JButton BotaoM23;
    private javax.swing.JButton BotaoM24;
    private javax.swing.JButton BotaoM25;
    private javax.swing.JButton BotaoM26;
    private javax.swing.JButton BotaoM9;
    private javax.swing.JPanel CS;
    private javax.swing.JPanel CS2;
    private javax.swing.JPanel CS3;
    private javax.swing.JPanel CS4;
    private javax.swing.JLabel CSSelecionado;
    private javax.swing.JLabel CSSelecionado2;
    private javax.swing.JLabel CSSelecionado6;
    private javax.swing.JPanel CS_Cad_Genero;
    private javax.swing.JPanel CT10;
    private javax.swing.JPanel CT18;
    private javax.swing.JPanel CT20;
    private javax.swing.JPanel CT7;
    private javax.swing.JTextField CTBOX_Convite_Email1;
    private javax.swing.JTextField CTBOX_Convite_Email2;
    private javax.swing.JTextField CTBOX_Convite_Email3;
    private javax.swing.JTextField CTBOX_Convite_Email4;
    private javax.swing.JTextField CTBOX_Convite_Email5;
    private javax.swing.JTextField CTBOX_Convite_Email6;
    private javax.swing.JPanel CTO;
    private javax.swing.JPanel CTO1;
    private javax.swing.JPanel CTO10;
    private javax.swing.JPanel CTO13;
    private javax.swing.JPanel CTO19;
    private javax.swing.JPanel CTO2;
    private javax.swing.JPanel CTO3;
    private javax.swing.JPanel CTO8;
    private javax.swing.JPanel CTOSenha;
    private javax.swing.JPanel CTOUsuario;
    private javax.swing.JPanel CTO_Cad_Email;
    private javax.swing.JPanel CTO_Cad_Nome;
    private javax.swing.JPanel CTO_Cad_Senha;
    private javax.swing.JPanel CTO_Cad_SenhaConfirma;
    private javax.swing.JPasswordField CTO_Log_Senha;
    private javax.swing.JTextField CTO_Log_Usuario;
    private javax.swing.JPanel CT_Cad_Celular;
    private javax.swing.JPanel CT_Cad_DataNascimento;
    private javax.swing.JPanel CT_Convite_Email1;
    private javax.swing.JPanel CT_Convite_Email2;
    private javax.swing.JPanel CT_Convite_Email3;
    private javax.swing.JPanel CT_Convite_Email4;
    private javax.swing.JPanel CT_Convite_Email5;
    private javax.swing.JPanel CT_Convite_Email6;
    private javax.swing.JLabel CaixaBotton;
    private javax.swing.JLabel CaixaBotton2;
    private javax.swing.JLabel CaixaBotton6;
    private javax.swing.JLabel CaixaMeio;
    private javax.swing.JLabel CaixaMeio2;
    private javax.swing.JLabel CaixaMeio6;
    private javax.swing.JLabel CaixaTop2;
    private javax.swing.JLabel CaixaTop4;
    private javax.swing.JLabel CaixaTop8;
    private javax.swing.JLabel CampoGuia;
    private javax.swing.JLabel CampoSelecao;
    private javax.swing.JLabel CampoSelecao2;
    private javax.swing.JLabel CampoSelecao3;
    private javax.swing.JLabel CampoSelecao4;
    private javax.swing.JPasswordField CampoSenha;
    private javax.swing.JTextField CampoTexto;
    private javax.swing.JScrollPane CampoTextoLongo;
    private javax.swing.JScrollPane CampoTextoLongo1;
    private javax.swing.JScrollPane CampoTextoLongo2;
    public javax.swing.JPanel Desktop;
    private javax.swing.JPanel DevTools;
    private javax.swing.JLabel EvtHome_Data;
    private javax.swing.JTextArea EvtHome_Descricao;
    private javax.swing.JLabel EvtHome_Hora;
    private javax.swing.JLabel EvtHome_Host;
    private javax.swing.JLabel EvtHome_Titulo_Evento;
    private javax.swing.JLabel Fundo;
    private javax.swing.JLabel Fundo1;
    private javax.swing.JLabel Fundo10;
    private javax.swing.JLabel Fundo11;
    private javax.swing.JLabel Fundo12;
    private javax.swing.JLabel Fundo13;
    private javax.swing.JLabel Fundo3;
    private javax.swing.JLabel Fundo4;
    private javax.swing.JLabel Fundo6;
    private javax.swing.JLabel Fundo7;
    private javax.swing.JLabel Fundo8;
    private javax.swing.JLabel FundoCTOUsuario;
    private javax.swing.JPanel Guias;
    private javax.swing.JLabel ImagemFundo;
    private javax.swing.JLabel ImagemFundo15;
    private javax.swing.JLabel ImagemFundo19;
    private javax.swing.JLabel ImagemFundo2;
    private javax.swing.JLabel ImagemFundo20;
    private javax.swing.JLabel ImagemFundo28;
    private javax.swing.JLabel ImagemFundo29;
    private javax.swing.JLabel ImagemFundo3;
    private javax.swing.JLabel ImagemFundo4;
    private javax.swing.JLabel ImagemFundo45;
    private javax.swing.JLabel ImagemFundo5;
    private javax.swing.JLabel ImagemFundo51;
    private javax.swing.JLabel ImagemFundo8;
    private javax.swing.JPanel Lista;
    private javax.swing.JPanel Lista2;
    private javax.swing.JPanel Lista6;
    private javax.swing.JLabel MSGCad;
    private javax.swing.JLabel MobileMuckUp;
    private javax.swing.JLabel MsgLogin;
    private javax.swing.JComboBox<String> Navegar;
    private javax.swing.JButton Opc1;
    private javax.swing.JButton Opc13;
    private javax.swing.JButton Opc14;
    private javax.swing.JButton Opc15;
    private javax.swing.JButton Opc16;
    private javax.swing.JButton Opc1_2;
    private javax.swing.JButton Opc2;
    private javax.swing.JButton Opc2_2;
    private javax.swing.JButton Opc3;
    private javax.swing.JButton Opc3_2;
    private javax.swing.JButton Opc4;
    private javax.swing.JButton Opc4_2;
    private javax.swing.JPanel PDescricaoEvento;
    private javax.swing.JPanel PDescricaoValores;
    private javax.swing.JPanel PValoresEvento;
    public javax.swing.JPanel PainelPrincipal;
    private javax.swing.JPanel Paleta;
    private javax.swing.JLabel R_EvtHome_CustoIndividual;
    private javax.swing.JLabel R_EvtHome_CustoTotal;
    private javax.swing.JLabel R_EvtHome_MembrosTotal;
    private javax.swing.JLabel Rotulo;
    private javax.swing.JLabel Rotulo11;
    private javax.swing.JLabel Rotulo12;
    private javax.swing.JLabel Rotulo14;
    private javax.swing.JLabel Rotulo8;
    private javax.swing.JLabel Rotulo9;
    private javax.swing.JPanel TCadastro;
    private javax.swing.JPanel TCadastro_Editar;
    private javax.swing.JPanel TConvidar;
    private javax.swing.JPanel TEvento_Criar;
    private javax.swing.JPanel TEvento_Editar;
    private javax.swing.JPanel TEvento_Home;
    private javax.swing.JPanel THome;
    private javax.swing.JPanel TItens_Adicionar;
    private javax.swing.JPanel TItens_VerEditar;
    private javax.swing.JPanel TLogin;
    private javax.swing.JPanel TSolicitacoes;
    private javax.swing.JTable T_EvtHome_Itens;
    private javax.swing.JTable T_EvtHome_Participantes;
    private javax.swing.JTable T_Home_Eventos;
    private javax.swing.JTextArea TesteTexto;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable4;
    private javax.swing.JTable jTable6;
    private javax.swing.JTable jTable9;
    private javax.swing.JScrollPane jscrollll;
    // End of variables declaration//GEN-END:variables
}
