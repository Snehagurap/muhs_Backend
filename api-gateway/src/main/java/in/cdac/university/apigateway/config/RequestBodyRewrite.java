package in.cdac.university.apigateway.config;

import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import in.cdac.university.apigateway.exception.FormDataTamperedException;
import in.cdac.university.apigateway.exception.InvalidFormValuesException;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.Part;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Slf4j
public class RequestBodyRewrite implements RewriteFunction<String, String> {

    private final String clientRequestSecurityToken;

    public RequestBodyRewrite(String clientRequestSecurityToken) {
        this.clientRequestSecurityToken = clientRequestSecurityToken;
    }

    private void jsonToString(Object body, StringBuilder result) {
        if (body instanceof Map bodyAsMap) {
            for (Object key: bodyAsMap.keySet()) {
                Object value = bodyAsMap.get(key);
                if (value instanceof String valueAsString) {
                    checkValidCharacters(valueAsString);
                    result.append(valueAsString);
                } else if (value instanceof List valueAsList) {
                    for (Object o : valueAsList)
                        jsonToString(o, result);
                } else if (value instanceof Map valueAsMap) {
                    jsonToString(valueAsMap, result);
                }
            }
        } else if(body instanceof ArrayList<?> bodyAsList) {
            String string = bodyAsList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            result.append(string);
        } else if (body instanceof String bodyAsString) {
            result.append(bodyAsString);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Publisher<String> apply(ServerWebExchange serverWebExchange, String body) {
        MediaType contentType = serverWebExchange.getRequest().getHeaders().getContentType();
        if (body != null && contentType != null && contentType.includes(MediaType.MULTIPART_FORM_DATA)) {
            // File upload
            return Mono.just(body);
        } else {
            Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LAZILY_PARSED_NUMBER).create();
            if(body!=null && body.startsWith("[")){
                ArrayList<Long> listbody = gson.fromJson(body, ArrayList.class);
                if(listbody != null && !listbody.isEmpty()){
                    StringBuilder sb = new StringBuilder();
                    jsonToString(listbody, sb);
                    String values = sb.toString();
                    String serverRequestSecurityToken = Hashing.sha256().hashString(values, StandardCharsets.UTF_8).toString();
                    if(!this.clientRequestSecurityToken.equals(serverRequestSecurityToken)) {
                        log.error("Value: {}", values);
                        log.error("Server Token : {}", serverRequestSecurityToken);
                        log.error("Client Token : {}", this.clientRequestSecurityToken);
                        throw new FormDataTamperedException("Form data tampered");
                    }
                }
                return Mono.just(gson.toJson(listbody, ArrayList.class));
            }
            else{
                Map<String, Object> map = gson.fromJson(body, Map.class);
                if (map != null && !map.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    jsonToString(map, sb);
                    String values = sb.toString();
                    String serverRequestSecurityToken = Hashing.sha256().hashString(values, StandardCharsets.UTF_8).toString();
                    if (!this.clientRequestSecurityToken.equals(serverRequestSecurityToken)) {
                        log.error("Value: {}", values);
                        log.error("Server Token: {}", serverRequestSecurityToken);
                        log.error("Client Token: {}", this.clientRequestSecurityToken);
                        throw new FormDataTamperedException("Form data tampered");
                    }
                }
                return Mono.just(gson.toJson(map, Map.class));
            }
        }
    }

    private static final List<Pattern> scriptPatterns = new ArrayList<>();
    static {

        scriptPatterns.add(Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE));
        scriptPatterns.add(Pattern.compile("</script>", Pattern.CASE_INSENSITIVE));
        scriptPatterns.add(Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE));
        scriptPatterns.add(Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE));
        scriptPatterns.add(Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onblur(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onClick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onmouseover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onmousedown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onchange(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("ondblclick(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onfocus(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onkeydown(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onkeyup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onmouseout(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onmouseup(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onmousemover(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
        scriptPatterns.add(Pattern.compile("onselect(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL));
    }

    private static final Pattern tablePattern = Pattern.compile("<.*>", Pattern.CASE_INSENSITIVE);
    private static final Pattern metaCharacterPattern = Pattern.compile("[!@#\\$+()\\[\\]%\\^&*]{3,}");

    private void checkValidCharacters(String value) {

        Matcher m;

        m = tablePattern.matcher(value);
        if (m.find()) {
            throw new InvalidFormValuesException("Invalid characters found in the input: " + value);
        }

        // Filter for 3 or more consecutive meta characters
        m = metaCharacterPattern.matcher(value);
        if (m.find()) {
            String temp = value;
            temp = temp.replace("$$$", "").replace("^^^", "").replace("@@@", "").replace("###", "");
            m = metaCharacterPattern.matcher(temp);
            if (m.find()) {
                throw new InvalidFormValuesException("Invalid characters found in the input: " + value);
            }
        }

        scriptPatterns.forEach(pattern -> {
            Matcher matcher = pattern.matcher(value);
            if (matcher.find()) {
                throw new InvalidFormValuesException("Invalid characters found in the input: " + value);
            }
        });
    }
}
