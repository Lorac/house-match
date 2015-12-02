package ca.ulaval.glo4003.housematch.persistence.statistics;

import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.domain.statistics.StatisticsRepository;
import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlStatisticsRepository implements StatisticsRepository {

    private final XmlRepositoryMarshaller<XmlStatisticsRootElement> xmlRepositoryMarshaller;
    private XmlStatisticsRootElement xmlStatisticRootElement;
    private ConcurrentHashMap<String, Object> statistics;

    public XmlStatisticsRepository(final XmlRepositoryMarshaller<XmlStatisticsRootElement> xmlRepositoryMarshaller) {
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
