package unsl.filters;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import javax.servlet.*;
import java.io.IOException;

@Component
@Order(1)
public class ResquestResponseFilter implements Filter {
    int failRatio=50;
    int minResponseTime=100;
    int maxResponseTime=1000;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        long responseTime=Math.round(Math.random()*(maxResponseTime-minResponseTime)+minResponseTime);
        long fail=Math.round(Math.random()*100);
        try {
            Thread.sleep(responseTime);
        } catch (InterruptedException e) {
            throw new RuntimeException();
        }
        if(fail< failRatio){
            throw new RuntimeException();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
