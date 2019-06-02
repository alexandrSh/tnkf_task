package tnkf.task.service.soap;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.util.Map;

/**
 * ResoursFile.
 *
 * @author Aleksandr_Sharomov
 */
public class ResoursFile {

    public static String getResource(String path, Map<String, String> macros) throws IOException {
        String resources = IOUtils.toString(
                ResoursFile.class.getResourceAsStream(path), "UTF-8"
        );
        for (Map.Entry<String, String> e : macros.entrySet()) {
            resources = resources.replaceAll(e.getKey(), e.getValue());
        }
        return resources;
    }
}
