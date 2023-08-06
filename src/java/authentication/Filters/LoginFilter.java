package authentication.Filters;

import authentication.Beans.AutenticationBean;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse resp = (HttpServletResponse) response;
       //Retrieve the authtication bean
       AutenticationBean session = (AutenticationBean) req.getSession().getAttribute("authBean");
       //Any after login accessible pages should be listed here
       String[] afterLog = {"logout.faces", "default.faces", "admin_dashboard.faces", "agent_dashboard.faces", "user_dashboard.faces"};
       String url=req.getRequestURI();
       if (session==null || !session.isLogged()) {
           boolean risk=false;
           for (String afterLog1 : afterLog) {
               if (url.contains(afterLog1)) {
                   risk=true;
                   break;
               }
           }
           if (risk) {
               resp.sendRedirect(req.getServletContext().getContextPath()+"/join.faces");
           } 
           else {
               chain.doFilter(request, response);
           }
       } else {
           if (url.contains("register.xhtml") || url.contains("login.faces")
                   || url.contains("emailVerification.faces") || url.contains("emailRecovery.faces")
                   || url.contains("userRecovery.faces") ) {
               resp.sendRedirect(req.getServletContext().getContextPath()+"/default.faces");
           }
           else if (url.contains("logout.faces")) {
               req.getSession().removeAttribute("authBean");
               resp.sendRedirect(req.getServletContext().getContextPath()+"/login.faces");
           }
           else {
               chain.doFilter(request, response);
           }
       }
    }
    
    @Override
    public void destroy() {  }
    
}
