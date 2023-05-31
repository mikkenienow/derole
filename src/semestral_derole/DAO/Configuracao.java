/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package semestral_derole.DAO;

public class Configuracao extends Object {

	private String driver;
	private String user;
	private String password;
	private String dbURL;
	private String host;
	private String port;
	private String database;
	private String diretorioServidor;
	private String diretorioUpload;
	private String diretorioDownload;
	private String diretorioDownloadVirtual;
	private String smtp;
        
	public Configuracao() {
		driver = "com.mysql.jdbc.Driver";
		user = "**";
		password = "**";
                host = "**";
                port = "**";
                database = "**";
		dbURL = "jdbc:mysql://"+ host + ":" + port + "/" + database;

	}

	public String getDriver() {
		return this.driver;
	}

	public String getUser() {
		return this.user;
	}

	public String getPassword() {
		return this.password;
	}

	public String getDbURL() {
		return this.dbURL;
	}

	public String getDiretorioServidor() {
		return this.diretorioServidor;
	}

	public String getDiretorioUpload() {
		return this.diretorioUpload;
	}

	public String getDiretorioDownload() {
		return this.diretorioDownload;
	}

	public String getDiretorioDownloadVirtual() {
		return this.diretorioDownloadVirtual;
	}

	public String getSmtp() {
		return this.smtp;
	}

  protected Object clone() throws CloneNotSupportedException
  {
    // TODO:  Override this java.lang.Object method
    return super.clone();
  }

  public boolean equals(Object obj)
  {
    // TODO:  Override this java.lang.Object method
    return super.equals(obj);
  }

  protected void finalize() throws Throwable
  {
    // TODO:  Override this java.lang.Object method
    super.finalize();
  }

  public int hashCode()
  {
    // TODO:  Override this java.lang.Object method
    return super.hashCode();
  }

  public String toString()
  {
    // TODO:  Override this java.lang.Object method
    return super.toString();
  }
}
