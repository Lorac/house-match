package ca.ulaval.glo4003.housematch.persistence.statistics;

import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.statistics.StatisticRepository;

public class XmlStatisticRepository implements StatisticRepository {

    private final XmlRepositoryMarshaller<XmlStatisticRootElement> xmlRepositoryMarshaller;
    private XmlStatisticRootElement xmlStatisticRootElement;
    private ConcurrentHashMap<String, Object> statistics;

    public XmlStatisticRepository(final XmlRepositoryMarshaller<XmlStatisticRootElement> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    private void initRepository() {
        xmlStatisticRootElement = xmlRepositoryMarshaller.unmarshal();
        statistics = xmlStatisticRootElement.getStatistics();
    }

    @Override
    public Object get(String statisticName, Object defaultValue) {
        if (statistics.containsKey(statisticName)) {
            return statistics.get(statisticName);
        } else {
            return defaultValue;
        }
    }

    @Override
    public void persist(String statisticName, Object value) {
        statistics.put(statisticName, value);
        marshal();
    }

    private void marshal() {
        xmlStatisticRootElement.setStatistics(statistics);
        xmlRepositoryMarshaller.marshal(xmlStatisticRootElement);
    }
}
