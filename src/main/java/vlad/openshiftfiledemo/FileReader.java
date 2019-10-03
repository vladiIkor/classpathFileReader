package vlad.openshiftfiledemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URI;
import java.net.URL;

@Component
public class FileReader {

    @Value("${message}")
    private String message;

    @PostConstruct
    public void read() throws IOException {
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);

        Resource[] resources = resolver.getResources("classpath:/*");

        for (Resource res : resources) {
            String filename = res.getFilename();
            URI uri = res.getURI();
            URL url = res.getURL();

            System.out.println("Resource: fileName - " + filename + ", URI: " + uri + ", URL: " + url + ", exists: " + res.exists() + ", isFile: " + res.isFile());
        }
        System.out.println(message);

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
