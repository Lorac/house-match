package ca.ulaval.glo4003.housematch.persistence.statistics;

import java.util.concurrent.ConcurrentHashMap;

import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo4003.housematch.statistics.StatisticsRepository;

public class XmlStatisticsRepository implements StatisticsRepository {

    private final XmlRepositoryMarshaller<XmlStatisticsRootElement> xmlRepositoryMarshaller;
    private XmlStatisticsRootElement xmlStatisticRootElement;
    private ConcurrentHashMap<String, Integer> statistics;

    public XmlStatisticsRepository(final XmlRepositoryMarshaller<XmlStatisticsRootElement> xmlRepositoryMarshaller) {
        this.xmlRepositoryMarshaller = xmlRepositoryMarshaller;
        initRepository();
    }

    private void initRepository() {
        xmlStatisticRootElement = xmlRepositoryMarshaller.unmarshal();
        statistics = xmlStatisticRootElement.getStatistics();
    }

    @Override
    public Integer get(String statisticName, Integer defaultValue) {
        if (statistics.containsKey(statisticName)) {
            return statistics.get(statisticName);
        } else {
            return defaultValue;
        }
    }

    @Override
    public void persist(String statisticName, Integer value) {
        statistics.put(statisticName, value);
        marshal();
    }

    private void marshal() {
        xmlStatisticRootElement.setStatistics(statistics);
        xmlRepositoryMarshaller.marshal(xmlStatisticRootElement);
    }
}
