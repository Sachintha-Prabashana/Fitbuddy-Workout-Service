package lk.ijse.eca.workoutservice.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;

/**
 * Intercepts outgoing RestClient calls and propagates
 * X-User-* identity headers from the original incoming request.
 * This ensures inter-service calls behind the gateway carry
 * the authenticated user's identity.
 */
@Component
public class HeaderPropagationInterceptor implements ClientHttpRequestInterceptor {

    private static final String[] HEADERS_TO_PROPAGATE = {
            "X-User-Id",
            "X-User-Email",
            "X-User-Role"
    };

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        
        ServletRequestAttributes attributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attributes != null) {
            HttpServletRequest incomingRequest = attributes.getRequest();
            for (String header : HEADERS_TO_PROPAGATE) {
                String value = incomingRequest.getHeader(header);
                if (value != null && !value.isEmpty()) {
                    request.getHeaders().set(header, value);
                }
            }
        }

        return execution.execute(request, body);
    }
}
