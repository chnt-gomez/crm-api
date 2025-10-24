    package com.vmartinez.crm.advicers;

    import com.vmartinez.crm.entity.Audit;
    import com.vmartinez.crm.repository.AuditRepository;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.MethodParameter;
    import org.springframework.http.MediaType;
    import org.springframework.http.converter.HttpMessageConverter;

    import org.springframework.http.server.ServerHttpRequest;
    import org.springframework.http.server.ServerHttpResponse;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

    import java.time.Instant;

    @ControllerAdvice
    public class AuditControllerAdvice implements ResponseBodyAdvice<Object> {

        @Autowired
        private AuditRepository repository;

        @Override
        public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
            return true; // just intercepting all requests
        }

        @Override
        public Object beforeBodyWrite(Object body,
                                      MethodParameter returnType,
                                      MediaType selectedContentType,
                                      Class<? extends HttpMessageConverter<?>>
                                                  selectedConverterType,
                                      ServerHttpRequest request,
                                      ServerHttpResponse response) {
            try {
                String method = request.getMethod() != null ? request.getMethod().name() : "UNKNOWN";
                String uri = request.getURI().toString();

                // Response status is not accessible here
                int status = 200;

                String responseBody = body != null ? body.toString() : null;

                Audit audit = new Audit(
                        method,
                        uri,
                        status,
                        Instant.now(),
                        null, // request body - optional via filter
                        responseBody
                );

                repository.save(audit);
            } catch (Exception  e) {
                e.printStackTrace();
            }
            return body;
        }
    }
