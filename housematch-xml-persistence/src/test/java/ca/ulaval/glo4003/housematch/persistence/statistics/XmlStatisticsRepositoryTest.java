package ca.ulaval.glo4003.housematch.persistence.statistics;

import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.ConcurrentHashMap;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.persistence.marshalling.XmlRepositoryMarshaller;

public class XmlStatisticsRepositoryTest {

    private static final String SAMPLE_STATISTIC_NAME = "sampleName";
    private static final String SAMPLE_UNEXISTING_STATISTIC_NAME = "sampleUnexistingName";
    private static final Integer SAMPLE_STATISTIC_VALUE = 2;
    private static final Integer SAMPLE_DEFAULT_VALUE = 5;

    private XmlRepositoryMarshaller<XmlStatisticsRootElement> xmlRepositoryMarshallerMock;
    private XmlStatisticsRootElement xmlStatisticsRootElementMock;

    private XmlStatisticsRepository xmlStatisticsRepository;

    @Before
    public void init() {
        initMocks();
        stubMethods();
        xmlStatisticsRepository = new XmlStatisticsRepository(xmlRepositoryMarshallerMock);
    }

    @SuppressWarnings("unchecked")
    private void initMocks() {
        xmlStatisticsRootElementMock = mock(XmlStatisticsRootElement.class);
        xmlRepositoryMarshallerMock = mock(XmlRepositoryMarshaller.class);
    }

    private void stubMethods() {
        when(xmlRepositoryMarshallerMock.unmarshal()).thenReturn(xmlStatisticsRootElementMock);
        when(xmlStatisticsRootElementMock.getStatistics()).thenReturn(new ConcurrentHashMap<String, Integer>());
    }

    @Test
    public void persistingStatisticPersistsStatisticToRepository() throws Exception {
        xmlStatisticsRepository.persist(SAMPLE_STATISTIC_NAME, SAMPLE_STATISTIC_VALUE);
        Object returnedValue = xmlStatisticsRepository.get(SAMPLE_STATISTIC_NAME, SAMPLE_DEFAULT_VALUE);
        assertSame(SAMPLE_STATISTIC_VALUE, returnedValue);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void persistingStatisticMarshallsTheUsersInTheRepositoryMarshaller() throws Exception {
        xmlStatisticsRepository.persist(SAMPLE_STATISTIC_NAME, SAMPLE_STATISTIC_VALUE);

        verify(xmlStatisticsRootElementMock).setStatistics(any(ConcurrentHashMap.class));
        verify(xmlRepositoryMarshallerMock).marshal(xmlStatisticsRootElementMock);
    }

    @Test
    public void gettingStatisticUsingNonExistingStatisticNameReturnsTheDefaultValue() throws Exception {
        Object returnedValue = xmlStatisticsRepository.get(SAMPLE_UNEXISTING_STATISTIC_NAME, SAMPLE_DEFAULT_VALUE);
        assertSame(SAMPLE_DEFAULT_VALUE, returnedValue);
    }
}
