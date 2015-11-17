package ca.ulaval.glo4003.housematch.persistence.statistics;

import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "housematch")
public class XmlStatisticsRootElement {

    private ConcurrentHashMap<String, Integer> statistics = new ConcurrentHashMap<String, Integer>();

    @XmlElementWrapper(name = "statistics")
    @XmlElement(name = "statistic")
    public ConcurrentHashMap<String, Integer> getStatistics() {
        return this.statistics;
    }

    public void setStatistics(ConcurrentHashMap<String, Integer> statistics) {
        this.statistics = statistics;
    }
}
