package ca.ulaval.glo4003.housematch.persistence.statistics;

import java.util.concurrent.ConcurrentHashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "housematch")
public class XmlStatisticsRootElement {

    private ConcurrentHashMap<String, Object> statistics;

    @XmlElementWrapper(name = "statistics")
    @XmlElement(name = "statistic")
    public ConcurrentHashMap<String, Object> getStatistics() {
        return this.statistics;
    }

    public void setStatistics(ConcurrentHashMap<String, Object> statistics) {
        this.statistics = statistics;
    }
}
