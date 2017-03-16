package track.container;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import track.container.config.Bean;
import track.container.config.ConfigReader;
import track.container.config.InvalidConfigurationException;


public class JsonConfigReader implements ConfigReader {

    @Override
    public List<Bean> parseBeans(File configFile) throws InvalidConfigurationException {
        List<Bean> returnList = new ArrayList<>();

        ObjectMapper jsonMapper = new ObjectMapper();
        try {
            JsonNode rootNode = jsonMapper.readTree(configFile);
            JsonNode beansNode = rootNode.path("beans");
            Iterator<JsonNode> beansIterator = beansNode.elements();
            while (beansIterator.hasNext()) {
                JsonNode bean = beansIterator.next();
                returnList.add(jsonMapper.treeToValue(bean, Bean.class));
            }
        } catch (Exception e) {
            throw new InvalidConfigurationException(e);
        }
        return returnList;
    }
}
