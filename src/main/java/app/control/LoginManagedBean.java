package app.control;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import app.dao.UsuarioDAO;
import app.model.Usuario;

@ManagedBean(name = "LoginBean")
@SessionScoped
public class LoginManagedBean implements Serializable{

      /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private UsuarioDAO usuarioDAO = new UsuarioDAO(Usuario.class);
      private Usuario usuario = new Usuario();
      private static Logger LOGGER = Logger.getLogger(LoginManagedBean.class);

      public String login() {

    	  	try{
    	  		boolean valido = usuarioDAO.validateUser(usuario.getNomeUsuario(), usuario.getSenha());
    	  		if ( valido ) {

            	HttpSession session = SessionBean.getSession();
                session.setAttribute("nomeUsuario",usuario.getNomeUsuario() );

                  return "Inicio?faces-redirect=true";
            } else {

            	FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Usuario e Senha incorretos",
                                "Por favor retorne a pagina de login"));
            	return "login?faces-redirect=true";
            }
    	  	}catch(Exception e){
    	  		LOGGER.error("Erro ao efetuar login", e);
    	  		FacesContext.getCurrentInstance().addMessage(
                        null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Erro ao efetuar login:",
                                e.getMessage()));
    	  		return "login?faces-redirect=true";
    	  	}

      }

      public Usuario getUsuario() {
            return usuario;
      }

      public void setUsuario(Usuario usuario) {
            this.usuario = usuario;
      }

     public String logout() {
       HttpSession session = SessionBean.getSession();
       session.invalidate();
          return "login?faces-redirect=true";
      }

     public String inserirUsuario(String novoNomeUsuario, String novaSenha){

    	 Usuario novoUsuario = new Usuario();
    	 novoUsuario.setNomeUsuario(novoNomeUsuario);
    	 novoUsuario.setSenha(novaSenha);

    	 LOGGER.info("Verificando se existe usuario igual na base.");
    	 if(usuarioDAO.findByName(novoNomeUsuario) == null){
    		 LOGGER.info("Criando usuario.");
    		 usuarioDAO.save(novoUsuario);
    		 FacesContext.getCurrentInstance().addMessage(
                     null,
                     new FacesMessage(FacesMessage.SEVERITY_INFO,
                             "Sucesso",
                             "Usuario " + novoNomeUsuario + " foi cadastrado com sucesso!"));
    		 LOGGER.info("Sucesso: Usuario " + novoNomeUsuario + " foi cadastrado com sucesso!");
    		 novoNomeUsuario = null;
    	 }else{
    		 FacesContext.getCurrentInstance().addMessage(
                     null,
                     new FacesMessage(FacesMessage.SEVERITY_WARN,
                             "Atencao",
                             "Usuario " + novoNomeUsuario + " ja cadastrado!"));
    		 LOGGER.info("Atencao: Usuario " + novoNomeUsuario + " ja cadastrado!");
    		 novoNomeUsuario = null;
    	 }


    	 return "";
     }

}