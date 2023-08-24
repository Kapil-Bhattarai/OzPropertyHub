package authentication.Filters;

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
import oz.user.UserController;

@WebFilter("/*")
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
       HttpServletRequest req = (HttpServletRequest) request;
       HttpServletResponse resp = (HttpServletResponse) response;
      
       UserController session = (UserController) req.getSession().getAttribute("userBean");
     
       String[] afterLog = {"logout.faces?faces-redirect=true", "default.faces?faces-redirect=true", "admin_dashboard.faces?faces-redirect=true", "agent_dashboard.faces?faces-redirect=true", "user_dashboard.faces?faces-redirect=true"};
       String url=req.getRequestURI();
       if (session==null || !session.isLoggedIn()) {
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
               req.getSession().removeAttribute("userBean");
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
