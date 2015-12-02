package ca.ulaval.glo4003.housematch.domain.statistics;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ca.ulaval.glo4003.housematch.domain.statistics.UserStatistics;

public class UserStatisticsTest {

    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_BUYERS = 4;
    private static final Integer SAMPLE_NUMBER_OF_ACTIVE_SELLERS = 3;

    private UserStatistics userStatistics;

    @Before
    public void init() {
        userStatistics = new UserStatistics();
    }

    @Test
    public void settingTheNumberOfActiveBuyersSetsTheNumberOfActiveBuyers() {
        userStatistics.setNumberOfActiveBuyers(SAMPLE_NUMBER_OF_ACTIVE_BUYERS);
        assertEquals(SAMPLE_NUMBER_OF_ACTIVE_BUYERS, userStatistics.getNumberOfActiveBuyers());
    }

    @Test
    public void settingTheNumberOfActiveSellersSetsTheNumberOfActiveSellers() {
        userStatistics.setNumberOfActiveSellers(SAMPLE_NUMBER_OF_ACTIVE_SELLERS);
        assertEquals(SAMPLE_NUMBER_OF_ACTIVE_SELLERS, userStatistics.getNumberOfActiveSellers());
    }
}
