package semestral_derole.POJO;

public class UsuariosEventosPOJO {
    int Id_UsuarioEvento,Id_Usuario=0,Id_Evento=0, Status_convite;

    public int getStatus_convite() {
        return Status_convite;
    }

    public void setStatus_convite(int Status_convite) {
        this.Status_convite = Status_convite;
    }
    boolean pago;

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

    public int getId_UsuarioEvento() {
        return Id_UsuarioEvento;
    }

    public void setId_UsuarioEvento(int Id_UsuarioEvento) {
        this.Id_UsuarioEvento = Id_UsuarioEvento;
    }

    public int getId_Usuario() {
        return Id_Usuario;
    }

    public void setId_Usuario(int Id_Usuario) {
        this.Id_Usuario = Id_Usuario;
    }

    public int getId_Evento() {
        return Id_Evento;
    }

    public void setId_Evento(int Id_Evento) {
        this.Id_Evento = Id_Evento;
    }
}
