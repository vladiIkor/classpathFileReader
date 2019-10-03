package vlad.openshiftfiledemo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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
            System.out.println("----------- known_hosts -----------");
            System.out.println("Resource: fileName - " + filename + ", URI: " + uri + ", URL: " + url + ", exists: " + res.exists() + ", isFile: " + res.isFile());

            if (filename.equals("known_hosts")) {
                displayFileContent(res);
            }
            System.out.println("------------------------------------");
        }
        System.out.println(message);

    }

    private void displayFileContent(Resource res) {
        try {
            InputStream is = res.getInputStream();
            Reader reader = new InputStreamReader(is, StandardCharsets.UTF_8.newDecoder());
            BufferedReader br = new BufferedReader(reader);
            br.lines().forEach(line -> System.out.println(line));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
