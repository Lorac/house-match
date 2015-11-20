package ca.ulaval.glo4003.housematch.persistence.statistics;

import static org.junit.Assert.assertSame;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

public class XmlStatisticsRootElementTest {

    private XmlStatisticsRootElement xmlStatisticsRootElement;
    private ConcurrentHashMap<String, Object> statistics = new ConcurrentHashMap<String, Object>();

    @Before
    public void init() {
        xmlStatisticsRootElement = new XmlStatisticsRootElement();
    }

    @Test
    public void setingTheUsersSetsTheUsers() {
        xmlStatisticsRootElement.setStatistics(statistics);
        assertSame(statistics, xmlStatisticsRootElement.getStatistics());
    }
}