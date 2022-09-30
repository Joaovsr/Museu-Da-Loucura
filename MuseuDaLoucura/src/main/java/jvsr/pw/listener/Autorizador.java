package jvsr.pw.listener;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import jvsr.pw.mb.LoginBean;

public class Autorizador implements PhaseListener {

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {		
		FacesContext context = event.getFacesContext();

		String no_login_sites[] = {"/login.xhtml",
				"/agendamento.xhtml",
				"/cancelamento.xhtml", 
				"/atracao.xhtml", 
				"/erro.xhtml", 
				"/sucesso.xhtml",
				"/erro_alteracao.xhtml",
				"/confirmacao_alteracao.xhtml", 
				"/erro_cancelamento.xhtml",
				"/confirmacao_cancelamento.xhtml" };
		
		for(int i=0; i<no_login_sites.length; i++) {
			if ( no_login_sites[i].equals(context.getViewRoot().getViewId()) )
				return;
		}
		
		LoginBean loginBean = context.getApplication().evaluateExpressionGet
				(context, "#{loginBean}", LoginBean.class);

		if (!loginBean.isLogado()) {
			NavigationHandler handler = context.getApplication().getNavigationHandler();

			handler.handleNavigation(context, null, "login?faces-redirect=true");

			context.renderResponse();
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {		

	}

	@Override
	public PhaseId getPhaseId() {		
		return PhaseId.RESTORE_VIEW;
	}
}
