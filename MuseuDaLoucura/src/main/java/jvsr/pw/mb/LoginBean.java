package jvsr.pw.mb;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import jvsr.pw.dao.UsuarioDAO;
import jvsr.pw.modelo.Usuario;

@SessionScoped
@ManagedBean
public class LoginBean {
	private Usuario usuario = new Usuario();
	
	public String efetuaLogin() {
		UsuarioDAO usuarioDAO = new UsuarioDAO();
		boolean existe = usuarioDAO.existe(usuario);
		if(existe) {
			return "agendamento?faces-redirect=true";
		}else {
			usuario = new Usuario();
			return "login?faces-redirect=true";
		}
	}
	
	public boolean isLogado() {
		return usuario.getLogin() != null;
	}
	
	public String logout() {
		usuario = new Usuario();
		return "login?faces-redirect=true";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}